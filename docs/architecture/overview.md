# Архитектурный обзор («Любопытная Катя»)

В этом документе приведена детальная архитектура Android-приложения «Любопытная Катя», структура слоёв, потоки данных и схема взаимодействия компонентов.

---

## 1. Архитектурный паттерн (Clean Architecture + MVVM)

Приложение спроектировано по принципам **Clean Architecture** с разделением ответственности на три ключевых слоя:

```
┌─────────────────────────────────────────────────────────────┐
│                      UI / Presentation                      │
│   MainActivity (Single Activity) + Jetpack Compose Screens   │
│   (Alphabet, ParrotTrainer, Flashcards, Dictionary, etc.)   │
└──────────────────────────────▲──────────────────────────────┘
                               │ StateFlow (UI State) / Events
┌──────────────────────────────▼──────────────────────────────┐
│                         ViewModel                           │
│     MainViewModel (AndroidViewModel, CoroutineScope)        │
└──────────────────────────────▲──────────────────────────────┘
                               │ Flow / Suspend functions
┌──────────────────────────────▼──────────────────────────────┐
│                      Domain & Data Layer                    │
│   RussianLearningRepository (Единый фасад бизнес-логики)    │
│   ├── Room Database (AppDatabase v2, DAOs, SQLite)          │
│   ├── Static Educational Datasets (Alphabet, Lessons, etc.) │
│   └── System Services (TtsManager, Notifications, PDF)      │
└─────────────────────────────────────────────────────────────┘
```

### Принципы слоёв:
1. **Presentation Layer (UI)**:
   - Полностью декларативный UI на **Jetpack Compose** без использования классических XML-layout.
   - Экраны (`Screen.kt`) являются чистыми функциями, реагирующими на `StateFlow`, транслируемые из `MainViewModel`.
   - Однонаправленный поток данных (UDF): события пользователя передаются в ViewModel, обновленное состояние отдаётся обратно в UI.
2. **ViewModel Layer**:
   - `MainViewModel` сохраняет и удерживает состояние при изменении конфигураций экрана.
   - Преобразует холодные `Flow` из репозитория в горячие `StateFlow` через оператор `.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ...)`.
   - Управляет состоянием маскота Кеши (`ParrotMood`, реплики, стрик) и расчётом точности произношения (алгоритм Левенштейна).
3. **Data Layer**:
   - `RussianLearningRepository` абстрагирует источники данных от ViewModel.
   - Изолирует работу с потоками (`Dispatchers.IO`).
   - Синхронизирует сущности базы данных и инициирует сайд-эффекты (начисление XP, разблокировка бейджей).

---

## 2. Потоки данных (Data Flow)

### Сценарий имитационного тренинга («Метод попугая»):
1. **Выбор уровня / урока**: UI запрашивает текущий `LessonItem` из `MainViewModel`.
2. **Воспроизведение стимула**:
   - Пользователь нажимает кнопку воспроизведения.
   - `MainViewModel` переводит `ParrotMood` в состояние `SPEAKING`.
   - `TtsManager.speak(lesson.ttsText)` отправляет текст в движок синтеза.
   - По завершении фразы коллбэк `UtteranceProgressListener` переводит `ParrotMood` в режим ожидания `LISTENING`.
3. **Повторение и сверка**:
   - Пользователь проговаривает фразу.
   - Текст сверяется с эталоном через нормализацию (удаление знаков препинания, перевод в нижний регистр) и метрику Левенштейна (`calculateSimilarity`).
   - Порог совпадения > 0.6 считается успешным освоением.
4. **Реакция и награда**:
   - При успехе: Кеша радуется (`ParrotMood.HAPPY`), произносит случайную похвалу из `ParrotLessonData.praises`, увеличивается `consecutiveStreak`.
   - В БД через `RussianLearningRepository.addXp(earnedXp)` начисляются очки, проверяются условия разблокировки достижений.
   - При ошибке: Кеша подбадривает (`ParrotMood.TRY_AGAIN`), сбрасывает стрик и предлагает послушать ещё раз.

---

## 3. Схема базы данных (Room v2)

База данных SQLite (`russian_learning_database`) состоит из 4 таблиц:

