package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DictionaryWord
import com.example.ui.MainViewModel

@Composable
fun FlashcardsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val words by viewModel.allWords.collectAsState()
    var currentIndex by remember { mutableStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    var autoPlayAudio by remember { mutableStateOf(true) }

    val currentWord: DictionaryWord? = if (words.isNotEmpty()) {
        words[currentIndex.coerceIn(0, words.size - 1)]
    } else null

    // Smooth narration on word switch if autoPlayAudio enabled
    LaunchedEffect(currentIndex, autoPlayAudio) {
        if (autoPlayAudio && currentWord != null) {
            viewModel.speakText(currentWord.word)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Блок 1: Заголовок карточек с границей и тенью
        ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Карточки со словами",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-0.3).sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Плавное появление • Озвучка диктором",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Auto-narration toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(
                        text = "Авто-звук",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Switch(
                        checked = autoPlayAudio,
                        onCheckedChange = { autoPlayAudio = it },
                        modifier = Modifier.testTag("switch_auto_audio")
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (currentWord != null) {
            // Counter
            Text(
                text = "Карточка ${currentIndex + 1} из ${words.size}",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Smooth Content Transition with animated fade & scale
            AnimatedContent(
                targetState = currentWord,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(400, easing = FastOutSlowInEasing)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(400)))
                        .togetherWith(
                            fadeOut(animationSpec = tween(250)) +
                                    scaleOut(targetScale = 1.05f, animationSpec = tween(250))
                        )
                },
                label = "flashcard_smooth_animation",
                modifier = Modifier.weight(1f)
            ) { word ->
                FlashcardItemView(
                    word = word,
                    isFlipped = isFlipped,
                    onFlip = { isFlipped = !isFlipped },
                    onSpeak = { text -> viewModel.speakText(text) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Repeat Later
                OutlinedButton(
                    onClick = {
                        isFlipped = false
                        if (currentIndex < words.size - 1) {
                            currentIndex++
                        } else {
                            currentIndex = 0
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("btn_repeat_later"),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(imageVector = Icons.Default.Replay, contentDescription = "Повторить")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Повторить")
                }

                // Mastered / Know it
                Button(
                    onClick = {
                        viewModel.markWordMastered(currentWord, true)
                        isFlipped = false
                        if (currentIndex < words.size - 1) {
                            currentIndex++
                        } else {
                            currentIndex = 0
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("btn_know_word"),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Знаю")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Знаю! (+15 XP)")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Navigation Row (Previous / Flip / Next)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        isFlipped = false
                        if (currentIndex > 0) currentIndex-- else currentIndex = words.size - 1
                    },
                    modifier = Modifier.testTag("btn_card_prev")
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Предыдущая")
                }

                FilledTonalButton(
                    onClick = { isFlipped = !isFlipped },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("btn_flip_card")
                ) {
                    Icon(imageVector = Icons.Default.Flip, contentDescription = "Перевернуть")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isFlipped) "Показать слово" else "Показать пример")
                }

                IconButton(
                    onClick = {
                        isFlipped = false
                        if (currentIndex < words.size - 1) currentIndex++ else currentIndex = 0
                    },
                    modifier = Modifier.testTag("btn_card_next")
                ) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Следующая")
                }
            }
        }
    }
}

@Composable
fun FlashcardItemView(
    word: DictionaryWord,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onSpeak: (String) -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onFlip() }
            .testTag("flashcard_body")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Category & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = word.category,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = word.partOfSpeech,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Center Content (Flip side dependent)
            if (!isFlipped) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = word.stressMarked,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 44.sp
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Нажмите, чтобы увидеть значение и контекст",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Значение:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = word.definitionRu,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 17.sp,
                            lineHeight = 24.sp
                        ),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Пример в контексте:",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "«${word.exampleSentence}»",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                ),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            // Bottom Audio Playback Button
            IconButton(
                onClick = {
                    if (!isFlipped) {
                        onSpeak(word.word)
                    } else {
                        onSpeak("${word.word}. ${word.exampleSentence}")
                    }
                },
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .testTag("btn_card_speak")
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Озвучить диктором",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
