package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class LetterType(val titleRu: String) {
    VOWEL("Гласная"),
    CONSONANT_VOICED("Согласная (звонкая)"),
    CONSONANT_VOICELESS("Согласная (глухая)"),
    SIGN("Знак")
}

data class WordExample(
    val word: String,
    val context: String,
    val emoji: String
)

data class LetterItem(
    val letter: String,
    val lowerLetter: String,
    val sound: String,
    val type: LetterType,
    val description: String,
    val examples: List<WordExample>
)

@Entity(tableName = "dictionary_words")
data class DictionaryWord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    val stressMarked: String,
    val partOfSpeech: String,
    val category: String,
    val definitionRu: String,
    val exampleSentence: String,
    val isFavorite: Boolean = false,
    val isMastered: Boolean = false,
    val repetitionCount: Int = 0
)

data class LessonItem(
    val id: Int,
    val level: Int,
    val title: String,
    val targetText: String,
    val stimulusCommand: String = "Повтори: $targetText",
    val phoneticTip: String,
    val contextDescription: String,
    val isCompleted: Boolean = false
)

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val id: Int = 1,
    val xp: Int = 40,
    val levelName: String = "Любознательный птенец",
    val currentStreak: Int = 1,
    val lastActiveDate: String = "",
    val wordsLearnedCount: Int = 0,
    val lessonsCompletedCount: Int = 0,
    val minutesPracticedToday: Int = 5,
    val todayDate: String = "",
    val speechRate: Float = 0.9f,
    val speechPitch: Float = 1.0f,
    val selectedVoice: String = "",
    val themeMode: String = "SYSTEM"
)

@Entity(tableName = "study_plan")
data class StudyPlan(
    @PrimaryKey val id: Int = 1,
    val dailyMinutesGoal: Int = 15,
    val dailyWordsGoal: Int = 10,
    val focusArea: String = "Все разделы",
    val reminderHour: Int = 19,
    val reminderMinute: Int = 0,
    val isReminderEnabled: Boolean = true
)

@Entity(tableName = "badges")
data class BadgeItem(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean = false,
    val unlockedDate: String? = null,
    val requiredProgress: String = ""
)

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val avatarEmoji: String,
    val xp: Int,
    val streak: Int,
    val isCurrentUser: Boolean = false,
    val statusText: String = "Учит русский язык"
)
