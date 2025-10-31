package com.example.univibe.data.mock

import com.example.univibe.domain.models.*
import kotlin.system.getTimeMillis

// ============================================================================
// CURRENT USER PROFILE
// ============================================================================

private val currentUserId = "user_1"

fun getCurrentUserProfile(): UserProfileData = UserProfileData(
    id = currentUserId,
    username = "alexchen",
    fullName = "Alex Chen",
    email = "alex.chen@university.edu",
    avatarUrl = null,
    bannerUrl = null,
    bio = "🚀 CS Student | AI Enthusiast | Coffee Addict ☕ Always up for collaboration!",
    major = "Computer Science",
    graduationYear = 2026,
    university = "State University",
    interests = listOf("AI/ML", "Web Dev", "Open Source", "Design", "Startups"),
    skills = listOf("Kotlin", "Python", "React", "SQL", "Docker"),
    clubs = listOf("CS Club President", "AI Research Lab", "Startup Club"),
    followersCount = 847,
    followingCount = 234,
    postsCount = 128,
    isVerified = true,
    joinedDate = getTimeMillis() - (365 * 24 * 60 * 60 * 1000),
    website = "alexchen.dev",
    location = "San Francisco, CA",
    pronouns = "he/him",
    isPrivate = false
)

fun getOtherUserProfile(): UserProfileData = UserProfileData(
    id = "user_2",
    username = "sarahkwon",
    fullName = "Sarah Kwon",
    email = "sarah.kwon@university.edu",
    avatarUrl = null,
    bio = "Product Designer | UX Enthusiast | Podcast Lover 🎙️",
    major = "Product Design",
    graduationYear = 2025,
    university = "State University",
    interests = listOf("UX/UI", "Product Management", "Psychology", "Travel"),
    skills = listOf("Figma", "User Research", "Prototyping", "CSS"),
    clubs = listOf("Design Club", "Women in Tech"),
    followersCount = 523,
    followingCount = 187,
    postsCount = 89,
    isVerified = false,
    isFollowing = true,
    isFollowedBy = false,
    joinedDate = getTimeMillis() - (180 * 24 * 60 * 60 * 1000),
    location = "San Francisco, CA"
)

fun getAllMockUsers(): List<UserProfileData> = listOf(
    getCurrentUserProfile(),
    getOtherUserProfile(),
    UserProfileData(
        id = "user_3",
        username = "jpriya",
        fullName = "Priya Joshi",
        email = "priya.joshi@university.edu",
        bio = "Business Major | Entrepreneur | Always learning",
        major = "Business Administration",
        graduationYear = 2025,
        interests = listOf("Entrepreneurship", "Finance", "Marketing"),
        followersCount = 342,
        followingCount = 156,
        postsCount = 67
    ),
    UserProfileData(
        id = "user_4",
        username = "mikelsteen",
        fullName = "Mike Steen",
        email = "mike.steen@university.edu",
        bio = "Biology researcher | Sports enthusiast",
        major = "Biology",
        graduationYear = 2027,
        interests = listOf("Research", "Sports", "Fitness"),
        followersCount = 218,
        followingCount = 203,
        postsCount = 45
    )
)

// ============================================================================
// USER PREFERENCES & SETTINGS
// ============================================================================

fun getCurrentUserPreferences(): UserPreferences = UserPreferences(
    userId = currentUserId,
    theme = "system",
    notificationsEnabled = true,
    emailNotifications = true,
    pushNotifications = true,
    messageNotifications = true,
    soundEnabled = true,
    vibrationEnabled = true,
    privacyLevel = "public",
    showOnlineStatus = true,
    allowMessagesFromAnyone = true,
    allowCommentsOnPosts = true,
    allowTagging = true,
    twoFactorEnabled = true,
    language = "en",
    fontSize = "medium"
)

// ============================================================================
// PRIVACY SETTINGS
// ============================================================================

fun getPrivacySettings(userId: String = currentUserId): PrivacySettings = PrivacySettings(
    userId = userId,
    profileVisibility = "public",
    showFollowersList = true,
    showFollowingList = true,
    showActivityStatus = true,
    allowSearchByEmail = true,
    allowSearchByPhone = false,
    showBirthday = false,
    dataCollectionEnabled = true
)

// ============================================================================
// NOTIFICATION SETTINGS
// ============================================================================

fun getNotificationSettings(userId: String = currentUserId): NotificationSettings = 
    NotificationSettings(
        userId = userId,
        likeNotifications = true,
        commentNotifications = true,
        followNotifications = true,
        messageNotifications = true,
        mentionNotifications = true,
        shareNotifications = true,
        eventNotifications = true,
        clubNotifications = true
    )

