package com.example.univibe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.univibe.ui.theme.UniVibeTheme
import com.example.univibe.ui.screens.auth.SplashScreen
import cafe.adriel.voyager.navigator.Navigator

/**
 * Main App composable that sets up the complete navigation structure for UniVibe.
 * 
 * This is the root composable that:
 * - Initializes the theme
 * - Starts with beautiful authentication flow
 * - Sets up Voyager-based navigation
 * - Manages app-wide navigation state
 */
@Composable
@Preview
fun App() {
    UniVibeTheme {
        // Start with authentication flow using Voyager Navigator
        // The SplashScreen will handle navigation to subsequent auth screens
        Navigator(SplashScreen)
    }
}