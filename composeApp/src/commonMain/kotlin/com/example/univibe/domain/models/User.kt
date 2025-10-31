package com.example.univibe.domain.models

data class User(
    val id: String,
    val username: String,
    val fullName: String,
    val email: String,
    val avatarUrl: String? = null,
    val bio: String? = null,
    val major: String? = null,
    val graduationYear: Int? = null,
    val interests: List<String> = emptyList(),
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val isOnline: Boolean = false,
    val isVerified: Boolean = false,
    val joinedDate: Long = 0L
)

// Helper to get user initials for avatar
fun User.getInitials(): String {
    return fullName.split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .take(2)
        .joinToString("")
}