// ============================================================================
// ACCOUNT SETTINGS
// ============================================================================

fun getAccountSettings(userId: String = currentUserId): AccountSettings = AccountSettings(
    userId = userId,
    passwordLastChanged = getTimeMillis() - (30 * 24 * 60 * 60 * 1000),
    twoFactorEnabled = true,
    twoFactorMethod = "authenticator",
    loginSessions = listOf(
        LoginSession(
            id = "session_1",
            deviceName = "iPhone 15 Pro",
            deviceType = "mobile",
            platform = "ios",
            ipAddress = "192.168.1.1",
            location = "San Francisco, CA",
            lastActive = getTimeMillis() - 3600000,
            isCurrentSession = true
        ),
        LoginSession(
            id = "session_2",
            deviceName = "MacBook Pro",
            deviceType = "desktop",
            platform = "macos",
            ipAddress = "192.168.1.5",
            location = "San Francisco, CA",
            lastActive = getTimeMillis() - 86400000,
            isCurrentSession = false
        ),
        LoginSession(
            id = "session_3",
            deviceName = "Samsung Galaxy S24",
            deviceType = "mobile",
            platform = "android",
            ipAddress = "192.168.1.10",
            location = "San Francisco, CA",
            lastActive = getTimeMillis() - (7 * 24 * 60 * 60 * 1000),
            isCurrentSession = false
        )
    ),
    connectedApps = listOf(
        ConnectedApp(
            id = "app_1",
            appName = "GitHub",
            permissions = listOf("read:user", "repo"),
            connectedDate = getTimeMillis() - (90 * 24 * 60 * 60 * 1000),
            lastAccessDate = getTimeMillis() - 86400000
        ),
        ConnectedApp(
            id = "app_2",
            appName = "Spotify",
            permissions = listOf("user-read-private", "user-top-read"),
            connectedDate = getTimeMillis() - (60 * 24 * 60 * 60 * 1000),
            lastAccessDate = getTimeMillis() - 3600000
        )
    )
)

// ============================================================================
// USER ACTIVITY STATS
// ============================================================================

fun getUserActivityStats(userId: String = currentUserId): UserActivityStats = 
    UserActivityStats(
        userId = userId,
        postsCount = 128,
        commentsCount = 456,
        likesCount = 1289,
        followersCount = 847,
        followingCount = 234,
        storiesCount = 23,
        eventsAttended = 12,
        clubsJoined = 5,
        streakDays = 47,
        totalPoints = 3450
    )

// ============================================================================
// ACHIEVEMENTS & BADGES
// ============================================================================

fun getUserAchievements(userId: String = currentUserId): List<UserAchievement> = listOf(
    UserAchievement(
        id = "ach_1",
        userId = userId,
        name = "First Post",
        description = "Posted your first piece of content",
        icon = "🎉",
        unlockedDate = getTimeMillis() - (365 * 24 * 60 * 60 * 1000),
        category = "engagement"
    ),
    UserAchievement(
        id = "ach_2",
        userId = userId,
        name = "Social Butterfly",
        description = "Reached 100 followers",
        icon = "🦋",
        unlockedDate = getTimeMillis() - (300 * 24 * 60 * 60 * 1000),
        category = "community"
    ),
    UserAchievement(
        id = "ach_3",
        userId = userId,
        name = "Week Warrior",
        description = "Posted every day for a week",
        icon = "🎯",
        unlockedDate = getTimeMillis() - (200 * 24 * 60 * 60 * 1000),
        category = "engagement"
    ),
    UserAchievement(
        id = "ach_4",
        userId = userId,
        name = "Community Champion",
        description = "Reached 500 followers",
        icon = "👑",
        unlockedDate = getTimeMillis() - (100 * 24 * 60 * 60 * 1000),
        category = "community"
    ),
    UserAchievement(
        id = "ach_5",
        userId = userId,
        name = "Event Attendee",
        description = "Attended 5 events",
        icon = "🎪",
        unlockedDate = getTimeMillis() - (30 * 24 * 60 * 60 * 1000),
        category = "events"
    ),
    UserAchievement(
        id = "ach_6",
        userId = userId,
        name = "Knowledge Seeker",
        description = "Joined 3 clubs",
        icon = "📚",
        unlockedDate = getTimeMillis() - (15 * 24 * 60 * 60 * 1000),
        category = "learning"
    )
)

// ============================================================================
// BLOCKED USERS
// ============================================================================

