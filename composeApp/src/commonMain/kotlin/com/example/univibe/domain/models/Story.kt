package com.example.univibe.domain.models

data class Story(
    val id: String,
    val userId: String,
    val user: User,
    val imageUrl: String,
    val createdAt: Long,
    val expiresAt: Long,
    val isViewed: Boolean = false
)

data class StoryGroup(
    val userId: String,
    val user: User,
    val stories: List<Story>,
    val hasUnviewedStories: Boolean
)