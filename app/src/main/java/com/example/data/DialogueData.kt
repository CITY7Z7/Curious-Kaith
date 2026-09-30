package com.example.data

enum class SpeakerRole(val displayName: String, val emoji: String) {
    KESHA("Кеша (Собеседник)", "🦜"),
    USER("Ты (Ученик)", "👤")
}

data class DialogueTurn(
    val id: Int,
    val speaker: SpeakerRole,
    val text: String,
    val ttsText: String = text,
    val phoneticTip: String = "",
    val situationHint: String = ""
)

data class DialogueScenario(
    val id: Int,
    val title: String,
    val emoji: String,
    val description: String,
    val location: String,
    val targetLevel: String = "A1-A2",
    val turns: List<DialogueTurn>
)

object DialogueData {
    val scenarios: List<DialogueScenario> = listOf(
        // 1. КОФЕЙНЯ
        DialogueScenario(
            id = 1,
            title = "В кофейне",
            emoji = "☕",
            description = "Заказ горячего напитка и свежей выпечки",
            location = "Уютная пекарня",
            turns = listOf(
                DialogueTurn(
                    id = 1,
                    speaker = SpeakerRole.KESHA,
                    text = "Здравствуйте! Что вы будете пить?",
                    ttsText = "Здрáвствуйте! Что вы бýдете пить?",
                    situationHint = "Бариста приветствует вас у стойки."
                ),
                DialogueTurn(
                    id = 2,
                    speaker = SpeakerRole.USER,
                    text = "Здравствуйте! Мне, пожалуйста, чёрный кофе и круассан.",
                    ttsText = "Здрáвствуйте! Мне, пожáлуйста, чёрный кóфе и круассáн.",
                    phoneticTip = "Чётко произнесите [кóфе] с твёрдым звуком [ф']. В слове «пожалуйста» [йуста].",
                    situationHint = "Сделайте вежливый заказ."
                ),
                DialogueTurn(
                    id = 3,
                    speaker = SpeakerRole.KESHA,
                    text = "Отличный выбор! Вам здесь или с собой?",
                    ttsText = "Отлúчный вь́бор! Вам здесь úли с собóй?",
                    situationHint = "Уточнение формата подачи."
                ),
                DialogueTurn(
                    id = 4,
                    speaker = SpeakerRole.USER,
                    text = "С собой, спасибо! Сколько с меня?",
                    ttsText = "С собóй, спасúбо! Скóлько с меня́?",
                    phoneticTip = "Слитное [с сабóй], ударение в слове «меня́» на [я].",
                    situationHint = "Попросите напиток навынос и спросите цену."
                ),
                DialogueTurn(
                    id = 5,
                    speaker = SpeakerRole.KESHA,
                    text = "Триста рублей. Одну минуту, ваш заказ готов. Приятного аппетита!",
                    ttsText = "Трúста рублéй. Однý минýту, ваш закáз готóв. Прия́тного аппетúта!",
                    situationHint = "Бариста отдаёт ароматный заказ."
                )
            )
        ),

        // 2. ЗНАКОМСТВО
        DialogueScenario(
            id = 2,
            title = "Первое знакомство",
            emoji = "🤝",
            description = "Приветствие, обмен именами и рассказ о себе",
            location = "Языковой клуб",
            turns = listOf(
                DialogueTurn(
                    id = 1,
                    speaker = SpeakerRole.KESHA,
                    text = "Привет! Меня зовут Кеша. А как тебя зовут?",
                    ttsText = "Привéт! Меня́ зовýт Кéша. А как тебя́ зовýт?",
                    situationHint = "Новый знакомый проявляет интерес."
                ),
                DialogueTurn(
                    id = 2,
                    speaker = SpeakerRole.USER,
                    text = "Привет! Меня зовут Анна. Очень приятно познакомиться!",
                    ttsText = "Привéт! Меня́ зовýт Áнна. Óчень прия́тно познакóмиться!",
                    phoneticTip = "Мягкое [н'] в «Анна», восклицательная интонация дружелюбия.",
                    situationHint = "Назовите своё имя и выразите радость встречи."
                ),
                DialogueTurn(
                    id = 3,
                    speaker = SpeakerRole.KESHA,
                    text = "Мне тоже очень приятно! Откуда ты приехала?",
                    ttsText = "Мне тóже óчень прия́тно! Откýда ты приéхала?",
                    situationHint = "Вопрос о родном городе или стране."
                ),
                DialogueTurn(
                    id = 4,
                    speaker = SpeakerRole.USER,
                    text = "Я приехала из Рима. Я с удовольствием учу русский язык!",
                    ttsText = "Я приéхала из Рúма. Я с удовóльствием учý рýсский язы́к!",
                    phoneticTip = "Звук [ы] в слове «язы́к», [учý] — ударение на окончание.",
                    situationHint = "Расскажите откуда вы и о своём увлечении языком."
                ),
                DialogueTurn(
                    id = 5,
                    speaker = SpeakerRole.KESHA,
                    text = "Здорово! У тебя отличное произношение! Будем дружить!",
                    ttsText = "Здóрово! У тебя́ отлúчное произношéние! Бýдем дружúть!",
                    situationHint = "Кеша рад новому другу."
                )
            )
        ),

        // 3. В ГОРОДЕ (ОРИЕНТАЦИЯ)
        DialogueScenario(
            id = 3,
            title = "В городе (Навигация)",
            emoji = "🚇",
            description = "Как спросить дорогу до метро и понять ответ",
            location = "Центральная площадь",
            turns = listOf(
                DialogueTurn(
                    id = 1,
                    speaker = SpeakerRole.USER,
                    text = "Извините, пожалуйста, вы не подскажете, где станция метро?",
                    ttsText = "Извинúте, пожáлуйста, вы не подскáжете, где стáнция метрó?",
                    phoneticTip = "Мягкое «извините», в слове «метро» ударение строго на [ó].",
                    situationHint = "Вежливо обратитесь к прохожему на улице."
                ),
                DialogueTurn(
                    id = 2,
                    speaker = SpeakerRole.KESHA,
                    text = "Добрый день! Да, конечно. Метро совсем близко.",
                    ttsText = "Дóбрый день! Да, конéчно. Метрó совсéм блúзко.",
                    situationHint = "Прохожий готов помочь."
                ),
                DialogueTurn(
                    id = 3,
                    speaker = SpeakerRole.KESHA,
                    text = "Идите прямо до светофора, а затем поверните направо.",
                    ttsText = "Идúте пря́мо до светофóра, а затéм повернúте напрáво.",
                    situationHint = "Инструкция движения."
                ),
                DialogueTurn(
                    id = 4,
                    speaker = SpeakerRole.USER,
                    text = "Прямо и направо? Это далеко пешком?",
                    ttsText = "Пря́мо и напрáво? Э́то далекó пешкóм?",
                    phoneticTip = "Вопросительная интонация (ИК-3) с подъёмом на слове «пешкóм».",
                    situationHint = "Уточните расстояние."
                ),
                DialogueTurn(
                    id = 5,
                    speaker = SpeakerRole.KESHA,
                    text = "Нет, всего пять минут спокойным шагом. Вход рядом с аптекой!",
                    ttsText = "Нет, всегó пять минýт спокóйным шáгом. Вход ря́дом с аптéкой!",
                    situationHint = "Кеша даёт понятный ориентир."
                ),
                DialogueTurn(
                    id = 6,
                    speaker = SpeakerRole.USER,
                    text = "Огромное спасибо за помощь! Хорошего вам дня!",
                    ttsText = "Огрóмное спасúбо за пóмощь! Хорóшего вам дня!",
                    phoneticTip = "Слово «помощь» оканчивается мягким шипящим [щ'].",
                    situationHint = "Тепло поблагодарите прохожего."
                )
            )
        ),

        // 4. В АПТЕКЕ
        DialogueScenario(
            id = 4,
            title = "В аптеке",
            emoji = "💊",
            description = "Покупка лекарств и объяснение самочувствия",
            location = "Городская аптека",
            turns = listOf(
                DialogueTurn(
                    id = 1,
                    speaker = SpeakerRole.KESHA,
                    text = "Здравствуйте! Чем я могу вам помочь?",
                    ttsText = "Здрáвствуйте! Чем я могý вам помóчь?",
                    situationHint = "Фармацевт у витрины."
                ),
                DialogueTurn(
                    id = 2,
                    speaker = SpeakerRole.USER,
                    text = "Здравствуйте! У меня сильно болит голова и есть насморк.",
                    ttsText = "Здрáвствуйте! У меня́ сúльно болúт головá и есть нáсморк.",
                    phoneticTip = "Ударение: «болúт головá». Звук [х] в «насморк» не оглушается.",
                    situationHint = "Опишите свои симптомы."
                ),
                DialogueTurn(
                    id = 3,
                    speaker = SpeakerRole.KESHA,
                    text = "Вот эффективные таблетки от боли и капли для носа. Принимайте два раза в день.",
                    ttsText = "Вот эффектúвные таблéтки от бóли и кáпли для нóса. Принимáйте два рáза в день.",
                    situationHint = "Фармацевт рекомендует лекарства."
                ),
                DialogueTurn(
                    id = 4,
                    speaker = SpeakerRole.USER,
                    text = "Понятно. Дайте, пожалуйста, эти таблетки и капли. Сколько это стоит?",
                    ttsText = "Поня́тно. Дáйте, пожáлуйста, э́ти таблéтки и кáпли. Скóлько э́то стóит?",
                    phoneticTip = "Глагол «дáйте» произносите мягко. Ударение на «стóит».",
                    situationHint = "Согласитесь на покупку и спросите стоимость."
                ),
                DialogueTurn(
                    id = 5,
                    speaker = SpeakerRole.KESHA,
                    text = "Всего четыреста рублей. Поправляйтесь скорее!",
                    ttsText = "Всегó четь́реста рублéй. Поправля́йтесь скорéе!",
                    situationHint = "Пожелание скорейшего выздоровления."
                )
            )
        ),

        // 5. В МАГАЗИНЕ
        DialogueScenario(
            id = 5,
            title = "В продуктовом магазине",
            emoji = "🍎",
            description = "Покупка свежих фруктов и овощей на развес",
            location = "Фруктовая лавка",
            turns = listOf(
                DialogueTurn(
                    id = 1,
                    speaker = SpeakerRole.KESHA,
                    text = "Добрый день! Посмотрите, какие спелые яблоки и груши!",
                    ttsText = "Дóбрый день! Посмотрúте, какúе спéлые я́блоки и грýши!",
                    situationHint = "Продавец предлагает свежий товар."
                ),
                DialogueTurn(
                    id = 2,
                    speaker = SpeakerRole.USER,
                    text = "Здравствуйте! Скажите, пожалуйста, сколько стоят красные яблоки?",
                    ttsText = "Здрáвствуйте! Скажúте, пожáлуйста, скóлько стóят крáсные я́блоки?",
                    phoneticTip = "Ударение в «я́блоки» на первый слог. [скóлько стóят].",
                    situationHint = "Поинтересуйтесь ценой яблок."
                ),
                DialogueTurn(
                    id = 3,
                    speaker = SpeakerRole.KESHA,
                    text = "Они стоят сто двадцать рублей за килограмм. Очень сочные и сладкие!",
                    ttsText = "Онú стóят сто двáдцать рублéй за килогрáмм. Óчень сóчные и слáдкие!",
                    situationHint = "Ответ продавца о качестве и цене."
                ),
                DialogueTurn(
                    id = 4,
                    speaker = SpeakerRole.USER,
                    text = "Отлично! Взвесьте мне два килограмма, пожалуйста.",
                    ttsText = "Отлúчно! Взвéсьте мне два килогрáмма, пожáлуйста.",
                    phoneticTip = "Слово «взвéсьте» — мягкое звучание [з'в'эст'и].",
                    situationHint = "Назовите нужное количество."
                ),
                DialogueTurn(
                    id = 5,
                    speaker = SpeakerRole.KESHA,
                    text = "Готово! С вас двести сорок рублей. Спасибо за покупку!",
                    ttsText = "Готóво! С вас двéсти сóрок рублéй. Спасúбо за покýпку!",
                    situationHint = "Вручение пакета с фруктами."
                )
            )
        ),

        // 6. В ОТЕЛЕ
        DialogueScenario(
            id = 6,
            title = "В отеле (Ресепшн)",
            emoji = "🏨",
            description = "Заселение в номер и вопросы о завтраке",
            location = "Стойка регистрации отеля",
            turns = listOf(
                DialogueTurn(
                    id = 1,
                    speaker = SpeakerRole.KESHA,
                    text = "Добрый вечер! Рады приветствовать вас в нашем отеле!",
                    ttsText = "Дóбрый вéчер! Рáды привéтствовать вас в нáшем отéле!",
                    situationHint = "Администратор встречает гостя."
                ),
                DialogueTurn(
                    id = 2,
                    speaker = SpeakerRole.USER,
                    text = "Добрый вечер! У меня забронирован стандартный номер на три ночи.",
                    ttsText = "Дóбрый вéчер! У меня́ забронúрован стандáртный нóмер на три нóчи.",
                    phoneticTip = "Ударение: «забронúрован», твёрдое окончание в слове «стандáртный».",
                    situationHint = "Сообщите о своей брони."
                ),
                DialogueTurn(
                    id = 3,
                    speaker = SpeakerRole.KESHA,
                    text = "Да, вижу вашу бронь. Ваш номер двести четыре на втором этаже. Вот электронный ключ.",
                    ttsText = "Да, вúжу вáшу бронь. Ваш нóмер двéсти четь́ре на вторóм этажé. Вот электрóнный ключ.",
                    situationHint = "Выдача ключа от номера."
                ),
                DialogueTurn(
                    id = 4,
                    speaker = SpeakerRole.USER,
                    text = "Большое спасибо! Подскажите, пожалуйста, во сколько завтра завтрак?",
                    ttsText = "Большóе спасúбо! Подскажúте, пожáлуйста, во скóлько зáвтра зáвтрак?",
                    phoneticTip = "Ритмичная фраза «зáвтра зáвтрак» — оба ударения на первый слог.",
                    situationHint = "Узнайте расписание утреннего питания."
                ),
                DialogueTurn(
                    id = 5,
                    speaker = SpeakerRole.KESHA,
                    text = "Завтрак проходит с семи до десяти утра в ресторане на первом этаже. Приятного отдыха!",
                    ttsText = "Зáвтрак прохóдит с семú до десятú утрá в ресторáне на пéрвом этажé. Прия́тного óтдыха!",
                    situationHint = "Кеша желает приятного проживания."
                )
            )
        )
    )
}
