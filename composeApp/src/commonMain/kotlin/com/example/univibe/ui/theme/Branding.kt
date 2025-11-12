package com.example.univibe.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

/**
 * Branding tokens for UniVibe. Keep all brand-specific values here for easy swapping later.
 */
@Immutable
object UniVibeBranding {
    // Core colors
    val primaryColor = Color(0xFF800020) // Deep burgundy/maroon
    val secondaryColor = Color(0xFF4A90E2) // Light blue accent
    val backgroundColor = Color(0xFFFFFFFF)
    val surfaceColor = Color(0xFFF5F5F5)

    // Typography choices (can be swapped to custom fonts later)
    val headlineFont: FontFamily = FontFamily.Default
    val bodyFont: FontFamily = FontFamily.Default

    // Placeholder logo meta (used by splash or about page later)
    const val appName: String = "UniVibe"
    const val logoText: String = "UniVibe"
}
