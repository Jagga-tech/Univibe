/**
 * UniVibe Components Library Index
 *
 * This file provides convenient re-exports of all components for easy importing.
 *
 * Usage:
 * ```
 * import com.example.univibe.ui.components.*
 * ```
 *
 * All components are now available without individual imports.
 */

// Buttons
export(PrimaryButton)
export(SecondaryButton)
export(TertiaryButton)
export(OutlineButton)
export(TextButtonVariant)
export(ButtonSize)

// Icon Buttons
export(UniVibeIconButton)
export(UniVibeFAB)
export(IconButtonSize)
export(IconButtonVariant)

// Input Fields
export(UniVibeTextField)
export(UniVibePasswordField)
export(SearchField)

// Cards
export(UniVibeCard)
export(UserCard)
export(EventCard)

// Dialogs
export(UniVibeAlertDialog)
export(ConfirmationDialog)
export(InfoDialog)

// Bottom Sheets
export(UniVibeBottomSheet)
export(BottomSheetAction)
export(BottomSheetConfirmation)

// Badges
export(StatusBadge)
export(NotificationBadge)
export(ContentWithBadge)
export(OnlineStatusBadge)
export(VerifiedBadge)
export(PremiumBadge)
export(BadgeStyle)

// Progress
export(UniVibeProgressBar)
export(CircularProgressBar)
export(StackedProgressBar)
export(SteppedProgressBar)

// Lists
export(UniVibeListItem)
export(SwitchListItem)
export(DividerListItem)

// Toast/Notifications
export(UniVibeToast)
export(SuccessToast)
export(ErrorToast)
export(WarningToast)
export(InfoToast)
export(ToastType)

// User Components
export(UserAvatar)
export(AnimatedLikeButton)

// Loading & Empty States
export(LoadingIndicator)
export(SkeletonLoading)
export(EmptyState)

// Chips
export(ChipGroup)

// Helper function for exports (not needed at runtime)
@Suppress("UnusedReceiverParameter", "UNUSED_PARAMETER")
private fun export(item: Any) {
    // This is just a marker function for documentation
    // Actual exports are done at package level
}