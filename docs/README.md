# Любопытная Катя — документация (`docs/`)

Интерактивное Android-приложение для изучения русского языка методом прямого подражания и полного погружения («Метод попугая»).

---

## Что это

**«Любопытная Катя» (школа «Говорун»)** — специализированное мобильное приложение для освоения русского языка как иностранного или родного через активное слуховое восприятие и повторение живой речи. 

В основе методики лежит принцип естественного усвоения языка:
1. **Принцип полного погружения**: обучение строится на аутентичных русских звуках, словах и фразах без постоянного переключения на языки-посредники.
2. **Метод попугая (Имитационный тренинг)**: стимул (аудио) → удержание в кратковременной памяти → немедленное воспроизведение вслух → интеллектуальная сверка сходства произношения (алгоритм Левенштейна) → мгновенное эмоциональное подкрепление от маскота Кеши.
3. **Геймифицированная прогрессия**:
   - Жёсткий шлюз алфавита (пока все 33 буквы не пройдены или не сдан экспресс-зачёт, продвинутые секции заблокированы).
   - Очки опыта (XP), динамический стрик дней (ударный режим), бейджи достижений и таблица лидеров.
4. **Автономность и приватность**:
   - 100% оффлайн-работа базы данных (Room SQLite).
   - Локальный синтез речи (Android TextToSpeech).
   - Локальные ежедневные напоминания через AlarmManager без внешних push-серверов.
   - Локальная генерация официального PDF-сертификата/отчёта об успехах.

---

## Стек

### Основное — на чём работает продукт
- **Платформа и ОС**: Android (Min SDK 24 / Android 7.0, Target SDK 36 / Android 15+).
- **Язык**: Kotlin 2.x (строгая типизация, сопрограммы, StateFlow).
- **UI & Design System**: Jetpack Compose, Material Design 3 (M3), поддержка динамических тем (светлая/тёмная/системная), Edge-to-Edge display.
- **Архитектура**: MVVM (Model-View-ViewModel) + Repository Pattern + Clean Architecture слои.
- **Локальная база данных**: AndroidX Room 2.6.x (SQLite с KSP-кодогенерацией, реактивные `Flow<List<T>>`).
- **Синтез речи (TTS)**: Android System `TextToSpeech` API (локаль `ru_RU`, селектор установленных русских голосов, кастомные темп и высота тона).
- **Фонетический анализатор**: Собственная реализация метрики расстояния Левенштейна для валидации произношения при имитации.
- **Системные сервисы**:
  - `AlarmManager` + `BroadcastReceiver` (`DailyReminderReceiver`) для надежных ежедневных напоминаний.
  - `NotificationManager` с выделенным каналом нотификаций (`daily_study_reminders`).
  - `PdfDocument` (`android.graphics.pdf`) для нативной отрисовки отчетов формата A4.
  - `FileProvider` (`androidx.core.content.FileProvider`) для безопасного экспорта документов во внешние приложения.

### Вспомогательное — чем это обслуживается
- **Сборочная система**: Gradle (Kotlin DSL, `build.gradle.kts`), Version Catalog (`gradle/libs.versions.toml`).
- **Кодогенерация**: Google KSP (Kotlin Symbol Processing) для Room и Moshi.
- **Управление секретами**: `Secrets Gradle Plugin` с поддержкой `.env` и `.env.example`.
- **Тестирование**:
  - JUnit 4 — юнит-тесты бизнес-логики, словаря, уроков и алфавита.
  - Robolectric — изолированные тесты компонентов Android на JVM без необходимости эмулятора.
- **Контроль версий и автоматизация**: Git, GitHub CLI (`gh`).

---

## Как стартовать

### Требования к окружению
- Android Studio Ladybug / Meerkat или более поздняя версия (JDK 17 / Gradle 8.x).
- Android SDK с установленными платформами API 34-36 и Build Tools.

### Сборка и запуск через Gradle
```bash
# Сборка отладочного APK
gradle :app:assembleDebug

# Запуск локальных JVM юнит-тестов и проверок логики
gradle :app:testDebugUnitTest

# Проверка компиляции проекта через инструментарий студии
compile_applet
```

Готовый APK сохраняется по пути:
`app/build/outputs/apk/debug/app-debug.apk` (или корневая директория `.build-outputs/app-debug.apk`).

---

## Проверки и качество

Каждый таск перед отправкой проходит многоуровневый фильтр качества:

1. **Компиляция и линтинг**:
   - Отсутствие синтаксических ошибок и неразрешённых зависимостей (`compile_applet`).
   - Отсутствие неиспользуемых импортов и предупреждений компилятора.
