package com.example.univibe.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════════════════════════════════
// Brand Colors - Core Identity
// ═══════════════════════════════════════════════════════════════════════
object BrandColors {
    val Burgundy = Color(0xFFA0433C)
    val BurgundyLight = Color(0xFFD45A4F)
    val BurgundyDark = Color(0xFF752E29)
    
    val Gold = Color(0xFFC89344)
    val GoldLight = Color(0xFFFFB968)
    val GoldDark = Color(0xFF9A6D2E)
    
    val Cream = Color(0xFFE8D4C4)
    val CreamLight = Color(0xFFFFF5ED)
    val CreamDark = Color(0xFFCBBAAD)
    
    val Teal = Color(0xFF4ECDC4)
    val TealLight = Color(0xFF7EDDD7)
    val TealDark = Color(0xFF3AA39C)
}

// ============== LIGHT THEME COLORS ==============

// Core Brand Colors
private val LightPrimary = BrandColors.Burgundy
private val LightSecondary = BrandColors.Gold
private val LightTertiary = BrandColors.Teal
private val LightBackground = Color(0xFFFFFBFF)
private val LightSurface = Color(0xFFFFFBFF)

// Surface Variants (for better depth and layering)
private val LightSurfaceContainer = Color(0xFFF7F0EE)
private val LightSurfaceContainerHigh = Color(0xFFEFE8E6)
private val LightSurfaceContainerHighest = Color(0xFFE7DFDD)

// State Colors (for interactive feedback)
private val LightStateHover = Color(0xFFF0EAE8)
private val LightStatePressed = Color(0xFFE5DEDAD)
private val LightStateFocused = Color(0xFFDDD4D1)
private val LightStateDisabled = Color(0xFFF5F0ED)

// Semantic Colors
private val LightSuccess = Color(0xFF2ECC71) // Green
private val LightWarning = Color(0xFFF59E0B) // Amber
private val LightInfo = Color(0xFF3B82F6) // Blue
private val LightError = Color(0xFFBA1A1A) // Red

// Gradient Colors (for special cards/headers)
private val LightGradientStart = BrandColors.Burgundy
private val LightGradientEnd = BrandColors.Teal

// ============== DARK THEME COLORS ==============

// Core Brand Colors
private val DarkPrimary = Color(0xFFFFB4AB)
private val DarkSecondary = Color(0xFFFFD700) // Gold
private val DarkTertiary = Color(0xFF7DD3C0)
private val DarkBackground = Color(0xFF1C1B1F)
private val DarkSurface = Color(0xFF1C1B1F)

// Surface Variants
private val DarkSurfaceContainer = Color(0xFF2F2D31)
private val DarkSurfaceContainerHigh = Color(0xFF3A3841)
private val DarkSurfaceContainerHighest = Color(0xFF49474D)

// State Colors
private val DarkStateHover = Color(0xFF4A4851)
private val DarkStatePressed = Color(0xFF5C5A62)
private val DarkStateFocused = Color(0xFF6D6B73)
private val DarkStateDisabled = Color(0xFF36343A)

// Semantic Colors
private val DarkSuccess = Color(0xFF81C784) // Light Green
private val DarkWarning = Color(0xFFFFD54F) // Light Amber
private val DarkInfo = Color(0xFF64B5F6) // Light Blue
private val DarkError = Color(0xFFF2B8B5)

/**
 * Creates a light color scheme for UniVibe using Material 3 colors.
 */
fun lightColorScheme(): ColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF8DEDB),
    onPrimaryContainer = Color(0xFF3E0D07),
    secondary = LightSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFEDD9),
    onSecondaryContainer = Color(0xFF452B0B),
    tertiary = LightTertiary,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xB34ECDC4),
    onTertiaryContainer = Color(0xFF00504A),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = LightBackground,
    onBackground = Color(0xFF1C1B1F),
    surface = LightSurface,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF5DDD9),
    onSurfaceVariant = Color(0xFF6D5B56),
    outline = Color(0xFF8B7B78),
    outlineVariant = Color(0xFFDCC9C3),
    scrim = Color.Black,
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF8EFEF),
    inversePrimary = Color(0xFFFFB4AB),
)

/**
 * Creates a dark color scheme for UniVibe using Material 3 colors.
 */
fun darkColorScheme(): ColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color(0xFF651E18),
    primaryContainer = Color(0xFF8C2F27),
    onPrimaryContainer = Color(0xFFFFDAD5),
    secondary = DarkSecondary,
    onSecondary = Color(0xFF704F1F),
    secondaryContainer = Color(0xFF936F3B),
    onSecondaryContainer = Color(0xFFFFEDD9),
    tertiary = DarkTertiary,
    onTertiary = Color(0xFF003D38),
    tertiaryContainer = Color(0xFF005450),
    onTertiaryContainer = Color(0xFFA3EEEA),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    background = DarkBackground,
    onBackground = Color(0xFFE7E1E6),
    surface = DarkSurface,
    onSurface = Color(0xFFE7E1E6),
    surfaceVariant = Color(0xFF6D5B56),
    onSurfaceVariant = Color(0xFFDCC9C3),
    outline = Color(0xFFA08F8A),
    outlineVariant = Color(0xFF6D5B56),
    scrim = Color.Black,
    inverseSurface = Color(0xFFE7E1E6),
    inverseOnSurface = Color(0xFF1C1B1F),
    inversePrimary = Color(0xFFA0433C),
)

