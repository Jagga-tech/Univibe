package com.example.univibe

import androidx.compose.ui.window.ComposeUIViewController
import com.example.univibe.data.preferences.ThemePreferences
import com.example.univibe.data.storage.IosThemeStorage

fun MainViewController() = ComposeUIViewController {
    // Initialize theme preferences with iOS storage on app startup
    ThemePreferences.initialize(IosThemeStorage())
    
    App()
}