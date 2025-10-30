# Navigation System - Quick Reference

## 🗺️ Available Routes

| Route | Type | Bottom Nav | Screen |
|-------|------|-----------|--------|
| `home` | Main | ✅ Yes | HomeScreen |
| `discover` | Main | ✅ Yes | DiscoverScreen |
| `hub` | Main | ✅ Yes | HubScreen |
| `messages` | Main | ✅ Yes | MessagesScreen |
| `profile` | Main | ✅ Yes | ProfileScreen |
| `search_results` | Detail | ❌ No | SearchResultsScreen |
| `settings` | Detail | ❌ No | SettingsScreen |
| `study_sessions` | Detail | ❌ No | StudySessionsScreen |
| `notifications` | Detail | ❌ No | NotificationsScreen |
| `chat_detail/{userId}` | Detail | ❌ No | ChatScreen |
| `event_details/{eventId}` | Detail | ❌ No | EventDetailsScreen |
| `group_details/{groupId}` | Detail | ❌ No | GroupDetailsScreen |

---

## 🚀 Common Navigation Commands

### Get Navigation State (in any screen)
```kotlin
val navigationState = rememberAppNavigationState()
```

### Navigate to Detail Screen
```kotlin
// Search Results
navigationState.navigate(NavigationRoute.SearchResults.route)

// Settings
navigationState.navigate(NavigationRoute.Settings.route)

// Chat with specific user
navigationState.navigate(NavigationRoute.ChatDetail("user123").route)

// Event details
navigationState.navigate(NavigationRoute.EventDetails("event456").route)

// Group details
navigationState.navigate(NavigationRoute.GroupDetails("group789").route)
```

### Navigate to Main Tab (clears history)
```kotlin
navigationState.navigateToMainRoute(NavigationRoute.Home.route)
navigationState.navigateToMainRoute(NavigationRoute.Discover.route)
navigationState.navigateToMainRoute(NavigationRoute.Hub.route)
navigationState.navigateToMainRoute(NavigationRoute.Messages.route)
navigationState.navigateToMainRoute(NavigationRoute.Profile.route)
```

### Go Back
```kotlin
navigationState.goBack()
```

### Check if Back is Possible
```kotlin
if (navigationState.canGoBack()) {
    navigationState.goBack()
}
```

---

## 📱 Screen Callbacks (What each screen should implement)

### Main Screens (5)

**HomeScreen**
```kotlin
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit
)
```

**DiscoverScreen, HubScreen, MessagesScreen, ProfileScreen**
```kotlin
@Composable
fun DiscoverScreen(onBackClick: () -> Unit)
```

### Detail Screens (7)

**All detail screens have same pattern**
```kotlin
@Composable
fun SettingsScreen(onBackClick: () -> Unit)
```

**Parameterized screens**
```kotlin
@Composable
fun ChatScreen(userId: String, onBackClick: () -> Unit)

@Composable
fun EventDetailsScreen(eventId: String, onBackClick: () -> Unit)

@Composable
fun GroupDetailsScreen(groupId: String, onBackClick: () -> Unit)
```

---

## 🔄 Navigation Flow Patterns

### Pattern 1: Navigate from Main to Detail
```kotlin
HomeScreen
  ↓
user clicks search
  ↓
navigationState.navigate(SearchResults)
  ↓
SearchResultsScreen (no bottom nav)
```

### Pattern 2: Go Back from Detail to Main
```kotlin
SearchResultsScreen
  ↓
user clicks back button
  ↓
onBackClick → navigationState.goBack()
  ↓
HomeScreen (bottom nav visible)
```

### Pattern 3: Navigate with Parameters
```kotlin
HubScreen
  ↓
user clicks event
  ↓
navigationState.navigate(
    NavigationRoute.EventDetails(eventId).route
)
  ↓
EventDetailsScreen(eventId) (no bottom nav)
```

### Pattern 4: Switch Main Tabs
```kotlin
HomeScreen
  ↓
user clicks "Discover" in bottom nav
  ↓
navigationState.navigateToMainRoute(Discover)
  ↓
DiscoverScreen (bottom nav updated, back stack cleared)
```

---

## 🎯 Implementation Checklist (Adding a New Screen)

### Step 1: Add Route
```kotlin
// In NavigationRoute.kt
data object MyNewScreen : NavigationRoute("my_new_screen")
```

