package com.example.data

enum class GrammarFocus(val title: String, val badgeEmoji: String, val formula: String) {
    ACCUSATIVE_OBJECT("Винительный объект", "👀", "Я вижу / люблю + [кого/что?]"),
    GENITIVE_EXISTENCE("Наличие и отсутствие", "📦", "У меня есть / нет + [кого/чего?]"),
    PREPOSITIONAL_LOCATION("Местонахождение", "📍", "Я нахожусь в / на + [ком/чём?]"),
    DATIVE_RECIPIENT("Адресат и польза", "📞", "Я звоню / помогаю + [кому?]"),
    GENDER_AGREEMENT("Согласование рода", "🏷️", "Это мой / моя / моё + [существительное]"),
    VERBS_OF_MOTION("Глаголы движения", "🚶‍♂️/🚗", "Я иду (пешком) vs Я еду (на транспорте)")
}

data class MatrixSlotOption(
    val slotId: Int,
    val slotWord: String,
    val slotStressMarked: String,
    val fullSentence: String,
    val fullSentenceTts: String,
    val meaningRu: String,
    val grammaticalHint: String
)

data class SpeechMatrixItem(
    val id: Int,
    val category: GrammarFocus,
    val title: String,
    val framePrefix: String,
    val frameQuestion: String,
    val pedagogicalNote: String,
    val options: List<MatrixSlotOption>
)

