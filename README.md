# Любопытная Катя (Curious Katyusha)

> Интерактивное мобильное приложение для изучения русского языка методом полного погружения и прямого подражания («метод попугая»).

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Language](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![UI](https://img.shields.io/badge/UI-Jetpack%20Compose%20(M3)-blue.svg)](https://developer.android.com/jetpack/compose)
[![Database](https://img.shields.io/badge/Database-Room%20(SQLite)-orange.svg)](https://developer.android.com/training/data-storage/room)

---

## 🎯 Цель проекта

Предоставить учащимся эффективный инструмент освоения чистой русской речи через активное повторение за интерактивным маскотом попугаем Кешей. Приложение исключает языки-посредники, фокусируясь на фонетике, правильных ударениях, контекстных примерах и моментальной обратной связи.

---

## 🛠 Стек технологий

- **Язык**: Kotlin 2.x (Coroutines, StateFlow)
- **UI**: Jetpack Compose, Material Design 3, Dynamic Color
- **Хранение данных**: AndroidX Room (SQLite), Repository Pattern
- **Аудио и речь**: Android TextToSpeech (локаль `ru_RU`, кастомные голоса)
- **Уведомления**: AlarmManager + BroadcastReceiver (100% автономная работа)
- **Отчёты**: Android PdfDocument (нативная генерация сертификата A4) + FileProvider

---

## 🚀 Быстрый запуск

```bash
# Сборка проекта
gradle :app:assembleDebug

# Запуск юнит-тестов
gradle :app:testDebugUnitTest
```

---

## 📚 Документация проекта

Вся подробная техническая и пользовательская документация находится в каталоге [`docs/`](docs/):

- 📖 **[docs/README.md](docs/README.md)** — Главная документация проекта: архитектура, стек, запуск, качество, переменные окружения, диагностика, схема БД.
- 📜 **[docs/CHANGELOG.md](docs/CHANGELOG.md)** — Журнал изменений и история версий проекта.
- 🏛 **[docs/architecture/overview.md](docs/architecture/overview.md)** — Архитектурный обзор (Clean Architecture, MVVM, потоки данных, дерево проекта).
- 🧩 **Модули проекта**:
  - [Алфавит (`docs/modules/alphabet/`)](docs/modules/alphabet/README.md)
  - [Тренажёр «Попугай» (`docs/modules/parrot-trainer/`)](docs/modules/parrot-trainer/README.md)
  - [Карточки со словами (`docs/modules/flashcards/`)](docs/modules/flashcards/README.md)
  - [Встроенный словарь (`docs/modules/dictionary/`)](docs/modules/dictionary/README.md)
  - [Геймификация и Прогресс (`docs/modules/gamification-progress/`)](docs/modules/gamification-progress/README.md)
  - [Системные сервисы: TTS, Alarm, PDF (`docs/modules/services-system/`)](docs/modules/services-system/README.md)
  - [Хранилище данных и Room (`docs/modules/data-storage/`)](docs/modules/data-storage/README.md)
