package com.example.data.repository

import com.example.data.DictionaryData
import com.example.data.GamificationData
import com.example.data.db.AppDatabase
import com.example.data.model.BadgeItem
import com.example.data.model.DictionaryWord
import com.example.data.model.StudyPlan
import com.example.data.model.UserProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RussianLearningRepository(private val db: AppDatabase) {

    val allWords: Flow<List<DictionaryWord>> = db.dictionaryDao().getAllWords()
    val favoriteWords: Flow<List<DictionaryWord>> = db.dictionaryDao().getFavoriteWords()
    val userProgress: Flow<UserProgress?> = db.userProgressDao().getUserProgress()
    val studyPlan: Flow<StudyPlan?> = db.studyPlanDao().getStudyPlan()
    val allBadges: Flow<List<BadgeItem>> = db.badgeDao().getAllBadges()

    suspend fun initializeDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        if (db.dictionaryDao().getWordCount() == 0) {
            db.dictionaryDao().insertWords(DictionaryData.initialWords)
        }
        if (db.badgeDao().getBadgeCount() == 0) {
            db.badgeDao().insertBadges(GamificationData.initialBadges)
        }
        // Initialize default study plan if needed
        db.studyPlanDao().saveStudyPlan(
            StudyPlan(
                id = 1,
                dailyMinutesGoal = 15,
                dailyWordsGoal = 10,
                focusArea = "Все разделы",
                reminderHour = 19,
                reminderMinute = 0,
                isReminderEnabled = true
            )
        )
        // Initialize default user progress if needed
        val todayStr = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        db.userProgressDao().insertOrUpdate(
            UserProgress(
                id = 1,
                xp = 60,
                levelName = "Любознательный птенец",
                currentStreak = 3,
                lastActiveDate = todayStr,
                wordsLearnedCount = 5,
                lessonsCompletedCount = 3,
                minutesPracticedToday = 6,
                todayDate = todayStr,
                speechRate = 0.9f,
                speechPitch = 1.0f,
                selectedVoice = "",
                themeMode = "SYSTEM"
            )
        )
    }

    suspend fun updateWord(word: DictionaryWord) = withContext(Dispatchers.IO) {
        db.dictionaryDao().updateWord(word)
    }

    suspend fun toggleFavorite(word: DictionaryWord) = withContext(Dispatchers.IO) {
        db.dictionaryDao().updateWord(word.copy(isFavorite = !word.isFavorite))
    }

    suspend fun markWordMastered(word: DictionaryWord, mastered: Boolean) = withContext(Dispatchers.IO) {
        val updated = word.copy(
            isMastered = mastered,
            repetitionCount = if (mastered) word.repetitionCount + 1 else word.repetitionCount
        )
        db.dictionaryDao().updateWord(updated)
        if (mastered) {
            addXp(points = 15, wordsLearnedDelta = 1)
        }
    }

    suspend fun addXp(points: Int, wordsLearnedDelta: Int = 0) = withContext(Dispatchers.IO) {
        db.userProgressDao().addXpAndWords(points, wordsLearnedDelta)
        // Check for badge unlocks
        val todayStr = SimpleDateFormat("dd.MM", Locale.getDefault()).format(Date())
        db.badgeDao().unlockBadge("badge_first_sound", todayStr)
    }

    suspend fun saveStudyPlan(plan: StudyPlan) = withContext(Dispatchers.IO) {
        db.studyPlanDao().saveStudyPlan(plan)
    }

    suspend fun updateSpeechSettings(rate: Float, pitch: Float, voice: String) = withContext(Dispatchers.IO) {
        db.userProgressDao().updateSpeechSettings(rate, pitch, voice)
    }

    suspend fun updateThemeMode(themeMode: String) = withContext(Dispatchers.IO) {
        db.userProgressDao().updateThemeMode(themeMode)
    }
}
