package com.example.univibe.ui.navigation

import androidx.compose.runtime.Composable
import com.example.univibe.ui.screens.home.HomeScreen
import com.example.univibe.ui.screens.discover.DiscoverScreen
import com.example.univibe.ui.screens.hub.HubScreen
import com.example.univibe.ui.screens.messages.MessagesScreen
import com.example.univibe.ui.screens.profile.ProfileScreen
import com.example.univibe.ui.screens.search.SearchResultsScreen
import com.example.univibe.ui.screens.settings.SettingsScreen
import com.example.univibe.ui.screens.settings.ThemePickerScreen
import com.example.univibe.ui.screens.messages.ChatScreen
import com.example.univibe.ui.screens.hub.EventDetailsScreen
import com.example.univibe.ui.screens.hub.GroupDetailsScreen
import com.example.univibe.ui.screens.studysessions.StudySessionsScreen
import com.example.univibe.ui.screens.notifications.NotificationsScreen

/**
 * Navigation graph that renders the appropriate screen based on the current route.
 * This is the main router for the UniVibe app.
 *
 * @param navigationState The current navigation state.
 * @param currentRoute The current route to display.
 */
@Composable
fun AppNavigationGraph(
    navigationState: AppNavigationState,
    currentRoute: String
) {
    when {
        // MAIN ROUTES (Bottom Navigation Visible)
        currentRoute == NavigationRoute.Home.route -> {
            HomeScreen(
                onNavigate = { route ->
                    when (route) {
                        "search" -> navigationState.navigate(NavigationRoute.SearchResults.route)
                        "notifications" -> navigationState.navigate(NavigationRoute.Notifications.route)
                        "settings" -> navigationState.navigate(NavigationRoute.Settings.route)
                        else -> navigationState.navigate(route)
                    }
                }
            )
        }
        
        currentRoute == NavigationRoute.Discover.route -> {
            DiscoverScreen(
                onBackClick = { navigationState.goBack() }
            )
        }
        
        currentRoute == NavigationRoute.Hub.route -> {
            HubScreen(
                onEventClick = { eventId ->
                    navigationState.navigate(NavigationRoute.EventDetails(eventId).route)
                },
                onGroupClick = { groupId ->
                    navigationState.navigate(NavigationRoute.GroupDetails(groupId).route)
                },
                onBackClick = { navigationState.goBack() }
            )
        }
        
        currentRoute == NavigationRoute.Messages.route -> {
            MessagesScreen(
                onConversationClick = { conversationId ->
                    navigationState.navigate(NavigationRoute.ChatDetail(conversationId).route)
                },
                onBackClick = { navigationState.goBack() }
            )
        }
        
        currentRoute == NavigationRoute.Profile.route -> {
            ProfileScreen(
                onSettingsClick = { navigationState.navigate(NavigationRoute.Settings.route) },
                onBackClick = { navigationState.goBack() }
            )
        }
        
        // DETAIL/MODAL ROUTES (Bottom Navigation Hidden)
        currentRoute == NavigationRoute.SearchResults.route -> {
            SearchResultsScreen(
                onBackClick = { navigationState.goBack() }
            )
        }
        
        currentRoute == NavigationRoute.Settings.route -> {
            SettingsScreen(
                onBackClick = { navigationState.goBack() },
                onThemeClick = { navigationState.navigate(NavigationRoute.ThemePicker.route) }
            )
        }
        
        currentRoute == NavigationRoute.ThemePicker.route -> {
            ThemePickerScreen(
                onBackClick = { navigationState.goBack() },
                onCustomThemeClick = { 
                    // Navigation to custom theme would be handled separately
                    // For now, we'll keep it simple and just show custom theme in picker
                }
            )
        }
        
        currentRoute == NavigationRoute.StudySessions.route -> {
            StudySessionsScreen(
                onBackClick = { navigationState.goBack() }
            )
        }
        
        currentRoute == NavigationRoute.Notifications.route -> {
            NotificationsScreen(
                onBackClick = { navigationState.goBack() }
            )
        }
        
        // PARAMETERIZED ROUTES
        currentRoute.startsWith("chat_detail/") -> {
            val conversationId = currentRoute.substringAfterLast("/")
            ChatScreen(conversationId).Content()
        }
        
        currentRoute.startsWith("event_details/") -> {
            val eventId = currentRoute.substringAfterLast("/")
            EventDetailsScreen(
                onBackClick = { navigationState.goBack() }
            )
        }
        
        currentRoute.startsWith("group_details/") -> {
            val groupId = currentRoute.substringAfterLast("/")
            GroupDetailsScreen(
                onBackClick = { navigationState.goBack() }
            )
        }
        
        // Default fallback to Home
        else -> {
            navigationState.navigateToMainRoute(NavigationRoute.Home.route)
            HomeScreen(
                onNavigate = { route ->
                    when (route) {
                        "search" -> navigationState.navigate(NavigationRoute.SearchResults.route)
                        "notifications" -> navigationState.navigate(NavigationRoute.Notifications.route)
                        "settings" -> navigationState.navigate(NavigationRoute.Settings.route)
                        else -> navigationState.navigate(route)
                    }
                }
            )
        }
    }
}