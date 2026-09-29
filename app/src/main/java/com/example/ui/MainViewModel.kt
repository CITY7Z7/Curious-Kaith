package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AlphabetData
import com.example.data.GamificationData
import com.example.data.ParrotLessonData
import com.example.data.db.AppDatabase
import com.example.data.model.BadgeItem
import com.example.data.model.DictionaryWord
import com.example.data.model.LeaderboardUser
import com.example.data.model.LessonItem
import com.example.data.model.LetterItem
import com.example.data.model.StudyPlan
import com.example.data.model.UserProgress
import com.example.data.repository.RussianLearningRepository
import com.example.service.NotificationHelper
import com.example.service.PdfReportExporter
import com.example.service.TtsManager
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

enum class ParrotMood {
    NEUTRAL, SPEAKING, LISTENING, HAPPY, TRY_AGAIN
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RussianLearningRepository
    val ttsManager: TtsManager = TtsManager(application)

    init {
        val db = AppDatabase.getDatabase(application)
        repository = RussianLearningRepository(db)
        viewModelScope.launch {
            repository.initializeDatabaseIfEmpty()
        }
    }

    val userProgress: StateFlow<UserProgress> = repository.userProgress
        .combine(MutableStateFlow(Unit)) { progress, _ ->
            progress ?: UserProgress()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProgress()
        )

    val studyPlan: StateFlow<StudyPlan> = repository.studyPlan
        .combine(MutableStateFlow(Unit)) { plan, _ ->
            plan ?: StudyPlan()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StudyPlan()
        )