object SpeechMatrixData {
    val matrices: List<SpeechMatrixItem> = listOf(
        // 1. ВИНИТЕЛЬНЫЙ ПАДЕЖ: ПРЯМОЙ ОБЪЕКТ
        SpeechMatrixItem(
            id = 1,
            category = GrammarFocus.ACCUSATIVE_OBJECT,
            title = "Я вижу... (Прямой объект)",
            framePrefix = "Я вижу",
            frameQuestion = "[кого / что?]",
            pedagogicalNote = "В винительном падеже женский род меняет окончание -А на -У (мама → маму), а неодушевлённый мужской и средний род не меняются (дом → дом).",
            options = listOf(
                MatrixSlotOption(
                    slotId = 1,
                    slotWord = "маму",
                    slotStressMarked = "мáму",
                    fullSentence = "Я вижу маму.",
                    fullSentenceTts = "Я ви́жу мáму.",
                    meaningRu = "В поле зрения находится моя мама (одушевлённое лицо, ж.р.).",
                    grammaticalHint = "Мама (ж.р.) → вижу мам-у"
                ),
                MatrixSlotOption(
                    slotId = 2,
                    slotWord = "книгу",
                    slotStressMarked = "кни́гу",
                    fullSentence = "Я вижу книгу.",
                    fullSentenceTts = "Я ви́жу кни́гу.",
                    meaningRu = "Смотрю на печатную книгу на столе.",
                    grammaticalHint = "Книга (ж.р.) → вижу книг-у"
                ),
                MatrixSlotOption(
                    slotId = 3,
                    slotWord = "город",
                    slotStressMarked = "гóрод",
                    fullSentence = "Я вижу город.",
                    fullSentenceTts = "Я ви́жу гóрод.",
                    meaningRu = "Наблюдаю панораму города из окна.",
                    grammaticalHint = "Город (м.р., неодуш.) → форма не меняется"
                ),
                MatrixSlotOption(
                    slotId = 4,
                    slotWord = "сестру",
                    slotStressMarked = "сестру́",
                    fullSentence = "Я вижу сестру.",
                    fullSentenceTts = "Я ви́жу сестру́.",
                    meaningRu = "Встречаю взглядом родную сестру.",
                    grammaticalHint = "Сестра (ж.р.) → вижу сестр-у (ударение на окончание)"
                ),
                MatrixSlotOption(
                    slotId = 5,
                    slotWord = "окно",
                    slotStressMarked = "окнó",
                    fullSentence = "Я вижу окно.",
                    fullSentenceTts = "Я ви́жу окнó.",
                    meaningRu = "Взгляд направлен на светлое окно.",
                    grammaticalHint = "Окно (ср.р.) → форма совпадает с именительным"
                ),
                MatrixSlotOption(
                    slotId = 6,
                    slotWord = "друга",
                    slotStressMarked = "дру́га",
                    fullSentence = "Я вижу друга.",
                    fullSentenceTts = "Я ви́жу дру́га.",
                    meaningRu = "Замечаю своего близкого товарища.",
                    grammaticalHint = "Друг (м.р., одуш.) → принимает окончание -А"
                )
            )
        ),

        // 2. РОДИТЕЛЬНЫЙ ПАДЕЖ: НАЛИЧИЕ VS ОТСУТСТВИЕ
        SpeechMatrixItem(
            id = 2,
            category = GrammarFocus.GENITIVE_EXISTENCE,
            title = "У меня нет... (Отсутствие)",
            framePrefix = "У меня нет",
            frameQuestion = "[кого / чего?]",
            pedagogicalNote = "Русская конструкция отрицания «У меня нет...» требует родительного падежа (мужской род: -А/-Я, женский род: -Ы/-И).",
            options = listOf(
                MatrixSlotOption(
                    slotId = 1,
                    slotWord = "времени",
                    slotStressMarked = "врéмени",
                    fullSentence = "У меня нет времени.",
                    fullSentenceTts = "У меня́ нет врéмени.",
                    meaningRu = "Я спешу, у меня нет свободных минут.",
                    grammaticalHint = "Время (разносклоняемое) → нет врем-ени"
                ),
                MatrixSlotOption(
                    slotId = 2,
                    slotWord = "билета",
                    slotStressMarked = "билéта",
                    fullSentence = "У меня нет билета.",
                    fullSentenceTts = "У меня́ нет билéта.",
                    meaningRu = "Мне нужно купить проездной или билет в кассе.",
                    grammaticalHint = "Билет (м.р.) → нет билет-а"
                ),
                MatrixSlotOption(
                    slotId = 3,
                    slotWord = "сестры",
                    slotStressMarked = "сестры́",
                    fullSentence = "У меня нет сестры.",
                    fullSentenceTts = "У меня́ нет сестры́.",
                    meaningRu = "Я единственный ребёнок или у меня только братья.",
                    grammaticalHint = "Сестра (ж.р.) → нет сестр-ы"
                ),
                MatrixSlotOption(
                    slotId = 4,
                    slotWord = "вопроса",
                    slotStressMarked = "вопрóса",
                    fullSentence = "У меня нет вопроса.",
                    fullSentenceTts = "У меня́ нет вопрóса.",
                    meaningRu = "Мне всё понятно, вопросов не осталось.",
                    grammaticalHint = "Вопрос (м.р.) → нет вопрос-а"
                ),
                MatrixSlotOption(
                    slotId = 5,
                    slotWord = "ключа",
                    slotStressMarked = "ключá",
                    fullSentence = "У меня нет ключа.",
                    fullSentenceTts = "У меня́ нет ключá.",
                    meaningRu = "Я не могу открыть замок двери.",
                    grammaticalHint = "Ключ (м.р.) → нет ключ-а (ударение на -а)"
                ),
                MatrixSlotOption(
                    slotId = 6,
                    slotWord = "денег",
                    slotStressMarked = "дéнег",
                    fullSentence = "У меня нет денег.",
                    fullSentenceTts = "У меня́ нет дéнег.",
                    meaningRu = "Кошелёк пуст, нужны средства для оплаты.",
                    grammaticalHint = "Деньги (мн.ч.) → нет ден-ег"
                )
            )
        ),

        // 3. ПРЕДЛОЖНЫЙ ПАДЕЖ: МЕСТОНАХОЖДЕНИЕ (В / НА)
        SpeechMatrixItem(
            id = 3,
            category = GrammarFocus.PREPOSITIONAL_LOCATION,
            title = "Я сейчас в / на... (Место)",
            framePrefix = "Я сейчас",
            frameQuestion = "[где? в чём? на чём?]",
            pedagogicalNote = "Окончание места почти всегда -Е: «в парк-е», «на работ-е». Предлог «В» используется для закрытых пространств, «НА» — для открытых поверхностей и событий.",
            options = listOf(
                MatrixSlotOption(
                    slotId = 1,
                    slotWord = "в Москве",
                    slotStressMarked = "в Москвé",
                    fullSentence = "Я сейчас в Москве.",
                    fullSentenceTts = "Я сейчáс в Москвé.",
                    meaningRu = "Нахожусь в столице России.",
                    grammaticalHint = "Москва → предлог В + окончание -е"
                ),
                MatrixSlotOption(
                    slotId = 2,
                    slotWord = "на работе",
                    slotStressMarked = "на рабóте",
                    fullSentence = "Я сейчас на работе.",
                    fullSentenceTts = "Я сейчáс на рабóте.",
                    meaningRu = "Занимаюсь трудовыми обязанностями в офисе.",
                    grammaticalHint = "Работа (деятельность) → предлог НА + окончание -е"
                ),
                MatrixSlotOption(
                    slotId = 3,
                    slotWord = "в театре",
                    slotStressMarked = "в теáтре",
                    fullSentence = "Я сейчас в театре.",
                    fullSentenceTts = "Я сейчáс в теáтре.",
                    meaningRu = "Смотрю спектакль в здании театра.",
                    grammaticalHint = "Театр (здание) → предлог В + окончание -е"
                ),
                MatrixSlotOption(
                    slotId = 4,
                    slotWord = "на вокзале",
                    slotStressMarked = "на вокзáле",
                    fullSentence = "Я сейчас на вокзале.",
                    fullSentenceTts = "Я сейчáс на вокзáле.",
                    meaningRu = "Ожидаю поезд на железнодорожной станции.",
                    grammaticalHint = "Вокзал → традиционно предлог НА + окончание -е"
                ),
                MatrixSlotOption(
                    slotId = 5,
                    slotWord = "в парке",
                    slotStressMarked = "в пáрке",
                    fullSentence = "Я сейчас в парке.",
                    fullSentenceTts = "Я сейчáс в пáрке.",
                    meaningRu = "Гуляю на свежем воздухе среди деревьев.",
                    grammaticalHint = "Парк (территория) → предлог В + окончание -е"
                ),
                MatrixSlotOption(
                    slotId = 6,
                    slotWord = "дома",
                    slotStressMarked = "дóма",
                    fullSentence = "Я сейчас дома.",
                    fullSentenceTts = "Я сейчáс дóма.",
                    meaningRu = "В своей квартире или у себя в жилье.",
                    grammaticalHint = "Особое наречие места (без предлога, ударение дóма)"
                )
            )
        ),

        // 4. ДАТЕЛЬНЫЙ ПАДЕЖ: АДРЕСАТ ЗВОНКА И ПОМОЩИ
        SpeechMatrixItem(
            id = 4,
            category = GrammarFocus.DATIVE_RECIPIENT,
            title = "Я звоню... (Адресат)",
            framePrefix = "Я звоню",
            frameQuestion = "[кому?]",
            pedagogicalNote = "Дательный падеж обозначает человека, к которому обращено действие: мужской род принимает -У/-Ю, женский род -Е.",
            options = listOf(
                MatrixSlotOption(
                    slotId = 1,
                    slotWord = "маме",
                    slotStressMarked = "мáме",
                    fullSentence = "Я звоню маме.",
                    fullSentenceTts = "Я звоню́ мáме.",
                    meaningRu = "Набираю номер мамы по телефону.",
                    grammaticalHint = "Мама (ж.р.) → звоню мам-е"
                ),
                MatrixSlotOption(
                    slotId = 2,
                    slotWord = "другу",
                    slotStressMarked = "дру́гу",
                    fullSentence = "Я звоню другу.",
                    fullSentenceTts = "Я звоню́ дру́гу.",
                    meaningRu = "Звоню близкому приятелю.",
                    grammaticalHint = "Друг (м.р.) → звоню друг-у"
                ),
                MatrixSlotOption(
                    slotId = 3,
                    slotWord = "врачу",
                    slotStressMarked = "врачу́",
                    fullSentence = "Я звоню врачу.",
                    fullSentenceTts = "Я звоню́ врачу́.",
                    meaningRu = "Записываюсь на консультацию к доктору.",
                    grammaticalHint = "Врач (м.р.) → звоню врач-у (ударение на -у)"
                ),
                MatrixSlotOption(
                    slotId = 4,
                    slotWord = "сестре",
                    slotStressMarked = "сестрé",
                    fullSentence = "Я звоню сестре.",
                    fullSentenceTts = "Я звоню́ сестрé.",
                    meaningRu = "Разговариваю с сестрой по видеосвязи.",
                    grammaticalHint = "Сестра (ж.р.) → звоню сестр-е"
                ),
                MatrixSlotOption(
                    slotId = 5,
                    slotWord = "брату",
                    slotStressMarked = "брáту",
                    fullSentence = "Я звоню брату.",
                    fullSentenceTts = "Я звоню́ брáту.",
                    meaningRu = "Хочу обсудить новости с братом.",
                    grammaticalHint = "Брат (м.р.) → звоню брат-у"
                ),
                MatrixSlotOption(
                    slotId = 6,
                    slotWord = "учителю",
                    slotStressMarked = "учи́телю",
                    fullSentence = "Я звоню учителю.",
                    fullSentenceTts = "Я звоню́ учи́телю.",
                    meaningRu = "Уточняю домашнее задание у преподавателя.",
                    grammaticalHint = "Учитель (на мягкий знак) → звоню учител-ю"
                )
            )
        ),

        // 5. СОГЛАСОВАНИЕ РОДА: ЭТО МОЙ / МОЯ / МОЁ
        SpeechMatrixItem(
            id = 5,
            category = GrammarFocus.GENDER_AGREEMENT,
            title = "Это мой / моя / моё... (Род)",
            framePrefix = "Это",
            frameQuestion = "[мой / моя / моё + предмет]",
            pedagogicalNote = "Притяжательное местоимение строго согласуется с грамматическим родом предмета: Мужской (мой), Женский (моя), Средний (моё).",
            options = listOf(
                MatrixSlotOption(
                    slotId = 1,
                    slotWord = "мой дом",
                    slotStressMarked = "мой дом",
                    fullSentence = "Это мой дом.",
                    fullSentenceTts = "Э́то мой дом.",
                    meaningRu = "Здание или жильё, где я живу (мужской род).",
                    grammaticalHint = "Дом (м.р., нулевое окончание) → МОЙ"
                ),
                MatrixSlotOption(
                    slotId = 2,
                    slotWord = "моя комната",
                    slotStressMarked = "моя́ кóмната",
                    fullSentence = "Это моя комната.",
                    fullSentenceTts = "Э́то моя́ кóмната.",
                    meaningRu = "Моё личное помещение в квартире (женский род).",
                    grammaticalHint = "Комната (ж.р., на -а) → МОЯ"
                ),
                MatrixSlotOption(
                    slotId = 3,
                    slotWord = "моё окно",
                    slotStressMarked = "моё окнó",
                    fullSentence = "Это моё окно.",
                    fullSentenceTts = "Э́то моё окнó.",
                    meaningRu = "Окно моей комнаты с красивым видом (средний род).",
                    grammaticalHint = "Окно (ср.р., на -о) → МОЁ"
                ),
                MatrixSlotOption(
                    slotId = 4,
                    slotWord = "мой город",
                    slotStressMarked = "мой гóрод",
                    fullSentence = "Это мой город.",
                    fullSentenceTts = "Э́то мой гóрод.",
                    meaningRu = "Город, где я родился или сейчас живу.",
                    grammaticalHint = "Город (м.р.) → МОЙ"
                ),
                MatrixSlotOption(
                    slotId = 5,
                    slotWord = "моя книга",
                    slotStressMarked = "моя́ кни́га",
                    fullSentence = "Это моя книга.",
                    fullSentenceTts = "Э́то моя́ кни́га.",
                    meaningRu = "Книга, которую я читаю на русском языке.",
                    grammaticalHint = "Книга (ж.р.) → МОЯ"
                ),
                MatrixSlotOption(
                    slotId = 6,
                    slotWord = "моё кафе",
                    slotStressMarked = "моё кафé",
                    fullSentence = "Это моё кафе.",
                    fullSentenceTts = "Э́то моё кафé.",
                    meaningRu = "Моё любимое кафе, куда я хожу пить чай.",
                    grammaticalHint = "Кафе (несклоняемое, ср.р.) → МОЁ"
                )
            )
        ),

        // 6. ГЛАГОЛЫ ДВИЖЕНИЯ: ИДТИ (ПЕШКОМ) VS ЕХАТЬ (НА ТРАНСПОРТЕ)
        SpeechMatrixItem(
            id = 6,
            category = GrammarFocus.VERBS_OF_MOTION,
            title = "Идти пешком vs Ехать на транспорте",
            framePrefix = "Я",
            frameQuestion = "[иду (ногами) / еду (колёсами)]",
            pedagogicalNote = "В русском языке фундаментально различаются движение пешком («идти») и движение на транспорте («ехать»).",
            options = listOf(
                MatrixSlotOption(
                    slotId = 1,
                    slotWord = "иду пешком в парк",
                    slotStressMarked = "иду́ пешкóм в парк",
                    fullSentence = "Я иду пешком в парк.",
                    fullSentenceTts = "Я иду́ пешкóм в парк.",
                    meaningRu = "Передвигаюсь своими ногами на прогулку.",
                    grammaticalHint = "Идти = движение на своих ногах (шагом)"
                ),
                MatrixSlotOption(
                    slotId = 2,
                    slotWord = "еду на метро",
                    slotStressMarked = "éду на метрó",
                    fullSentence = "Я еду на метро.",
                    fullSentenceTts = "Я éду на метрó.",
                    meaningRu = "Использую подземный поезд для перемещения.",
                    grammaticalHint = "Ехать = движение с помощью любого транспорта"
                ),
                MatrixSlotOption(
                    slotId = 3,
                    slotWord = "иду домой",
                    slotStressMarked = "иду́ домóй",
                    fullSentence = "Я иду домой.",
                    fullSentenceTts = "Я иду́ домóй.",
                    meaningRu = "Возвращаюсь пешком в своё жильё.",
                    grammaticalHint = "Идти + направление «домой»"
                ),
                MatrixSlotOption(
                    slotId = 4,
                    slotWord = "еду на автобусе",
                    slotStressMarked = "éду на автóбусе",
                    fullSentence = "Я еду на автобусе.",
                    fullSentenceTts = "Я éду на автóбусе.",
                    meaningRu = "Еду по городскому маршруту на автобусе.",
                    grammaticalHint = "Ехать на чём? → предложный падеж транспорта"
                ),
                MatrixSlotOption(
                    slotId = 5,
                    slotWord = "иду в магазин",
                    slotStressMarked = "иду́ в магази́н",
                    fullSentence = "Я иду в магазин.",
                    fullSentenceTts = "Я иду́ в магази́н.",
                    meaningRu = "Направляюсь пешком за покупками.",
                    grammaticalHint = "Идти в куда? → винительный падеж цели"
                ),
                MatrixSlotOption(
                    slotId = 6,
                    slotWord = "еду на такси",
                    slotStressMarked = "éду на такси́",
                    fullSentence = "Я еду на такси.",
                    fullSentenceTts = "Я éду на такси́.",
                    meaningRu = "Вызвал машину с водителем для быстрой поездки.",
                    grammaticalHint = "Ехать на такси (несклоняемое существительное)"
                )
            )
        )
    )
}
