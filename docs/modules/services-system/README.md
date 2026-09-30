# Модуль: Системные сервисы (`docs/modules/services-system/`)

## 1. Назначение и концепция
Модуль содержит низкоуровневые системные сервисы Android, обеспечивающие работу приложения без внешних серверов (синтез речи, уведомления, печать отчётов).

## 2. Ключевые сервисы

### 2.1. `TtsManager.kt` (Android TextToSpeech)
- **Назначение**: Озвучивание русских букв, звуков, слов и фраз.
- **Особенности**:
  - Инициализация `TextToSpeech` с локалью `Locale("ru", "RU")` (фоллбэк на `Locale("ru")`).
  - Слушатель `UtteranceProgressListener` для отслеживания момента начала и окончания воспроизведения (управление состоянием клюва маскота `ParrotMood.SPEAKING` -> `LISTENING`).
  - Поддержка настройки скорости речи (`speechRate`, по умолчанию 0.9f для чёткой артикуляции) и высоты тона (`speechPitch`, 1.0f).
  - Динамическое сканирование и выбор доступных в системе русских голосов (`availableRussianVoices`).
  - Корректное освобождение ресурсов в `shutdown()`.

### 2.2. `NotificationHelper.kt` и `DailyReminderReceiver.kt` (Alarm & Notifications)
- **Назначение**: Локальные ежедневные напоминания об уроках в заданное пользователем время.
- **Особенности**:
  - Канал уведомлений: `daily_study_reminders` («Ежедневные напоминания: Русский язык») с поддержкой вибрации.
  - Планирование через `AlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)`.
  - Автоматическое восстановление расписания после перезагрузки устройства через интент-фильтр `android.intent.action.BOOT_COMPLETED` в `DailyReminderReceiver`.
  - Нажатие на уведомление открывает `MainActivity` с флагами `FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK`.

### 2.3. `PdfReportExporter.kt` (Генерация PDF и шеринг)
- **Назначение**: Формирование визуального сертификата-отчёта об успехах ученика.
- **Особенности**:
  - Отрисовка на стандартном листе A4 ($595 \times 842$ pt) через `android.graphics.pdf.PdfDocument`.
  - Векторная отрисовка шапки с золотым акцентом, карточек статистики, таблицы изученных слов и печати «✓ ПРОВЕРЕНО МЕТОД ПОПУГАЯ».
  - Сохранение в приватный кэш `context.cacheDir/reports/otchet_russkiy_yazyk.pdf`.
  - Шеринг через `FileProvider` (`com.aistudio.russianparrot.kzqp.fileprovider`), сконфигурированный в `res/xml/file_paths.xml`.

## 3. Манифест и разрешения
- `android.permission.RECORD_AUDIO`: для аудио-практики.
- `android.permission.VIBRATE`: тактильный отклик.
- `android.permission.POST_NOTIFICATIONS`: показ уведомлений на Android 13+.
- `android.permission.RECEIVE_BOOT_COMPLETED`: решедулинг будильников.
- `FileProvider`: провайдер для безопасного обмена PDF-файлами.
