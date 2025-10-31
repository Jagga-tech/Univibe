package com.example.univibe.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home as HomeOutlined
import androidx.compose.material.icons.outlined.Search as SearchOutlined
import androidx.compose.material.icons.outlined.Dashboard as DashboardOutlined
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Person as PersonOutlined
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Navigation Screens
 * 
 * Defines all screens in the app with their icons and labels for Voyager navigation
 */
sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null,
    val selectedIcon: ImageVector? = null
) {
    // Main Tab Screens
    data object Home : Screen(
        route = "home",
        title = "Home",
        icon = HomeOutlined,
        selectedIcon = Home
    )
    
    data object Discover : Screen(
        route = "discover",
        title = "Discover",
        icon = SearchOutlined,
        selectedIcon = Search
    )
    
    data object Hub : Screen(
        route = "hub",
        title = "Hub",
        icon = DashboardOutlined,
        selectedIcon = Dashboard
    )
    
    data object Messages : Screen(
        route = "messages",
        title = "Messages",
        icon = ChatBubbleOutline,
        selectedIcon = ChatBubble
    )
    
    data object Profile : Screen(
        route = "profile",
        title = "Profile",
        icon = PersonOutlined,
        selectedIcon = Person
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