```
┌────────────────────────┐       ┌────────────────────────┐
│    dictionary_words    │       │     user_progress      │
├────────────────────────┤       ├────────────────────────┤
│ id (PK, Int)           │       │ id (PK = 1, Int)       │
│ word (Text)            │       │ xp (Int)               │
│ stressMarked (Text)    │       │ levelName (Text)       │
│ partOfSpeech (Text)    │       │ currentStreak (Int)    │
│ category (Text)        │       │ wordsLearnedCount(Int) │
│ definitionRu (Text)    │       │ lessonsCompleted(Int)  │
│ exampleSentence (Text) │       │ isAlphabetMastered(Bool│
│ isFavorite (Bool)      │       │ learnedLettersList(Txt)│
│ isMastered (Bool)      │       │ speechRate / Pitch     │
│ repetitionCount (Int)  │       │ themeMode (Text)       │
└────────────────────────┘       └────────────────────────┘

┌────────────────────────┐       ┌────────────────────────┐
│       study_plan       │       │         badges         │
├────────────────────────┤       ├────────────────────────┤
│ id (PK = 1, Int)       │       │ id (PK, String)        │
│ dailyMinutesGoal (Int) │       │ title (Text)           │
│ dailyWordsGoal (Int)   │       │ description (Text)     │
│ focusArea (Text)       │       │ iconEmoji (Text)       │
│ reminderHour (Int)     │       │ isUnlocked (Bool)      │
│ reminderMinute (Int)   │       │ unlockedDate (Text?)   │
│ isReminderEnabled(Bool)│       │ requiredProgress (Text)│
└────────────────────────┘       └────────────────────────┘
```

---

## 4. Структура проекта (Файловое дерево)

```
.
├── AGENTS.md                          # Системные правила и инструкции для AI/разработчика
├── metadata.json                      # Метаданные платформы Google AI Studio (название, права)
├── build.gradle.kts                   # Корневой скрипт сборки
├── settings.gradle.kts                # Настройки проекта и подключение модулей
├── gradle/
│   └── libs.versions.toml             # Каталог версий зависимостей (Version Catalog)
├── docs/                              # Документация проекта
│   ├── README.md                      # Главная точка входа в документацию
│   ├── CHANGELOG.md                   # История изменений и версий
│   ├── architecture/
│   │   └── overview.md                # Этот документ
│   └── modules/                       # Документация модулей системы
│       ├── alphabet/README.md
│       ├── parrot-trainer/README.md
│       ├── flashcards/README.md
│       ├── dictionary/README.md
│       ├── gamification-progress/README.md
│       ├── services-system/README.md
│       └── data-storage/README.md
└── app/                               # Основной модуль приложения
    ├── build.gradle.kts               # Скрипт сборки Android-приложения
    ├── src/
    │   ├── main/
    │   │   ├── AndroidManifest.xml    # Манифест приложения, права и сервисы
    │   │   ├── java/com/example/
    │   │   │   ├── MainActivity.kt    # Единственная Activity, хост для Scaffold и навигации
    │   │   │   ├── data/              # Слой данных
    │   │   │   │   ├── AlphabetData.kt
    │   │   │   │   ├── DialogueData.kt
    │   │   │   │   ├── DictionaryData.kt
    │   │   │   │   ├── GamificationData.kt
    │   │   │   │   ├── IntonationData.kt
    │   │   │   │   ├── ParrotLessonData.kt
    │   │   │   │   ├── db/
    │   │   │   │   │   ├── AppDatabase.kt
    │   │   │   │   │   └── Daos.kt
    │   │   │   │   ├── model/
    │   │   │   │   │   └── Models.kt
    │   │   │   │   └── repository/
    │   │   │   │       └── RussianLearningRepository.kt
    │   │   │   ├── service/           # Системные сервисы и утилиты
    │   │   │   │   ├── DailyReminderReceiver.kt
    │   │   │   │   ├── NotificationHelper.kt
    │   │   │   │   ├── PdfReportExporter.kt
    │   │   │   │   └── TtsManager.kt
    │   │   │   └── ui/                # Пользовательский интерфейс (Compose)
    │   │   │       ├── MainViewModel.kt
    │   │   │       ├── components/
    │   │   │       │   ├── LockedSectionView.kt
    │   │   │       │   ├── ParrotMascotView.kt
    │   │   │       │   └── TopBarWithStats.kt
    │   │   │       ├── navigation/
    │   │   │       │   └── Screen.kt
    │   │   │       ├── screens/
    │   │   │       │   ├── AlphabetScreen.kt
    │   │   │       │   ├── DictionaryScreen.kt
    │   │   │       │   ├── FlashcardsScreen.kt
    │   │   │       │   ├── ParrotTrainerScreen.kt
    │   │   │       │   ├── ProgressScreen.kt
    │   │   │       │   └── SettingsScreen.kt
    │   │   │       └── theme/
    │   │   │           ├── Color.kt
    │   │   │           ├── Theme.kt
    │   │   │           └── Type.kt
    │   │   └── res/                   # Ресурсы приложения (иконки, строки, стили)
    │   └── test/                      # Локальные JVM тесты
    │       └── java/com/example/
    │           ├── RussianLearningUnitTest.kt
    │           ├── ExampleUnitTest.kt
    │           └── ExampleRobolectricTest.kt
```
