package com.example.univibe.domain.models

data class Reel(
    val id: String,
    val creatorId: String,
    val creatorName: String,
    val creatorUsername: String,
    val creatorAvatarUrl: String? = null,
    val thumbnailUrl: String,
    val caption: String,
    val audioTitle: String? = null,
    val audioArtist: String? = null,
    val tags: List<String> = emptyList(),
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val shareCount: Int = 0,
    val viewCount: Int = 0,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val isFollowing: Boolean = false,
    val createdAt: Long = 0L,
    val duration: Int = 0 // seconds
)
