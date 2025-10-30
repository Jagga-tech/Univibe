package com.example.univibe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.univibe.ui.theme.UniVibeTheme
import com.example.univibe.ui.navigation.rememberAppNavigationState
import com.example.univibe.ui.navigation.MainScaffold
import com.example.univibe.ui.navigation.AppNavigationGraph
import com.example.univibe.ui.navigation.NavigationRoute

/**
 * Main App composable that sets up the complete navigation structure for UniVibe.
 * 
 * This is the root composable that:
 * - Initializes the theme
 * - Sets up navigation state management
 * - Renders the main scaffold with bottom navigation
 * - Routes to the appropriate screen based on the current navigation state
 */
@Composable
@Preview
fun App() {
    UniVibeTheme {
        // Create and remember the navigation state
        val navigationState = rememberAppNavigationState()
        val currentRoute = navigationState.currentRoute
        
        // Main scaffold with bottom navigation and content
        MainScaffold(
            currentRoute = currentRoute,
            onNavigate = { route ->
                // When clicking bottom nav items, navigate to main route and clear stack
                navigationState.navigateToMainRoute(route)
            },
            modifier = Modifier.fillMaxSize()
        ) {
            // Render the appropriate screen based on current route
            AppNavigationGraph(
                navigationState = navigationState,
                currentRoute = currentRoute
            )
        }
    }
}