### Step 2: Create Composable
```kotlin
@Composable
fun MyNewScreen(
    onBackClick: () -> Unit = {}
) {
    // Your screen content
}
```

### Step 3: Add to Graph
```kotlin
// In AppNavigationGraph.kt
currentRoute == NavigationRoute.MyNewScreen.route -> {
    MyNewScreen(onBackClick = { navigationState.goBack() })
}
```

### Step 4: Navigate to It
```kotlin
navigationState.navigate(NavigationRoute.MyNewScreen.route)
```

---

## 🔍 Route Helpers

### Check if Route is Main (shows bottom nav)
```kotlin
NavigationRoute.isMainRoute("home")  // true
NavigationRoute.isMainRoute("settings")  // false
```

### Get Base Route (strip parameters)
```kotlin
NavigationRoute.getBaseRoute("chat_detail/user123")  // "chat_detail"
```

### Get All Main Routes
```kotlin
NavigationRoute.getMainRoutes()  // {"home", "discover", "hub", "messages", "profile"}
```

---

## 🛠️ Debugging

### Print Current Route
```kotlin
println("Current route: ${navigationState.currentRoute}")
```

### Check Navigation Stack
```kotlin
// In AppNavigationState.kt (add debug function)
fun debugStack() {
    println("Stack: ${navigationStack.joinToString(" → ")}")
}
```

### Verify Route Exists
```kotlin
try {
    navigationState.navigate(route)
} catch (e: Exception) {
    println("Invalid route: $route")
}
```

---

## ⚠️ Common Mistakes

### ❌ Don't: Create new AppNavigationState each time
```kotlin
fun HomeScreen() {
    val nav = rememberAppNavigationState()  // ❌ New instance
}
```

### ✅ Do: Pass navigation state through
```kotlin
@Composable
fun HomeScreen(navigationState: AppNavigationState)
```

### ❌ Don't: Navigate from screen without callback
```kotlin
fun MyScreen() {
    // No way to navigate - screen is isolated
}
```

### ✅ Do: Accept callback
```kotlin
fun MyScreen(onNavigate: (String) -> Unit) {
    // Can navigate now
}
```

### ❌ Don't: Build route strings manually
```kotlin
navigate("chat_detail/" + userId)  // ❌ String building
```

### ✅ Do: Use route class
```kotlin
navigate(NavigationRoute.ChatDetail(userId).route)  // ✅ Type-safe
```

---

## 📊 Quick Stats

- **Total Routes**: 12
- **Main Routes**: 5
- **Detail Routes**: 7
- **Parameterized Routes**: 3 (chat, event, group)
- **Navigation Files**: 4 (Route, State, Graph, Scaffold)
- **Screens Integrated**: 12

---

## 🔗 Related Files

| File | Purpose |
|------|---------|
| `NavigationRoute.kt` | Define all routes |
| `AppNavigationState.kt` | Manage state & history |
| `AppNavigationGraph.kt` | Route to screens |
| `MainScaffold.kt` | Layout with nav |
| `BottomNavBar.kt` | 5 main tabs |
| `App.kt` | Entry point |
| `NAVIGATION_GUIDE.md` | Full documentation |

---

## 💡 Tips & Tricks

### Tip 1: Make callbacks optional with defaults
```kotlin
@Composable
fun MyScreen(
    onBackClick: () -> Unit = {}  // Safe default
)
```

### Tip 2: Extract userId from route in composable
```kotlin
ChatScreen(
    userId = currentRoute.substringAfterLast("/"),
    onBackClick = { navigationState.goBack() }
)
```

### Tip 3: Use NavigationRoute helpers to avoid string errors
```kotlin
// Good - won't compile if typo
val route = NavigationRoute.Settings.route

// Bad - won't catch typo
val route = "settings"  // Could be "setting" typo
```

### Tip 4: Always check canGoBack before critical operations
```kotlin
if (navigationState.canGoBack()) {
    navigationState.goBack()
} else {
    // Root screen - maybe exit app or show menu
}
```

---

## 🎓 Learn More

See `NAVIGATION_GUIDE.md` for:
- Complete architecture overview
- Detailed routing logic
- Screen integration patterns
- Best practices
- Testing examples
- How to add new screens

---

**Navigation System Ready to Use!** ✅
