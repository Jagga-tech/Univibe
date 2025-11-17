package com.example.univibe.ui.screens.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.univibe.ui.navigation.VoyagerMainScaffold

/**
 * Entry point to the main authenticated app
 * This screen loads the main app interface after successful authentication
 */
object MainAppEntry : Screen {
    @Composable
    override fun Content() {
        // Load the main app scaffold with bottom navigation
        VoyagerMainScaffold()
    }
}