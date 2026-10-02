# Модуль: Хранилище данных (`docs/modules/data-storage/`)

## 1. Назначение и концепция
Модуль обеспечивает локальное долговременное хранение пользовательских данных, прогресса обучения, словаря и учебных планов с использованием **Room Persistence Library** поверх встроенной SQLite.

## 2. Архитектура хранилища

### 2.1. База данных: `AppDatabase.kt`
- Аннотация: `@Database(entities = [DictionaryWord::class, UserProgress::class, StudyPlan::class, BadgeItem::class], version = 2, exportSchema = false)`.
- Имя файла БД: `"russian_learning_database"`.
- Паттерн Singleton с защитой от гонок потоков (`@Volatile INSTANCE` + `synchronized`).
- Стратегия миграции: `fallbackToDestructiveMigration(dropAllTables = true)` для безопасного обновления структуры таблиц.

### 2.2. Сущности и таблицы (`Models.kt`)
1. **`DictionaryWord` (`dictionary_words`)**:
   - `id`: Int (PrimaryKey, autoGenerate = true)
   - `word`: String — словарная форма
   - `stressMarked`: String — слово с ударением
   - `partOfSpeech`: String — часть речи
   - `category`: String — тема
   - `definitionRu`: String — определение
   - `exampleSentence`: String — предложение-пример
   - `isFavorite`: Boolean — избранное
   - `isMastered`: Boolean — освоено
   - `repetitionCount`: Int — количество повторений
2. **`UserProgress` (`user_progress`)**:
   - `id`: Int (PrimaryKey, фикс. 1)
   - `xp`: Int, `levelName`: String, `currentStreak`: Int
   - `lastActiveDate`: String, `wordsLearnedCount`: Int, `lessonsCompletedCount`: Int
   - `minutesPracticedToday`: Int, `todayDate`: String
   - `speechRate`: Float, `speechPitch`: Float, `selectedVoice`: String
   - `themeMode`: String ("LIGHT", "DARK", "SYSTEM")
   - `isAlphabetMastered`: Boolean, `learnedLettersList`: String
   - Вспомогательный метод `getLearnedLettersSet(): Set<String>`
3. **`StudyPlan` (`study_plan`)**:
   - `id`: Int (PrimaryKey, фикс. 1)
   - `dailyMinutesGoal`: Int (цель минут в день)
   - `dailyWordsGoal`: Int (цель слов в день)
   - `focusArea`: String (фокусное направление)
   - `reminderHour`: Int, `reminderMinute`: Int, `isReminderEnabled`: Boolean
4. **`BadgeItem` (`badges`)**:
   - `id`: String (PrimaryKey)
   - `title`: String, `description`: String, `iconEmoji`: String
   - `isUnlocked`: Boolean, `unlockedDate`: String?, `requiredProgress`: String

### 2.3. Data Access Objects (`Daos.kt`)
- `DictionaryDao`:
  - `getAllWords(): Flow<List<DictionaryWord>>`
  - `getWordsByCategory(category: String): Flow<List<DictionaryWord>>`
  - `getFavoriteWords(): Flow<List<DictionaryWord>>`
  - `getMasteredWords(): Flow<List<DictionaryWord>>`
  - `insertWords(words: List<DictionaryWord>)`
  - `updateWord(word: DictionaryWord)`
  - `getWordCount(): Int`
- `UserProgressDao`:
  - `getUserProgress(): Flow<UserProgress?>`
  - `insertOrUpdate(progress: UserProgress)`
  - `addXpAndWords(points: Int, wordsDelta: Int)`
  - `updateSpeechSettings(rate: Float, pitch: Float, voice: String)`
  - `updateAlphabetProgress(mastered: Boolean, letters: String)`
  - `updateThemeMode(themeMode: String)`
- `StudyPlanDao`:
  - `getStudyPlan(): Flow<StudyPlan?>`
  - `saveStudyPlan(plan: StudyPlan)`
- `BadgeDao`:
  - `getAllBadges(): Flow<List<BadgeItem>>`
  - `insertBadges(badges: List<BadgeItem>)`
  - `unlockBadge(badgeId: String, date: String)`
  - `getBadgeCount(): Int`

### 2.4. Репозиторий: `RussianLearningRepository.kt`
Инкапсулирует вызовы DAO и фоновую диспетчеризацию через `withContext(Dispatchers.IO)`. Обеспечивает начальный сид данных (`initializeDatabaseIfEmpty()`), если таблицы пусты при первом старте.

## 3. Зависимости
- `androidx.room:room-runtime`, `androidx.room:room-ktx`
- KSP: `androidx.room:room-compiler`
- Coroutines / Flow
