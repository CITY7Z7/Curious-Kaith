package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp
import com.example.service.NotificationHelper
import com.example.ui.MainViewModel
import com.example.ui.components.TopBarWithStats
import com.example.ui.navigation.Screen
import com.example.ui.screens.AlphabetScreen
import com.example.ui.screens.DictionaryScreen
import com.example.ui.screens.FlashcardsScreen
import com.example.ui.screens.ParrotTrainerScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.RussianLearningTheme
import com.example.ui.theme.ThemeMode

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        NotificationHelper.createNotificationChannel(this)

        setContent {
            val themeMode by viewModel.themeMode.collectAsState()
            val userProgress by viewModel.userProgress.collectAsState()

            RussianLearningTheme(themeMode = themeMode) {
                var currentScreen by remember { mutableStateOf(Screen.TRAINER) }

                BackHandler(enabled = currentScreen != Screen.TRAINER) {
                    currentScreen = Screen.TRAINER
                }

                Scaffold(
                    topBar = {
                        TopBarWithStats(
                            userProgress = userProgress,
                            themeMode = themeMode,
                            onToggleTheme = {
                                val nextMode = when (themeMode) {
                                    ThemeMode.LIGHT -> ThemeMode.DARK
                                    ThemeMode.DARK -> ThemeMode.SYSTEM
                                    ThemeMode.SYSTEM -> ThemeMode.LIGHT
                                }
                                viewModel.updateThemeMode(nextMode)
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.testTag("main_navigation_bar")
                        ) {
                            Screen.values().forEach { screen ->
                                NavigationBarItem(
                                    selected = currentScreen == screen,
                                    onClick = { currentScreen = screen },
                                    icon = {
                                        Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.title
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = screen.title,
                                            fontSize = 11.sp
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    modifier = Modifier.testTag(screen.testTag)
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)

                    when (currentScreen) {
                        Screen.TRAINER -> ParrotTrainerScreen(
                            viewModel = viewModel,
                            modifier = screenModifier
                        )
                        Screen.ALPHABET -> AlphabetScreen(
                            viewModel = viewModel,
                            modifier = screenModifier
                        )
                        Screen.CARDS -> FlashcardsScreen(
                            viewModel = viewModel,
                            modifier = screenModifier
                        )
                        Screen.DICTIONARY -> DictionaryScreen(
                            viewModel = viewModel,
                            modifier = screenModifier
                        )
                        Screen.PROGRESS -> ProgressScreen(
                            viewModel = viewModel,
                            modifier = screenModifier
                        )
                        Screen.SETTINGS -> SettingsScreen(
                            viewModel = viewModel,
                            modifier = screenModifier
                        )
                    }
                }
            }
        }
    }
}
