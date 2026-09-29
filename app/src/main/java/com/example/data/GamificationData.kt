package com.example.data

import com.example.data.model.BadgeItem
import com.example.data.model.LeaderboardUser

object GamificationData {
    val initialBadges: List<BadgeItem> = listOf(
        BadgeItem(
            id = "badge_first_sound",
            title = "Первый звук",
            description = "Произнесите свой первый русский звук вместе с попугаем Кешей.",
            iconEmoji = "🐣",
            isUnlocked = true,
            unlockedDate = "Сегодня",
            requiredProgress = "1 звук"
        ),
        BadgeItem(
            id = "badge_alphabet",
            title = "Знаток Азбуки",
            description = "Прослушайте и изучите русские буквы от А до Я.",
            iconEmoji = "🔤",
            isUnlocked = false,
            requiredProgress = "33 буквы"
        ),
        BadgeItem(
            id = "badge_parrot_mimic",
            title = "Эхо Кеши",
            description = "Успешно повторите 10 слов подряд без пауз.",
            iconEmoji = "🦜",
            isUnlocked = false,
            requiredProgress = "10 повторений"
        ),
        BadgeItem(
            id = "badge_vocab_master",
            title = "Словарный запас",
            description = "Освойте более 20 слов во встроенном словаре с примерами.",
            iconEmoji = "📚",
            isUnlocked = false,
            requiredProgress = "20 слов"
        ),
        BadgeItem(
            id = "badge_streak_3",
            title = "Огненный темп",
            description = "Занимайтесь русским языком каждый день без перерывов.",
            iconEmoji = "🔥",
            isUnlocked = true,
            unlockedDate = "Вчера",
            requiredProgress = "3 дня подряд"
        ),
        BadgeItem(
            id = "badge_phrases",
            title = "Живой диалог",
            description = "Освойте разговорные фразы 4-го уровня сложности.",
            iconEmoji = "💬",
            isUnlocked = false,
            requiredProgress = "Уровень 4"
        ),
        BadgeItem(
            id = "badge_xp_500",
            title = "Золотой говорун",
            description = "Наберите более 500 очков опыта в упражнениях.",
            iconEmoji = "🏆",
            isUnlocked = false,
            requiredProgress = "500 XP"
        )
    )

    fun getLeaderboard(userXp: Int): List<LeaderboardUser> {
        val simulatedRivals = listOf(
            LeaderboardUser(1, "Анна Соколова", "🦊", 1420, 12, false, "Читает Пушкина"),
            LeaderboardUser(2, "Марк Лебедев", "🐻", 1190, 8, false, "Учит глаголы"),
            LeaderboardUser(3, "Кеша Пернатый", "🦜", 850, 15, false, "Повторяет всё"),
            LeaderboardUser(4, "София Морозова", "🦉", 640, 5, false, "Словарный запас"),
            LeaderboardUser(5, "Лукас Вагнер", "🦁", 490, 4, false, "Слушает произношение"),
            LeaderboardUser(6, "Елена Орлова", "🐬", 320, 2, false, "Изучает азбуку"),
            LeaderboardUser(7, "Дэвид Браун", "🦝", 180, 1, false, "Первые слоги")
        )

        val currentUser = LeaderboardUser(
            rank = 0,
            name = "Вы (Ученик)",
            avatarEmoji = "⭐",
            xp = userXp,
            streak = 3,
            isCurrentUser = true,
            statusText = "Метод попугая"
        )

        val all = (simulatedRivals + currentUser).sortedByDescending { it.xp }
        return all.mapIndexed { index, user ->
            user.copy(rank = index + 1)
        }
    }
}
