# Модуль: Словарь (`docs/modules/dictionary/`)

## 1. Назначение и концепция
Встроенный оффлайн-словарь русского языка с акцентом на правильное ударение, части речи и контекстное употребление слов в живой речи. Служит справочной базой для карточек и тренажёра.

## 2. Ключевые компоненты
- `app/src/main/java/com/example/ui/screens/DictionaryScreen.kt`:
  - Поисковая строка с живой фильтрацией по слову и определению в реальном времени.
  - Горизонтальный список чипов категорий («Все», «Приветствия», «Семья», «Еда», «Город», «Глаголы» и др.).
  - Карточки словарных статей с ударениями, частями речи, толкованиями и примерами.
  - Действия над словом: добавить/удалить из избранного (сердечко), прослушать звучание, отметить освоенным.
- `app/src/main/java/com/example/data/DictionaryData.kt`:
  - Начальный массив базовой лексики (`initialWords`) для первоначального засева (seed) базы данных Room.
  - Каждая запись содержит: `word`, `stressMarked`, `partOfSpeech`, `category`, `definitionRu`, `exampleSentence`.
- `app/src/main/java/com/example/data/db/Daos.kt` (`DictionaryDao`):
  - Реактивные запросы `getAllWords()`, `getWordsByCategory()`, `getFavoriteWords()`, `getMasteredWords()`.

## 3. Реактивная фильтрация
В `MainViewModel` фильтрация построена на реактивном объединении через `combine`:
```kotlin
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
}.stateIn(...)
```
Это гарантирует мгновенный отклик интерфейса без лишних запросов к SQLite.

## 4. Зависимости
- `AppDatabase.kt` / `DictionaryDao`
- `DictionaryWord`
- `TtsManager.kt`