fun getBlockedUsers(userId: String = currentUserId): List<BlockedUser> = listOf(
    BlockedUser(
        userId = userId,
        blockedUserId = "user_spam_1",
        reason = "Spam content",
        blockedDate = getTimeMillis() - (60 * 24 * 60 * 60 * 1000),
        mutedOnly = false
    ),
    BlockedUser(
        userId = userId,
        blockedUserId = "user_troll_1",
        reason = "Harassment",
        blockedDate = getTimeMillis() - (30 * 24 * 60 * 60 * 1000),
        mutedOnly = false
    )
)

// ============================================================================
// MUTED USERS
// ============================================================================

fun getMutedUsers(userId: String = currentUserId): List<BlockedUser> = listOf(
    BlockedUser(
        userId = userId,
        blockedUserId = "user_5",
        reason = "Too many notifications",
        blockedDate = getTimeMillis() - (15 * 24 * 60 * 60 * 1000),
        mutedOnly = true
    )
)

// ============================================================================
// PROFILE COMPLETENESS
// ============================================================================

fun getProfileCompleteness(userId: String = currentUserId): ProfileCompleteness {
    val profile = if (userId == currentUserId) getCurrentUserProfile() else getOtherUserProfile()
    
    val profilePictureComplete = profile.avatarUrl != null
    val bioComplete = profile.bio.isNotEmpty()
    val majorComplete = profile.major.isNotEmpty()
    val interestsComplete = profile.interests.isNotEmpty()
    val skillsComplete = profile.skills.isNotEmpty()
    val socialLinksComplete = profile.website != null
    val privacySettingsComplete = true
    
    val completedItems = listOf(
        profilePictureComplete,
        bioComplete,
        majorComplete,
        interestsComplete,
        skillsComplete,
        socialLinksComplete,
        privacySettingsComplete
    ).count { it }
    
    val completionPercentage = (completedItems * 100) / 7
    
    return ProfileCompleteness(
        userId = userId,
        profilePictureComplete = profilePictureComplete,
        bioComplete = bioComplete,
        majorComplete = majorComplete,
        interestsComplete = interestsComplete,
        skillsComplete = skillsComplete,
        socialLinksComplete = socialLinksComplete,
        privacySettingsComplete = privacySettingsComplete,
        completionPercentage = completionPercentage
    )
}

// ============================================================================
// PROFILE MANAGEMENT FUNCTIONS
// ============================================================================

// Mutable state for demo purposes
var currentProfile = getCurrentUserProfile()
var currentPreferences = getCurrentUserPreferences()
var currentNotificationSettings = getNotificationSettings()
var currentAccountSettings = getAccountSettings()

fun updateUserProfile(profile: UserProfileData): UserProfileData {
    currentProfile = profile
    return profile
}

fun updateUserBio(bio: String): UserProfileData {
    currentProfile = currentProfile.copy(bio = bio)
    return currentProfile
}

fun updateUserInterests(interests: List<String>): UserProfileData {
    currentProfile = currentProfile.copy(interests = interests)
    return currentProfile
}

fun updateUserSkills(skills: List<String>): UserProfileData {
    currentProfile = currentProfile.copy(skills = skills)
    return currentProfile
}

fun updateUserPreferences(preferences: UserPreferences): UserPreferences {
    currentPreferences = preferences
    return preferences
}

fun updateNotificationSettings(settings: NotificationSettings): NotificationSettings {
    currentNotificationSettings = settings
    return settings
}

fun updateTheme(theme: String): UserPreferences {
    currentPreferences = currentPreferences.copy(theme = theme)
    return currentPreferences
}

fun toggleNotifications(enabled: Boolean): UserPreferences {
    currentPreferences = currentPreferences.copy(notificationsEnabled = enabled)
    return currentPreferences
}

fun toggleTwoFactor(enabled: Boolean): AccountSettings {
    currentAccountSettings = currentAccountSettings.copy(twoFactorEnabled = enabled)
    return currentAccountSettings
}

fun addBlockedUser(blockedUserId: String, reason: String? = null): BlockedUser {
    val blockedUser = BlockedUser(
        userId = currentUserId,
        blockedUserId = blockedUserId,
        reason = reason,
        blockedDate = getTimeMillis(),
        mutedOnly = false
    )
    return blockedUser
}

fun removeBlockedUser(blockedUserId: String) {
    // In real app, remove from database
}

fun changePassword(newPassword: String) {
    currentAccountSettings = currentAccountSettings.copy(
        passwordLastChanged = getTimeMillis()
    )
}

fun logoutSession(sessionId: String) {
    currentAccountSettings = currentAccountSettings.copy(
        loginSessions = currentAccountSettings.loginSessions.filter { it.id != sessionId }
    )
}

fun disconnectApp(appId: String) {
    currentAccountSettings = currentAccountSettings.copy(
        connectedApps = currentAccountSettings.connectedApps.filter { it.id != appId }
    )
}