// ============== COLOR SCHEME ACCESSORS ==============

/**
 * Extended color palette for UniVibe organized by theme.
 * Use these within UniVibeTheme context to access colors.
 */
object UniVibeColors {
    // Light Theme Colors
    object Light {
        object Surface {
            val default = LightSurface
            val container = LightSurfaceContainer
            val containerHigh = LightSurfaceContainerHigh
            val containerHighest = LightSurfaceContainerHighest
        }
        
        object State {
            val hover = LightStateHover
            val pressed = LightStatePressed
            val focused = LightStateFocused
            val disabled = LightStateDisabled
        }
        
        object Semantic {
            val success = LightSuccess
            val warning = LightWarning
            val info = LightInfo
            val error = LightError
        }
        
        object Gradient {
            val start = LightGradientStart
            val end = LightGradientEnd
        }
    }
    
    // Dark Theme Colors
    object Dark {
        object Surface {
            val default = DarkSurface
            val container = DarkSurfaceContainer
            val containerHigh = DarkSurfaceContainerHigh
            val containerHighest = DarkSurfaceContainerHighest
        }
        
        object State {
            val hover = DarkStateHover
            val pressed = DarkStatePressed
            val focused = DarkStateFocused
            val disabled = DarkStateDisabled
        }
        
        object Semantic {
            val success = DarkSuccess
            val warning = DarkWarning
            val info = DarkInfo
            val error = DarkError
        }
        
        object Gradient {
            val start = DarkPrimary
            val end = DarkTertiary
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════
// Semantic Colors - For specific use cases
// ═══════════════════════════════════════════════════════════════════════
object SemanticColors {
    // Status colors
    val Success = Color(0xFF2ECC71)
    val SuccessContainer = Color(0xFFC8F7DC)
    val OnSuccessContainer = Color(0xFF002109)
    
    val Error = Color(0xFFEF4444)
    val ErrorContainer = Color(0xFFFFEBEE)
    val OnErrorContainer = Color(0xFF3D0000)
    
    val Warning = Color(0xFFF59E0B)
    val WarningContainer = Color(0xFFFFEBCC)
    val OnWarningContainer = Color(0xFF4D2800)
    
    val Info = Color(0xFF3B82F6)
    val InfoContainer = Color(0xFFD8E7FF)
    val OnInfoContainer = Color(0xFF001B3D)
    
    // Online status
    val Online = Color(0xFF10B981)
    val Away = Color(0xFFFBBF24)
    val Busy = Color(0xFFEF4444)
    val Offline = Color(0xFF9CA3AF)
    
    // Verification badge
    val Verified = Color(0xFF3B82F6)
    
    // Special highlights
    val Premium = Color(0xFFFFD700)
    val Featured = Color(0xFF8B5CF6)
}

// ═══════════════════════════════════════════════════════════════════════
// Gradient Colors - For special effects
// ═══════════════════════════════════════════════════════════════════════
object GradientColors {
    // Story gradients (unviewed stories)
    val StoryUnviewed = listOf(
        Color(0xFFFF6B35),
        Color(0xFFFF8E53),
        Color(0xFFFFA06B),
        BrandColors.Gold
    )
    
    // Premium feature gradient
    val Premium = listOf(
        Color(0xFFFFD700),
        BrandColors.Gold,
        BrandColors.Burgundy
    )
    
    // Campus gradient
    val Campus = listOf(
        BrandColors.Teal,
        Color(0xFF44A3D5),
        Color(0xFF9B59B6)
    )
    
    // Sunset gradient
    val Sunset = listOf(
        BrandColors.Burgundy,
        BrandColors.BurgundyLight,
        Color(0xFFFF6B35)
    )
    
    // Success gradient
    val Success = listOf(
        Color(0xFF2ECC71),
        BrandColors.Teal
    )
}

// ═══════════════════════════════════════════════════════════════════════
// Extended Color Palette - For additional use cases
// ═══════════════════════════════════════════════════════════════════════
object ExtendedColors {
    // Social media colors (for share buttons)
    val Facebook = Color(0xFF1877F2)
    val Instagram = Color(0xFFE4405F)
    val Twitter = Color(0xFF1DA1F2)
    val LinkedIn = Color(0xFF0A66C2)
    val Snapchat = Color(0xFFFFFC00)
    
    // Category colors
    val Academic = Color(0xFF3B82F6)
    val Social = Color(0xFFEC4899)
    val Sports = Color(0xFF10B981)
    val Arts = Color(0xFF8B5CF6)
    val Career = Color(0xFFF59E0B)
    val Wellness = Color(0xFF06B6D4)
}