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
            type = LetterType.VOWEL,
            description = "Гласный звук. Рот широко открыт, воздух выходит свободно.",
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
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий согласный. Губы смыкаются и размыкаются с голосом.",
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
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий согласный. Верхние зубы слегка касаются нижней губы.",
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
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий согласный. Задняя часть языка поднимается к нёбу.",
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
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий согласный. Кончик языка упирается в верхние зубы.",
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
            type = LetterType.VOWEL,
            description = "Йотированная гласная. В начале слова звучит как [й'э]. Смягчает согласный.",
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
            type = LetterType.VOWEL,
            description = "Всегда ударная гласная в русских словах. Звучит как [й'о] или [о].",
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
            type = LetterType.CONSONANT_VOICED,
            description = "Всегда твёрдый звонкий шипящий согласный. Голос вибрирует.",
            examples = listOf(
                WordExample("Жук", "Большой блестящий жук", "🪲"),
                WordExample("Жираф", "Высокий пятнистый жираф", "🦒"),
                WordExample("Жизнь", "Прекрасная активная жизнь", "✨")
            )
        ),
        LetterItem(
            letter = "З",
            lowerLetter = "з",
            sound = "[з] / [з']",
            type = LetterType.CONSONANT_VOICED,
            description = "Звонкий свистящий звук, похож на жужжание пчелы.",
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
            type = LetterType.VOWEL,
            description = "Гласный звук. Губы растянуты в лёгкую улыбку.",
            examples = listOf(
                WordExample("Игла", "Тонкая швейная игла", "🪡"),
                WordExample("Игрушка", "Любимая мягкая игрушка", "🧸"),
                WordExample("Ирис", "Красивый синий ирис", "🌸")
            )
        ),
        LetterItem(
            letter = "Й",
            lowerLetter = "й",
            sound = "[й']",
            type = LetterType.CONSONANT_VOICED,
            description = "Всегда мягкий звонкий согласный («И краткое»). Быстрый и чёткий звук.",
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
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный. Произносится без участия голоса.",
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
            type = LetterType.CONSONANT_VOICED,
            description = "Сонорный согласный. Кончик языка прижимается к верхним зубам.",
            examples = listOf(
                WordExample("Лиса", "Хитрая рыжая лиса", "🦊"),
                WordExample("Луна", "Круглая белая луна", "🌕"),
                WordExample("Лимон", "Кислый жёлтый лимон", "🍋")
            )
        ),
        LetterItem(
            letter = "М",
            lowerLetter = "м",
            sound = "[м] / [м']",
            type = LetterType.CONSONANT_VOICED,
            description = "Сонорный губной согласный. Губы плотно сомкнуты, воздух идёт через нос.",
            examples = listOf(
                WordExample("Мама", "Самая добрая мама", "❤️"),
                WordExample("Медведь", "Бурый сильный медведь", "🐻"),
                WordExample("Мост", "Длинный мост через реку", "🌉")
            )
        ),
        LetterItem(
            letter = "Н",
            lowerLetter = "н",
            sound = "[н] / [н']",
            type = LetterType.CONSONANT_VOICED,
            description = "Сонорный носовой согласный. Кончик языка у верхних дёсен.",
            examples = listOf(
                WordExample("Нос", "Курносый милый нос", "👃"),
                WordExample("Небо", "Синее ясное небо", "☁️"),
                WordExample("Ночь", "Тихая звёздная ночь", "🌌")
            )
        ),
        LetterItem(
            letter = "О",
            lowerLetter = "о",
            sound = "[о]",
            type = LetterType.VOWEL,
            description = "Гласный звук. Губы округлены и слегка вытянуты вперёд под ударением.",
            examples = listOf(
                WordExample("Окно", "Светлое широкое окно", "🪟"),
                WordExample("Озеро", "Глубокое чистое озеро", "🏞️"),
                WordExample("Облако", "Белое лёгкое облако", "⛅")
            )
        ),
        LetterItem(
            letter = "П",
            lowerLetter = "п",
            sound = "[п] / [п']",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный. Губы смыкаются и резко размыкаются воздухом.",
            examples = listOf(
                WordExample("Попугай", "Умный говорящий попугай", "🦜"),
                WordExample("Папа", "Сильный и заботливый папа", "👨"),
                WordExample("Птица", "Быстрая певчая птица", "🐦")
            )
        ),
        LetterItem(
            letter = "Р",
            lowerLetter = "р",
            sound = "[р] / [р']",
            type = LetterType.CONSONANT_VOICED,
            description = "Вибрирующий согласный. Кончик языка дрожит у альвеол.",
            examples = listOf(
                WordExample("Река", "Широкая быстрая река", "🌊"),
                WordExample("Рыба", "Золотая рыбка в воде", "🐠"),
                WordExample("Рука", "Дружеская крепкая рука", "🤝")
            )
        ),
        LetterItem(
            letter = "С",
            lowerLetter = "с",
            sound = "[с] / [с']",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой свистящий согласный, похож на свист ветра.",
            examples = listOf(
                WordExample("Солнце", "Яркое тёплое солнце", "☀️"),
                WordExample("Собака", "Верная добрая собака", "🐕"),
                WordExample("Снег", "Белый пушистый снег", "❄️")
            )
        ),
        LetterItem(
            letter = "Т",
            lowerLetter = "т",
            sound = "[т] / [т']",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный. Кончик языка смыкается с верхними зубами.",
            examples = listOf(
                WordExample("Тигр", "Полосатый мощный тигр", "🐯"),
                WordExample("Трава", "Сочная зелёная трава", "🌱"),
                WordExample("Торт", "Сладкий праздничный торт", "🎂")
            )
        ),
        LetterItem(
            letter = "У",
            lowerLetter = "у",
            sound = "[у]",
            type = LetterType.VOWEL,
            description = "Гласный звук. Губы сильно вытянуты трубочкой вперёд.",
            examples = listOf(
                WordExample("Утка", "Жёлтая весёлая утка", "🦆"),
                WordExample("Улитка", "Маленькая медленная улитка", "🐌"),
                WordExample("Утро", "Доброе солнечное утро", "🌅")
            )
        ),
        LetterItem(
            letter = "Ф",
            lowerLetter = "ф",
            sound = "[ф] / [ф']",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный, парный к звонкому [в].",
            examples = listOf(
                WordExample("Фонарь", "Яркий уличный фонарь", "🏮"),
                WordExample("Флаг", "Развевающийся красивый флаг", "🚩"),
                WordExample("Фрукт", "Сладкий витаминный фрукт", "🍎")
            )
        ),
        LetterItem(
            letter = "Х",
            lowerLetter = "х",
            sound = "[х] / [х']",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Глухой согласный звук. Воздух с трением проходит через заднюю часть нёба.",
            examples = listOf(
                WordExample("Хлеб", "Свежий душистый хлеб", "🍞"),
                WordExample("Хомяк", "Пушистый забавный хомяк", "🐹"),
                WordExample("Холод", "Морозный зимний холод", "🧊")
            )
        ),
        LetterItem(
            letter = "Ц",
            lowerLetter = "ц",
            sound = "[ц]",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Всегда твёрдый глухой звук. Сочетание слитных [т] и [с].",
            examples = listOf(
                WordExample("Цветок", "Нежный полевой цветок", "🌷"),
                WordExample("Царь", "Мудрый справедливый царь", "👑"),
                WordExample("Цыплёнок", "Маленький жёлтый цыплёнок", "🐥")
            )
        ),
        LetterItem(
            letter = "Ч",
            lowerLetter = "ч",
            sound = "[ч']",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Всегда мягкий глухой шипящий согласный звук.",
            examples = listOf(
                WordExample("Чай", "Горячий душистый чай", "🫖"),
                WordExample("Часы", "Точные настенные часы", "⏰"),
                WordExample("Человек", "Добрый и умный человек", "🧑")
            )
        ),
        LetterItem(
            letter = "Ш",
            lowerLetter = "ш",
            sound = "[ш]",
            type = LetterType.CONSONANT_VOICELESS,
            description = "Всегда твёрдый глухой шипящий звук, как шелест листьев.",
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
            type = LetterType.CONSONANT_VOICELESS,
            description = "Всегда мягкий долгий шипящий согласный звук.",
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
            type = LetterType.SIGN,
            description = "Твёрдый разделительный знак. Не имеет собственного звука.",
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
            type = LetterType.VOWEL,
            description = "Гласный звук среднего ряда. Язык отодвинут назад.",
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
            type = LetterType.SIGN,
            description = "Мягкий знак. Смягчает предшествующий согласный звук.",
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
            type = LetterType.VOWEL,
            description = "Гласный звук. Произносится твёрдо, без предшествующего [й].",
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
            type = LetterType.VOWEL,
            description = "Йотированная гласная. В начале слова звучит как [й'у].",
            examples = listOf(
                WordExample("Юла", "Крутящаяся яркая юла", "🪀"),
                WordExample("Юг", "Тёплый солнечный юг", "🌴"),
                WordExample("Юрист", "Опытный грамотный юрист", "⚖️")
            )
        ),
        LetterItem(
            letter = "Я",
            lowerLetter = "я",
            sound = "[й'а] / [а]",
            type = LetterType.VOWEL,
            description = "Йотированная гласная. В начале слова звучит как [й'а].",
            examples = listOf(
                WordExample("Яблоко", "Красное сладкое яблоко", "🍎"),
                WordExample("Ягода", "Спелая лесная ягода", "🍓"),
                WordExample("Якорь", "Тяжёлый морской якорь", "⚓")
            )
        )
    )
}
