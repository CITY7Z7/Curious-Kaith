package com.example.data

import com.example.data.model.LessonItem

object ParrotLessonData {
    val lessons: List<LessonItem> = listOf(
        // ================= УРОВЕНЬ 1: ЗВУКИ И СЛОГИ =================
        LessonItem(
            id = 101,
            level = 1,
            title = "Звук [А]",
            targetText = "А",
            stimulusCommand = "Повтори: А",
            phoneticTip = "Рот широко открыт. Звук чистый и звонкий.",
            contextDescription = "Базовый гласный звук"
        ),
        LessonItem(
            id = 102,
            level = 1,
            title = "Звук [О]",
            targetText = "О",
            ttsText = "О́",
            stimulusCommand = "Повтори: О",
            phoneticTip = "Губы округлены колечком.",
            contextDescription = "Базовый гласный звук"
        ),
        LessonItem(
            id = 103,
            level = 1,
            title = "Звук [У]",
            targetText = "У",
            stimulusCommand = "Повтори: У",
            phoneticTip = "Губы вытянуты плотной трубочкой вперёд.",
            contextDescription = "Гласный звук"
        ),
        LessonItem(
            id = 104,
            level = 1,
            title = "Слог [МА]",
            targetText = "Ма",
            stimulusCommand = "Повтори: Ма",
            phoneticTip = "Губы смыкаются [М], затем легко открываются [А].",
            contextDescription = "Первый слог"
        ),
        LessonItem(
            id = 105,
            level = 1,
            title = "Слог [ПА]",
            targetText = "Па",
            stimulusCommand = "Повтори: Па",
            phoneticTip = "Чёткий толчок губами на звуке [П].",
            contextDescription = "Парный глухой слог"
        ),
        LessonItem(
            id = 106,
            level = 1,
            title = "Слог [ЛА]",
            targetText = "Ла",
            stimulusCommand = "Повтори: Ла",
            phoneticTip = "Кончик языка плотно прижат к верхним зубам.",
            contextDescription = "Звонкий певучий слог"
        ),
        LessonItem(
            id = 107,
            level = 1,
            title = "Слог [БА]",
            targetText = "Ба",
            stimulusCommand = "Повтори: Ба",
            phoneticTip = "Звонкий губной взрыв с голосом.",
            contextDescription = "Звонкий слог"
        ),
        LessonItem(
            id = 108,
            level = 1,
            title = "Слог [ДА]",
            targetText = "Да",
            stimulusCommand = "Повтори: Да",
            phoneticTip = "Язык у верхних зубов. Звучит утвердительно.",
            contextDescription = "Слово-согласие"
        ),
        LessonItem(
            id = 109,
            level = 1,
            title = "Слог [ТА]",
            targetText = "Та",
            stimulusCommand = "Повтори: Та",
            phoneticTip = "Глухой чёткий отскок языка.",
            contextDescription = "Глухой слог"
        ),
        LessonItem(
            id = 110,
            level = 1,
            title = "Слог [КО]",
            targetText = "Ко",
            ttsText = "Кó",
            stimulusCommand = "Повтори: Ко",
            phoneticTip = "Задняя часть нёба и округлённые губы.",
            contextDescription = "Твёрдый слог"
        ),
        LessonItem(
            id = 111,
            level = 1,
            title = "Слог [МУ]",
            targetText = "Му",
            stimulusCommand = "Повтори: Му",
            phoneticTip = "Глубокий носовой звук и вытянутые губы.",
            contextDescription = "Протяжный слог"
        ),
        LessonItem(
            id = 112,
            level = 1,
            title = "Слог [РУ]",
            targetText = "Ру",
            stimulusCommand = "Повтори: Ру",
            phoneticTip = "Энергичная вибрация кончика языка.",
            contextDescription = "Вибрирующий слог"
        ),

        // ================= УРОВЕНЬ 2: ПРОСТЫЕ СУЩЕСТВИТЕЛЬНЫЕ =================
        LessonItem(
            id = 201,
            level = 2,
            title = "Слово «Дом»",
            targetText = "Дом",
            stimulusCommand = "Повтори: Дом",
            phoneticTip = "Твёрдое [д], глубокое [о], смычка на [м].",
            contextDescription = "Уютное жилище"
        ),
        LessonItem(
            id = 202,
            level = 2,
            title = "Слово «Вода»",
            targetText = "Вода",
            stimulusCommand = "Повтори: Вода",
            phoneticTip = "Ударение на [а]: водá. Первое [о] звучит как лёгкое [а].",
            contextDescription = "Источник жизни"
        ),
        LessonItem(
            id = 203,
            level = 2,
            title = "Слово «Кот»",
            targetText = "Кот",
            stimulusCommand = "Повтори: Кот",
            phoneticTip = "Чёткое краткое произношение без растягивания.",
            contextDescription = "Домашний пушистый любимец"
        ),
        LessonItem(
            id = 204,
            level = 2,
            title = "Слово «Хлеб»",
            targetText = "Хлеб",
            stimulusCommand = "Повтори: Хлеб",
            phoneticTip = "В конце слова звонкое [б] оглушается в [п]: [хл'эп].",
            contextDescription = "Главная пища на столе"
        ),
        LessonItem(
            id = 205,
            level = 2,
            title = "Слово «Сыр»",
            targetText = "Сыр",
            stimulusCommand = "Повтори: Сыр",
            phoneticTip = "Твёрдое и глубокое [ы], вибрирующее [р].",
            contextDescription = "Вкусный молочный продукт"
        ),
        LessonItem(
            id = 206,
            level = 2,
            title = "Слово «Стол»",
            targetText = "Стол",
            stimulusCommand = "Повтори: Стол",
            phoneticTip = "Слитное [ст], твёрдое окончание на [л].",
            contextDescription = "Мебель в комнате"
        ),
        LessonItem(
            id = 207,
            level = 2,
            title = "Слово «Чай»",
            targetText = "Чай",
            stimulusCommand = "Повтори: Чай",
            phoneticTip = "Мягкое шипящее [ч'], широкое [а], краткое [й].",
            contextDescription = "Горячий ароматный напиток"
        ),
        LessonItem(
            id = 208,
            level = 2,
            title = "Слово «Окно»",
            targetText = "Окно",
            stimulusCommand = "Повтори: Окно",
            phoneticTip = "Ударение на [о]: окнó. Первая гласная звучит как [а].",
            contextDescription = "Свет в комнату"
        ),
        LessonItem(
            id = 209,
            level = 2,
            title = "Слово «Мама»",
            targetText = "Мама",
            stimulusCommand = "Повтори: Мама",
            phoneticTip = "Ударение на первый слог: мáма.",
            contextDescription = "Самый близкий человек"
        ),
        LessonItem(
            id = 210,
            level = 2,
            title = "Слово «Папа»",
            targetText = "Папа",
            stimulusCommand = "Повтори: Папа",
            phoneticTip = "Ударение на первый слог: пáпа.",
            contextDescription = "Родитель и защитник"
        ),
        LessonItem(
            id = 211,
            level = 2,
            title = "Слово «Рука»",
            targetText = "Рука",
            stimulusCommand = "Повтори: Рука",
            phoneticTip = "Ударение на [а]: рукá.",
            contextDescription = "Часть тела"
        ),
        LessonItem(
            id = 212,
            level = 2,
            title = "Слово «Рыба»",
            targetText = "Рыба",
            stimulusCommand = "Повтори: Рыба",
            phoneticTip = "Ударение на [ы]: рь́ба.",
            contextDescription = "Плавает в реке и море"
        ),

        // ================= УРОВЕНЬ 3: ПРОСТЕЙШИЕ ГЛАГОЛЫ =================
        LessonItem(
            id = 301,
            level = 3,
            title = "Глагол «Иди»",
            targetText = "Иди",
            stimulusCommand = "Повтори: Иди",
            phoneticTip = "Ударение на [и]: идú. Мягкое [д'].",
            contextDescription = "Призыв к движению вперёд"
        ),
        LessonItem(
            id = 302,
            level = 3,
            title = "Глагол «Стой»",
            targetText = "Стой",
            stimulusCommand = "Повтори: Стой",
            phoneticTip = "Энергичный короткий слог. Остановка.",
            contextDescription = "Команда остановиться"
        ),
        LessonItem(
            id = 303,
            level = 3,
            title = "Глагол «Смотри»",
            targetText = "Смотри",
            stimulusCommand = "Повтори: Смотри",
            phoneticTip = "Ударение на [и]: смотрú. Мягкое [р'].",
            contextDescription = "Обратить внимание глазами"
        ),
        LessonItem(
            id = 304,
            level = 3,
            title = "Глагол «Ешь»",
            targetText = "Ешь",
            stimulusCommand = "Повтори: Ешь",
            phoneticTip = "Звучит мягко в начале [й'э] и шипит твёрдо [ш].",
            contextDescription = "Приём пищи"
        ),
        LessonItem(
            id = 305,
            level = 3,
            title = "Глагол «Пей»",
            targetText = "Пей",
            stimulusCommand = "Повтори: Пей",
            phoneticTip = "Мягкое [п'], плавное [э], краткое [й].",
            contextDescription = "Утолить жажду водой или чаем"
        ),
        LessonItem(
            id = 306,
            level = 3,
            title = "Глагол «Спи»",
            targetText = "Спи",
            stimulusCommand = "Повтори: Спи",
            phoneticTip = "Слитное [сп'] с мягким окончанием.",
            contextDescription = "Ночной отдых"
        ),
        LessonItem(
            id = 307,
            level = 3,
            title = "Глагол «Читай»",
            targetText = "Читай",
            stimulusCommand = "Повтори: Читай",
            phoneticTip = "Ударение на второй слог: читáй.",
            contextDescription = "Чтение интересной книги"
        ),
        LessonItem(
            id = 308,
            level = 3,
            title = "Глагол «Беги»",
            targetText = "Беги",
            stimulusCommand = "Повтори: Беги",
            phoneticTip = "Ударение на [и]: бегú. Мягкие звуки [б'] и [г'].",
            contextDescription = "Быстрое движение ногами"
        ),
        LessonItem(
            id = 309,
            level = 3,
            title = "Глагол «Слушай»",
            targetText = "Слушай",
            stimulusCommand = "Повтори: Слушай",
            phoneticTip = "Ударение на первый слог: слýшай.",
            contextDescription = "Внимание звукам речи"
        ),
        LessonItem(
            id = 310,
            level = 3,
            title = "Глагол «Говори»",
            targetText = "Говори",
            stimulusCommand = "Повтори: Говори",
            phoneticTip = "Ударение на конец: говорú. Гласные звучат как [гаварú].",
            contextDescription = "Произношение слов"
        ),
        LessonItem(
            id = 311,
            level = 3,
            title = "Глагол «Дай»",
            targetText = "Дай",
            stimulusCommand = "Повтори: Дай",
            phoneticTip = "Твёрдое и ясное звучание.",
            contextDescription = "Просьба передать предмет"
        ),
        LessonItem(
            id = 312,
            level = 3,
            title = "Глагол «Возьми»",
            targetText = "Возьми",
            stimulusCommand = "Повтори: Возьми",
            phoneticTip = "Ударение на [и]: возьмú. Мягкие согласные [з'] и [м'].",
            contextDescription = "Взять в руки"
        ),

        // ================= УРОВЕНЬ 4: ФРАЗЫ И ДИАЛОГИ =================
        LessonItem(
            id = 401,
            level = 4,
            title = "Приветствие «Привет!»",
            targetText = "Привет!",
            stimulusCommand = "Повтори: Привет!",
            phoneticTip = "Дружелюбная интонация с подъёмом голоса. [прив'эт]",
            contextDescription = "Тёплое приветствие друга"
        ),
        LessonItem(
            id = 402,
            level = 4,
            title = "Вопрос «Как дела?»",
            targetText = "Как дела?",
            stimulusCommand = "Повтори: Как дела?",
            phoneticTip = "Вопросительная мелодия русской речи.",
            contextDescription = "Интерес к собеседнику"
        ),
        LessonItem(
            id = 403,
            level = 4,
            title = "Ответ «Всё хорошо!»",
            targetText = "Всё хорошо!",
            stimulusCommand = "Повтори: Всё хорошо!",
            phoneticTip = "Звук [ш]: произносим [вс'о харашó].",
            contextDescription = "Позитивный ответ"
        ),
        LessonItem(
            id = 404,
            level = 4,
            title = "Утро «Доброе утро!»",
            targetText = "Доброе утро!",
            stimulusCommand = "Повтори: Доброе утро!",
            phoneticTip = "Мягкая, приветливая утренняя интонация.",
            contextDescription = "Приветствие в начале дня"
        ),
        LessonItem(
            id = 405,
            level = 4,
            title = "Вежливость «Спасибо большое!»",
            targetText = "Спасибо большое!",
            stimulusCommand = "Повтори: Спасибо большое!",
            phoneticTip = "Ударения: спасúбо большóе. Искренняя благодарность.",
            contextDescription = "Выражение признательности"
        ),
        LessonItem(
            id = 406,
            level = 4,
            title = "Прощание «До свидания!»",
            targetText = "До свидания!",
            stimulusCommand = "Повтори: До свидания!",
            phoneticTip = "Слитное звучание предлога и слова: [да св'идáн'ий'а].",
            contextDescription = "Вежливое прощание"
        ),
        LessonItem(
            id = 407,
            level = 4,
            title = "Фраза «Я говорю по-русски!»",
            targetText = "Я говорю по-русски!",
            stimulusCommand = "Повтори: Я говорю по-русски!",
            phoneticTip = "Уверенная интонация гордости за свой прогресс.",
            contextDescription = "Главная фраза ученика"
        ),
        LessonItem(
            id = 408,
            level = 4,
            title = "Вопрос «Где выход?»",
            targetText = "Где выход?",
            stimulusCommand = "Повтори: Где выход?",
            phoneticTip = "Вопросительное ударение на слове «выход».",
            contextDescription = "Ориентация в пространстве"
        ),
        LessonItem(
            id = 409,
            level = 4,
            title = "Пожелание «Приятного аппетита!»",
            targetText = "Приятного аппетита!",
            stimulusCommand = "Повтори: Приятного аппетита!",
            phoneticTip = "Окончание -ого произносится как [ова]: [пр'ий'áтнава апит'úта].",
            contextDescription = "Пожелание перед едой"
        ),
        LessonItem(
            id = 410,
            level = 4,
            title = "Желание «Я хочу чай!»",
            targetText = "Я хочу чай!",
            stimulusCommand = "Повтори: Я хочу чай!",
            phoneticTip = "Чёткое произношение [ч'] в обоих словах.",
            contextDescription = "Простая бытовая просьба"
        )
    )

    // Positive praise responses in strict accordance with the parrot method:
    val praises: List<String> = listOf(
        "Отлично! Точно повторил!",
        "Молодец! Прекрасное звучание!",
        "Замечательно! Чистая русская речь!",
        "Браво! Кеша в восторге!",
        "Превосходно! Продолжай так же!",
        "Верно! Ты схватываешь на лету!"
    )

    val retries: List<String> = listOf(
        "Почти получилось! Послушай ещё раз и повтори.",
        "Ещё разок! Слушай внимательно ритм.",
        "Попробуй снова, чётче выдели звук.",
        "Кеша ждёт: повтори ещё раз!"
    )
}
