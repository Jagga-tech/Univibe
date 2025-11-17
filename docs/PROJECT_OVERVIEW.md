# UniVibe — Project Overview

This document gives you a high-level map of the UniVibe Kotlin Multiplatform (KMP) codebase so you can quickly find your way around and understand how the parts fit together.

## Tech Stack
- Kotlin Multiplatform (Android, iOS)
- Compose Multiplatform for UI
- Voyager for tab/navigation scaffolding
- Material 3 design system
- Kotlinx Serialization for models

## Top-level Modules
- composeApp: Shared KMP module containing common UI, domain models, mock data, and platform-specific sources.
- iosApp: iOS host application (entry point, assets, and Xcode project).

## Run/Build
- Android: ./gradlew :composeApp:assembleDebug
- iOS: Open iosApp in Xcode or use IDE run configuration.

## composeApp Structure

Key source sets inside composeApp/src:
- commonMain: Shared code for all platforms (most UI and domain logic).
- androidMain, iosMain: Platform-specific implementations where needed.
- commonTest: Shared tests.

### Entry Point
- commonMain/kotlin/com/example/univibe/App.kt
  - Root Composable. Applies UniVibeTheme and renders VoyagerMainScaffold (responsive navigation across tabs).

### Navigation
- commonMain/kotlin/com/example/univibe/ui/navigation/
  - VoyagerMainScaffold.kt: Defines WindowSize enum and 3 responsive scaffolds (Compact/Medium/Expanded). Provides BottomNavigationBar, NavigationRail, and Permanent Drawer UIs.
  - NavigationRoute.kt, NavigationItem.kt, NavigationHost.kt, AppNavigationGraph.kt, AppNavigationState.kt: Route definitions, state helpers, and transitions.
  - NAVIGATION_GUIDE.md: In-directory documentation for navigation patterns.

### UI Screens
Screens are grouped by feature areas. Notable paths include:
- ui/screens/home/: HomeScreen, StoriesRow, HomeTab
- ui/screens/discover/: DiscoverScreen, TrendingCard, DiscoverTab
- ui/screens/hub/: HubScreen, EventDetailsScreen, GroupDetailsScreen, HubTab
- ui/screens/messages/: MessagesScreen, ChatScreen, ConversationScreen, MessagesTab
- ui/screens/profile/: ProfileScreen, MyProfileScreen, EditProfileScreen, ViewProfileScreen, ProfileTab
- ui/screens/features/: Feature group screens like ReelsScreen, StoryViewerScreen, JobsScreen, ClubsScreen, etc.
- ui/screens/settings/ and ui/screens/notifications/: Settings and Notifications UIs

Supporting UI:
- ui/theme/: Material theme, colors, typography, icons.
- ui/components/: Reusable composables (e.g., TextIcon, UISymbols) used by navigation and screens.
- ui/animations/: Standardized animation specs and transitions (including navigation animation helpers).

### Domain Layer
- commonMain/kotlin/com/example/univibe/domain/models/
  - Core models: User, Post, Comment, Reel, Story, Club, Event, Message, etc.
  - Example: Story.kt defines Story, StoryGroup, and supporting enums StoryType and StoryReaction with @Serializable annotations.

### Data Layer (Mocks)
- commonMain/kotlin/com/example/univibe/data/mock/
  - MockEvents.kt, MockMessages.kt, MockProfiles.kt, MockStudySessions.kt, etc.
  - Provides sample data for UI development; swap later with real repositories/data sources.

## Theming
- UniVibeTheme in ui/theme/ wraps the app with Material 3 styling.
- Icons, color schemes, and typography live under ui/theme/ and platform-specific resources in androidMain/ and iOS assets.

## Platform-specific Code
- androidMain/: Android-specific code and resources (e.g., icons, drawables, permissions bridges when needed).
- iosMain/: iOS-specific code (platform interop, if any).

## Notable Flows
- App.kt -> VoyagerMainScaffold: Sets up tabs and responsive container.
- Tabs map to screen groups (HomeTab, DiscoverTab, HubTab, MessagesTab, ProfileTab) with navigation managed by Voyager.
- Feature screens and detail screens are composed within these tabs.

## Contributing Tips
- Start by locating your target feature area under ui/screens/.
- Use domain/models for data contracts; add serialization if models are shared across boundaries.
- For new screens, follow patterns in existing screens and add navigation entries via NavigationRoute/Item if needed.
- Keep platform-specific code in androidMain/ or iosMain/ only when necessary.

## Where to look next
- ui/navigation/NAVIGATION_GUIDE.md for deeper navigation details.
- ui/theme/ for customizing look & feel.
- data/mock/ to see how mock data is supplied to screens.
