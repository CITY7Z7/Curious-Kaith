package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.ParrotMood

@Composable
fun ParrotMascotView(
    mood: ParrotMood,
    speechBubbleText: String,
    modifier: Modifier = Modifier
) {
    val bounceAnim = remember { Animatable(1f) }

    LaunchedEffect(mood) {
        when (mood) {
            ParrotMood.HAPPY -> {
                bounceAnim.animateTo(
                    targetValue = 1.12f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(280, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            }
            ParrotMood.SPEAKING -> {
                bounceAnim.animateTo(
                    targetValue = 1.05f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(200, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            }
            ParrotMood.LISTENING -> {
                bounceAnim.animateTo(
                    targetValue = 1.03f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(500, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            }
            else -> {
                bounceAnim.snapTo(1f)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Speech Bubble
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            shadowElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val moodEmoji = when (mood) {
                    ParrotMood.SPEAKING -> "🗣️"
                    ParrotMood.LISTENING -> "👂"
                    ParrotMood.HAPPY -> "🎉"
                    ParrotMood.TRY_AGAIN -> "💡"
                    ParrotMood.NEUTRAL -> "💬"
                }

                Text(
                    text = moodEmoji,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )

                Text(
                    text = speechBubbleText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Mascot Avatar Box
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(92.dp)
                .scale(bounceAnim.value)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFBBF24),
                            Color(0xFFD97706),
                            Color(0xFFB45309)
                        )
                    )
                )
                .border(3.dp, Color(0xFFFEF3C7), CircleShape)
        ) {
            // Cute animated parrot representation
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = when (mood) {
                        ParrotMood.HAPPY -> "🦜✨"
                        ParrotMood.SPEAKING -> "🦜📢"
                        ParrotMood.LISTENING -> "🦜🎧"
                        ParrotMood.TRY_AGAIN -> "🦜❓"
                        ParrotMood.NEUTRAL -> "🦜"
                    },
                    fontSize = 42.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Тренер Кеша",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}
