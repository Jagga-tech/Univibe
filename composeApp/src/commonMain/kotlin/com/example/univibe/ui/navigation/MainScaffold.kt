package com.example.univibe.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Main app scaffold that combines the bottom navigation bar with the content area.
 * This serves as the primary layout structure for the UniVibe app.
 *
 * The bottom navigation bar is only shown for main routes (Home, Discover, Hub, Messages, Profile).
 * Detail screens and modals do not show the bottom nav.
 *
 * @param currentRoute The currently active navigation route.
 * @param onNavigate Callback when a navigation item is clicked.
 * @param content The main content composable to display in the center.
 */
@Composable
fun MainScaffold(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            // Only show bottom nav bar for main routes
            if (shouldShowBottomNavBar(currentRoute)) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = onNavigate
                )
            }
        }
    ) { paddingValues ->
        // Content is automatically padded to avoid overlap with bottom bar
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            content()
        }
    }
}