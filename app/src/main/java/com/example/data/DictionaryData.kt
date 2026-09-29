package com.example.data

import com.example.data.model.DictionaryWord

object DictionaryData {
    val initialWords: List<DictionaryWord> = listOf(
        // ЕДА И НАПИТКИ
        DictionaryWord(
            word = "Хлеб",
            stressMarked = "Хлеб",
            partOfSpeech = "Существительное",
            category = "Еда",
            definitionRu = "Главный продукт питания, выпекаемый из муки.",
            exampleSentence = "Свежий душистый хлеб лежит на деревянном столе."
        ),
        DictionaryWord(
            word = "Вода",
            stressMarked = "Вода́",
            partOfSpeech = "Существительное",
            category = "Еда",
            definitionRu = "Прозрачная чистая жидкость для питья и жизни.",
            exampleSentence = "В жаркий летний день холодная вода утоляет жажду."
        ),
        DictionaryWord(
            word = "Молоко",
            stressMarked = "Молоко́",
            partOfSpeech = "Существительное",
            category = "Еда",
            definitionRu = "Белый питательный напиток от коровы.",
            exampleSentence = "Маленький котёнок с удовольствием пьёт тёплое молоко."
        ),
        DictionaryWord(
            word = "Сыр",
            stressMarked = "Сыр",
            partOfSpeech = "Существительное",
            category = "Еда",
            definitionRu = "Плотный молочный продукт жёлтого цвета.",
            exampleSentence = "На завтрак мы едим бутерброд с маслом и сыром."
        ),
        DictionaryWord(
            word = "Чай",
            stressMarked = "Чай",
            partOfSpeech = "Существительное",
            category = "Еда",
            definitionRu = "Ароматный горячий напиток из сушёных листьев.",
            exampleSentence = "Вечером вся семья пьёт горячий чай с лимоном."
        ),
        DictionaryWord(
            word = "Яблоко",
            stressMarked = "Я́блоко",
            partOfSpeech = "Существительное",
            category = "Еда",
            definitionRu = "Круглый сочный плод яблони, сладкий или кисловатый.",
            exampleSentence = "Красное спелое яблоко упало с ветки прямо в траву."
        ),
        DictionaryWord(
            word = "Суп",
            stressMarked = "Суп",
            partOfSpeech = "Существительное",
            category = "Еда",
            definitionRu = "Жидкое горячее первое блюдо с овощами.",
            exampleSentence = "На обед бабушка сварила вкусный куриный суп."
        ),
        DictionaryWord(
            word = "Каша",
            stressMarked = "Ка́ша",
            partOfSpeech = "Существительное",
            category = "Еда",
            definitionRu = "Блюдо из сваренной крупы на молоке или воде.",
            exampleSentence = "Овсяная каша с мёдом даёт силы на весь день."
        ),

        // СЕМЬЯ И ЛЮДИ
        DictionaryWord(
            word = "Мама",
            stressMarked = "Ма́ма",
            partOfSpeech = "Существительное",
            category = "Семья",
            definitionRu = "Женщина по отношению к своим детям; самый родной человек.",
            exampleSentence = "Мама ласково обняла сына и улыбнулась ему."
        ),
        DictionaryWord(
            word = "Папа",
            stressMarked = "Па́па",
            partOfSpeech = "Существительное",
            category = "Семья",
            definitionRu = "Мужчина по отношению к своим детям; глава семьи.",
            exampleSentence = "Папа учит меня кататься на двухколёсном велосипеде."
        ),
        DictionaryWord(
            word = "Брат",
            stressMarked = "Брат",
            partOfSpeech = "Существительное",
            category = "Семья",
            definitionRu = "Сын тех же родителей по отношению к другим детям.",
            exampleSentence = "Мой старший брат помогает мне учить русский язык."
        ),
        DictionaryWord(
            word = "Сестра",
            stressMarked = "Сестра́",
            partOfSpeech = "Существительное",
            category = "Семья",
            definitionRu = "Дочь тех же родителей по отношению к другим детям.",
            exampleSentence = "Младшая сестра весело рисует разноцветными карандашами."
        ),
        DictionaryWord(
            word = "Друг",
            stressMarked = "Друг",
            partOfSpeech = "Существительное",
            category = "Семья",
            definitionRu = "Человек, с которым связывает взаимное доверие и дружба.",
            exampleSentence = "Верный друг всегда готов прийти на помощь в трудную минуту."
        ),
        DictionaryWord(
            word = "Человек",
            stressMarked = "Челове́к",
            partOfSpeech = "Существительное",
            category = "Семья",
            definitionRu = "Живое мыслящее существо, обладающее речью.",
            exampleSentence = "Каждый человек стремится к знаниям и счастью."
        ),

        // ДОМ И БЫТ
        DictionaryWord(
            word = "Дом",
            stressMarked = "Дом",
            partOfSpeech = "Существительное",
            category = "Дом",
            definitionRu = "Здание, служащее жильём для людей.",
            exampleSentence = "Наш новый дом окружён красивым цветущим садом."
        ),
        DictionaryWord(
            word = "Окно",
            stressMarked = "Окно́",
            partOfSpeech = "Существительное",
            category = "Дом",
            definitionRu = "Отверстие в стене для света и свежего воздуха.",
            exampleSentence = "Утром через широкое окно в комнату заглянуло солнце."
        ),
        DictionaryWord(
            word = "Дверь",
            stressMarked = "Дверь",
            partOfSpeech = "Существительное",
            category = "Дом",
            definitionRu = "Створка для закрытия прохода в помещение.",
            exampleSentence = "Гость тихо постучал в деревянную входную дверь."
        ),
        DictionaryWord(
            word = "Стол",
            stressMarked = "Стол",
            partOfSpeech = "Существительное",
            category = "Дом",
            definitionRu = "Предмет мебели с широкой горизонтальной доской на ножках.",
            exampleSentence = "Посреди большой кухни стоит круглый обеденный стол."
        ),
        DictionaryWord(
            word = "Книга",
            stressMarked = "Кни́га",
            partOfSpeech = "Существительное",
            category = "Дом",
            definitionRu = "Сшитые вместе листы бумаги с печатным текстом.",
            exampleSentence = "Увлекательная книга открывает двери в новый удивительный мир."
        ),
        DictionaryWord(
            word = "Часы",
            stressMarked = "Часы́",
            partOfSpeech = "Существительное",
            category = "Дом",
            definitionRu = "Прибор, показывающий текущее время.",
            exampleSentence = "Старинные настенные часы мерно отсчитывают секунды."
        ),

        // ДЕЙСТВИЯ (ГЛАГОЛЫ)
        DictionaryWord(
            word = "Читать",
            stressMarked = "Чита́ть",
            partOfSpeech = "Глагол",
            category = "Действия",
            definitionRu = "Воспринимать написанный или напечатанный текст.",
            exampleSentence = "Каждый день я люблю читать русские рассказы и стихи."
        ),
        DictionaryWord(
            word = "Говорить",
            stressMarked = "Говори́ть",
            partOfSpeech = "Глагол",
            category = "Действия",
            definitionRu = "Владеть речью, выражать мысли словами.",
            exampleSentence = "Мы учимся свободно и красиво говорить по-русски."
        ),
        DictionaryWord(
            word = "Слушать",
            stressMarked = "Слу́шать",
            partOfSpeech = "Глагол",
            category = "Действия",
            definitionRu = "Направлять слух на какие-либо звуки.",
            exampleSentence = "Ученик внимательно слушает произношение диктора."
        ),
        DictionaryWord(
            word = "Писать",
            stressMarked = "Писа́ть",
            partOfSpeech = "Глагол",
            category = "Действия",
            definitionRu = "Изображать на бумаге графические знаки и буквы.",
            exampleSentence = "Я старательно учусь писать русские буквы в тетради."
        ),
        DictionaryWord(
            word = "Идти",
            stressMarked = "Идти́",
            partOfSpeech = "Глагол",
            category = "Действия",
            definitionRu = "Передвигаться, ступая ногами.",
            exampleSentence = "Утром приятно спокойно идти по тенистому парку."
        ),
        DictionaryWord(
            word = "Смотреть",
            stressMarked = "Смотре́ть",
            partOfSpeech = "Глагол",
            category = "Действия",
            definitionRu = "Направлять взгляд, чтобы увидеть что-либо.",
            exampleSentence = "Дети любят с интересом смотреть добрые мультфильмы."
        ),
        DictionaryWord(
            word = "Любить",
            stressMarked = "Люби́ть",
            partOfSpeech = "Глагол",
            category = "Действия",
            definitionRu = "Испытывать глубокую привязанность и симпатию.",
            exampleSentence = "Я искренне люблю изучать новые языки и культуры."
        ),

        // ПРИРОДА И ЖИВОТНЫЕ
        DictionaryWord(
            word = "Кот",
            stressMarked = "Кот",
            partOfSpeech = "Существительное",
            category = "Природа",
            definitionRu = "Домашнее хищное животное с мягкой шерстью.",
            exampleSentence = "Пушистый кот свернулся клубком на тёплом подоконнике."
        ),
        DictionaryWord(
            word = "Собака",
            stressMarked = "Соба́ка",
            partOfSpeech = "Существительное",
            category = "Природа",
            definitionRu = "Преданное домашнее четвероногое животное.",
            exampleSentence = "Преданная собака радостно встречает хозяина у порога."
        ),
        DictionaryWord(
            word = "Попугай",
            stressMarked = "Попуга́й",
            partOfSpeech = "Существительное",
            category = "Природа",
            definitionRu = "Яркая птица, способная подражать человеческой речи.",
            exampleSentence = "Умный попугай Кеша весело повторяет русские фразы."
        ),
        DictionaryWord(
            word = "Солнце",
            stressMarked = "Со́лнце",
            partOfSpeech = "Существительное",
            category = "Природа",
            definitionRu = "Центральное небесное светило, дающее свет и тепло.",
            exampleSentence = "Яркое солнце освещает верхушки вечнозелёных сосен."
        ),
        DictionaryWord(
            word = "Лес",
            stressMarked = "Лес",
            partOfSpeech = "Существительное",
            category = "Природа",
            definitionRu = "Множество деревьев, растущих на большом пространстве.",
            exampleSentence = "В густом берёзовом лесу приятно пахнет влажным мхом."
        ),
        DictionaryWord(
            word = "Река",
            stressMarked = "Река́",
            partOfSpeech = "Существительное",
            category = "Природа",
            definitionRu = "Постоянный водный поток, текущий в естественном русле.",
            exampleSentence = "Широкая река плавно несёт свои прозрачные воды к морю."
        ),

        // ПРИВЕТСТВИЯ И ВЕЖЛИВОСТЬ
        DictionaryWord(
            word = "Привет",
            stressMarked = "Приве́т",
            partOfSpeech = "Междометие",
            category = "Приветствия",
            definitionRu = "Дружеское неофициальное приветствие при встрече.",
            exampleSentence = "«Привет! Очень рад нашей новой встрече!»"
        ),
        DictionaryWord(
            word = "Здравствуйте",
            stressMarked = "Здра́вствуйте",
            partOfSpeech = "Приветствие",
            category = "Приветствия",
            definitionRu = "Официальное вежливое пожелание здоровья при встрече.",
            exampleSentence = "«Здравствуйте, уважаемые коллеги и дорогие друзья!»"
        ),
        DictionaryWord(
            word = "Спасибо",
            stressMarked = "Спаси́бо",
            partOfSpeech = "Слово вежливости",
            category = "Приветствия",
            definitionRu = "Слово искренней благодарности за услугу или помощь.",
            exampleSentence = "«Большое спасибо вам за тёплый приём и поддержку!»"
        ),
        DictionaryWord(
            word = "Пожалуйста",
            stressMarked = "Пожа́луйста",
            partOfSpeech = "Слово вежливости",
            category = "Приветствия",
            definitionRu = "Слово вежливого обращения, просьбы или ответа на спасибо.",
            exampleSentence = "«Возьмите, пожалуйста, эту интересную книгу.»"
        ),
        DictionaryWord(
            word = "Хорошо",
            stressMarked = "Хорошо́",
            partOfSpeech = "Наречие",
            category = "Приветствия",
            definitionRu = "Одобрительный ответ, выражающий согласие или качество.",
            exampleSentence = "«Всё идёт очень хорошо, мы сделали большой прогресс!»"
        )
    )
}
