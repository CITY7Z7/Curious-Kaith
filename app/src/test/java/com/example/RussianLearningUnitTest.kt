package com.example

import com.example.data.AlphabetData
import com.example.data.ContrastCategory
import com.example.data.DialogueData
import com.example.data.DictionaryData
import com.example.data.GamificationData
import com.example.data.IntonationData
import com.example.data.IntonationType
import com.example.data.MinimalPairsData
import com.example.data.ParrotLessonData
import com.example.data.SpeakerRole
import com.example.data.SpeechMatrixData
import com.example.data.GrammarFocus
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

    @Test
    fun testIntonationContrastSetsCompleteness() {
        assertTrue("Must contain contrast sets", IntonationData.contrastSets.size >= 5)
        for (set in IntonationData.contrastSets) {
            assertTrue("Set base topic must not be blank", set.baseTopic.isNotBlank())
            assertTrue("Set must contain at least 3 intonation items", set.items.size >= 3)

            val hasIK1 = set.items.any { it.type == IntonationType.IK1 }
            val hasIK3 = set.items.any { it.type == IntonationType.IK3 }
            assertTrue("Each set must have IK-1 (Statement)", hasIK1)
            assertTrue("Each set must have IK-3 (Question)", hasIK3)

            for (item in set.items) {
                assertTrue("Phrase text must not be blank", item.phraseText.isNotBlank())
                assertTrue("Center word must not be blank", item.centerWord.isNotBlank())
                assertTrue("Pedagogical tip must not be blank", item.pedagogicalTip.isNotBlank())
                assertTrue("Pitch points must have at least 3 points", item.pitchCurvePoints.size >= 3)
                for (p in item.pitchCurvePoints) {
                    assertTrue("Pitch point must be in range 0.0..1.0", p in 0f..1f)
                }
            }
        }
    }

    @Test
    fun testMinimalPairsDataCompleteness() {
        assertTrue("Must contain minimal pairs", MinimalPairsData.pairs.size >= 10)

        val hardSoftPairs = MinimalPairsData.pairs.filter { it.category == ContrastCategory.HARD_SOFT }
        val voicePairs = MinimalPairsData.pairs.filter { it.category == ContrastCategory.VOICED_VOICELESS }

        assertTrue("Must contain hard/soft contrast pairs", hardSoftPairs.size >= 6)
        assertTrue("Must contain voiced/voiceless contrast pairs", voicePairs.size >= 4)

        for (pair in MinimalPairsData.pairs) {
            assertTrue("Contrast key must not be blank", pair.contrastKey.isNotBlank())
            assertTrue("Word A must not be blank", pair.wordA.word.isNotBlank())
            assertTrue("Word B must not be blank", pair.wordB.word.isNotBlank())
            assertTrue("Transcription A must not be blank", pair.wordA.transcription.isNotBlank())
            assertTrue("Transcription B must not be blank", pair.wordB.transcription.isNotBlank())
            assertTrue("Phonetic role A must not be blank", pair.wordA.phoneticRole.isNotBlank())
            assertTrue("Phonetic role B must not be blank", pair.wordB.phoneticRole.isNotBlank())
            assertTrue("Pedagogical explanation must not be blank", pair.pedagogicalExplanation.isNotBlank())
            assertTrue("Word A and Word B must differ", pair.wordA.word != pair.wordB.word)
        }
    }

    @Test
    fun testSpeechMatricesCompleteness() {
        assertEquals("Must contain 6 key speech pattern matrices", 6, SpeechMatrixData.matrices.size)

        val categories = SpeechMatrixData.matrices.map { it.category }.toSet()
        assertEquals("All 6 GrammarFocus categories must be covered", 6, categories.size)

        for (matrix in SpeechMatrixData.matrices) {
            assertTrue("Matrix title must not be blank", matrix.title.isNotBlank())
            assertTrue("Frame prefix must not be blank", matrix.framePrefix.isNotBlank())
            assertTrue("Frame question must not be blank", matrix.frameQuestion.isNotBlank())
            assertTrue("Pedagogical note must not be blank", matrix.pedagogicalNote.isNotBlank())
            assertTrue("Matrix must have at least 5 slot substitution options", matrix.options.size >= 5)

            for (option in matrix.options) {
                assertTrue("Slot word must not be blank", option.slotWord.isNotBlank())
                assertTrue("Slot stress marked must not be blank", option.slotStressMarked.isNotBlank())
                assertTrue("Full sentence must not be blank", option.fullSentence.isNotBlank())
                assertTrue("Full sentence TTS must not be blank", option.fullSentenceTts.isNotBlank())
                assertTrue("Meaning RU must not be blank", option.meaningRu.isNotBlank())
                assertTrue("Grammatical hint must not be blank", option.grammaticalHint.isNotBlank())
                assertTrue("Full sentence must contain slot word", option.fullSentence.contains(option.slotWord))
            }
        }
    }
}
