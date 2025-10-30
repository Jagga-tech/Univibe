# UniVibe Navigation System - Complete Guide

## Overview

The UniVibe app uses a unified navigation system that manages all 12 screens with proper state management and screen hierarchy. The system is built on:

- **NavigationRoute** - Sealed class defining all available routes
- **AppNavigationState** - State manager handling navigation history and current route
- **AppNavigationGraph** - Router that renders the appropriate screen
- **MainScaffold** - Layout wrapper with conditional bottom navigation
- **BottomNavBar** - Bottom navigation for main screens

---

## Architecture

### 1. **Navigation Hierarchy**

The app has two types of screens:

#### **Main Routes** (Bottom Navigation Visible)
- Home (`home`)
- Discover (`discover`)
- Hub (`hub`)
- Messages (`messages`)
- Profile (`profile`)

#### **Detail Routes** (Bottom Navigation Hidden)
- Search Results (`search_results`)
- Settings (`settings`)
- Study Sessions (`study_sessions`)
- Notifications (`notifications`)
- Chat Detail (`chat_detail/{userId}`)
- Event Details (`event_details/{eventId}`)
- Group Details (`group_details/{groupId}`)

---

## Navigation Routes

### Sealed Class: `NavigationRoute`

```kotlin
sealed class NavigationRoute(val route: String)
```

#### Main Routes (5)
```
- Home.route = "home"
- Discover.route = "discover"
- Hub.route = "hub"
- Messages.route = "messages"
- Profile.route = "profile"
```

#### Detail Routes (8)
```
- SearchResults.route = "search_results"
- Settings.route = "settings"
- StudySessions.route = "study_sessions"
- Notifications.route = "notifications"
- ChatDetail(userId).route = "chat_detail/{userId}"
- EventDetails(eventId).route = "event_details/{eventId}"
- GroupDetails(groupId).route = "group_details/{groupId}"
```

#### Helper Methods
```kotlin
NavigationRoute.getMainRoutes()      // Returns Set of main route strings
NavigationRoute.isMainRoute(route)   // Check if route shows bottom nav
NavigationRoute.getBaseRoute(route)  // Extract base route (ignores params)
```

---

## Navigation State Management

### Class: `AppNavigationState`

Manages navigation history and current route.

#### Key Methods

```kotlin
// Navigate to a new route
fun navigate(route: String, clearStack: Boolean = false)

// Go back to previous route (returns true if successful)
fun goBack(): Boolean

// Navigate to main route (clears back stack)
fun navigateToMainRoute(mainRoute: String)

// Check if back is possible
fun canGoBack(): Boolean
```

#### Usage in Composables

```kotlin
// Create/remember navigation state
val navigationState = rememberAppNavigationState()

// Navigate
navigationState.navigate(NavigationRoute.SearchResults.route)

// Navigate to main route (clears detail screens)
navigationState.navigateToMainRoute(NavigationRoute.Home.route)

// Go back
navigationState.goBack()
```

---

## Screen Rendering

### Component: `AppNavigationGraph`

Routes to the correct screen based on current route.

```kotlin
@Composable
fun AppNavigationGraph(
    navigationState: AppNavigationState,
    currentRoute: String
)
```

#### Screen Routing Logic

```
currentRoute == "home" → HomeScreen()
currentRoute == "discover" → DiscoverScreen()
currentRoute == "hub" → HubScreen()
currentRoute == "messages" → MessagesScreen()
currentRoute == "profile" → ProfileScreen()
currentRoute == "search_results" → SearchResultsScreen()
currentRoute == "settings" → SettingsScreen()
currentRoute == "study_sessions" → StudySessionsScreen()
currentRoute == "notifications" → NotificationsScreen()
currentRoute.startsWith("chat_detail/") → ChatScreen(userId)
currentRoute.startsWith("event_details/") → EventDetailsScreen(eventId)
currentRoute.startsWith("group_details/") → GroupDetailsScreen(groupId)
```

---

## Screen Integration

Each screen receives navigation callbacks:

### Main Screens (5)
```kotlin
HomeScreen(
    onSearchClick = { navigate(SearchResults) },
    onNotificationClick = { navigate(Notifications) },
    onSettingsClick = { navigate(Settings) }
)

DiscoverScreen(onBackClick = { goBack() })
HubScreen(
    onEventClick = { eventId -> navigate(EventDetails(eventId)) },
    onGroupClick = { groupId -> navigate(GroupDetails(groupId)) },
    onBackClick = { goBack() }
)
MessagesScreen(
    onConversationClick = { userId -> navigate(ChatDetail(userId)) },
    onBackClick = { goBack() }
)
ProfileScreen(
    onSettingsClick = { navigate(Settings) },
    onBackClick = { goBack() }
)
```

### Detail Screens (8)
All detail screens have consistent `onBackClick` callbacks:

```kotlin
SearchResultsScreen(onBackClick = { goBack() })
SettingsScreen(onBackClick = { goBack() })
StudySessionsScreen(onBackClick = { goBack() })
NotificationsScreen(onBackClick = { goBack() })
ChatScreen(userId, onBackClick = { goBack() })
EventDetailsScreen(eventId, onBackClick = { goBack() })
GroupDetailsScreen(groupId, onBackClick = { goBack() })
```

