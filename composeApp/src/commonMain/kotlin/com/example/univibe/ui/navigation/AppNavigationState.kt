package com.example.univibe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Navigation state manager for the UniVibe app.
 * Manages the current route and navigation history.
 */
class AppNavigationState {
    private val currentRouteState = mutableStateOf<String>(NavigationRoute.Home.route)
    private val navigationStack = mutableListOf(NavigationRoute.Home.route)
    
    val currentRoute: String
        get() = currentRouteState.value
    
    /**
     * Navigate to a new route.
     * @param route The route to navigate to.
     * @param clearStack Whether to clear the back stack (for main routes).
     */
    fun navigate(route: String, clearStack: Boolean = false) {
        if (route == currentRouteState.value) return
        
        if (clearStack) {
            navigationStack.clear()
            navigationStack.add(NavigationRoute.Home.route)
        }
        
        navigationStack.add(route)
        currentRouteState.value = route
    }
    
    /**
     * Navigate back to the previous route.
     */
    fun goBack(): Boolean {
        return if (navigationStack.size > 1) {
            navigationStack.removeAt(navigationStack.size - 1)
            currentRouteState.value = navigationStack.last()
            true
        } else {
            false
        }
    }
    
    /**
     * Navigate to a main route (clears detail screens from stack).
     */
    fun navigateToMainRoute(mainRoute: String) {
        navigate(mainRoute, clearStack = true)
    }
    
    /**
     * Check if we can go back.
     */
    fun canGoBack(): Boolean = navigationStack.size > 1
}

/**
 * Create and remember an AppNavigationState.
 */
@Composable
fun rememberAppNavigationState(): AppNavigationState {
    return remember { AppNavigationState() }
}