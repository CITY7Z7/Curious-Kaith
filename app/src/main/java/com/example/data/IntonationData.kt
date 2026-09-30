package com.example.data

enum class IntonationType(
    val code: String,
    val title: String,
    val directionDescription: String,
    val arrowSymbol: String,
    val pitchMultiplier: Float
) {
    IK1(
        code = "ИК-1",
        title = "Повествование (Завершённая мысль)",
        directionDescription = "Ровный тон с резким понижением на гласном центра",
        arrowSymbol = "↘",
        pitchMultiplier = 0.95f
    ),
    IK2(
        code = "ИК-2",
        title = "Специальный вопрос (С вопросительным словом)",
        directionDescription = "Усиленное ударение и нисходящий спад на вопросительном слове",
        arrowSymbol = "↗↘",
        pitchMultiplier = 1.05f
    ),
    IK3(
        code = "ИК-3",
        title = "Общий вопрос (Без вопросительного слова)",
        directionDescription = "Резкий восходящий взлёт тона на интонационном центре",
        arrowSymbol = "↗",
        pitchMultiplier = 1.25f
    ),
    IK4(
        code = "ИК-4",
        title = "Сопоставительный вопрос (С союзом «А...»)",
        directionDescription = "Понижение в начале с плавным подъёмом в конце",
        arrowSymbol = "↘↗",
        pitchMultiplier = 1.15f
    ),
    IK5(
        code = "ИК-5",
        title = "Восклицание и высокая оценка",
        directionDescription = "Высокий тон на первом центре и спад на втором",
        arrowSymbol = "↗──↘",
        pitchMultiplier = 1.20f
    )
}

data class IntonationItem(
    val id: Int,
    val type: IntonationType,
    val phraseText: String,
    val centerWord: String,
    val centerSyllable: String,
    val ttsText: String,
    val speechPitch: Float,
    val communicativeMeaning: String,
    val pedagogicalTip: String,
    val pitchCurvePoints: List<Float> // normalized heights 0.0 .. 1.0 for melody contour
)

data class IntonationContrastSet(
    val id: Int,
    val baseTopic: String,
    val description: String,
    val items: List<IntonationItem>
)

