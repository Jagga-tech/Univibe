package com.example.univibe.ui.theme

import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp

/**
 * Elevation levels for UniVibe following Material 3 guidelines.
 * Use these values for consistent card elevations and shadow depths.
 * 
 * Elevation creates visual hierarchy and improves app structure clarity.
 * Higher elevation = more prominent/interactive elements.
 */
object UniVibeElevation {
    // No elevation - flat surfaces
    val level0 = 0.dp
    
    // Subtle elevation - slightly raised surfaces (cards, buttons at rest)
    val level1 = 1.dp
    
    // Low elevation - small cards, chips, toggles
    val level2 = 3.dp
    
    // Medium elevation - interactive cards, input fields
    val level3 = 6.dp
    
    // High elevation - floating action buttons, modals, bottom sheets
    val level4 = 8.dp
    
    // Very high elevation - important modals, full-screen dialogs
    val level5 = 12.dp
    
    // ============== SEMANTIC ELEVATION ASSIGNMENTS ==============
    
    /**
     * Elevation for standard cards (posts, events, groups)
     */
    val cardDefault = level2
    
    /**
     * Elevation for interactive cards (hoverable on desktop, pressable)
     */
    val cardInteractive = level3
    
    /**
     * Elevation for cards in elevated/focus state
     */
    val cardFocused = level4
    
    /**
     * Elevation for buttons in normal state
     */
    val buttonDefault = level0
    
    /**
     * Elevation for buttons in hovered/active state
     */
    val buttonActive = level2
    
    /**
     * Elevation for input fields
     */
    val inputField = level1
    
    /**
     * Elevation for chips and small interactive elements
     */
    val chip = level1
    
    /**
     * Elevation for bottom navigation bar
     */
    val bottomNav = level3
    
    /**
     * Elevation for floating action buttons
     */
    val fab = level4
    
    /**
     * Elevation for floating elements (stickers, tooltips)
     */
    val floating = level4
    
    /**
     * Elevation for modal dialogs
     */
    val modal = level5
    
    /**
     * Elevation for bottom sheet components
     */
    val bottomSheet = level5
    
    /**
     * Elevation for top app bar (if prominent)
     */
    val appBar = level2
    
    /**
     * Elevation for dropdown menus
     */
    val dropdown = level4
}

/**
 * Helper composable for applying elevations consistently.
 * 
 * Usage:
 * ```
 * Surface(
 *     elevation = UniVibeElevation.cardDefault,
 *     shape = RoundedCornerShape(Dimensions.CornerRadius.large)
 * ) {
 *     // Content
 * }
 * ```
 */