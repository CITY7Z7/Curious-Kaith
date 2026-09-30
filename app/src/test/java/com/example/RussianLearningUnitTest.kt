package com.example

import com.example.data.AlphabetData
import com.example.data.DialogueData
import com.example.data.DictionaryData
import com.example.data.GamificationData
import com.example.data.ParrotLessonData
import com.example.data.SpeakerRole
import com.example.data.model.LetterType
import com.example.data.model.UserProgress
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
    fun testAlphabetLockCondition() {
        val initialProgress = UserProgress(isAlphabetMastered = false, learnedLettersList = "А,Б,В")
        assertFalse("Alphabet should not be mastered with 3 letters", initialProgress.isAlphabetMastered)
        assertEquals(3, initialProgress.getLearnedLettersSet().size)

        val allLetters = AlphabetData.letters.map { it.letter }.joinToString(",")
        val completedProgress = UserProgress(isAlphabetMastered = true, learnedLettersList = allLetters)
        assertTrue("Alphabet should be mastered", completedProgress.isAlphabetMastered)
        assertEquals(33, completedProgress.getLearnedLettersSet().size)
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
    fun testKoPronunciationStress() {
        val koLesson = ParrotLessonData.lessons.find { it.id == 110 }
        assertTrue("Lesson 110 must exist", koLesson != null)
        assertEquals("Target text should be Ко", "Ко", koLesson?.targetText)
        assertEquals("TTS text must have stress mark to prevent reduction to Ka", "Кó", koLesson?.ttsText)
    }

    @Test
    fun testLeaderboardUserRanking() {
        val board = GamificationData.getLeaderboard(500)
        assertTrue("Leaderboard must contain users", board.isNotEmpty())
        val currentUser = board.find { it.isCurrentUser }
        assertTrue("Current user must be on leaderboard", currentUser != null)
        assertEquals("Current user XP must match", 500, currentUser?.xp)
    }

    @Test
    fun testDialogueScenariosCompleteness() {
        assertEquals("Must contain 6 real-life dialogue scenarios", 6, DialogueData.scenarios.size)
        for (scenario in DialogueData.scenarios) {
            assertTrue("Scenario title must not be empty", scenario.title.isNotBlank())
            assertTrue("Scenario location must not be empty", scenario.location.isNotBlank())
            assertTrue("Scenario must have at least 4 turns", scenario.turns.size >= 4)

            val hasKesha = scenario.turns.any { it.speaker == SpeakerRole.KESHA }
            val hasUser = scenario.turns.any { it.speaker == SpeakerRole.USER }
            assertTrue("Scenario must have Kesha speaker", hasKesha)
            assertTrue("Scenario must have User speaker", hasUser)

            for (turn in scenario.turns) {
                assertTrue("Turn text must not be blank", turn.text.isNotBlank())
                assertTrue("TTS text must not be blank", turn.ttsText.isNotBlank())
            }
        }
    }
}
