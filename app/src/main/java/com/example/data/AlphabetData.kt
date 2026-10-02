package com.example.data

import com.example.data.model.LetterItem
import com.example.data.model.LetterType
import com.example.data.model.WordExample

object AlphabetData {
    val letters: List<LetterItem> = listOf(
        LetterItem(
            letter = "А",
            lowerLetter = "а",
            sound = "[а]",
            soundClean = "[а]",
            letterName = "А",
            ttsFast = "А́",
            type = LetterType.VOWEL,
            description = "Гласный звук. Рот широко открыт, воздух выходит свободно.",
            audioAssetPath = "audio/letters/letter_a.opus",
            examples = listOf(
                WordExample("Арбуз", "Сладкий сочный арбуз", "🍉"),
                WordExample("Аист", "Белый аист в небе", "🪶"),
                WordExample("Автобус", "Большой синий автобус", "🚌")
            )
        ),
        LetterItem(
            letter = "Б",
            lowerLetter = "б",
            sound = "[б] / [б']",
            soundClean = "[б]",
            letterName = "Бэ",
            ttsFast = "Бэ",
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий согласный. Губы смыкаются и размыкаются с голосом.",
            audioAssetPath = "audio/letters/letter_b.opus",
            examples = listOf(
                WordExample("Банан", "Спелый жёлтый банан", "🍌"),
                WordExample("Бабочка", "Красивая бабочка на цветке", "🦋"),
                WordExample("Белка", "Быстрая белка в лесу", "🐿️")
            )
        ),
        LetterItem(
            letter = "В",
            lowerLetter = "в",
            sound = "[в] / [в']",
            soundClean = "[в]",
            letterName = "Вэ",
            ttsFast = "Вэ",
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий согласный. Верхние зубы слегка касаются нижней губы.",
            audioAssetPath = "audio/letters/letter_v.opus",
            examples = listOf(
                WordExample("Вода", "Чистая холодная вода", "💧"),
                WordExample("Волк", "Серый волк в чаще", "🐺"),
                WordExample("Ветер", "Свежий утренний ветер", "💨")
            )
        ),
        LetterItem(
            letter = "Г",
            lowerLetter = "г",
            sound = "[г] / [г']",
            soundClean = "[г]",
            letterName = "Гэ",
            ttsFast = "Гэ",
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий согласный. Задняя часть языка поднимается к нёбу.",
            audioAssetPath = "audio/letters/letter_g.opus",
            examples = listOf(
                WordExample("Гриб", "Белый гриб под ёлкой", "🍄"),
                WordExample("Гора", "Высокая снежная гора", "⛰️"),
                WordExample("Голубь", "Мирный голубь в парке", "🕊️")
            )
        ),
        LetterItem(
            letter = "Д",
            lowerLetter = "д",
            sound = "[д] / [д']",
            soundClean = "[д]",
            letterName = "Дэ",
            ttsFast = "Дэ",
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий согласный. Кончик языка упирается в верхние зубы.",
            audioAssetPath = "audio/letters/letter_d.opus",
            examples = listOf(
                WordExample("Дом", "Уютный тёплый дом", "🏡"),
                WordExample("Дерево", "Зелёное высокое дерево", "🌳"),
                WordExample("Дождь", "Тёплый летний дождь", "🌧️")
            )
        ),
        LetterItem(
            letter = "Е",
            lowerLetter = "е",
            sound = "[й'э] / [э]",
            soundClean = "[й'э]",
            letterName = "Е",
            ttsFast = "Е́",
            type = LetterType.VOWEL,
            description = "Йотированная гласная. В начале слова звучит как [й'э]. Смягчает согласный.",
            audioAssetPath = "audio/letters/letter_ye.opus",
            examples = listOf(
                WordExample("Енот", "Полосатый забавный енот", "🦝"),
                WordExample("Ель", "Пушистая зелёная ель", "🌲"),
                WordExample("Еда", "Вкусная домашняя еда", "🍲")
            )
        ),
        LetterItem(
            letter = "Ё",
            lowerLetter = "ё",
            sound = "[й'о] / [о]",
            soundClean = "[й'о]",
            letterName = "Ё",
            ttsFast = "Ё",
            type = LetterType.VOWEL,
            description = "Всегда ударная гласная в русских словах. Звучит как [й'о] или [о].",
            audioAssetPath = "audio/letters/letter_yo.opus",
            examples = listOf(
                WordExample("Ёж", "Колючий маленький ёж", "🦔"),
                WordExample("Ёлка", "Нарядная новогодняя ёлка", "🎄"),
                WordExample("Ёрш", "Резвая речная рыбка ёрш", "🐟")
            )
        ),
        LetterItem(
            letter = "Ж",
            lowerLetter = "ж",
            sound = "[ж]",
            soundClean = "[ж]",
            letterName = "Жэ",
            ttsFast = "Жэ",
            type = LetterType.CONSONANT_VOICED,
            description = "Всегда твёрдый звонкий шипящий согласный. Голос вибрирует.",
            audioAssetPath = "audio/letters/letter_zh.opus",
            examples = listOf(
                WordExample("Жук", "Большой блестящий жук", "🪲"),
                WordExample("Жираф", "Высокий пятнистый жираф", "🦒"),
                WordExample("Жёлудь", "Гладкий дубовый жёлудь", "🌰")
            )
        ),
        LetterItem(
            letter = "З",
            lowerLetter = "з",
            sound = "[з] / [з']",
            soundClean = "[з]",
            letterName = "Зэ",
            ttsFast = "Зэ",
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий свистящий звук, похож на жужжание пчелы.",
            audioAssetPath = "audio/letters/letter_z.opus",
            examples = listOf(
                WordExample("Заяц", "Быстрый пушистый заяц", "🐇"),
                WordExample("Звезда", "Яркая ночная звезда", "⭐"),
                WordExample("Зонт", "Большой зонт от дождя", "☂️")
            )
        ),
        LetterItem(
            letter = "И",
            lowerLetter = "и",
            sound = "[и]",
            soundClean = "[и]",
            letterName = "И",
            ttsFast = "И́",
            type = LetterType.VOWEL,
            description = "Гласный звук. Губы растянуты в лёгкую улыбку.",
            audioAssetPath = "audio/letters/letter_i.opus",
            examples = listOf(
                WordExample("Игла", "Тонкая швейная игла", "🪡"),
                WordExample("Игрушка", "Любимая мягкая игрушка", "🧸"),
                WordExample("Ирис", "Красивый синий и́рис", "🌸")
            )
        ),
        LetterItem(
            letter = "Й",
            lowerLetter = "й",
            sound = "[й']",
            soundClean = "[й']",
            letterName = "Й",
            ttsFast = "Йот",
            type = LetterType.CONSONANT_VOICED,
            description = "Всегда мягкий звонкий согласный (звук [й], йот). Короткий и энергичный.",
            audioAssetPath = "audio/letters/letter_j.opus",
            examples = listOf(
                WordExample("Йогурт", "Клубничный свежий йогурт", "🥣"),
                WordExample("Йод", "Антисептический жёлтый йод", "🩹"),
                WordExample("Май", "Солнечный весенний май", "🌿")
            )
        ),
        LetterItem(
            letter = "К",
            lowerLetter = "к",
            sound = "[к] / [к']",
            soundClean = "[к]",
            letterName = "Ка",
            ttsFast = "Ка",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный. Произносится без участия голоса.",
            audioAssetPath = "audio/letters/letter_k.opus",
            examples = listOf(
                WordExample("Кот", "Рыжий пушистый кот", "🐈"),
                WordExample("Книга", "Интересная новая книга", "📖"),
                WordExample("Кофе", "Ароматный горячий кофе", "☕")
            )
        ),
        LetterItem(
            letter = "Л",
            lowerLetter = "л",
            sound = "[л] / [л']",
            soundClean = "[л]",
            letterName = "Эль",
            ttsFast = "Эль",
            type = LetterType.CONSONANT_VOICED,
            description = "Сонорный согласный. Кончик языка прижимается к верхним зубам.",
            audioAssetPath = "audio/letters/letter_l.opus",
            examples = listOf(
                WordExample("Лиса", "Хитрая рыжая лиса", "🦊"),
                WordExample("Луна", "Круглая яркая луна", "🌙"),
                WordExample("Лимон", "Кислый жёлтый лимон", "🍋")
            )
        ),
        LetterItem(
            letter = "М",
            lowerLetter = "м",
            sound = "[м] / [м']",
            soundClean = "[м]",
            letterName = "Эм",
            ttsFast = "Эм",
            type = LetterType.CONSONANT_VOICED,
            description = "Сонорный носовой согласный. Губы плотно сомкнуты, воздух идёт через нос.",
            audioAssetPath = "audio/letters/letter_m.opus",
            examples = listOf(
                WordExample("Мама", "Добрая любимая мама", "👩"),
                WordExample("Медведь", "Бурый лесной медведь", "🐻"),
                WordExample("Мяч", "Разноцветный круглый мяч", "⚽")
            )
        ),
        LetterItem(
            letter = "Н",
            lowerLetter = "н",
            sound = "[н] / [н']",
            soundClean = "[н]",
            letterName = "Эн",
            ttsFast = "Эн",
            type = LetterType.CONSONANT_VOICED,
            description = "Сонорный носовой звук. Язык прижат к верхним деснам.",
            audioAssetPath = "audio/letters/letter_n.opus",
            examples = listOf(
                WordExample("Нос", "Любопытный маленький нос", "👃"),
                WordExample("Небо", "Синее ясное небо", "☁️"),
                WordExample("Ножницы", "Острые портновские ножницы", "✂️")
            )
        ),
        LetterItem(
            letter = "О",
            lowerLetter = "о",
            sound = "[о]",
            soundClean = "[о]",
            letterName = "О",
            ttsFast = "О́",
            type = LetterType.VOWEL,
            description = "Гласный звук. Губы округлены в форме колечка. Под ударением звучит как чистый [о].",
            audioAssetPath = "audio/letters/letter_o.opus",
            examples = listOf(
                WordExample("Окно", "Большое светлое окно", "🪟"),
                WordExample("Облако", "Белое пушистое облако", "☁️"),
                WordExample("Остров", "Зелёный тёплый остров", "🏝️")
            )
        ),
        LetterItem(
            letter = "П",
            lowerLetter = "п",
            sound = "[п] / [п']",
            soundClean = "[п]",
            letterName = "Пэ",
            ttsFast = "Пэ",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный. Губы смыкаются и резко размыкаются воздухом.",
            audioAssetPath = "audio/letters/letter_p.opus",
            examples = listOf(
                WordExample("Поезд", "Скорый пассажирский поезд", "🚆"),
                WordExample("Птица", "Поющая лесная птица", "🐦"),
                WordExample("Пальма", "Высокая тропическая пальма", "🌴")
            )
        ),
        LetterItem(
            letter = "Р",
            lowerLetter = "р",
            sound = "[р] / [р']",
            soundClean = "[р]",
            letterName = "Эр",
            ttsFast = "Эр",
            type = LetterType.CONSONANT_VOICED,
            description = "Дрожащий сонорный звук. Кончик языка вибрирует у альвеол.",
            audioAssetPath = "audio/letters/letter_r.opus",
            examples = listOf(
                WordExample("Рыба", "Золотая быстрая рыба", "🐟"),
                WordExample("Радуга", "Семицветная яркая радуга", "🌈"),
                WordExample("Роза", "Ароматная красная роза", "🌹")
            )
        ),
        LetterItem(
            letter = "С",
            lowerLetter = "с",
            sound = "[с] / [с']",
            soundClean = "[с]",
            letterName = "Эс",
            ttsFast = "Эс",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой свистящий согласный, напоминает свист ветра.",
            audioAssetPath = "audio/letters/letter_s.opus",
            examples = listOf(
                WordExample("Солнце", "Жаркое летнее солнце", "☀️"),
                WordExample("Слон", "Добрый индийский слон", "🐘"),
                WordExample("Снег", "Белый пушистый снег", "❄️")
            )
        ),
        LetterItem(
            letter = "Т",
            lowerLetter = "т",
            sound = "[т] / [т']",
            soundClean = "[т]",
            letterName = "Тэ",
            ttsFast = "Тэ",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный. Кончик языка ударяет о верхние зубы.",
            audioAssetPath = "audio/letters/letter_t.opus",
            examples = listOf(
                WordExample("Тигр", "Полосатый уссурийский тигр", "🐅"),
                WordExample("Трава", "Сочная зелёная трава", "🌱"),
                WordExample("Торт", "Праздничный сладкий торт", "🎂")
            )
        ),
        LetterItem(
            letter = "У",
            lowerLetter = "у",
            sound = "[у]",
            soundClean = "[у]",
            letterName = "У",
            ttsFast = "У́",
            type = LetterType.VOWEL,
            description = "Гласный звук. Губы вытянуты плотной трубочкой вперёд.",
            audioAssetPath = "audio/letters/letter_u.opus",
            examples = listOf(
                WordExample("Утка", "Пёстрая дикая утка", "🦆"),
                WordExample("Улитка", "Медленная лесная улитка", "🐌"),
                WordExample("Удочка", "Длинная рыболовная удочка", "🎣")
            )
        ),
        LetterItem(
            letter = "Ф",
            lowerLetter = "ф",
            sound = "[ф] / [ф']",
            soundClean = "[ф]",
            letterName = "Эф",
            ttsFast = "Эф",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный. Верхние зубы прижимаются к нижней губе.",
            audioAssetPath = "audio/letters/letter_f.opus",
            examples = listOf(
                WordExample("Флаг", "Развевающийся на ветру флаг", "🚩"),
                WordExample("Фонарь", "Яркий уличный фонарь", "🏮"),
                WordExample("Фрукт", "Свежий сладкий фрукт", "🍎")
            )
        ),
        LetterItem(
            letter = "Х",
            lowerLetter = "х",
            sound = "[х] / [х']",
            soundClean = "[х]",
            letterName = "Ха",
            ttsFast = "Ха",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный, похож на тёплый выдох на ладони.",
            audioAssetPath = "audio/letters/letter_kh.opus",
            examples = listOf(
                WordExample("Хлеб", "Свежий ароматный хлеб", "🍞"),
                WordExample("Хомяк", "Забавный щёкастый хомяк", "🐹"),
                WordExample("Холод", "Морозный зимний холод", "🥶")
            )
        ),
        LetterItem(
            letter = "Ц",
            lowerLetter = "ц",
            sound = "[ц]",
            soundClean = "[ц]",
            letterName = "Цэ",
            ttsFast = "Цэ",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Всегда твёрдый глухой звук. Слитное сочетание [т] и [с].",
            audioAssetPath = "audio/letters/letter_ts.opus",
            examples = listOf(
                WordExample("Цветок", "Нежный полевой цветок", "🌼"),
                WordExample("Цыплёнок", "Маленький жёлтый цыплёнок", "🐥"),
                WordExample("Царь", "Мудрый сказочный царь", "👑")
            )
        ),
        LetterItem(
            letter = "Ч",
            lowerLetter = "ч",
            sound = "[ч']",
            soundClean = "[ч']",
            letterName = "Че",
            ttsFast = "Че",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Всегда мягкий глухой звук. Слитное сочетание [т'] и [ш'].",
            audioAssetPath = "audio/letters/letter_ch.opus",
            examples = listOf(
                WordExample("Часы", "Настенные тикающие часы", "⏰"),
                WordExample("Чай", "Горячий травяной чай", "☕"),
                WordExample("Черепаха", "Большая морская черепаха", "🐢")
            )
        ),
        LetterItem(
            letter = "Ш",
            lowerLetter = "ш",
            sound = "[ш]",
            soundClean = "[ш]",
            letterName = "Ша",
            ttsFast = "Ша",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Всегда твёрдый глухой шипящий звук, как шелест листьев.",
            audioAssetPath = "audio/letters/letter_sh.opus",
            examples = listOf(
                WordExample("Шар", "Красный воздушный шар", "🎈"),
                WordExample("Школа", "Любимая светлая школа", "🏫"),
                WordExample("Шапка", "Тёплая вязаная шапка", "🧢")
            )
        ),
        LetterItem(
            letter = "Щ",
            lowerLetter = "щ",
            sound = "[щ']",
            soundClean = "[щ']",
            letterName = "Ща",
            ttsFast = "Ща",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Всегда мягкий долгий шипящий согласный звук.",
            audioAssetPath = "audio/letters/letter_shch.opus",
            examples = listOf(
                WordExample("Щука", "Хищная речная щука", "🐟"),
                WordExample("Щенок", "Игривый маленький щенок", "🐶"),
                WordExample("Щётка", "Мягкая зубная щётка", "🪥")
            )
        ),
        LetterItem(
            letter = "Ъ",
            lowerLetter = "ъ",
            sound = "—",
            soundClean = "разделительный",
            letterName = "Твёрдый знак",
            ttsFast = "Твёрдый знак",
            isSign = true,
            type = LetterType.SIGN,
            description = "Твёрдый разделительный знак. Не имеет звука, разделяет приставку и корень.",
            audioAssetPath = "audio/letters/letter_hard.opus",
            examples = listOf(
                WordExample("Подъём", "Крутой подъём на гору", "🧗"),
                WordExample("Объявление", "Важное объявление на стене", "📢"),
                WordExample("Съезд", "Удобный съезд с дороги", "🛣️")
            )
        ),
        LetterItem(
            letter = "Ы",
            lowerLetter = "ы",
            sound = "[ы]",
            soundClean = "[ы]",
            letterName = "Ы",
            ttsFast = "Ы́",
            type = LetterType.VOWEL,
            description = "Гласный звук среднего ряда. Язык отодвинут назад.",
            audioAssetPath = "audio/letters/letter_y.opus",
            examples = listOf(
                WordExample("Сыр", "Вкусный жёлтый сыр", "🧀"),
                WordExample("Рыба", "Большая речная рыба", "🐟"),
                WordExample("Мыло", "Ароматное душистое мыло", "🧼")
            )
        ),
        LetterItem(
            letter = "Ь",
            lowerLetter = "ь",
            sound = "—",
            soundClean = "смягчающий",
            letterName = "Мягкий знак",
            ttsFast = "Мягкий знак",
            isSign = true,
            type = LetterType.SIGN,
            description = "Мягкий знак. Не имеет звука, смягчает предшествующий согласный.",
            audioAssetPath = "audio/letters/letter_soft.opus",
            examples = listOf(
                WordExample("Конь", "Быстрый вороной конь", "🐎"),
                WordExample("Соль", "Белая морская соль", "🧂"),
                WordExample("День", "Прекрасный солнечный день", "☀️")
            )
        ),
        LetterItem(
            letter = "Э",
            lowerLetter = "э",
            sound = "[э]",
            soundClean = "[э]",
            letterName = "Э",
            ttsFast = "Э́",
            type = LetterType.VOWEL,
            description = "Гласный звук. Произносится твёрдо, без предшествующего [й].",
            audioAssetPath = "audio/letters/letter_e.opus",
            examples = listOf(
                WordExample("Эхо", "Громкое лесное эхо", "🗣️"),
                WordExample("Экран", "Яркий сенсорный экран", "📱"),
                WordExample("Экзамен", "Успешно сданный экзамен", "📝")
            )
        ),
        LetterItem(
            letter = "Ю",
            lowerLetter = "ю",
            sound = "[й'у] / [у]",
            soundClean = "[й'у]",
            letterName = "Ю",
            ttsFast = "Ю́",
            type = LetterType.VOWEL,
            description = "Йотированная гласная. В начале слова звучит как [й'у].",
            audioAssetPath = "audio/letters/letter_yu.opus",
            examples = listOf(
                WordExample("Юла", "Быстро крутящаяся юла", "🪀"),
                WordExample("Юг", "Тёплый солнечный юг", "🌴"),
                WordExample("Юбка", "Красивая пышная юбка", "👗")
            )
        ),
        LetterItem(
            letter = "Я",
            lowerLetter = "я",
            sound = "[й'а] / [а]",
            soundClean = "[й'а]",
            letterName = "Я",
            ttsFast = "Я́",
            type = LetterType.VOWEL,
            description = "Йотированная гласная. В начале слова звучит как [й'а].",
            audioAssetPath = "audio/letters/letter_ya.opus",
            examples = listOf(
                WordExample("Яблоко", "Сладкое спелое яблоко", "🍎"),
                WordExample("Яхта", "Белая парусная яхта", "⛵"),
                WordExample("Якорь", "Тяжёлый морской якорь", "⚓")
            )
        )
    )
}
