package com.example.univibe.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.*
import com.example.univibe.ui.theme.*
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.components.UISymbols
import com.example.univibe.ui.screens.home.HomeTab
import com.example.univibe.ui.screens.discover.DiscoverTab
import com.example.univibe.ui.screens.hub.HubTab
import com.example.univibe.ui.screens.messages.MessagesTab
import com.example.univibe.ui.screens.profile.ProfileTab

/**
 * Window Size Classes for responsive design
 */
enum class WindowSize {
    Compact,   // Phone: < 600dp
    Medium,    // Tablet: 600dp - 840dp
    Expanded   // Desktop: > 840dp
}

/**
 * Main Scaffold with responsive navigation using Voyager
 * 
 * Adapts navigation UI based on screen size:
 * - Compact (Phone): Bottom Navigation Bar
 * - Medium (Tablet): Navigation Rail
 * - Expanded (Desktop): Permanent Navigation Drawer + Right Panel
 */
@Composable
fun VoyagerMainScaffold() {
    // Cross-platform window size detection using BoxWithConstraints
    // Breakpoints follow common Material guidance: 0-599 (Compact), 600-839 (Medium), 840+ (Expanded)
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val widthDp = maxWidth
        val windowSize = remember(widthDp) {
            when {
                widthDp < 600.dp -> WindowSize.Compact
                widthDp < 840.dp -> WindowSize.Medium
                else -> WindowSize.Expanded
            }
        }

        TabNavigator(HomeTab) { navigator ->
            when (windowSize) {
                WindowSize.Compact -> navigator.CompactScaffold()
                WindowSize.Medium -> navigator.MediumScaffold()
                WindowSize.Expanded -> navigator.ExpandedScaffold()
            }
        }
    }
}

/**
 * Compact Layout (Phone)
 * Bottom navigation bar
 */
@Composable
private fun TabNavigator.CompactScaffold() {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            CurrentTab()
        }
    }
}

/**
 * Medium Layout (Tablet)
 * Navigation rail on left
 */
@Composable
private fun TabNavigator.MediumScaffold() {
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRailComponent()
        Box(modifier = Modifier.fillMaxSize()) {
            CurrentTab()
        }
    }
}

/**
 * Expanded Layout (Desktop)
 * Permanent drawer on left + optional right panel
 */
@Composable
private fun TabNavigator.ExpandedScaffold() {
    Row(modifier = Modifier.fillMaxSize()) {
        PermanentNavigationDrawerComponent()
        Box(
            modifier = Modifier
                .weight(1f)
                .widthIn(max = 1200.dp)
        ) {
            CurrentTab()
        }
        // TODO: Optional right panel for trending, suggestions, etc.
    }
}

/**
 * Bottom Navigation Bar (Phone)
 */
@Composable
private fun TabNavigator.BottomNavigationBar() {
    NavigationBar(
        tonalElevation = 8.dp
    ) {
        TabNavigationItem(HomeTab)
        TabNavigationItem(DiscoverTab)
        TabNavigationItem(HubTab)
        TabNavigationItem(MessagesTab)
        TabNavigationItem(ProfileTab)
    }
}

/**
 * Navigation Rail (Tablet)
 */
@Composable
private fun TabNavigator.NavigationRailComponent() {
    NavigationRail(
        modifier = Modifier.fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        TabNavigationRailItem(HomeTab)
        TabNavigationRailItem(DiscoverTab)
        TabNavigationRailItem(HubTab)
        TabNavigationRailItem(MessagesTab)
        TabNavigationRailItem(ProfileTab)
    }
}

/**
 * Permanent Navigation Drawer (Desktop)
 */
@Composable
private fun TabNavigator.PermanentNavigationDrawerComponent() {
    PermanentDrawerSheet(
        modifier = Modifier.width(240.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        // Logo
        Text(
            "UniVibe",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TabNavigationDrawerItem(HomeTab)
        TabNavigationDrawerItem(DiscoverTab)
        TabNavigationDrawerItem(HubTab)
        TabNavigationDrawerItem(MessagesTab)
        TabNavigationDrawerItem(ProfileTab)
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Settings at bottom
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            onClick = { /* Navigate to settings */ },
            icon = { TextIcon(symbol = UISymbols.SETTINGS, contentDescription = "Settings") }
        )
    }
}

/**
 * Bottom Navigation Item
 */
@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab
    
    NavigationBarItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(
                    painter = painter,
                    contentDescription = tab.options.title
                )
            }
        },
        label = { Text(tab.options.title) }
    )
}

/**
 * Navigation Rail Item
 */
@Composable
private fun TabNavigator.TabNavigationRailItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab
    
    NavigationRailItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(
                    painter = painter,
                    contentDescription = tab.options.title
                )
            }
        },
        label = { Text(tab.options.title) }
    )
}

/**
 * Navigation Drawer Item
 */
@Composable
private fun TabNavigator.TabNavigationDrawerItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab
    
    NavigationDrawerItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(
                    painter = painter,
                    contentDescription = tab.options.title
                )
            }
        },
        label = { Text(tab.options.title) }
    )
}