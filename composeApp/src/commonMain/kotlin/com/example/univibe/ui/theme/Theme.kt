package com.example.univibe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * UniVibe theme that applies Material 3 styling with custom colors and typography.
 *
 * @param darkTheme Whether to use dark color scheme. If null, system dark mode is detected.
 * @param content The composable content to apply the theme to.
 */
@Composable
fun UniVibeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = UniVibeTypography,
        content = content
    )
}