package com.example.univibe.domain.models

import kotlinx.serialization.Serializable

/**
 * Complete user profile information for viewing and editing.
 * Extends the basic User model with additional profile details.
 */
@Serializable
data class UserProfileData(
    val id: String,
    val username: String,
    val fullName: String,
    val email: String,
    val avatarUrl: String? = null,
    val bannerUrl: String? = null,
    val bio: String = "",
    val major: String = "",
    val graduationYear: Int? = null,
    val university: String = "",
    val interests: List<String> = emptyList(),
    val skills: List<String> = emptyList(),
    val clubs: List<String> = emptyList(),
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val postsCount: Int = 0,
    val isVerified: Boolean = false,
    val isFollowing: Boolean = false,
    val isFollowedBy: Boolean = false,
    val joinedDate: Long = 0L,
    val website: String? = null,
    val location: String? = null,
    val phoneNumber: String? = null,
    val pronouns: String? = null,
    val birthday: String? = null,
    val isPrivate: Boolean = false,
    val isBlocked: Boolean = false
)

/**
 * User preferences and settings.
 */
@Serializable
data class UserPreferences(
    val userId: String,
    val theme: String = "system", // "light", "dark", "system"
    val notificationsEnabled: Boolean = true,
    val emailNotifications: Boolean = true,
    val pushNotifications: Boolean = true,
    val messageNotifications: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val privacyLevel: String = "public", // "public", "friends", "private"
    val showOnlineStatus: Boolean = true,
    val allowMessagesFromAnyone: Boolean = true,
    val allowCommentsOnPosts: Boolean = true,
    val allowTagging: Boolean = true,
    val twoFactorEnabled: Boolean = false,
    val activityStatus: String = "online", // "online", "away", "offline"
    val lastSeen: Long = 0L,
    val language: String = "en",
    val fontSize: String = "medium", // "small", "medium", "large"
    val blockedUsers: List<String> = emptyList(),
    val mutedUsers: List<String> = emptyList()
)

/**
 * Privacy settings for user profile.
 */
@Serializable
data class PrivacySettings(
    val userId: String,
    val profileVisibility: String = "public", // "public", "friends", "private"
    val showFollowersList: Boolean = true,
    val showFollowingList: Boolean = true,
    val showActivityStatus: Boolean = true,
    val allowSearchByEmail: Boolean = true,
    val allowSearchByPhone: Boolean = true,
    val showBirthday: Boolean = false,
    val dataCollectionEnabled: Boolean = true
)

/**
 * Notification settings for specific notification types.
 */
@Serializable
data class NotificationSettings(
    val userId: String,
    val likeNotifications: Boolean = true,
    val commentNotifications: Boolean = true,
    val followNotifications: Boolean = true,
    val messageNotifications: Boolean = true,
    val mentionNotifications: Boolean = true,
    val shareNotifications: Boolean = true,
    val eventNotifications: Boolean = true,
    val clubNotifications: Boolean = true
)

/**
 * Account settings including security and authentication.
 */
@Serializable
data class AccountSettings(
    val userId: String,
    val passwordLastChanged: Long = 0L,
    val twoFactorEnabled: Boolean = false,
    val twoFactorMethod: String = "email", // "email", "sms", "authenticator"
    val loginSessions: List<LoginSession> = emptyList(),
    val connectedApps: List<ConnectedApp> = emptyList()
)

/**
 * Login session tracking.
 */
@Serializable
data class LoginSession(
    val id: String,
    val deviceName: String,
    val deviceType: String, // "mobile", "web", "tablet"
    val platform: String, // "ios", "android", "macos", "windows"
    val ipAddress: String,
    val location: String,
    val lastActive: Long,
    val isCurrentSession: Boolean = false
)

/**
 * Connected third-party applications.
 */
@Serializable
data class ConnectedApp(
    val id: String,
    val appName: String,
    val appIcon: String? = null,
    val permissions: List<String> = emptyList(),
    val connectedDate: Long,
    val lastAccessDate: Long
)

/**
 * User activity statistics.
 */
@Serializable
data class UserActivityStats(
    val userId: String,
    val postsCount: Int = 0,
    val commentsCount: Int = 0,
    val likesCount: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val storiesCount: Int = 0,
    val eventsAttended: Int = 0,
    val clubsJoined: Int = 0,
    val streakDays: Int = 0,
    val totalPoints: Int = 0
)

/**
 * User achievements and badges.
 */
@Serializable
data class UserAchievement(
    val id: String,
    val userId: String,
    val name: String,
    val description: String,
    val icon: String,
    val unlockedDate: Long,
    val category: String // "engagement", "community", "learning", "events"
)

/**
 * Block/mute entry for user management.
 */
@Serializable
data class BlockedUser(
    val userId: String,
    val blockedUserId: String,
    val reason: String? = null,
    val blockedDate: Long,
    val mutedOnly: Boolean = false
)

/**
 * Profile completion progress tracking.
 */
@Serializable
data class ProfileCompleteness(
    val userId: String,
    val profilePictureComplete: Boolean = false,
    val bioComplete: Boolean = false,
    val majorComplete: Boolean = false,
    val interestsComplete: Boolean = false,
    val skillsComplete: Boolean = false,
    val socialLinksComplete: Boolean = false,
    val privacySettingsComplete: Boolean = false,
    val completionPercentage: Int = 0
)