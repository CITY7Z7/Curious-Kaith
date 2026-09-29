package com.example

import com.example.data.AlphabetData
import com.example.data.DictionaryData
import com.example.data.GamificationData
import com.example.data.ParrotLessonData
import com.example.data.model.LetterType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RussianLearningUnitTest {

    @Test
    fun testRussianAlphabetCount() {
        assertEquals("Russian alphabet must have 33 letters", 33, AlphabetData.letters.size)

        val vowels = AlphabetData.letters.filter { it.type == LetterType.VOWEL }
        assertEquals("There must be 10 vowels", 10, vowels.size)

        val signs = AlphabetData.letters.filter { it.type == LetterType.SIGN }
        assertEquals("There must be 2 signs (Ъ, Ь)", 2, signs.size)
    }

    @Test
    fun testParrotLessonsLevels() {
        val level1 = ParrotLessonData.lessons.filter { it.level == 1 }
        val level2 = ParrotLessonData.lessons.filter { it.level == 2 }
        val level3 = ParrotLessonData.lessons.filter { it.level == 3 }
        val level4 = ParrotLessonData.lessons.filter { it.level == 4 }

        assertTrue("Level 1 must contain sounds and syllables", level1.isNotEmpty())
        assertTrue("Level 2 must contain simple nouns", level2.isNotEmpty())
        assertTrue("Level 3 must contain simple verbs", level3.isNotEmpty())
        assertTrue("Level 4 must contain short phrases and dialogues", level4.isNotEmpty())
    }

    @Test
    fun testDictionaryEntries() {
        assertTrue("Dictionary must contain initial words", DictionaryData.initialWords.size >= 20)
        for (w in DictionaryData.initialWords) {
            assertTrue("Word must not be blank", w.word.isNotBlank())
            assertTrue("Definition must be in Russian", w.definitionRu.isNotBlank())
            assertTrue("Context example must not be empty", w.exampleSentence.isNotBlank())
        }
    }

    @Test
    fun testLeaderboardUserRanking() {
        val board = GamificationData.getLeaderboard(500)
        assertTrue("Leaderboard must contain users", board.isNotEmpty())
        val currentUser = board.find { it.isCurrentUser }
        assertTrue("Current user must be on leaderboard", currentUser != null)
        assertEquals("Current user XP must match", 500, currentUser?.xp)
    }
}
