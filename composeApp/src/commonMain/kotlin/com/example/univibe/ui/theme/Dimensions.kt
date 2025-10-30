package com.example.univibe.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Central location for all dimension constants used throughout UniVibe app.
 * This includes spacing, corner radius, icon sizes, avatar sizes, and button heights.
 * 
 * Based on 4dp base unit for perfect alignment and consistency.
 */
object Dimensions {
    // ============== SPACING (4dp base unit) ==============
    object Spacing {
        // Extra small - 2dp (half unit)
        val xxs = 2.dp
        
        // Extra small - 4dp (base unit)
        val xs = 4.dp
        
        // Small - 8dp (2x base)
        val sm = 8.dp
        
        // Medium - 12dp (3x base)
        val md = 12.dp
        
        // Default - 16dp (4x base) - most common card padding
        val default = 16.dp
        
        // Large - 24dp (6x base) - section spacing
        val lg = 24.dp
        
        // Extra large - 32dp (8x base)
        val xl = 32.dp
        
        // Extra extra large - 48dp (12x base)
        val xxl = 48.dp
        
        // Extra extra extra large - 64dp (16x base)
        val xxxl = 64.dp
    }

    // ============== CORNER RADIUS ==============
    object CornerRadius {
        val small = 8.dp    // Small elements (chips, buttons)
        val medium = 12.dp  // Medium elements (cards, dialogs)
        val large = 16.dp   // Large elements (main cards, sheets)
        val extraLarge = 20.dp // Extra large elements (hero sections)
        val full = 9999.dp  // Circular elements
    }

    // ============== ICON SIZES ==============
    object IconSize {
        val tiny = 16.dp      // Extra small icons
        val small = 20.dp     // Small icons (in text)
        val medium = 24.dp    // Medium icons (standard)
        val large = 32.dp     // Large icons (prominent)
        val extraLarge = 48.dp // Extra large icons
    }

    // ============== AVATAR SIZES ==============
    object AvatarSize {
        val tiny = 24.dp      // Tiny avatars (inline)
        val small = 32.dp     // Small avatars (lists)
        val medium = 48.dp    // Medium avatars (standard)
        val large = 80.dp     // Large avatars (profiles)
        val xlarge = 120.dp   // Extra large avatars (hero sections)
    }

    // ============== TOUCH TARGET & INTERACTION ==============
    val MinTouchTarget = 48.dp // Minimum touch target size (Material Design)

    // ============== BUTTON DIMENSIONS ==============
    val ButtonHeight = 48.dp
    val ButtonHeightSmall = 40.dp
    val ButtonHeightLarge = 56.dp

    // ============== INPUT FIELD DIMENSIONS ==============
    val InputHeight = 56.dp
    val InputHeightSmall = 44.dp

    // ============== LAYOUT CONSTANTS ==============
    object Layout {
        // Max content width for desktop feed (prevents too-wide layouts)
        val MaxContentWidth = 720.dp
        
        // Standard card corner radius
        val CardCornerRadius = 16.dp
        
        // Small elements corner radius
        val SmallCornerRadius = 8.dp
    }

    // ============== CARD & COMPONENT DIMENSIONS ==============
    object Card {
        val DefaultPadding = Spacing.default
        val DefaultCornerRadius = Layout.CardCornerRadius
    }

    // ============== BOTTOM NAV & APP BARS ==============
    val BottomNavHeight = 80.dp
    val TopAppBarHeight = 64.dp

    // ============== DIALOG & MODAL ==============
    val DialogCornerRadius = 20.dp
}