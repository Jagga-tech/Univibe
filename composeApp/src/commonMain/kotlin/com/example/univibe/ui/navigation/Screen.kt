package com.example.univibe.ui.navigation

import com.example.univibe.ui.utils.UISymbols

/**
 * Navigation Screens
 * 
 * Defines all screens in the app with their icons and labels for Voyager navigation
 */
sealed class Screen(
    val route: String,
    val title: String,
    val icon: String? = null,
    val selectedIcon: String? = null
) {
    // Main Tab Screens
    data object Home : Screen(
        route = "home",
        title = "Home",
        icon = UISymbols.HOME_OUTLINED,
        selectedIcon = UISymbols.HOME_FILLED
    )
    
    data object Discover : Screen(
        route = "discover",
        title = "Discover",
        icon = UISymbols.SEARCH_OUTLINED,
        selectedIcon = UISymbols.SEARCH_FILLED
    )
    
    data object Hub : Screen(
        route = "hub",
        title = "Hub",
        icon = UISymbols.DASHBOARD_OUTLINED,
        selectedIcon = UISymbols.DASHBOARD_FILLED
    )
    
    data object Messages : Screen(
        route = "messages",
        title = "Messages",
        icon = UISymbols.CHAT_BUBBLE,
        selectedIcon = UISymbols.CHAT_BUBBLE
    )
    
    data object Profile : Screen(
        route = "profile",
        title = "Profile",
        icon = UISymbols.PERSON_OUTLINED,
        selectedIcon = UISymbols.PERSON_FILLED
    )
    
    // Auth Screens
    data object Splash : Screen("splash", "Splash")
    data object Welcome : Screen("welcome", "Welcome")
    data object Login : Screen("login", "Login")
    data object Register : Screen("register", "Register")
    data object ForgotPassword : Screen("forgot_password", "Forgot Password")
    data object Onboarding : Screen("onboarding", "Onboarding")
    
    // Detail Screens
    data class PostDetail(val postId: String) : Screen("post/$postId", "Post")
    data class UserProfile(val userId: String) : Screen("user/$userId", "Profile")
    data class Conversation(val conversationId: String) : Screen("conversation/$conversationId", "Chat")
    data class StudySessionDetail(val sessionId: String) : Screen("session/$sessionId", "Study Session")
    data class EventDetail(val eventId: String) : Screen("event/$eventId", "Event")
    data class ClubDetail(val clubId: String) : Screen("club/$clubId", "Club")
    
    // Create Screens
    data object CreatePost : Screen("create_post", "Create Post")
    data object CreateReel : Screen("create_reel", "Create Reel")
    data object CreateStudySession : Screen("create_session", "Create Study Session")
    data object CreateEvent : Screen("create_event", "Create Event")
    
    // Feature Screens
    data object Search : Screen("search", "Search")
    data object Notifications : Screen("notifications", "Notifications")
    data object Reels : Screen("reels", "Reels")
    data object Events : Screen("events", "Events")
    data object Clubs : Screen("clubs", "Clubs")
    data object StudySessions : Screen("study_sessions", "Study Sessions")
    data object Marketplace : Screen("marketplace", "Marketplace")
    data object Housing : Screen("housing", "Housing")
    data object Dining : Screen("dining", "Dining Hall")
    data object CampusMap : Screen("campus_map", "Campus Map")
    data object ShuttleTracker : Screen("shuttle", "Shuttle Tracker")
    data object Library : Screen("library", "Library")
    data object Settings : Screen("settings", "Settings")
    data object EditProfile : Screen("edit_profile", "Edit Profile")
    
    companion object {
        val mainTabScreens = listOf(Home, Discover, Hub, Messages, Profile)
    }
}