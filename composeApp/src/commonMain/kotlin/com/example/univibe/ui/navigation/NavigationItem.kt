package com.example.univibe.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Sealed class representing navigation destinations in UniVibe app.
 * Each destination has a route, icon, and label for the bottom navigation.
 */
sealed class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    data object Home : NavigationItem(
        route = "home",
        icon = Icons.Default.Home,
        label = "Home"
    )

    data object Discover : NavigationItem(
        route = "discover",
        icon = Icons.Default.Search,
        label = "Discover"
    )

    data object Hub : NavigationItem(
        route = "hub",
        icon = Icons.Default.Add,
        label = "Hub"
    )

    data object Messages : NavigationItem(
        route = "messages",
        icon = Icons.Default.ChatBubble,
        label = "Messages"
    )

    data object Profile : NavigationItem(
        route = "profile",
        icon = Icons.Default.Person,
        label = "Profile"
    )

    companion object {
        fun getAllItems(): List<NavigationItem> = listOf(
            Home,
            Discover,
            Hub,
            Messages,
            Profile
        )

        fun getItemByRoute(route: String): NavigationItem? {
            return getAllItems().find { it.route == route }
        }
    }
}