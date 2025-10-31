package com.example.univibe.data.mock

import com.example.univibe.domain.models.*
import java.util.UUID
import kotlin.system.getTimeMillis

object MockNotifications {
    
    private val notifications = mutableListOf<Notification>()
    
    init {
        // Initialize with realistic notifications
        val now = getTimeMillis()
        
        // Like on post
        notifications.add(
            Notification(
                id = UUID.randomUUID().toString(),
                type = NotificationType.LIKE_POST,
                title = "Sarah Johnson liked your post",
                message = "Your post about machine learning got a like",
                fromUserId = MockUsers.users[1].id,
                fromUser = MockUsers.users[1],
                relatedId = MockPosts.posts[0].id,
                relatedType = RelatedContentType.POST,
                actionUrl = "post/${MockPosts.posts[0].id}",
                isRead = false,
                createdAt = now - 300000 // 5 minutes ago
            )
        )
        
        // Comment on post
        notifications.add(
            Notification(
                id = UUID.randomUUID().toString(),
                type = NotificationType.COMMENT_ON_POST,
                title = "Alex Rodriguez commented on your post",
                message = "Great insights! I'd love to collaborate on this.",
                fromUserId = MockUsers.users[3].id,
                fromUser = MockUsers.users[3],
                relatedId = MockPosts.posts[1].id,
                relatedType = RelatedContentType.POST,
                actionUrl = "post/${MockPosts.posts[1].id}",
                isRead = false,
                createdAt = now - 600000 // 10 minutes ago
            )
        )
        
        // Follow
        notifications.add(
            Notification(
                id = UUID.randomUUID().toString(),
                type = NotificationType.FOLLOW,
                title = "Lisa Park started following you",
                message = "Artist | Designer | Creative Enthusiast",
                fromUserId = MockUsers.users[4].id,
                fromUser = MockUsers.users[4],
                relatedId = MockUsers.users[4].id,
                relatedType = RelatedContentType.USER,
                actionUrl = "profile/${MockUsers.users[4].id}",
                isRead = true,
                createdAt = now - 1800000 // 30 minutes ago
            )
        )
        
        // Like on comment
        notifications.add(
            Notification(
                id = UUID.randomUUID().toString(),
                type = NotificationType.LIKE_COMMENT,
                title = "David Kim liked your comment",
                message = "Someone appreciated your comment",
                fromUserId = MockUsers.users[5].id,
                fromUser = MockUsers.users[5],
                relatedId = "comment_123",
                relatedType = RelatedContentType.COMMENT,
                actionUrl = "comment/comment_123",
                isRead = true,
                createdAt = now - 3600000 // 1 hour ago
            )
        )
        
        // Mention
        notifications.add(
            Notification(
                id = UUID.randomUUID().toString(),
                type = NotificationType.MENTION,
                title = "Chris Taylor mentioned you",
                message = "@user Hey, check out this study group!",
                fromUserId = MockUsers.users[7].id,
                fromUser = MockUsers.users[7],
                relatedId = MockPosts.posts[3].id,
                relatedType = RelatedContentType.POST,
                actionUrl = "post/${MockPosts.posts[3].id}",
                isRead = true,
                createdAt = now - 5400000 // 1.5 hours ago
            )
        )
        
        // Message
        notifications.add(
            Notification(
                id = UUID.randomUUID().toString(),
                type = NotificationType.MESSAGE,
                title = "New message from Emily White",
                message = "Hey! Are you free for the study session tomorrow?",
                fromUserId = MockUsers.users[6].id,
                fromUser = MockUsers.users[6],
                relatedType = RelatedContentType.MESSAGE_THREAD,
                actionUrl = "messages/conversation_emily",
                isRead = false,
                createdAt = now - 120000 // 2 minutes ago
            )
        )
        
        // Achievement
        notifications.add(
            Notification(
                id = UUID.randomUUID().toString(),
                type = NotificationType.ACHIEVEMENT_UNLOCKED,
                title = "Achievement Unlocked!",
                message = "Social Butterfly - Got 100 likes on your posts",
                fromUserId = "", // No specific user for achievements
                relatedType = RelatedContentType.ACHIEVEMENT,
                isRead = true,
                createdAt = now - 86400000, // 1 day ago
                metadata = mapOf("achievement_id" to "social_butterfly")
            )
        )
    }
    
    fun getAllNotifications(): List<Notification> = notifications.sortedByDescending { it.createdAt }
    
    fun getUnreadNotifications(): List<Notification> = notifications.filter { !it.isRead }.sortedByDescending { it.createdAt }
    
    fun getUnreadCount(): Int = notifications.count { !it.isRead }
    
    fun markAsRead(notificationId: String) {
        val index = notifications.indexOfFirst { it.id == notificationId }
        if (index != -1) {
            notifications[index] = notifications[index].copy(isRead = true)
        }
    }
    
    fun markAllAsRead() {
        notifications.replaceAll { it.copy(isRead = true) }
    }
    
    fun deleteNotification(notificationId: String) {
        notifications.removeAll { it.id == notificationId }
    }
    
    fun clearAllNotifications() {
        notifications.clear()
    }
    
    fun addNotification(notification: Notification) {
        notifications.add(0, notification)
    }
    
    fun getNotificationsByType(type: NotificationType): List<Notification> {
        return notifications.filter { it.type == type }.sortedByDescending { it.createdAt }
    }
    
    fun getNotificationsForUser(userId: String): List<Notification> {
        return notifications.filter { it.fromUserId == userId }.sortedByDescending { it.createdAt }
    }
}