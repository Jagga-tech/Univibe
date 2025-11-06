package com.example.univibe.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Contextual

@Serializable
data class Message(
    val id: String,
    val conversationId: String,
    val senderId: String,
    @Contextual
    val sender: User? = null,
    val content: String,
    val createdAt: Long = 0,
    val isRead: Boolean = false,
    val mediaUrl: String? = null,
    val replyToMessageId: String? = null
)

@Serializable
data class Conversation(
    val id: String,
    val participantIds: List<String>,
    @Contextual
    val participants: List<User> = emptyList(),
    val lastMessage: Message? = null,
    val lastMessageTime: Long = 0,
    val unreadCount: Int = 0,
    val isGroup: Boolean = false,
    val groupName: String? = null,
    val groupImageUrl: String? = null,
    val createdAt: Long = 0
)

// For conversation list display
@Serializable
data class ConversationPreview(
    val conversation: Conversation,
    @Contextual
    val otherParticipant: User? = null, // For 1-on-1 conversations
    val lastMessagePreview: String? = null,
    val isActive: Boolean = false
)