2. **Юнит-тесты (`gradle :app:testDebugUnitTest`)**:
   - `RussianLearningUnitTest`: проверка полноты алфавита (33 буквы, 10 гласных, 2 знака), целостности уровней уроков, стресс-разметки для TTS (например, слог «Ко» озвучивается с ударением «Кó», чтобы исключить редукцию до «Ка»).
   - Проверка блокировки разделов при неполном алфавите.
   - Проверка корректности ранжирования лидерборда.
3. **Требования к UI и Accessibility**:
   - Все кликабельные элементы имеют минимальный размер тач-таргета **48x48 dp** (`minimumInteractiveComponentSize`).
   - Наличие уникальных `testTag` у всех интерактивных элементов (`main_navigation_bar`, `tab_alphabet`, `btn_parrot_speak`, `btn_repeat_action` и т.д.).
   - Полноценные `contentDescription` для скринридеров (TalkBack).
   - Поддержка Dynamic Color и контрастности Material 3.

---

## Переменные окружения

В проекте используется Secrets Gradle Plugin. Чувствительные данные вынесены из исходного кода:
- `.env` — локальный файл с секретами (добавлен в `.gitignore`).
- `.env.example` — эталонный шаблон переменных окружения:
  ```env
  # Пример конфигурации внешних интеграций
  GEMINI_API_KEY=
  FIREBASE_APPCHECK_DEBUG_TOKEN=
  ```
- Доступ в Kotlin-коде осуществляется строго через генерируемый класс `BuildConfig` (например, `BuildConfig.GEMINI_API_KEY`). Прямой хардкод ключей в репозиторий категорически запрещён.

---

## Диагностика

При возникновении сбоев в работе приложения проверяются следующие точки:

### 1. Логирование (Logcat)
Основные теги для мониторинга в Logcat:
- `TtsManager`: статус инициализации TTS-движка, доступность русских пакетов голосов (`Locale("ru", "RU")`), список доступных голосов (`voices`).
- `DailyReminderReceiver`: срабатывание будильника, время следующего триггера, регистрация канала.
- `PdfReportExporter`: генерация страниц, запись файла в `context.cacheDir/reports/`, выдача URI через FileProvider.
- `Room`: выполнение транзакций и миграций базы данных.

### 2. Разрешения (Permissions)
- `android.permission.RECORD_AUDIO`: необходимо для голосового ввода и тренировки произношения в `ParrotTrainerScreen`.
- `android.permission.POST_NOTIFICATIONS`: на Android 13+ (API 33+) требуется согласие пользователя для показа напоминаний.
- `android.permission.VIBRATE`: тактильный отклик при успехе/ошибке в тренажёре.
- `android.permission.RECEIVE_BOOT_COMPLETED`: перезапуск расписания напоминаний после включения устройства.

---

## Карта модулей

Логика проекта разделена на изолированные, слабосвязанные модули:

| Модуль | Расположение в коде | Документация | Описание |
|---|---|---|---|
| **Алфавит** | `ui/screens/AlphabetScreen.kt`, `data/AlphabetData.kt` | [docs/modules/alphabet/README.md](modules/alphabet/README.md) | Полный русский алфавит (33 буквы), режим спринт-тапа, звуки, примеры, учёт прогресса |
| **Тренажёр «Попугай»** | `ui/screens/ParrotTrainerScreen.kt`, `data/ParrotLessonData.kt` | [docs/modules/parrot-trainer/README.md](modules/parrot-trainer/README.md) | Имитационный тренинг произношения (4 уровня), маскот Кеша с эмоциями, метрика схожести |
| **Карточки слов** | `ui/screens/FlashcardsScreen.kt` | [docs/modules/flashcards/README.md](modules/flashcards/README.md) | Интерактивные карточки для заучивания лексики, показ ударений, статус освоения |
| **Словарь** | `ui/screens/DictionaryScreen.kt`, `data/DictionaryData.kt` | [docs/modules/dictionary/README.md](modules/dictionary/README.md) | Оффлайн-словарь с поиском, фильтром категорий, примерами в контексте и избранным |
| **Геймификация и Прогресс** | `ui/screens/ProgressScreen.kt`, `data/GamificationData.kt` | [docs/modules/gamification-progress/README.md](modules/gamification-progress/README.md) | Опыт (XP), дневные серии, система наград/бейджей, лидерборд, экспорт PDF-отчёта |
| **Системные сервисы** | `service/TtsManager.kt`, `service/NotificationHelper.kt`, `service/PdfReportExporter.kt` | [docs/modules/services-system/README.md](modules/services-system/README.md) | Синтез речи TTS, фоновые будильники и пуш-уведомления, нативная генерация PDF |
| **Хранилище данных** | `data/db/AppDatabase.kt`, `data/db/Daos.kt`, `data/repository/` | [docs/modules/data-storage/README.md](modules/data-storage/README.md) | Room Database, DAO-интерфейсы, миграции, реактивные Flow, Repository |

