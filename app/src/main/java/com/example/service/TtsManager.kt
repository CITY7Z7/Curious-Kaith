package com.example.service

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class TtsManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var mediaPlayer: MediaPlayer? = null

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _availableRussianVoices = MutableStateFlow<List<String>>(emptyList())
    val availableRussianVoices: StateFlow<List<String>> = _availableRussianVoices.asStateFlow()

    var speechRate: Float = 0.9f
        set(value) {
            field = value
            tts?.setSpeechRate(value)
        }

    var speechPitch: Float = 1.0f
        set(value) {
            field = value
            tts?.setPitch(value)
        }

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val russianLocale = Locale("ru", "RU")
            val result = tts?.setLanguage(russianLocale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("TtsManager", "Russian TTS data might be missing, trying generic ru locale")
                tts?.language = Locale("ru")
            }
            tts?.setSpeechRate(speechRate)
            tts?.setPitch(speechPitch)
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    _isSpeaking.value = false
                }
            })

            // Query available Russian voices
            try {
                val voices = tts?.voices
                if (voices != null) {
                    val ruVoices = voices
                        .filter { it.locale.language == "ru" }
                        .map { it.name }
                    _availableRussianVoices.value = ruVoices
                }
            } catch (e: Exception) {
                Log.e("TtsManager", "Error querying voices", e)
            }

            isInitialized = true
        } else {
            Log.e("TtsManager", "TextToSpeech init failed with status: $status")
        }
    }

    fun setVoiceByName(voiceName: String) {
        if (!isInitialized || voiceName.isBlank()) return
        try {
            val voices = tts?.voices ?: return
            val match = voices.firstOrNull { it.name == voiceName }
            if (match != null) {
                tts?.voice = match
            }
        } catch (e: Exception) {
            Log.e("TtsManager", "Could not set voice", e)
        }
    }

    /**
     * Нормализация текста перед передачей в системный TTS:
     * 1. Устраняет баг «И-краткое» при синтезе одиночной буквы «Й».
     * 2. Ставит ударение на «О» и «Ко», исключая редукцию до «Ка».
     */
    fun normalizePhonetics(rawText: String): String {
        return when (rawText.trim()) {
            "Й", "й" -> "Йот"
            "Ко", "ко" -> "Кó"
            "О", "о" -> "О́"
            "Ы", "ы" -> "Ы́"
            "Э", "э" -> "Э́"
            else -> rawText
        }
    }

    /**
     * Воспроизводит локальный зашитый аудиофайл из APK assets (offline, zero-network).
     * @return true если файл успешно найден и воспроизводится, false если файл отсутствует
     */
    fun playAssetAudio(assetRelativePath: String, onComplete: (() -> Unit)? = null): Boolean {
        return try {
            val afd = context.assets.openFd(assetRelativePath)
            stop()
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                prepare()
                _isSpeaking.value = true
                setOnCompletionListener {
                    _isSpeaking.value = false
                    it.release()
                    mediaPlayer = null
                    onComplete?.invoke()
                }
                setOnErrorListener { mp, _, _ ->
                    _isSpeaking.value = false
                    mp.release()
                    mediaPlayer = null
                    false
                }
                start()
            }
            true
        } catch (e: Exception) {
            // Файл отсутствует в assets, используется fallback на синтез речи
            false
        }
    }

    /**
     * Приоритетное воспроизведение: если передан путь к локальному аудиофайлу assets и он существует —
     * воспроизводит студийный звук. Иначе озвучивает нормализованный текст через локальный TTS.
     */
    fun speakOrPlayAsset(text: String, assetPath: String? = null, onComplete: (() -> Unit)? = null) {
        if (!assetPath.isNullOrBlank() && playAssetAudio(assetPath, onComplete)) {
            return
        }
        speak(text, onComplete)
    }

    fun speak(text: String, onComplete: (() -> Unit)? = null) {
        val normalized = normalizePhonetics(text)
        if (!isInitialized || tts == null) {
            onComplete?.invoke()
            return
        }
        val utteranceId = "RU_TTS_${System.currentTimeMillis()}"
        val params = Bundle()
        tts?.speak(normalized, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
    }

    fun stop() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
            mediaPlayer = null
        } catch (_: Exception) {}
        tts?.stop()
        _isSpeaking.value = false
    }

    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
}
