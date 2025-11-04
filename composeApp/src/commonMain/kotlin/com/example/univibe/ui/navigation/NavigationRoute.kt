package com.example.univibe.ui.navigation

/**
 * Sealed class representing all navigation routes in UniVibe app.
 * Divided into main routes (bottom nav visible) and detail routes (bottom nav hidden).
 */
sealed class NavigationRoute(val route: String) {
    
    // ============ MAIN ROUTES (Bottom Navigation Visible) ============
    data object Home : NavigationRoute("home")
    data object Discover : NavigationRoute("discover")
    data object Hub : NavigationRoute("hub")
    data object Messages : NavigationRoute("messages")
    data object Profile : NavigationRoute("profile")
    
    // ============ DETAIL/MODAL ROUTES (Bottom Navigation Hidden) ============
    
    // Search-related
    data object SearchResults : NavigationRoute("search_results")
    
    // Settings-related
    data object Settings : NavigationRoute("settings")
    data object ThemePicker : NavigationRoute("theme_picker")
    
    // Chat/Messaging details
    data class ChatDetail(val userId: String) : NavigationRoute("chat_detail/$userId") {
        companion object {
            fun createRoute(userId: String) = "chat_detail/$userId"
        }
    }
    
    // Events details
    data class EventDetails(val eventId: String) : NavigationRoute("event_details/$eventId") {
        companion object {
            fun createRoute(eventId: String) = "event_details/$eventId"
        }
    }
    
    // Groups details
    data class GroupDetails(val groupId: String) : NavigationRoute("group_details/$groupId") {
        companion object {
            fun createRoute(groupId: String) = "group_details/$groupId"
        }
    }
    
    // Study Sessions
    data object StudySessions : NavigationRoute("study_sessions")
    
    // Notifications/Activity
    data object Notifications : NavigationRoute("notifications")
    
    companion object {
        /**
         * Routes where bottom navigation should be visible.
         * These are the 5 main tab screens.
         */
        fun getMainRoutes(): Set<String> = setOf(
            Home.route,
            Discover.route,
            Hub.route,
            Messages.route,
            Profile.route
        )
        
        /**
         * Check if a route is a main route (should show bottom nav).
         */
        fun isMainRoute(route: String): Boolean {
            return getMainRoutes().contains(route.substringBefore("/"))
        }
        
        /**
         * Get the base route (without parameters).
         */
        fun getBaseRoute(route: String): String {
            return route.substringBefore("/")
        }
    }
}