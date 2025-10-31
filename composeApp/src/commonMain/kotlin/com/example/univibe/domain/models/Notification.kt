package com.example.univibe.domain.models

import kotlinx.serialization.Serializable
import kotlin.system.getTimeMillis

// Base notification model with custom action mapping
@Serializable
data class Notification(
    val id: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val fromUserId: String,
    val fromUser: User? = null,
    val relatedId: String? = null, // Post ID, Comment ID, etc.
    val relatedType: RelatedContentType? = null,
    val actionUrl: String? = null,
    val isRead: Boolean = false,
    val createdAt: Long = getTimeMillis(),
    val metadata: Map<String, String> = emptyMap() // For custom data
)

enum class NotificationType {
    LIKE_POST,
    LIKE_COMMENT,
    COMMENT_ON_POST,
    REPLY_TO_COMMENT,
    FOLLOW,
    FOLLOW_ACCEPTED,
    MESSAGE,
    MENTION,
    POST_SHARED,
    ACHIEVEMENT_UNLOCKED,
    CUSTOM
}

enum class RelatedContentType {
    POST,
    COMMENT,
    USER,
    ACHIEVEMENT,
    MESSAGE_THREAD
}

// Action mapping for notifications
data class NotificationAction(
    val type: NotificationType,
    val action: (notification: Notification) -> Unit
)