---

## База и интеграции

### Схема базы данных (Room DB: `russian_learning_database`, версия 2)
1. `dictionary_words`:
   - `id`: Int (PrimaryKey, autoGenerate)
   - `word`: String — словарная форма
   - `stressMarked`: String — слово со знаком ударения
   - `partOfSpeech`: String — часть речи (существительное, глагол и т.д.)
   - `category`: String — тематическая группа
   - `definitionRu`: String — толкование на русском языке
   - `exampleSentence`: String — фраза с контекстом употребления
   - `isFavorite`: Boolean — метка избранного
   - `isMastered`: Boolean — флаг полного освоения
   - `repetitionCount`: Int — счетчик успешных повторений
2. `user_progress`:
   - `id`: Int (PK = 1)
   - `xp`: Int, `levelName`: String, `currentStreak`: Int
   - `wordsLearnedCount`: Int, `lessonsCompletedCount`: Int, `minutesPracticedToday`: Int
   - `speechRate`: Float, `speechPitch`: Float, `selectedVoice`: String
   - `themeMode`: String (LIGHT / DARK / SYSTEM)
   - `isAlphabetMastered`: Boolean, `learnedLettersList`: String (список через запятую)
3. `study_plan`:
   - `id`: Int (PK = 1)
   - `dailyMinutesGoal`: Int, `dailyWordsGoal`: Int, `focusArea`: String
   - `reminderHour`: Int, `reminderMinute`: Int, `isReminderEnabled`: Boolean
4. `badges`:
   - `id`: String (PK)
   - `title`: String, `description`: String, `iconEmoji`: String
   - `isUnlocked`: Boolean, `unlockedDate`: String?, `requiredProgress`: String

### Внешние системные интеграции
- **Android TTS Subsystem**: не требует внешних облачных API, работает через встроенный движок синтеза Google TTS или вендорный движок.
- **Android Alarm Subsystem**: не требует сторонних сервисов (OneSignal, FCM и т.д.), использует локальный `AlarmManager` + `BroadcastReceiver`.
- **Android File Sharing**: интеграция через системный `Intent.ACTION_SEND` с `content://` URI от `FileProvider`.

---

## ДОКУМЕНТАЦИЯ ОБЯЗАТЕЛЬНА (читать роботу — исполнять буквально)

> **Изменил код = обновил `docs/` в том же таске. Код без доков = незавершённая задача.**

1. Любая модификация кода (фича, фикс багов, рефакторинг, добавление полей в БД) обязана сопровождаться обновлением соответствующей документации в папке `docs/` **в рамках одной итерации**.
2. Изменение публичного API, схемы БД или параметров сущностей влечёт немедленное обновление:
   - `docs/modules/<имя_модуля>/README.md`
   - `docs/architecture/overview.md`
   - `docs/CHANGELOG.md`
3. Отчёты со статусом выполнения без актуализированных документов являются недействительными.

---

## Правила для агента (чтобы не кольцеваться)

1. **Последовательность чтения перед таском**: 
   `AGENTS.md` → этот файл (`docs/README.md`) → README нужного модуля в `docs/modules/` → файл `overview.md`.
2. **Код — абсолютная истина**: Документация описывает только то, что физически реализовано и присутствует в коде. Домыслы и предположения («планируется», «возможно») фактом не признаются.
3. **Лимит на модификации**: Изменения более 3 связанных файлов за один раз требуют явного предварительного согласования с пользователем.
4. **Принцип Anti-Duplicate**: Перед созданием нового компонента, функции или сервиса проверь существующие аналоги в проекте. Расширяй существующий код вместо пложения дубликатов.
5. **StateFlow / Compose оптимизация**: Не инициировать холостые рекомпозиции и повторные записи в StateFlow без реального изменения данных.
6. **Обязательные проверки перед сдачей**:
   - `compile_applet` — успешная компиляция без ошибок.
   - `gradle :app:testDebugUnitTest` — прохождение всех автоматических тестов.
7. **Фиксация проблем**: При обнаружении любой сопутствующей ошибки, бага или ценной идеи оптимизации — зафиксируй её отдельной задачей (таском).
8. **Полнота покрытия модулей**: Каждый модуль в `app/` обязан иметь зеркальный файл описания в `docs/modules/<имя>/README.md`.
