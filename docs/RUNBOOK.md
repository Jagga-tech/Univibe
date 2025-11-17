# UniVibe Runbook

This runbook documents how to build, test, and troubleshoot UniVibe across Android and iOS using Kotlin Multiplatform.

## Minimum Supported Versions
- Android: minSdk 24, targetSdk 34, compileSdk 34
- iOS: iOS 15+, Xcode 15+
- JDK: 17

## Build
- Android (Debug):
  - macOS/Linux: `./gradlew :composeApp:assembleDebug`
  - Windows: `./gradlew.bat :composeApp:assembleDebug`
- iOS (Simulator binaries):
  - `./gradlew :composeApp:iosSimulatorArm64Binaries`

## Test
- Common unit tests: `./gradlew :composeApp:testDebugUnitTest`

## CI
GitHub Actions workflow lives at `.github/workflows/build.yml` and performs:
- Checkout, JDK 17 setup, Gradle setup
- Run common tests
- Build Android assembleDebug
- Build iOS Simulator binaries

## Architecture Notes
- Responsive navigation via Voyager with window size classes (Compact/Medium/Expanded).
- Repository abstractions defined in `domain/repository` and mock-backed implementations in `data/repository/mock`.
- API contracts for backend integration under `api/contracts` (mock-first strategy).
- Branding tokens at `ui/theme/Branding.kt` for easy theming swaps.
- Feature flags in `config/FeatureFlags.kt` to gate WIP features.

## Troubleshooting
- Gradle cache issues: try `./gradlew --stop && ./gradlew clean`.
- Kotlin/Compose versions: ensure Gradle sync completes; delete `.gradle/` and `~/.gradle/caches` if needed.
- iOS builds require macOS with Xcode 15+ and proper toolchains.
- Missing Android SDK: open Android Studio, install SDK Platform 34 and Build Tools.

## Local Development Tips
- Run shared tests frequently for fast feedback.
- Keep list items keyed with stable IDs (we use `key = item.id` patterns) to improve performance and UX.
- Prefer repository interfaces + mock implementations for new features.

## Release Prep (High level)
- Ensure tests green locally and on CI.
- Increment version codes/names as needed.
- Generate Android signed APK/AAB via a release workflow (TBD) and iOS archive (Xcode).
