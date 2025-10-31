package com.example.univibe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.univibe.ui.theme.UniVibeTheme
import com.example.univibe.ui.navigation.VoyagerMainScaffold

/**
 * Main App composable that sets up the complete navigation structure for UniVibe.
 * 
 * This is the root composable that:
 * - Initializes the theme
 * - Sets up Voyager-based navigation with tabs
 * - Renders the main scaffold with responsive navigation (bottom nav, rail, or drawer)
 * - Manages state across tab navigation
 */
@Composable
@Preview
fun App() {
    UniVibeTheme {
        // Main scaffold with responsive navigation (bottom bar for phone, rail for tablet, drawer for desktop)
        VoyagerMainScaffold()
    }
}