---

## Bottom Navigation Behavior

### Conditional Display

The bottom navigation bar is only shown for main routes:

```kotlin
fun shouldShowBottomNavBar(currentRoute: String): Boolean {
    return NavigationRoute.isMainRoute(currentRoute)
}
```

### Navigation Handling

- **Clicking a bottom nav item**: Navigates to main route and clears the back stack
- **Detail screen opened**: Bottom nav bar automatically hidden
- **Back button on detail screen**: Returns to last main route with bar visible

---

## Common Navigation Patterns

### 1. From Home to Search Results
```kotlin
navigationState.navigate(NavigationRoute.SearchResults.route)
// Bottom nav hidden, back button returns to Home
```

### 2. From Hub to Event Details
```kotlin
navigationState.navigate(NavigationRoute.EventDetails(eventId).route)
// Bottom nav hidden, back button returns to Hub
```

### 3. From Messages to Chat Detail
```kotlin
navigationState.navigate(NavigationRoute.ChatDetail(userId).route)
// Bottom nav hidden, back button returns to Messages
```

### 4. Switching Main Tabs
```kotlin
navigationState.navigateToMainRoute(NavigationRoute.Discover.route)
// Bottom nav visible, back stack cleared (can't go back to Home)
```

### 5. Deep Link (from any screen to Settings)
```kotlin
navigationState.navigate(NavigationRoute.Settings.route)
// Bottom nav hidden, back button returns to previous screen
```

---

## App Composition

### Main App Structure (App.kt)

```
App
├── UniVibeTheme
├── AppNavigationState (state management)
└── MainScaffold
    ├── BottomNavBar (conditional)
    └── AppNavigationGraph
        └── Current Screen (12 options)
```

---

## Navigation Flow Examples

### Example 1: Home → Settings
```
1. User on HomeScreen
2. Clicks Settings
3. navigate(Settings) → bottom nav hides
4. SettingsScreen displayed with back button
5. Click back → goBack() → returns to HomeScreen
```

### Example 2: Home → Hub → Event Details
```
1. User on HomeScreen
2. Click bottom nav "Hub" → navigateToMainRoute(Hub)
3. HubScreen displayed
4. Click event → navigate(EventDetails(id))
5. EventDetailsScreen displayed, bottom nav hidden
6. Click back → goBack() → returns to HubScreen
7. Click back again → goBack() → returns to HomeScreen
```

### Example 3: Search Results → User Profile Navigation
```
1. From HomeScreen
2. Navigate to SearchResults
3. Click "Follow User" → navigate to UserProfile (if implemented)
4. Navigate to UserSettings
5. Click back → returns to UserProfile
6. Click back → returns to SearchResults
7. Click back → returns to HomeScreen
```

---

## Adding New Screens

### Step 1: Add Route to NavigationRoute.kt
```kotlin
data object MyNewScreen : NavigationRoute("my_new_screen")
```

### Step 2: Create Screen Composable
```kotlin
@Composable
fun MyNewScreen(
    onBackClick: () -> Unit = {}
) {
    // Screen content
}
```

### Step 3: Add to AppNavigationGraph.kt
```kotlin
currentRoute == NavigationRoute.MyNewScreen.route -> {
    MyNewScreen(onBackClick = { navigationState.goBack() })
}
```

### Step 4: Import and Use
```kotlin
navigationState.navigate(NavigationRoute.MyNewScreen.route)
```

---

## Best Practices

1. **Always provide onBackClick** - All screens should accept back callbacks
2. **Use navigateToMainRoute for tabs** - Clears back stack for main nav items
3. **Use navigate for details** - Preserves back stack for detail screens
4. **Extract parameters carefully** - Use `substringAfterLast("/")` for route params
5. **Handle invalid routes** - AppNavigationGraph has fallback to Home
6. **Remember navigation state** - Use `rememberAppNavigationState()` in preview

---

## Testing Navigation

### Test Main Route Navigation
```kotlin
navigationState.navigateToMainRoute("discover")
assert(navigationState.currentRoute == "discover")
assert(!navigationState.canGoBack())
```

### Test Detail Screen Navigation
```kotlin
navigationState.navigate("search_results")
assert(navigationState.canGoBack())
navigationState.goBack()
assert(navigationState.currentRoute == "home")
```

### Test Parameterized Routes
```kotlin
navigationState.navigate(NavigationRoute.ChatDetail("user123").route)
assert(navigationState.currentRoute == "chat_detail/user123")
```

---

## Screen Count & Status

✅ **12 Complete Screens**

### Main Routes (5)
1. ✅ Home Screen
2. ✅ Discover Screen
3. ✅ Hub Screen
4. ✅ Messages Screen
5. ✅ Profile Screen

### Detail Routes (7)
6. ✅ Search Results Screen
7. ✅ Settings Screen
8. ✅ Study Sessions Screen
9. ✅ Notifications Screen
10. ✅ Chat Detail Screen (parameterized)
11. ✅ Event Details Screen (parameterized)
12. ✅ Group Details Screen (parameterized)

All screens fully integrated with navigation system! 🎉
