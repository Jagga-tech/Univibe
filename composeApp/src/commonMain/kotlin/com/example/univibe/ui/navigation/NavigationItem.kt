package com.example.univibe.ui.navigation

import com.example.univibe.ui.utils.UISymbols

/**
 * Sealed class representing navigation destinations in UniVibe app.
 * Each destination has a route, icon, and label for the bottom navigation.
 */
sealed class NavigationItem(
    val route: String,
    val icon: String,
    val label: String
) {
    data object Home : NavigationItem(
        route = "home",
        icon = UISymbols.HOME,
        label = "Home"
    )

    data object Discover : NavigationItem(
        route = "discover",
        icon = UISymbols.SEARCH,
        label = "Discover"
    )

    data object Hub : NavigationItem(
        route = "hub",
        icon = UISymbols.ADD,
        label = "Hub"
    )

    data object Messages : NavigationItem(
        route = "messages",
        icon = UISymbols.CHAT_BUBBLE,
        label = "Messages"
    )

    data object Profile : NavigationItem(
        route = "profile",
        icon = UISymbols.PERSON,
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