package com.example.univibe.domain.models

import androidx.compose.ui.graphics.Color

data class AppTheme(
    val id: String,
    val name: String,
    val primary: Long,
    val secondary: Long,
    val tertiary: Long,
    val background: Long,
    val surface: Long,
    val error: Long,
    val isDark: Boolean,
    val isCustom: Boolean = false
)

object ThemePresets {
    val DEFAULT_LIGHT = AppTheme(
        id = "default_light",
        name = "Default Light",
        primary = 0xFFA0433C,
        secondary = 0xFFC89344,
        tertiary = 0xFF4ECDC4,
        background = 0xFFFFFBFF,
        surface = 0xFFFFFBFF,
        error = 0xFFB3261E,
        isDark = false
    )
    
    val DEFAULT_DARK = AppTheme(
        id = "default_dark",
        name = "Default Dark",
        primary = 0xFFFFB4AB,
        secondary = 0xFFFFD700,
        tertiary = 0xFF7DD3C0,
        background = 0xFF1C1B1F,
        surface = 0xFF1C1B1F,
        error = 0xFFF2B8B5,
        isDark = true
    )
    
    val OCEAN = AppTheme(
        id = "ocean",
        name = "Ocean Blue",
        primary = 0xFF0077BE,
        secondary = 0xFF4DD0E1,
        tertiary = 0xFF00ACC1,
        background = 0xFFF0F8FF,
        surface = 0xFFE1F5FE,
        error = 0xFFD32F2F,
        isDark = false
    )
    
    val OCEAN_DARK = AppTheme(
        id = "ocean_dark",
        name = "Ocean Blue Dark",
        primary = 0xFF4DD0E1,
        secondary = 0xFF0077BE,
        tertiary = 0xFF00ACC1,
        background = 0xFF0A1929,
        surface = 0xFF1A2332,
        error = 0xFFEF5350,
        isDark = true
    )
    
    val SUNSET = AppTheme(
        id = "sunset",
        name = "Sunset Orange",
        primary = 0xFFFF6B35,
        secondary = 0xFFFFB627,
        tertiary = 0xFFFF8552,
        background = 0xFFFFF8F0,
        surface = 0xFFFFE4D6,
        error = 0xFFD32F2F,
        isDark = false
    )
    
    val SUNSET_DARK = AppTheme(
        id = "sunset_dark",
        name = "Sunset Orange Dark",
        primary = 0xFFFFB627,
        secondary = 0xFFFF6B35,
        tertiary = 0xFFFF8552,
        background = 0xFF1A0F0A,
        surface = 0xFF2A1A14,
        error = 0xFFEF5350,
        isDark = true
    )
    
    val FOREST = AppTheme(
        id = "forest",
        name = "Forest Green",
        primary = 0xFF2E7D32,
        secondary = 0xFF66BB6A,
        tertiary = 0xFF4CAF50,
        background = 0xFFF1F8E9,
        surface = 0xFFDCEDC8,
        error = 0xFFD32F2F,
        isDark = false
    )
    
    val FOREST_DARK = AppTheme(
        id = "forest_dark",
        name = "Forest Green Dark",
        primary = 0xFF66BB6A,
        secondary = 0xFF2E7D32,
        tertiary = 0xFF4CAF50,
        background = 0xFF0D1F0C,
        surface = 0xFF1A2F19,
        error = 0xFFEF5350,
        isDark = true
    )
    
    val LAVENDER = AppTheme(
        id = "lavender",
        name = "Lavender Purple",
        primary = 0xFF7E57C2,
        secondary = 0xFFBA68C8,
        tertiary = 0xFF9575CD,
        background = 0xFFF3E5F5,
        surface = 0xFFE1BEE7,
        error = 0xFFD32F2F,
        isDark = false
    )
    
    val LAVENDER_DARK = AppTheme(
        id = "lavender_dark",
        name = "Lavender Purple Dark",
        primary = 0xFFBA68C8,
        secondary = 0xFF7E57C2,
        tertiary = 0xFF9575CD,
        background = 0xFF1A0F1F,
        surface = 0xFF2A1A2F,
        error = 0xFFEF5350,
        isDark = true
    )
    
    val RUBY = AppTheme(
        id = "ruby",
        name = "Ruby Red",
        primary = 0xFFD32F2F,
        secondary = 0xFFE57373,
        tertiary = 0xFFEF5350,
        background = 0xFFFFF5F5,
        surface = 0xFFFFEBEE,
        error = 0xFFC62828,
        isDark = false
    )
    
    val RUBY_DARK = AppTheme(
        id = "ruby_dark",
        name = "Ruby Red Dark",
        primary = 0xFFEF5350,
        secondary = 0xFFD32F2F,
        tertiary = 0xFFE57373,
        background = 0xFF1F0A0A,
        surface = 0xFF2F1A1A,
        error = 0xFFFF6B6B,
        isDark = true
    )
    
    val GOLD = AppTheme(
        id = "gold",
        name = "Golden Yellow",
        primary = 0xFFF9A825,
        secondary = 0xFFFFD54F,
        tertiary = 0xFFFFB300,
        background = 0xFFFFFDE7,
        surface = 0xFFFFF9C4,
        error = 0xFFD32F2F,
        isDark = false
    )
    
    val GOLD_DARK = AppTheme(
        id = "gold_dark",
        name = "Golden Yellow Dark",
        primary = 0xFFFFD54F,
        secondary = 0xFFF9A825,
        tertiary = 0xFFFFB300,
        background = 0xFF1F1A0A,
        surface = 0xFF2F2A1A,
        error = 0xFFEF5350,
        isDark = true
    )
    
    val allPresets = listOf(
        DEFAULT_LIGHT,
        DEFAULT_DARK,
        OCEAN,
        OCEAN_DARK,
        SUNSET,
        SUNSET_DARK,
        FOREST,
        FOREST_DARK,
        LAVENDER,
        LAVENDER_DARK,
        RUBY,
        RUBY_DARK,
        GOLD,
        GOLD_DARK
    )
}