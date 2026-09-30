package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Style
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(
    val title: String,
    val icon: ImageVector,
    val testTag: String,
    val requiresAlphabet: Boolean = false
) {
    ALPHABET("Азбука", Icons.Default.RecordVoiceOver, "nav_alphabet", requiresAlphabet = false),
    TRAINER("Попугай", Icons.Default.Hearing, "nav_trainer", requiresAlphabet = true),
    CARDS("Карточки", Icons.Default.Style, "nav_cards", requiresAlphabet = true),
    DICTIONARY("Словарь", Icons.Default.Book, "nav_dictionary", requiresAlphabet = true),
    PROGRESS("Прогресс", Icons.Default.EmojiEvents, "nav_progress", requiresAlphabet = false),
    SETTINGS("Настройки", Icons.Default.Settings, "nav_settings", requiresAlphabet = false)
}
