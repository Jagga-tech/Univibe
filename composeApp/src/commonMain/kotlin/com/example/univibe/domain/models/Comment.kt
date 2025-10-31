package com.example.univibe.domain.models

data class Comment(
    val id: String,
    val postId: String,
    val authorId: String,
    val author: User,
    val content: String,
    val likeCount: Int = 0,
    val isLiked: Boolean = false,
    val createdAt: Long,
    val replyCount: Int = 0
)