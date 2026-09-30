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
import com.example.data.MinimalPairItem
import com.example.data.MinimalPairWord
import com.example.data.MinimalPairsData
import com.example.data.ContrastCategory
import com.example.data.ParrotLessonData
import com.example.data.SpeakerRole
import com.example.data.SpeechMatrixData
import com.example.data.SpeechMatrixItem
import com.example.data.MatrixSlotOption
import com.example.data.GrammarFocus
import com.example.data.TwisterData
import com.example.data.TwisterItem
import com.example.data.TwisterType
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

    // Minimal Pairs state (Level 7)
    val minimalPairsList: List<MinimalPairItem> = MinimalPairsData.pairs
    private val _selectedPairCategory = MutableStateFlow<ContrastCategory?>(null)
    val selectedPairCategory: StateFlow<ContrastCategory?> = _selectedPairCategory.asStateFlow()

    private val _selectedPairId = MutableStateFlow(1)
    val selectedPairId: StateFlow<Int> = _selectedPairId.asStateFlow()

    private val _selectedWordOption = MutableStateFlow("A") // "A" or "B"
    val selectedWordOption: StateFlow<String> = _selectedWordOption.asStateFlow()

    // Speech Matrix state (Level 8)
    val speechMatrices: List<SpeechMatrixItem> = SpeechMatrixData.matrices
    private val _selectedMatrixId = MutableStateFlow(1)
    val selectedMatrixId: StateFlow<Int> = _selectedMatrixId.asStateFlow()

    private val _selectedSlotId = MutableStateFlow(1)
    val selectedSlotId: StateFlow<Int> = _selectedSlotId.asStateFlow()

    // Tongue Twisters state (Level 9)
    val twistersList: List<TwisterItem> = TwisterData.twisters
    private val _selectedTwisterType = MutableStateFlow<TwisterType?>(null)
    val selectedTwisterType: StateFlow<TwisterType?> = _selectedTwisterType.asStateFlow()

    private val _selectedTwisterId = MutableStateFlow(1)
    val selectedTwisterId: StateFlow<Int> = _selectedTwisterId.asStateFlow()

    private val _isFastSpeechMode = MutableStateFlow(false)
    val isFastSpeechMode: StateFlow<Boolean> = _isFastSpeechMode.asStateFlow()

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
        } else if (level == 7) {
            val pair = getCurrentPair()
            _parrotMessage.value = "Контрасты: ${pair.contrastKey} («${pair.wordA.word}» vs «${pair.wordB.word}»). Послушай разницу!"
            speakPairWord("A")
        } else if (level == 8) {
            val matrix = getCurrentMatrix()
            val slot = getCurrentSlot()
            _parrotMessage.value = "Матрицы РКИ: ${matrix.title}. Подстановка «${slot.slotWord}». Послушай формулу!"
            speakCurrentMatrixSentence()
        } else if (level == 9) {
            val twister = getCurrentTwister()
            _parrotMessage.value = "Скороговорка: ${twister.title} (${twister.targetSound}). Послушай ритм и повтори!"
            speakCurrentTwister()
        } else {
            val lesson = getCurrentLesson()
            _parrotMessage.value = "Уровень $level. Послушай и повтори: ${lesson.targetText}"
        }
    }

    // ================= МИНИМАЛЬНЫЕ ПАРЫ (УРОВЕНЬ 7) =================
    fun getCurrentPair(): MinimalPairItem {
        return minimalPairsList.find { it.id == _selectedPairId.value } ?: minimalPairsList.first()
    }

    fun selectPairCategory(category: ContrastCategory?) {
        _selectedPairCategory.value = category
        val firstOfCategory = if (category == null) minimalPairsList.first() else minimalPairsList.firstOrNull { it.category == category } ?: minimalPairsList.first()
        _selectedPairId.value = firstOfCategory.id
        _selectedWordOption.value = "A"
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "Пара: «${firstOfCategory.wordA.word}» vs «${firstOfCategory.wordB.word}». Сравни звуки!"
    }

    fun selectPair(pairId: Int) {
        _selectedPairId.value = pairId
        _selectedWordOption.value = "A"
        val pair = getCurrentPair()
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "${pair.contrastKey}: «${pair.wordA.word}» vs «${pair.wordB.word}»"
        speakBothContrastWords()
    }

    fun selectWordOption(option: String) {
        _selectedWordOption.value = option
        val pair = getCurrentPair()
        val word = if (option == "A") pair.wordA else pair.wordB
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "Слово ${word.word}: ${word.phoneticRole}"
        speakPairWord(option)
    }

    fun speakPairWord(option: String) {
        val pair = getCurrentPair()
        val word = if (option == "A") pair.wordA else pair.wordB
        _parrotMood.value = ParrotMood.SPEAKING
        _parrotMessage.value = "«${word.word}» (${word.phoneticRole})\n${word.meaningRu}"
        ttsManager.speak(word.stressMarked) {
            _parrotMood.value = ParrotMood.LISTENING
        }
    }

    fun speakBothContrastWords() {
        val pair = getCurrentPair()
        _parrotMood.value = ParrotMood.SPEAKING
        _parrotMessage.value = "Сравни на слух:\n1. «${pair.wordA.word}» ➔ 2. «${pair.wordB.word}»"
        ttsManager.speak("${pair.wordA.stressMarked}. ... ${pair.wordB.stressMarked}") {
            _parrotMood.value = ParrotMood.LISTENING
        }
    }

    fun onUserRepeatedPairWord(spokenText: String? = null, isDirectConfirmation: Boolean = false) {
        val pair = getCurrentPair()
        val targetWord = if (_selectedWordOption.value == "A") pair.wordA else pair.wordB

        val isCorrect = if (isDirectConfirmation) {
            true
        } else if (!spokenText.isNullOrBlank()) {
            val cleanTarget = targetWord.word.lowercase().replace("[^а-яё]".toRegex(), "").trim()
            val cleanSpoken = spokenText.lowercase().replace("[^а-яё]".toRegex(), "").trim()
            cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken) ||
                    calculateSimilarity(cleanTarget, cleanSpoken) > 0.45
        } else {
            true
        }

        if (isCorrect) {
            _parrotMood.value = ParrotMood.HAPPY
            _consecutiveStreak.value += 1
            _parrotMessage.value = "Отлично! Чётко произнесено: «${targetWord.word}» (${targetWord.phoneticRole})!\n+15 Опыта (XP)!"
            ttsManager.speak("Отлично! Точное произношение!")
            viewModelScope.launch {
                repository.addXp(15, 1)
            }
        } else {
            _parrotMood.value = ParrotMood.TRY_AGAIN
            _parrotMessage.value = "Попробуй ещё раз! Внимание на артикуляцию:\n${targetWord.articulationHint}"
            ttsManager.speak("Послушай ещё раз: ${targetWord.stressMarked}")
        }
    }

    // ================= РЕЧЕВЫЕ МАТРИЦЫ РКИ (УРОВЕНЬ 8) =================
    fun getCurrentMatrix(): SpeechMatrixItem {
        return speechMatrices.find { it.id == _selectedMatrixId.value } ?: speechMatrices.first()
    }

    fun getCurrentSlot(): MatrixSlotOption {
        val matrix = getCurrentMatrix()
        return matrix.options.find { it.slotId == _selectedSlotId.value } ?: matrix.options.first()
    }

    fun selectMatrix(matrixId: Int) {
        _selectedMatrixId.value = matrixId
        _selectedSlotId.value = 1
        val matrix = getCurrentMatrix()
        val slot = getCurrentSlot()
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "Матрица: ${matrix.title}.\nСлот: «${slot.slotWord}». Послушай целую фразу!"
        speakCurrentMatrixSentence()
    }

    fun selectSlot(slotId: Int) {
        _selectedSlotId.value = slotId
        val matrix = getCurrentMatrix()
        val slot = getCurrentSlot()
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "Подстановка: «${slot.slotWord}»\n${slot.grammaticalHint}"
        speakCurrentMatrixSentence()
    }

    fun speakCurrentMatrixSentence() {
        val slot = getCurrentSlot()
        _parrotMood.value = ParrotMood.SPEAKING
        _parrotMessage.value = "«${slot.fullSentence}»\n${slot.meaningRu}"
        ttsManager.speak(slot.fullSentenceTts) {
            _parrotMood.value = ParrotMood.LISTENING
        }
    }

    fun onUserRepeatedMatrix(spokenText: String) {
        val slot = getCurrentSlot()
        val isCorrect = if (spokenText.isNotBlank()) {
            val cleanTarget = slot.fullSentence.lowercase().replace("[^а-яё]".toRegex(), "").trim()
            val cleanSpoken = spokenText.lowercase().replace("[^а-яё]".toRegex(), "").trim()
            cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken) ||
                    calculateSimilarity(cleanTarget, cleanSpoken) > 0.40
        } else {
            true
        }

        if (isCorrect) {
            _parrotMood.value = ParrotMood.HAPPY
            _consecutiveStreak.value += 1
            _parrotMessage.value = "Великолепно! Фраза освоена:\n«${slot.fullSentence}»!\n+12 Опыта (XP)!"
            ttsManager.speak("Отлично! Правильная конструкция!")
            viewModelScope.launch {
                repository.addXp(12, 1)
            }
        } else {
            _parrotMood.value = ParrotMood.TRY_AGAIN
            _parrotMessage.value = "Попробуй ещё раз! Подсказка грамматики:\n${slot.grammaticalHint}"
            ttsManager.speak("Послушай образец: ${slot.fullSentenceTts}")
        }
    }

    // ================= СКОРОГОВОРКИ И ЧИСТОГОВОРКИ (УРОВЕНЬ 9) =================
    fun getCurrentTwister(): TwisterItem {
        return twistersList.find { it.id == _selectedTwisterId.value } ?: twistersList.first()
    }

    fun selectTwisterType(type: TwisterType?) {
        _selectedTwisterType.value = type
        val firstOfType = if (type == null) twistersList.first() else twistersList.firstOrNull { it.type == type } ?: twistersList.first()
        _selectedTwisterId.value = firstOfType.id
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "Категория: ${firstOfType.type.title} (${firstOfType.targetSound}). Тренируем дикцию!"
    }

    fun selectTwister(twisterId: Int) {
        _selectedTwisterId.value = twisterId
        val twister = getCurrentTwister()
        _parrotMood.value = ParrotMood.NEUTRAL
        _parrotMessage.value = "${twister.title}: фокус на звук ${twister.targetSound}"
        speakCurrentTwister()
    }

    fun toggleSpeechSpeedMode() {
        _isFastSpeechMode.value = !_isFastSpeechMode.value
        val speedText = if (_isFastSpeechMode.value) "Быстрый темп 🚀 (1.25x)" else "Обучающий темп 🐢 (0.8x)"
        _parrotMessage.value = "Режим темпа: $speedText. Попробуем?"
        speakCurrentTwister()
    }

    fun speakCurrentTwister() {
        val twister = getCurrentTwister()
        val originalRate = ttsManager.speechRate
        val targetRate = if (_isFastSpeechMode.value) 1.25f else 0.8f
        ttsManager.speechRate = targetRate
        _parrotMood.value = ParrotMood.SPEAKING
        _parrotMessage.value = "«${twister.fullText}»\n${twister.funMeaningRu}"
        ttsManager.speak(twister.fullTextTts) {
            ttsManager.speechRate = originalRate
            _parrotMood.value = ParrotMood.LISTENING
        }
    }

    fun onUserRepeatedTwister(spokenText: String, isDirectConfirmation: Boolean = false) {
        val twister = getCurrentTwister()
        val isCorrect = if (isDirectConfirmation) {
            true
        } else if (spokenText.isNotBlank()) {
            val cleanTarget = twister.fullText.lowercase().replace("[^а-яё]".toRegex(), "").trim()
            val cleanSpoken = spokenText.lowercase().replace("[^а-яё]".toRegex(), "").trim()
            cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken) ||
                    calculateSimilarity(cleanTarget, cleanSpoken) > 0.35
        } else {
            true
        }

        val xpBonus = if (_isFastSpeechMode.value) 25 else 15

        if (isCorrect) {
            _parrotMood.value = ParrotMood.HAPPY
            _consecutiveStreak.value += 1
            val speedLabel = if (_isFastSpeechMode.value) "в быстром темпе 🚀" else "чётко и чисто 🎯"
            _parrotMessage.value = "Браво! Скороговорка освоена $speedLabel!\n+$xpBonus Опыта (XP)!"
            ttsManager.speak("Браво! Отличная дикция!")
            viewModelScope.launch {
                repository.addXp(xpBonus, 1)
            }
        } else {
            _parrotMood.value = ParrotMood.TRY_AGAIN
            _parrotMessage.value = "Попробуй ещё раз! Совет по артикуляции:\n${twister.pedagogicalTip}"
            ttsManager.speak("Послушай ещё раз: ${twister.title}")
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
