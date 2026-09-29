package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlueLight,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueContainerDark,
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = SecondaryAmberLight,
    onSecondary = Color.Black,
    secondaryContainer = SecondaryAmberContainerDark,
    onSecondaryContainer = Color(0xFFFEF3C7),
    tertiary = TertiaryRubyLight,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryRubyContainerDark,
    onTertiaryContainer = Color(0xFFFEE2E2),
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueContainerLight,
    onPrimaryContainer = PrimaryBlueDark,
    secondary = SecondaryAmber,
    onSecondary = Color.White,
    secondaryContainer = SecondaryAmberContainerLight,
    onSecondaryContainer = Color(0xFF78350F),
    tertiary = TertiaryRuby,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryRubyContainerLight,
    onTertiaryContainer = Color(0xFF7F1D1D),
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight
)

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

@Composable
fun RussianLearningTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = false, // prefer intentional branded palette
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
