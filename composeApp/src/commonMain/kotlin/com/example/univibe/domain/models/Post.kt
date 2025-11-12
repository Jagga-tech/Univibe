package com.example.univibe.domain.models

import kotlinx.serialization.Serializable

enum class PostType {
    TEXT,           // Regular text post
    IMAGE,          // Post with images
    ACHIEVEMENT,    // Achievement/milestone post
    POLL,           // Poll post
    EVENT,          // Event announcement
    QUESTION        // Question post
}

@Serializable
data class Post(
    val id: String,
    val authorId: String,
    val author: User,
    val type: PostType,
    val content: String,
    val imageUrls: List<String> = emptyList(),
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val shareCount: Int = 0,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false,
    val createdAt: Long,
    val course: String? = null,           // For academic posts
    val achievement: Achievement? = null, // For achievement posts
    val tags: List<String> = emptyList()
)

@Serializable
data class Achievement(
    val type: AchievementType,
    val title: String,
    val description: String,
    val iconUrl: String? = null
)

enum class AchievementType {
    GRADE,          // Got an A, Dean's list, etc
    CLUB,           // Club leadership, new member
    PROJECT,        // Completed project
    SCHOLARSHIP,    // Won scholarship
    COMPETITION,    // Won competition
    MILESTONE,      // Generic milestone
    ACADEMIC        // Academic achievement
}