object IntonationData {
    val contrastSets: List<IntonationContrastSet> = listOf(
        // Контраст 1: ЭТО СУП
        IntonationContrastSet(
            id = 1,
            baseTopic = "Обед: Суп",
            description = "Как одна и та же фраза меняет смысл от точки к вопросу",
            items = listOf(
                IntonationItem(
                    id = 101,
                    type = IntonationType.IK1,
                    phraseText = "Это суп.",
                    centerWord = "суп",
                    centerSyllable = "суп",
                    ttsText = "Э́то суп.",
                    speechPitch = 0.95f,
                    communicativeMeaning = "Утверждение: констатация факта, мысль завершена.",
                    pedagogicalTip = "Голос на слове «суп» спокойно опускается вниз. Спокойная точка.",
                    pitchCurvePoints = listOf(0.5f, 0.55f, 0.45f, 0.25f)
                ),
                IntonationItem(
                    id = 102,
                    type = IntonationType.IK3,
                    phraseText = "Это суп?",
                    centerWord = "суп",
                    centerSyllable = "суп",
                    ttsText = "Э́то сýп?",
                    speechPitch = 1.25f,
                    communicativeMeaning = "Общий вопрос: переспрос (это именно суп, а не компот?).",
                    pedagogicalTip = "Резкий взлёт тона на гласном [У] в слове «суп». Голос взлетает вверх!",
                    pitchCurvePoints = listOf(0.45f, 0.5f, 0.88f, 0.7f)
                ),
                IntonationItem(
                    id = 103,
                    type = IntonationType.IK2,
                    phraseText = "Какой это суп?",
                    centerWord = "Какой",
                    centerSyllable = "кой",
                    ttsText = "Какóй э́то суп?",
                    speechPitch = 1.05f,
                    communicativeMeaning = "Специальный вопрос: интерес к сорту или качеству супа.",
                    pedagogicalTip = "Голосовой акцент падает на слово «Какой», затем тон плавно спадает.",
                    pitchCurvePoints = listOf(0.85f, 0.7f, 0.45f, 0.35f)
                )
            )
        ),

        // Контраст 2: АННА ДОМА
        IntonationContrastSet(
            id = 2,
            baseTopic = "Люди: Анна дома",
            description = "Различие между новостью, переспросом и выяснением кто дома",
            items = listOf(
                IntonationItem(
                    id = 201,
                    type = IntonationType.IK1,
                    phraseText = "Анна дома.",
                    centerWord = "дома",
                    centerSyllable = "до",
                    ttsText = "Áнна дóма.",
                    speechPitch = 0.95f,
                    communicativeMeaning = "Утверждение: Анна находится у себя дома.",
                    pedagogicalTip = "На первом слоге «до́» тон опускается к концу слова.",
                    pitchCurvePoints = listOf(0.55f, 0.5f, 0.4f, 0.25f)
                ),
                IntonationItem(
                    id = 202,
                    type = IntonationType.IK3,
                    phraseText = "Анна дома?",
                    centerWord = "дома",
                    centerSyllable = "до",
                    ttsText = "Áнна дóма?",
                    speechPitch = 1.25f,
                    communicativeMeaning = "Вопрос о месте: дома ли Анна сейчас?",
                    pedagogicalTip = "Энергичный бросок голоса вверх на ударной гласной «дО-ма?».",
                    pitchCurvePoints = listOf(0.45f, 0.5f, 0.9f, 0.65f)
                ),
                IntonationItem(
                    id = 203,
                    type = IntonationType.IK2,
                    phraseText = "Кто дома?",
                    centerWord = "Кто",
                    centerSyllable = "Кто",
                    ttsText = "Кто́ дóма?",
                    speechPitch = 1.05f,
                    communicativeMeaning = "Вопрос о личности: кто именно сейчас находится дома?",
                    pedagogicalTip = "Сильный интонационный толчок на слове «Кто».",
                    pitchCurvePoints = listOf(0.9f, 0.6f, 0.4f, 0.3f)
                )
            )
        ),

        // Контраст 3: ПОЕЗДКА В ТЕАТР
        IntonationContrastSet(
            id = 3,
            baseTopic = "Город: Идём в театр",
            description = "Планы на вечер: согласие vs сомнение vs направление",
            items = listOf(
                IntonationItem(
                    id = 301,
                    type = IntonationType.IK1,
                    phraseText = "Мы идём в театр.",
                    centerWord = "театр",
                    centerSyllable = "атр",
                    ttsText = "Мы идём в теáтр.",
                    speechPitch = 0.95f,
                    communicativeMeaning = "Спокойное сообщение о совместных планах.",
                    pedagogicalTip = "Нисходящий плавный тон. В конце голос затихает.",
                    pitchCurvePoints = listOf(0.5f, 0.52f, 0.5f, 0.25f)
                ),
                IntonationItem(
                    id = 302,
                    type = IntonationType.IK3,
                    phraseText = "Мы идём в театр?",
                    centerWord = "театр",
                    centerSyllable = "атр",
                    ttsText = "Мы идём в теáтр?",
                    speechPitch = 1.25f,
                    communicativeMeaning = "Вопрос с удивлением или переспрос конечной цели.",
                    pedagogicalTip = "Взлёт интонации на слове «теáтр?». Не растягивайте гласные.",
                    pitchCurvePoints = listOf(0.4f, 0.45f, 0.55f, 0.92f)
                ),
                IntonationItem(
                    id = 303,
                    type = IntonationType.IK2,
                    phraseText = "Куда мы идём?",
                    centerWord = "Куда",
                    centerSyllable = "да",
                    ttsText = "Кудá мы идём?",
                    speechPitch = 1.05f,
                    communicativeMeaning = "Запрос маршрута и направления.",
                    pedagogicalTip = "Слово «Куда́» выделяется голосом выше всех остальных слов.",
                    pitchCurvePoints = listOf(0.88f, 0.55f, 0.45f, 0.35f)
                )
            )
        ),

        // Контраст 4: ГОРЯЧИЙ ЧАЙ
        IntonationContrastSet(
            id = 4,
            baseTopic = "Впечатление: Горячий чай",
            description = "Факт температуры vs предупреждение vs вопрос",
            items = listOf(
                IntonationItem(
                    id = 401,
                    type = IntonationType.IK1,
                    phraseText = "Чай горячий.",
                    centerWord = "горячий",
                    centerSyllable = "ря",
                    ttsText = "Чай горя́чий.",
                    speechPitch = 0.95f,
                    communicativeMeaning = "Предупреждение: будь осторожен, чай горячий.",
                    pedagogicalTip = "Ударение на «ря́», в конце фразы спад вниз.",
                    pitchCurvePoints = listOf(0.5f, 0.58f, 0.4f, 0.2f)
                ),
                IntonationItem(
                    id = 402,
                    type = IntonationType.IK3,
                    phraseText = "Чай горячий?",
                    centerWord = "горячий",
                    centerSyllable = "ря",
                    ttsText = "Чай горя́чий?",
                    speechPitch = 1.25f,
                    communicativeMeaning = "Вопрос: можно ли уже пить чай или он ещё не остыл?",
                    pedagogicalTip = "Пик тона приходится строго на ударный слог «-ря́-».",
                    pitchCurvePoints = listOf(0.45f, 0.88f, 0.65f, 0.5f)
                ),
                IntonationItem(
                    id = 403,
                    type = IntonationType.IK4,
                    phraseText = "А кофе?",
                    centerWord = "кофе",
                    centerSyllable = "ко",
                    ttsText = "А кóфе?",
                    speechPitch = 1.15f,
                    communicativeMeaning = "Сопоставительный вопрос: чай я понял, а что насчёт кофе?",
                    pedagogicalTip = "ИК-4: сначала небольшое понижение, а в конце вопросительный подъем.",
                    pitchCurvePoints = listOf(0.35f, 0.45f, 0.8f, 0.85f)
                )
            )
        ),

        // Контраст 5: ЗНАНИЕ ЯЗЫКА
        IntonationContrastSet(
            id = 5,
            baseTopic = "Речь: Русский язык",
            description = "Уверенность в речи vs сомнение собеседника",
            items = listOf(
                IntonationItem(
                    id = 501,
                    type = IntonationType.IK1,
                    phraseText = "Ты говоришь по-русски.",
                    centerWord = "русски",
                    centerSyllable = "рус",
                    ttsText = "Ты говори́шь по-рýсски.",
                    speechPitch = 0.95f,
                    communicativeMeaning = "Уверенное утверждение способностей ученика.",
                    pedagogicalTip = "Убедительный нисходящий тон.",
                    pitchCurvePoints = listOf(0.48f, 0.5f, 0.52f, 0.25f)
                ),
                IntonationItem(
                    id = 502,
                    type = IntonationType.IK3,
                    phraseText = "Ты говоришь по-русски?",
                    centerWord = "русски",
                    centerSyllable = "рус",
                    ttsText = "Ты говори́шь по-рýсски?",
                    speechPitch = 1.25f,
                    communicativeMeaning = "Вопрос собеседнику: понимает ли он русскую речь?",
                    pedagogicalTip = "Яркий взлёт на слоге «-рУс-». Самая частая русская интонация вопроса.",
                    pitchCurvePoints = listOf(0.42f, 0.48f, 0.92f, 0.7f)
                ),
                IntonationItem(
                    id = 503,
                    type = IntonationType.IK5,
                    phraseText = "Как красиво ты говоришь!",
                    centerWord = "красиво",
                    centerSyllable = "си",
                    ttsText = "Как краси́во ты говори́шь!",
                    speechPitch = 1.20f,
                    communicativeMeaning = "Восторг и эмоциональная похвала произношения.",
                    pedagogicalTip = "Двойная волна: высокий подъем на «краси́во» и тёплый спад в конце.",
                    pitchCurvePoints = listOf(0.6f, 0.95f, 0.7f, 0.4f)
                )
            )
        )
    )
}