    val allWords: StateFlow<List<DictionaryWord>> = repository.allWords
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allBadges: StateFlow<List<BadgeItem>> = repository.allBadges
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GamificationData.initialBadges
        )

    // Theme Mode
    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    // Trainer State
    private val _currentLevel = MutableStateFlow(1)
    val currentLevel: StateFlow<Int> = _currentLevel.asStateFlow()

    private val _currentLessonIndex = MutableStateFlow(0)
    val currentLessonIndex: StateFlow<Int> = _currentLessonIndex.asStateFlow()

    private val _parrotMood = MutableStateFlow(ParrotMood.NEUTRAL)
    val parrotMood: StateFlow<ParrotMood> = _parrotMood.asStateFlow()

    private val _parrotMessage = MutableStateFlow("Привет! Я попугай Кеша. Услышал — повтори!")
    val parrotMessage: StateFlow<String> = _parrotMessage.asStateFlow()

    private val _consecutiveStreak = MutableStateFlow(0)
    val consecutiveStreak: StateFlow<Int> = _consecutiveStreak.asStateFlow()

    // Dictionary filter states
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Все")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Alphabet state
    val alphabetLetters: List<LetterItem> = AlphabetData.letters
    private val _selectedLetter = MutableStateFlow<LetterItem?>(null)
    val selectedLetter: StateFlow<LetterItem?> = _selectedLetter.asStateFlow()

    // Export PDF Status Message
    private val _lastExportedPdf = MutableStateFlow<File?>(null)
    val lastExportedPdf: StateFlow<File?> = _lastExportedPdf.asStateFlow()

    // Filtered words for dictionary
    val filteredWords: StateFlow<List<DictionaryWord>> = combine(
        allWords,
        _searchQuery,
        _selectedCategory
    ) { words, query, category ->
        words.filter { word ->
            val matchQuery = query.isBlank() ||
                    word.word.contains(query, ignoreCase = true) ||
                    word.definitionRu.contains(query, ignoreCase = true)
            val matchCategory = category == "Все" || word.category == category
            matchQuery && matchCategory
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getLevelLessons(level: Int): List<LessonItem> {
        return ParrotLessonData.lessons.filter { it.level == level }
    }

    fun getCurrentLesson(): LessonItem {
        val levelLessons = getLevelLessons(_currentLevel.value)
        val idx = _currentLessonIndex.value.coerceIn(0, (levelLessons.size - 1).coerceAtLeast(0))
        return if (levelLessons.isNotEmpty()) levelLessons[idx] else ParrotLessonData.lessons.first()
    }

    fun selectLevel(level: Int) {
        _currentLevel.value = level
        _currentLessonIndex.value = 0
        _parrotMood.value = ParrotMood.NEUTRAL
        val lesson = getCurrentLesson()
        _parrotMessage.value = "Уровень $level. Послушай и повтори: ${lesson.targetText}"
    }

    fun nextLesson() {
        val levelLessons = getLevelLessons(_currentLevel.value)
        if (_currentLessonIndex.value < levelLessons.size - 1) {
            _currentLessonIndex.value += 1
        } else {
            _currentLessonIndex.value = 0
        }
        val lesson = getCurrentLesson()
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "Следующий звук! Послушай и повтори: ${lesson.targetText}"
    }

    fun prevLesson() {
        val levelLessons = getLevelLessons(_currentLevel.value)
        if (_currentLessonIndex.value > 0) {
            _currentLessonIndex.value -= 1
        } else {
            _currentLessonIndex.value = levelLessons.size - 1
        }
        val lesson = getCurrentLesson()
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "Послушай и повтори: ${lesson.targetText}"
    }

    // Step 1: Speak target sound/word
    fun speakTargetLesson() {
        val lesson = getCurrentLesson()
        _parrotMood.value = ParrotMood.SPEAKING
        _parrotMessage.value = lesson.stimulusCommand
        ttsManager.speak(lesson.targetText) {
            _parrotMood.value = ParrotMood.LISTENING
        }
    }

    fun speakText(text: String) {
        ttsManager.speak(text)
    }

    // Step 3 & 4: React to user's repetition (parrot mimicry)
    fun onUserRepeated(spokenText: String? = null, isDirectConfirmation: Boolean = false) {
        val lesson = getCurrentLesson()
        val isCorrect = if (isDirectConfirmation) {
            true
        } else if (!spokenText.isNullOrBlank()) {
            val cleanTarget = lesson.targetText.lowercase().replace("[^а-яё]".toRegex(), "")
            val cleanSpoken = spokenText.lowercase().replace("[^а-яё]".toRegex(), "")
            cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken) ||
                    calculateSimilarity(cleanTarget, cleanSpoken) > 0.6
        } else {
            true
        }

        if (isCorrect) {
            _parrotMood.value = ParrotMood.HAPPY
            val praise = ParrotLessonData.praises.random()
            _consecutiveStreak.value += 1
            _parrotMessage.value = "$praise\nТы отлично сказал «${lesson.targetText}»!"

            // Speak praise
            ttsManager.speak(praise)

            // Award XP
            viewModelScope.launch {
                val earnedXp = 10 + (_consecutiveStreak.value * 2).coerceAtMost(20)
                repository.addXp(earnedXp, 1)
            }
        } else {
            _parrotMood.value = ParrotMood.TRY_AGAIN
            val retry = ParrotLessonData.retries.random()
            _consecutiveStreak.value = 0
            _parrotMessage.value = "$retry\nСлушай внимательно: «${lesson.targetText}»."
            ttsManager.speak("Попробуй ещё раз: ${lesson.targetText}")
        }
    }

    private fun calculateSimilarity(s1: String, s2: String): Double {
        if (s1 == s2) return 1.0
        if (s1.isEmpty() || s2.isEmpty()) return 0.0
        val maxLen = maxOf(s1.length, s2.length)
        val distance = levenshteinDistance(s1, s2)
        return 1.0 - (distance.toDouble() / maxLen.toDouble())
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j
        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost
                )
            }
        }
        return dp[s1.length][s2.length]
    }

    fun selectLetter(letter: LetterItem?) {
        _selectedLetter.value = letter
        if (letter != null) {
            ttsManager.speak(letter.letter)
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(cat: String) {
        _selectedCategory.value = cat
    }

    fun toggleWordFavorite(word: DictionaryWord) {
        viewModelScope.launch {
            repository.toggleFavorite(word)
        }
    }

    fun markWordMastered(word: DictionaryWord, mastered: Boolean) {
        viewModelScope.launch {
            repository.markWordMastered(word, mastered)
        }
    }

    fun updateSpeechSettings(rate: Float, pitch: Float, voice: String) {
        ttsManager.speechRate = rate
        ttsManager.speechPitch = pitch
        if (voice.isNotBlank()) {
            ttsManager.setVoiceByName(voice)
        }
        viewModelScope.launch {
            repository.updateSpeechSettings(rate, pitch, voice)
        }
    }

    fun updateThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        viewModelScope.launch {
            repository.updateThemeMode(mode.name)
        }
    }

    fun saveStudyPlan(plan: StudyPlan, context: Context) {
        viewModelScope.launch {
            repository.saveStudyPlan(plan)
            if (plan.isReminderEnabled) {
                NotificationHelper.scheduleDailyReminder(
                    context,
                    plan.reminderHour,
                    plan.reminderMinute
                )
            } else {
                NotificationHelper.cancelReminder(context)
            }
        }
    }

    fun testReminderNotification(context: Context) {
        NotificationHelper.showReminderNotification(
            context,
            "Тестовое напоминание 🦜",
            "Кеша на связи! Напоминания работают отлично без внешних серверов."
        )
    }

    fun exportProgressPdf(context: Context) {
        val curProgress = userProgress.value
        val curPlan = studyPlan.value
        val words = allWords.value
        val file = PdfReportExporter.generateAndShareReport(
            context = context,
            userProgress = curProgress,
            studyPlan = curPlan,
            words = words
        )
        _lastExportedPdf.value = file
        if (file != null) {
            PdfReportExporter.sharePdfReport(context, file)
        }
    }

    fun getLeaderboard(): List<LeaderboardUser> {
        return GamificationData.getLeaderboard(userProgress.value.xp)
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
