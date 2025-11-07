package com.example.univibe.data.mock

import com.example.univibe.domain.models.*

object MockNotifications {
    
    val notifications = mutableListOf<Notification>()
    
    // Counter for generating unique IDs (KMP-compatible alternative to UUID)
    private var idCounter = 5000
    private fun generateId(): String = (++idCounter).toString()
    
    // Using a fixed timestamp for mock data
    private val now = 1704067200000L // January 1, 2024 00:00:00 UTC
    
    init {
        // Initialize with realistic notifications representing various types
        
        // 1. LIKE_POST - Someone liked your post (Read)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.LIKE_POST,
                title = "Sarah Johnson liked your post",
                message = "Your post about machine learning got a like",
                fromUserId = MockUsers.users[1].id,
                fromUser = MockUsers.users[1],
                relatedId = MockPosts.posts[0].id,
                relatedType = RelatedContentType.POST,
                actionUrl = "post/${MockPosts.posts[0].id}",
                isRead = true,
                createdAt = now - 3600000 // 1 hour ago
            )
        )
        
        // 2. COMMENT_ON_POST - Someone commented on your post (Unread)
        notifications.add(
            Notification(
                id = generateId(),
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
        
        // 3. FOLLOW - Someone followed you (Unread)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.FOLLOW,
                title = "Lisa Park started following you",
                message = "Artist | Designer | Creative Enthusiast",
                fromUserId = MockUsers.users[4].id,
                fromUser = MockUsers.users[4],
                relatedId = MockUsers.users[4].id,
                relatedType = RelatedContentType.USER,
                actionUrl = "profile/${MockUsers.users[4].id}",
                isRead = false,
                createdAt = now - 1800000 // 30 minutes ago
            )
        )
        
        // 4. LIKE_COMMENT - Someone liked your comment (Read)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.LIKE_COMMENT,
                title = "David Kim liked your comment",
                message = "Someone appreciated your comment",
                fromUserId = MockUsers.users[5].id,
                fromUser = MockUsers.users[5],
                relatedId = "comment_123",
                relatedType = RelatedContentType.COMMENT,
                actionUrl = "post/post_123",
                isRead = true,
                createdAt = now - 7200000 // 2 hours ago
            )
        )
        
        // 5. MENTION - Someone mentioned you (Read)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.MENTION,
                title = "Chris Taylor mentioned you",
                message = "@user Hey, check out this study group! Perfect for our CS algorithms course.",
                fromUserId = MockUsers.users[7].id,
                fromUser = MockUsers.users[7],
                relatedId = MockPosts.posts[3].id,
                relatedType = RelatedContentType.POST,
                actionUrl = "post/${MockPosts.posts[3].id}",
                isRead = true,
                createdAt = now - 10800000 // 3 hours ago
            )
        )
        
        // 6. MESSAGE - New message from someone (Unread)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.MESSAGE,
                title = "New message from Emily White",
                message = "Hey! Are you free for the study session tomorrow? We're meeting at the library.",
                fromUserId = MockUsers.users[6].id,
                fromUser = MockUsers.users[6],
                relatedType = RelatedContentType.MESSAGE_THREAD,
                actionUrl = "messages/conversation_${MockUsers.users[6].id}",
                isRead = false,
                createdAt = now - 120000 // 2 minutes ago
            )
        )
        
        // 7. FOLLOW_ACCEPTED - Your follow request was accepted (Read - 1 day ago)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.FOLLOW_ACCEPTED,
                title = "Jordan Martinez accepted your follow request",
                message = "You can now see their posts and updates",
                fromUserId = MockUsers.users[2].id,
                fromUser = MockUsers.users[2],
                relatedId = MockUsers.users[2].id,
                relatedType = RelatedContentType.USER,
                actionUrl = "profile/${MockUsers.users[2].id}",
                isRead = true,
                createdAt = now - 86400000 // 1 day ago
            )
        )
        
        // 8. ACHIEVEMENT_UNLOCKED - You unlocked an achievement (Unread)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.ACHIEVEMENT_UNLOCKED,
                title = "Achievement Unlocked! 🎯",
                message = "Social Butterfly - You got 50 likes on your posts!",
                fromUserId = "",
                relatedType = RelatedContentType.ACHIEVEMENT,
                isRead = false,
                createdAt = now - 300000, // 5 minutes ago
                metadata = mapOf("achievement_id" to "social_butterfly")
            )
        )
        
        // 9. POST_SHARED - Someone shared your post (Read - 2 days ago)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.POST_SHARED,
                title = "Marcus Johnson shared your post",
                message = "Your study tips post was shared with their followers",
                fromUserId = MockUsers.users[8].id,
                fromUser = MockUsers.users[8],
                relatedId = MockPosts.posts[0].id,
                relatedType = RelatedContentType.POST,
                actionUrl = "post/${MockPosts.posts[0].id}",
                isRead = true,
                createdAt = now - (2 * 86400000) // 2 days ago
            )
        )
        
        // 10. REPLY_TO_COMMENT - Someone replied to your comment (Unread)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.REPLY_TO_COMMENT,
                title = "Priya Kumar replied to your comment",
                message = "That's a great point! I hadn't thought of it that way.",
                fromUserId = MockUsers.users[9].id,
                fromUser = MockUsers.users[9],
                relatedId = "comment_456",
                relatedType = RelatedContentType.COMMENT,
                actionUrl = "post/post_456",
                isRead = false,
                createdAt = now - 1200000 // 20 minutes ago
            )
        )
        
        // Additional notifications for more realistic data
        
        // Like on different post (earlier today)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.LIKE_POST,
                title = "Taylor Chen liked your post",
                message = "Your campus life photo got a like",
                fromUserId = MockUsers.users[0].id,
                fromUser = MockUsers.users[0],
                relatedId = "post_999",
                relatedType = RelatedContentType.POST,
                actionUrl = "post/post_999",
                isRead = true,
                createdAt = now - 14400000 // 4 hours ago
            )
        )
        
        // Comment interaction (yesterday)
        notifications.add(
            Notification(
                id = generateId(),
                type = NotificationType.COMMENT_ON_POST,
                title = "Casey Williams commented on your post",
                message = "Awesome! Can you share the resources?",
                fromUserId = MockUsers.users[1].id,
                fromUser = MockUsers.users[1],
                relatedId = MockPosts.posts[2].id,
                relatedType = RelatedContentType.POST,
                actionUrl = "post/${MockPosts.posts[2].id}",
                isRead = true,
                createdAt = now - 86400000 // 1 day ago
            )
        )
    }
    
    /**
     * Get all notifications sorted by creation time (newest first).
     */
    fun getAllNotifications(): List<Notification> = notifications.sortedByDescending { it.createdAt }
    
    /**
     * Get only unread notifications.
     */
    fun getUnreadNotifications(): List<Notification> = notifications.filter { !it.isRead }.sortedByDescending { it.createdAt }
    
    /**
     * Get count of unread notifications.
     */
    fun getUnreadCount(): Int = notifications.count { !it.isRead }
    
    /**
     * Mark a specific notification as read.
     */
    fun markAsRead(notificationId: String) {
        val index = notifications.indexOfFirst { it.id == notificationId }
        if (index != -1) {
            notifications[index] = notifications[index].copy(isRead = true)
        }
    }
    
    /**
     * Mark all notifications as read.
     */
    fun markAllAsRead() {
        for (i in notifications.indices) {
            notifications[i] = notifications[i].copy(isRead = true)
        }
    }
    
    /**
     * Delete a notification by ID.
     */
    fun deleteNotification(notificationId: String) {
        notifications.removeAll { it.id == notificationId }
    }
    
    /**
     * Clear all notifications.
     */
    fun clearAllNotifications() {
        notifications.clear()
    }
    
    /**
     * Add a new notification (typically at the top of the list).
     */
    fun addNotification(notification: Notification) {
        notifications.add(0, notification)
    }
    
    /**
     * Get notifications filtered by type.
     */
    fun getNotificationsByType(type: NotificationType): List<Notification> {
        return notifications.filter { it.type == type }.sortedByDescending { it.createdAt }
    }
    
    /**
     * Get notifications from a specific user.
     */
    fun getNotificationsForUser(userId: String): List<Notification> {
        return notifications.filter { it.fromUserId == userId }.sortedByDescending { it.createdAt }
    }
    
    /**
     * Get notifications related to a specific content.
     */
    fun getNotificationsForContent(contentId: String): List<Notification> {
        return notifications.filter { it.relatedId == contentId }.sortedByDescending { it.createdAt }
    }
}
