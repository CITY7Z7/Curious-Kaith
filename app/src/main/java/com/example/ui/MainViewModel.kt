package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AlphabetData
import com.example.data.DialogueData
import com.example.data.DialogueScenario
import com.example.data.DialogueTurn
import com.example.data.GamificationData
import com.example.data.IntonationContrastSet
import com.example.data.IntonationData
import com.example.data.IntonationItem
import com.example.data.IntonationType
import com.example.data.ParrotLessonData
import com.example.data.SpeakerRole
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

    // Interactive Dialogues state (Level 5)
    val dialogueScenarios: List<DialogueScenario> = DialogueData.scenarios
    private val _selectedScenarioId = MutableStateFlow(1)
    val selectedScenarioId: StateFlow<Int> = _selectedScenarioId.asStateFlow()

    private val _currentDialogueTurnIndex = MutableStateFlow(0)
    val currentDialogueTurnIndex: StateFlow<Int> = _currentDialogueTurnIndex.asStateFlow()

    private val _dialogueCompleted = MutableStateFlow(false)
    val dialogueCompleted: StateFlow<Boolean> = _dialogueCompleted.asStateFlow()

    // Intonation Trainer state (Level 6)
    val intonationContrastSets: List<IntonationContrastSet> = IntonationData.contrastSets
    private val _selectedIntonationSetId = MutableStateFlow(1)
    val selectedIntonationSetId: StateFlow<Int> = _selectedIntonationSetId.asStateFlow()

    private val _selectedIntonationItemIndex = MutableStateFlow(0)
    val selectedIntonationItemIndex: StateFlow<Int> = _selectedIntonationItemIndex.asStateFlow()

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
        if (level == 5) {
            val scenario = getCurrentScenario()
            _parrotMessage.value = "Живой диалог: ${scenario.title}. Нажми «Слушать реплику»!"
            resetDialogue()
        } else if (level == 6) {
            val item = getCurrentIntonationItem()
            _parrotMessage.value = "Интонация: ${item.type.code} (${item.type.arrowSymbol}). Послушай мелодику фразы!"
            speakCurrentIntonation()
        } else {
            val lesson = getCurrentLesson()
            _parrotMessage.value = "Уровень $level. Послушай и повтори: ${lesson.targetText}"
        }
    }

    // ================= ИНТОНАЦИОННЫЕ МЕТОДЫ (УРОВЕНЬ 6) =================
    fun getCurrentIntonationSet(): IntonationContrastSet {
        return intonationContrastSets.find { it.id == _selectedIntonationSetId.value } ?: intonationContrastSets.first()
    }

    fun getCurrentIntonationItem(): IntonationItem {
        val set = getCurrentIntonationSet()
        val idx = _selectedIntonationItemIndex.value.coerceIn(0, (set.items.size - 1).coerceAtLeast(0))
        return set.items[idx]
    }

    fun selectIntonationSet(setId: Int) {
        _selectedIntonationSetId.value = setId
        _selectedIntonationItemIndex.value = 0
        val item = getCurrentIntonationItem()
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "Набор «${getCurrentIntonationSet().baseTopic}»: ${item.type.code} ${item.type.arrowSymbol}"
        speakCurrentIntonation()
    }

    fun selectIntonationItem(itemIndex: Int) {
        val set = getCurrentIntonationSet()
        _selectedIntonationItemIndex.value = itemIndex.coerceIn(0, set.items.size - 1)
        val item = getCurrentIntonationItem()
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "${item.type.code} (${item.type.title}): ${item.phraseText}"
        speakCurrentIntonation()
    }

    fun speakCurrentIntonation() {
        val item = getCurrentIntonationItem()
        val originalPitch = ttsManager.speechPitch
        _parrotMood.value = ParrotMood.SPEAKING
        _parrotMessage.value = "${item.type.code}: «${item.phraseText}»\n${item.communicativeMeaning}"

        ttsManager.speechPitch = item.speechPitch
        ttsManager.speak(item.ttsText) {
            ttsManager.speechPitch = originalPitch
            _parrotMood.value = ParrotMood.LISTENING
        }
    }

    fun onUserRepeatedIntonation(spokenText: String? = null, isDirectConfirmation: Boolean = false) {
        val item = getCurrentIntonationItem()
        val isCorrect = if (isDirectConfirmation) {
            true
        } else if (!spokenText.isNullOrBlank()) {
            val cleanTarget = item.phraseText.lowercase().replace("[^а-яё ]".toRegex(), "").trim()
            val cleanSpoken = spokenText.lowercase().replace("[^а-яё ]".toRegex(), "").trim()
            cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken) ||
                    calculateSimilarity(cleanTarget, cleanSpoken) > 0.45
        } else {
            true
        }

        if (isCorrect) {
            _parrotMood.value = ParrotMood.HAPPY
            _consecutiveStreak.value += 1
            _parrotMessage.value = "Браво! Прекрасная мелодика ${item.type.code} ${item.type.arrowSymbol}!\nТы отлично передал интонацию фразы!"
            ttsManager.speak("Браво! Отличная интонация!")
            viewModelScope.launch {
                repository.addXp(15, 1)
            }
        } else {
            _parrotMood.value = ParrotMood.TRY_AGAIN
            _parrotMessage.value = "Слушай внимательно мелодику ${item.type.code} ${item.type.arrowSymbol}:\n«${item.phraseText}»"
            ttsManager.speak("Послушай ещё раз: ${item.ttsText}")
        }
    }

    // ================= ДИАЛОГОВЫЕ МЕТОДЫ (УРОВЕНЬ 5) =================
    fun getCurrentScenario(): DialogueScenario {
        return dialogueScenarios.find { it.id == _selectedScenarioId.value } ?: dialogueScenarios.first()
    }

    fun getCurrentDialogueTurn(): DialogueTurn? {
        val scenario = getCurrentScenario()
        val idx = _currentDialogueTurnIndex.value
        return scenario.turns.getOrNull(idx)
    }

    fun selectScenario(scenarioId: Int) {
        _selectedScenarioId.value = scenarioId
        resetDialogue()
        val scenario = getCurrentScenario()
        _parrotMessage.value = "Сценарий: «${scenario.title}». Нажми «Слушать реплику» собеседника."
        val firstTurn = scenario.turns.firstOrNull()
        if (firstTurn?.speaker == SpeakerRole.KESHA) {
            speakCurrentDialogueTurn()
        }
    }

    fun resetDialogue() {
        _currentDialogueTurnIndex.value = 0
        _dialogueCompleted.value = false
        _parrotMood.value = ParrotMood.NEUTRAL
    }

    fun speakCurrentDialogueTurn() {
        val turn = getCurrentDialogueTurn() ?: return
        _parrotMood.value = ParrotMood.SPEAKING
        if (turn.speaker == SpeakerRole.KESHA) {
            _parrotMessage.value = "Кеша говорит:\n«${turn.text}»"
            ttsManager.speak(turn.ttsText) {
                // When Kesha finishes speaking, if the next turn is user, set mood to LISTENING
                val nextTurn = getCurrentScenario().turns.getOrNull(_currentDialogueTurnIndex.value + 1)
                if (nextTurn?.speaker == SpeakerRole.USER) {
                    _parrotMood.value = ParrotMood.LISTENING
                } else {
                    _parrotMood.value = ParrotMood.NEUTRAL
                }
            }
        } else {
            // Demonstrating how the user should pronounce their line
            _parrotMessage.value = "Образец речи:\n«${turn.text}»"
            ttsManager.speak(turn.ttsText) {
                _parrotMood.value = ParrotMood.LISTENING
            }
        }
    }

    fun onUserSpokeDialogueTurn(spokenText: String? = null, isDirectConfirmation: Boolean = false) {
        val currentTurn = getCurrentDialogueTurn() ?: return
        val scenario = getCurrentScenario()

        val isCorrect = if (isDirectConfirmation) {
            true
        } else if (!spokenText.isNullOrBlank()) {
            val cleanTarget = currentTurn.text.lowercase().replace("[^а-яё0-9 ]".toRegex(), " ").trim()
            val cleanSpoken = spokenText.lowercase().replace("[^а-яё0-9 ]".toRegex(), " ").trim()
            cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken) ||
                    calculateSimilarity(cleanTarget, cleanSpoken) > 0.45
        } else {
            true
        }

        if (isCorrect) {
            _parrotMood.value = ParrotMood.HAPPY
            val nextIndex = _currentDialogueTurnIndex.value + 1

            if (nextIndex >= scenario.turns.size) {
                // Completed entire dialogue!
                _currentDialogueTurnIndex.value = scenario.turns.size
                _dialogueCompleted.value = true
                _consecutiveStreak.value += 1
                _parrotMessage.value = "🎉 Великолепно! Диалог «${scenario.title}» успешно завершён!\n+30 Опыта (XP)!"
                ttsManager.speak("Браво! Диалог завершён! Ты говоришь по-русски великолепно!")
                viewModelScope.launch {
                    repository.addXp(30, 2)
                }
            } else {
                _currentDialogueTurnIndex.value = nextIndex
                _parrotMessage.value = "Отличная реплика! Диалог продолжается."
                viewModelScope.launch {
                    repository.addXp(10, 1)
                }
                val nextTurn = scenario.turns[nextIndex]
                if (nextTurn.speaker == SpeakerRole.KESHA) {
                    // Auto-speak Kesha's reaction/answer
                    speakCurrentDialogueTurn()
                } else {
                    _parrotMood.value = ParrotMood.LISTENING
                }
            }
        } else {
            _parrotMood.value = ParrotMood.TRY_AGAIN
            _parrotMessage.value = "Попробуй ещё раз! Повтори реплику чётко:\n«${currentTurn.text}»"
            ttsManager.speak("Попробуй ещё раз: ${currentTurn.ttsText}")
        }
    }

    fun nextDialogueTurn() {
        val scenario = getCurrentScenario()
        if (_currentDialogueTurnIndex.value < scenario.turns.size - 1) {
            _currentDialogueTurnIndex.value += 1
            val turn = getCurrentDialogueTurn()
            if (turn?.speaker == SpeakerRole.KESHA) {
                speakCurrentDialogueTurn()
            }
        }
    }

    fun prevDialogueTurn() {
        if (_currentDialogueTurnIndex.value > 0) {
            _currentDialogueTurnIndex.value -= 1
            _dialogueCompleted.value = false
            val turn = getCurrentDialogueTurn()
            if (turn?.speaker == SpeakerRole.KESHA) {
                speakCurrentDialogueTurn()
            }
        }
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
        ttsManager.speak(lesson.ttsText) {
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

    // Alphabet sprint tap-only mode
    private val _isTapOnlyMode = MutableStateFlow(false)
    val isTapOnlyMode: StateFlow<Boolean> = _isTapOnlyMode.asStateFlow()

    fun toggleTapOnlyMode() {
        _isTapOnlyMode.value = !_isTapOnlyMode.value
    }

    fun onLetterTapped(letter: LetterItem) {
        if (_isTapOnlyMode.value) {
            ttsManager.speak(letter.letter)
            viewModelScope.launch {
                repository.recordLetterPracticed(letter.letter, userProgress.value)
            }
        } else {
            selectLetter(letter)
        }
    }

    fun selectLetter(letter: LetterItem?) {
        _selectedLetter.value = letter
        if (letter != null) {
            ttsManager.speak(letter.letter)
            viewModelScope.launch {
                repository.recordLetterPracticed(letter.letter, userProgress.value)
            }
        }
    }

    fun unlockAlphabetByPass() {
        viewModelScope.launch {
            repository.setAlphabetMastered(true)
        }
    }

    fun resetAlphabetProgress() {
        viewModelScope.launch {
            repository.setAlphabetMastered(false)
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
