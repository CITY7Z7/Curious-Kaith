package com.example.data

enum class ContrastCategory(val title: String, val badgeEmoji: String, val description: String) {
    HARD_SOFT("Твёрдость — Мягкость", "🔲/🪶", "Смыслоразличительная роль палатализации и мягкого знака"),
    VOICED_VOICELESS("Звонкость — Глухость", "🔔/🔇", "Различие парных звонких и глухих согласных в сильной позиции")
}

data class MinimalPairWord(
    val word: String,
    val stressMarked: String,
    val transcription: String,
    val meaningRu: String,
    val phoneticRole: String, // e.g. "Твёрдый [л]" or "Мягкий [л']"
    val articulationHint: String
)

data class MinimalPairItem(
    val id: Int,
    val category: ContrastCategory,
    val contrastKey: String, // e.g. "[Л] vs [Л']" or "[Д] vs [Т]"
    val wordA: MinimalPairWord,
    val wordB: MinimalPairWord,
    val pedagogicalExplanation: String
)

object MinimalPairsData {
    val pairs: List<MinimalPairItem> = listOf(
        // ================= ТВЁРДОСТЬ — МЯГКОСТЬ =================
        MinimalPairItem(
            id = 1,
            category = ContrastCategory.HARD_SOFT,
            contrastKey = "[Л] vs [Л']",
            wordA = MinimalPairWord(
                word = "Угол",
                stressMarked = "У́гол",
                transcription = "[ýгал]",
                meaningRu = "Место, где сходятся две стены комнаты",
                phoneticRole = "Твёрдый звук [Л]",
                articulationHint = "Кончик языка плотно прижат к верхним зубам, спинка опущена."
            ),
            wordB = MinimalPairWord(
                word = "Уголь",
                stressMarked = "У́голь",
                transcription = "[ýгал']",
                meaningRu = "Чёрное полезное ископаемое для костра и печи",
                phoneticRole = "Мягкий звук [Л']",
                articulationHint = "Средняя спинка языка резко поднимается к нёбу. Звучит мягко и нежно."
            ),
            pedagogicalExplanation = "Смягчение звука [Л] мягким знаком меняет геометрический угол на чёрный уголь!"
        ),

        MinimalPairItem(
            id = 2,
            category = ContrastCategory.HARD_SOFT,
            contrastKey = "[Б] vs [Б']",
            wordA = MinimalPairWord(
                word = "Был",
                stressMarked = "Был",
                transcription = "[был]",
                meaningRu = "Прошедшее время глагола «быть» (он был здесь)",
                phoneticRole = "Твёрдый звук [Б] + глубокое [Ы]",
                articulationHint = "Губы плотно смыкаются, язык отодвинут назад на гласном [Ы]."
            ),
            wordB = MinimalPairWord(
                word = "Бил",
                stressMarked = "Бил",
                transcription = "[б'ил]",
                meaningRu = "Ударял, наносил удары (он бил в барабан)",
                phoneticRole = "Мягкий звук [Б'] + переднее [И]",
                articulationHint = "Губы сжаты, язык сразу устремляется вперёд к верхнему нёбу."
            ),
            pedagogicalExplanation = "Твёрдое «Был» означает существование, а мягкое «Бил» — действие удара."
        ),

        MinimalPairItem(
            id = 3,
            category = ContrastCategory.HARD_SOFT,
            contrastKey = "[Т] vs [Т']",
            wordA = MinimalPairWord(
                word = "Брат",
                stressMarked = "Брат",
                transcription = "[брат]",
                meaningRu = "Родной сын тех же родителей",
                phoneticRole = "Твёрдый звук [Т]",
                articulationHint = "Чёткий сухой щелчок кончиком языка о зубы без смягчения."
            ),
            wordB = MinimalPairWord(
                word = "Брать",
                stressMarked = "Брать",
                transcription = "[брат']",
                meaningRu = "Инфинитив: брать руками, принимать",
                phoneticRole = "Мягкий звук [Т']",
                articulationHint = "Кончик языка у нижних зубов, спинка касается нёба [т']."
            ),
            pedagogicalExplanation = "Существительное «Брат» оканчивается твёрдо, а инфинитив глагола «Брать» всегда мягкий."
        ),

        MinimalPairItem(
            id = 4,
            category = ContrastCategory.HARD_SOFT,
            contrastKey = "[Л] vs [Л']",
            wordA = MinimalPairWord(
                word = "Мел",
                stressMarked = "Мел",
                transcription = "[м'эл]",
                meaningRu = "Белый известняк для письма на школьной доске",
                phoneticRole = "Твёрдый [Л] в конце",
                articulationHint = "Твёрдое окончание, язык отталкивается от верхних зубов."
            ),
            wordB = MinimalPairWord(
                word = "Мель",
                stressMarked = "Мель",
                transcription = "[м'эл']",
                meaningRu = "Мелкое песчаное место в реке или море",
                phoneticRole = "Мягкий [Л'] в конце",
                articulationHint = "Мягкий знак растворяет окончание в нежный шелест."
            ),
            pedagogicalExplanation = "На мели корабль садится на дно, а мелом пишут слова на доске."
        ),

        MinimalPairItem(
            id = 5,
            category = ContrastCategory.HARD_SOFT,
            contrastKey = "[Н] vs [Н']",
            wordA = MinimalPairWord(
                word = "Банка",
                stressMarked = "Ба́нка",
                transcription = "[бáнка]",
                meaningRu = "Стеклянный или жестяной сосуд",
                phoneticRole = "Твёрдый [Н] в середине",
                articulationHint = "Язык плотно перекрывает носовой проход у верхних десен."
            ),
            wordB = MinimalPairWord(
                word = "Банька",
                stressMarked = "Ба́нька",
                transcription = "[бáн'ка]",
                meaningRu = "Тёплая деревянная русская баня с паром",
                phoneticRole = "Мягкий [Н'] в середине",
                articulationHint = "Спинка языка прижимается к твёрдому нёбу перед звуком [К]."
            ),
            pedagogicalExplanation = "В банке хранят варенье, а в баньке парятся с веником!"
        ),

        MinimalPairItem(
            id = 6,
            category = ContrastCategory.HARD_SOFT,
            contrastKey = "[Т] vs [Т']",
            wordA = MinimalPairWord(
                word = "Мат",
                stressMarked = "Мат",
                transcription = "[мат]",
                meaningRu = "Финальная победная позиция в шахматах или спортивный ковёр",
                phoneticRole = "Твёрдый [Т] в конце",
                articulationHint = "Сухой глухой удар языка."
            ),
            wordB = MinimalPairWord(
                word = "Мать",
                stressMarked = "Мать",
                transcription = "[мат']",
                meaningRu = "Женщина по отношению к своим детям, мама",
                phoneticRole = "Мягкий [Т'] в конце",
                articulationHint = "Нежное смягчённое окончание звука [Т'] с улыбкой губ."
            ),
            pedagogicalExplanation = "Мягкий знак в слове «Мать» выражает теплоту и родство."
        ),

        MinimalPairItem(
            id = 7,
            category = ContrastCategory.HARD_SOFT,
            contrastKey = "[В] vs [В']",
            wordA = MinimalPairWord(
                word = "Кров",
                stressMarked = "Кров",
                transcription = "[кроф]",
                meaningRu = "Уютный дом, жилище, защита от непогоды",
                phoneticRole = "Твёрдый согласный",
                articulationHint = "Нижняя губа касается верхних зубов без смягчения."
            ),
            wordB = MinimalPairWord(
                word = "Кровь",
                stressMarked = "Кровь",
                transcription = "[кроф']",
                meaningRu = "Красная жизненная жидкость в сосудах тела",
                phoneticRole = "Мягкий согласный [Ф']",
                articulationHint = "Мягкое прикосновение губы с подъёмом языка."
            ),
            pedagogicalExplanation = "Кров — это крыша над головой, а кровь — символ жизни."
        ),

        // ================= ЗВОНКОСТЬ — ГЛУХОСТЬ =================
        MinimalPairItem(
            id = 8,
            category = ContrastCategory.VOICED_VOICELESS,
            contrastKey = "[Д] vs [Т]",
            wordA = MinimalPairWord(
                word = "Дом",
                stressMarked = "Дом",
                transcription = "[дом]",
                meaningRu = "Здание, жилище, родной очаг",
                phoneticRole = "Звонкий [Д] с голосом",
                articulationHint = "Голосовые связки вибрируют с первой миллисекунды."
            ),
            wordB = MinimalPairWord(
                word = "Том",
                stressMarked = "Том",
                transcription = "[том]",
                meaningRu = "Отдельная книга большого собрания сочинений",
                phoneticRole = "Глухой [Т] без голоса",
                articulationHint = "Чистый шум воздуха без включения голосовых связок."
            ),
            pedagogicalExplanation = "Звонкое [Д] строит уютный дом, а глухое [Т] открывает книжный том."
        ),

        MinimalPairItem(
            id = 9,
            category = ContrastCategory.VOICED_VOICELESS,
            contrastKey = "[Б] vs [П]",
            wordA = MinimalPairWord(
                word = "Бочка",
                stressMarked = "Бо́чка",
                transcription = "[бóчка]",
                meaningRu = "Большая круглая деревянная емкость для воды или огурцов",
                phoneticRole = "Звонкий [Б] с голосом",
                articulationHint = "Мощный звонкий губной взрыв с вибрирующими связками."
            ),
            wordB = MinimalPairWord(
                word = "Почка",
                stressMarked = "По́чка",
                transcription = "[пóчка]",
                meaningRu = "Весенний зачаток листа на ветке дерева или орган тела",
                phoneticRole = "Глухой [П] без голоса",
                articulationHint = "Губной хлопок только воздухом, связки молчат."
            ),
            pedagogicalExplanation = "Звонкий взрыв превращает весеннюю почку в огромную бочку!"
        ),

        MinimalPairItem(
            id = 10,
            category = ContrastCategory.VOICED_VOICELESS,
            contrastKey = "[Ж] vs [Ш]",
            wordA = MinimalPairWord(
                word = "Жар",
                stressMarked = "Жар",
                transcription = "[жар]",
                meaningRu = "Сильное горячее тепло от огня или печи",
                phoneticRole = "Звонкий шипящий [Ж]",
                articulationHint = "Язык чашечкой у нёба, мощный гудящий голос."
            ),
            wordB = MinimalPairWord(
                word = "Шар",
                stressMarked = "Шар",
                transcription = "[шар]",
                meaningRu = "Геометрическое круглое тело, воздушный шарик",
                phoneticRole = "Глухой шипящий [Ш]",
                articulationHint = "Та же форма языка, но идёт только тихий шум ветра."
            ),
            pedagogicalExplanation = "Включили голос — получился горячий жар; выключили голос — летит круглый шар."
        ),

        MinimalPairItem(
            id = 11,
            category = ContrastCategory.VOICED_VOICELESS,
            contrastKey = "[Г] vs [К]",
            wordA = MinimalPairWord(
                word = "Гора",
                stressMarked = "Гора́",
                transcription = "[гарá]",
                meaningRu = "Высокая каменная возвышенность земли",
                phoneticRole = "Звонкий задненёбный [Г]",
                articulationHint = "Задняя часть спинки языка смыкается с нёбом при участии голоса."
            ),
            wordB = MinimalPairWord(
                word = "Кора",
                stressMarked = "Кора́",
                transcription = "[карá]",
                meaningRu = "Наружный защитный покров ствола дерева",
                phoneticRole = "Глухой задненёбный [К]",
                articulationHint = "Глухой короткий щелчок воздухом в глубине рта."
            ),
            pedagogicalExplanation = "На горе растут сосны, а у сосен шершавая кора."
        ),

        MinimalPairItem(
            id = 12,
            category = ContrastCategory.VOICED_VOICELESS,
            contrastKey = "[Д] vs [Т]",
            wordA = MinimalPairWord(
                word = "День",
                stressMarked = "День",
                transcription = "[д'эн']",
                meaningRu = "Светлое время суток от восхода до заката",
                phoneticRole = "Звонкий мягкий [Д']",
                articulationHint = "Мягкий звонкий звоночек голоса у нёба."
            ),
            wordB = MinimalPairWord(
                word = "Тень",
                stressMarked = "Тень",
                transcription = "[т'эн']",
                meaningRu = "Тёмное пространство от заслонённого солнца",
                phoneticRole = "Глухой мягкий [Т']",
                articulationHint = "Мягкий глухой шелест языка без вибрации связок."
            ),
            pedagogicalExplanation = "В ясный солнечный день человек отбрасывает прохладную тень."
        )
    )
}
