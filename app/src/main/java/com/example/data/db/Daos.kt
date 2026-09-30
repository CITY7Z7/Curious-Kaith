package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.BadgeItem
import com.example.data.model.DictionaryWord
import com.example.data.model.StudyPlan
import com.example.data.model.UserProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary_words ORDER BY word ASC")
    fun getAllWords(): Flow<List<DictionaryWord>>

    @Query("SELECT * FROM dictionary_words WHERE category = :category ORDER BY word ASC")
    fun getWordsByCategory(category: String): Flow<List<DictionaryWord>>

    @Query("SELECT * FROM dictionary_words WHERE isFavorite = 1 ORDER BY word ASC")
    fun getFavoriteWords(): Flow<List<DictionaryWord>>

    @Query("SELECT * FROM dictionary_words WHERE isMastered = 1")
    fun getMasteredWords(): Flow<List<DictionaryWord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<DictionaryWord>)

    @Update
    suspend fun updateWord(word: DictionaryWord)

    @Query("SELECT COUNT(*) FROM dictionary_words")
    suspend fun getWordCount(): Int
}

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun getUserProgress(): Flow<UserProgress?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: UserProgress)

    @Query("UPDATE user_progress SET xp = xp + :points, wordsLearnedCount = wordsLearnedCount + :wordsDelta WHERE id = 1")
    suspend fun addXpAndWords(points: Int, wordsDelta: Int)

    @Query("UPDATE user_progress SET speechRate = :rate, speechPitch = :pitch, selectedVoice = :voice WHERE id = 1")
    suspend fun updateSpeechSettings(rate: Float, pitch: Float, voice: String)

    @Query("UPDATE user_progress SET isAlphabetMastered = :mastered, learnedLettersList = :letters WHERE id = 1")
    suspend fun updateAlphabetProgress(mastered: Boolean, letters: String)

    @Query("UPDATE user_progress SET themeMode = :themeMode WHERE id = 1")
    suspend fun updateThemeMode(themeMode: String)
}

@Dao
interface StudyPlanDao {
    @Query("SELECT * FROM study_plan WHERE id = 1")
    fun getStudyPlan(): Flow<StudyPlan?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStudyPlan(plan: StudyPlan)
}

@Dao
interface BadgeDao {
    @Query("SELECT * FROM badges")
    fun getAllBadges(): Flow<List<BadgeItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadges(badges: List<BadgeItem>)

    @Query("UPDATE badges SET isUnlocked = 1, unlockedDate = :date WHERE id = :badgeId")
    suspend fun unlockBadge(badgeId: String, date: String)

    @Query("SELECT COUNT(*) FROM badges")
    suspend fun getBadgeCount(): Int
}
