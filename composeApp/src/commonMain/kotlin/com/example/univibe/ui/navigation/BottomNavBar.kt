package com.example.univibe.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.univibe.ui.components.TextIcon

/**
 * Bottom navigation bar for UniVibe with 5 main navigation items.
 * Shows icon and label for each item.
 *
 * @param currentRoute The currently active navigation route.
 * @param onNavigate Callback when a navigation item is clicked.
 */
@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        NavigationItem.getAllItems().forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = {
                    TextIcon(
                        symbol = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}

/**
 * Check if bottom navigation bar should be visible for the current route.
 * Only shown for main routes (Home, Discover, Hub, Messages, Profile).
 */
fun shouldShowBottomNavBar(currentRoute: String): Boolean {
    return NavigationRoute.isMainRoute(currentRoute)
}