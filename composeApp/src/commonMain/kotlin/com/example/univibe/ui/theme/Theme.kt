package com.example.univibe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.univibe.data.preferences.ThemePreferences

/**
 * UniVibe theme that applies Material 3 styling with custom colors and typography.
 * Dynamically applies theme colors based on ThemePreferences.
 *
 * @param darkTheme Whether to use dark color scheme. If null, system dark mode is detected.
 * @param content The composable content to apply the theme to.
 */
@Composable
fun UniVibeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Collect current theme from preferences
    val currentTheme by ThemePreferences.currentTheme.collectAsState()
    
    // Create color scheme from selected theme
    val colorScheme = if (currentTheme.isDark) {
        darkColorScheme(
            primary = Color(currentTheme.primary),
            secondary = Color(currentTheme.secondary),
            tertiary = Color(currentTheme.tertiary),
            background = Color(currentTheme.background),
            surface = Color(currentTheme.surface),
            error = Color(currentTheme.error)
        )
    } else {
        lightColorScheme(
            primary = Color(currentTheme.primary),
            secondary = Color(currentTheme.secondary),
            tertiary = Color(currentTheme.tertiary),
            background = Color(currentTheme.background),
            surface = Color(currentTheme.surface),
            error = Color(currentTheme.error)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = UniVibeTypography,
        content = content
    )
}