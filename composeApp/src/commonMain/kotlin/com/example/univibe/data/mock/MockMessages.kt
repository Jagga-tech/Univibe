package com.example.univibe.data.mock

import com.example.univibe.domain.models.*

object MockMessages {
    
    private val messages = mutableListOf<Message>()
    private val conversations = mutableListOf<Conversation>()
    
    // Counter for generating unique IDs (KMP-compatible alternative to UUID)
    private var idCounter = 1000
    private fun generateId(): String = (++idCounter).toString()
    
    // Using a fixed timestamp for mock data (represents a baseline "now" for consistent behavior)
    private val now = 1704067200000L // January 1, 2024 00:00:00 UTC
    
    init {
        // Current user ID (would be replaced with actual logged-in user)
        val currentUserId = "1"
        
        // Create conversations with different users
        val user2 = MockUsers.users[1] // Sarah Johnson
        val user3 = MockUsers.users[2] // Michael Chen
        val user6 = MockUsers.users[5] // David Kim
        val user7 = MockUsers.users[6] // Emily White
        
        // ===== CONVERSATION 1: With Emily White =====
        val conversation1Id = generateId()
        val conv1Messages = listOf(
            Message(
                id = generateId(),
                conversationId = conversation1Id,
                senderId = user7.id,
                sender = user7,
                content = "Hey! Are you free for the study session tomorrow?",
                createdAt = now - 120000,
                isRead = false
            ),
            Message(
                id = generateId(),
                conversationId = conversation1Id,
                senderId = user7.id,
                sender = user7,
                content = "We're planning to study for the algorithms exam",
                createdAt = now - 115000,
                isRead = false
            ),
            Message(
                id = generateId(),
                conversationId = conversation1Id,
                senderId = currentUserId,
                sender = MockUsers.users[0],
                content = "Sure! What time works for you?",
                createdAt = now - 60000,
                isRead = true
            ),
            Message(
                id = generateId(),
                conversationId = conversation1Id,
                senderId = user7.id,
                sender = user7,
                content = "Around 7 PM at the library?",
                createdAt = now - 30000,
                isRead = true
            )
        )
        conversations.add(
            Conversation(
                id = conversation1Id,
                participantIds = listOf(currentUserId, user7.id),
                participants = listOf(MockUsers.users[0], user7),
                lastMessage = conv1Messages.last(),
                lastMessageTime = conv1Messages.last().createdAt,
                unreadCount = 2,
                isGroup = false
            )
        )
        messages.addAll(conv1Messages)
        
        // ===== CONVERSATION 2: With Sarah Johnson =====
        val conversation2Id = generateId()
        val conv2Messages = listOf(
            Message(
                id = generateId(),
                conversationId = conversation2Id,
                senderId = currentUserId,
                sender = MockUsers.users[0],
                content = "Did you finish the project presentation?",
                createdAt = now - 3600000,
                isRead = true
            ),
            Message(
                id = generateId(),
                conversationId = conversation2Id,
                senderId = user2.id,
                sender = user2,
                content = "Almost done! Just need to add a few more slides",
                createdAt = now - 3500000,
                isRead = true
            ),
            Message(
                id = generateId(),
                conversationId = conversation2Id,
                senderId = user2.id,
                sender = user2,
                content = "Want to do a quick review together?",
                createdAt = now - 3400000,
                isRead = true
            ),
            Message(
                id = generateId(),
                conversationId = conversation2Id,
                senderId = currentUserId,
                sender = MockUsers.users[0],
                content = "Yeah, sounds good! Let's meet tomorrow",
                createdAt = now - 3000000,
                isRead = true
            )
        )
        conversations.add(
            Conversation(
                id = conversation2Id,
                participantIds = listOf(currentUserId, user2.id),
                participants = listOf(MockUsers.users[0], user2),
                lastMessage = conv2Messages.last(),
                lastMessageTime = conv2Messages.last().createdAt,
                unreadCount = 0,
                isGroup = false
            )
        )
        messages.addAll(conv2Messages)
        
        // ===== CONVERSATION 3: With Michael Chen =====
        val conversation3Id = generateId()
        val conv3Messages = listOf(
            Message(
                id = generateId(),
                conversationId = conversation3Id,
                senderId = user3.id,
                sender = user3,
                content = "Hey, are you attending the tech talk on Friday?",
                createdAt = now - 7200000,
                isRead = true
            ),
            Message(
                id = generateId(),
                conversationId = conversation3Id,
                senderId = currentUserId,
                sender = MockUsers.users[0],
                content = "I'm planning to! Should be interesting",
                createdAt = now - 7100000,
                isRead = true
            ),
            Message(
                id = generateId(),
                conversationId = conversation3Id,
                senderId = user3.id,
                sender = user3,
                content = "Cool! Let's sit together",
                createdAt = now - 6900000,
                isRead = true
            )
        )
        conversations.add(
            Conversation(
                id = conversation3Id,
                participantIds = listOf(currentUserId, user3.id),
                participants = listOf(MockUsers.users[0], user3),
                lastMessage = conv3Messages.last(),
                lastMessageTime = conv3Messages.last().createdAt,
                unreadCount = 0,
                isGroup = false
            )
        )
        messages.addAll(conv3Messages)
        
        // ===== CONVERSATION 4: With David Kim =====
        val conversation4Id = generateId()
        val conv4Messages = listOf(
            Message(
                id = generateId(),
                conversationId = conversation4Id,
                senderId = user6.id,
                sender = user6,
                content = "Want to form a study group for physics?",
                createdAt = now - 86400000,
                isRead = true
            ),
            Message(
                id = generateId(),
                conversationId = conversation4Id,
                senderId = currentUserId,
                sender = MockUsers.users[0],
                content = "Sure! I could use some help with quantum mechanics",
                createdAt = now - 86300000,
                isRead = true
            )
        )
        conversations.add(
            Conversation(
                id = conversation4Id,
                participantIds = listOf(currentUserId, user6.id),
                participants = listOf(MockUsers.users[0], user6),
                lastMessage = conv4Messages.last(),
                lastMessageTime = conv4Messages.last().createdAt,
                unreadCount = 0,
                isGroup = false
            )
        )
        messages.addAll(conv4Messages)
    }
    
    fun getAllConversations(): List<Conversation> {
        return conversations.sortedByDescending { it.lastMessageTime }
    }
    
    fun getConversationById(conversationId: String): Conversation? {
        return conversations.find { it.id == conversationId }
    }
    
    fun getMessagesForConversation(conversationId: String): List<Message> {
        return messages.filter { it.conversationId == conversationId }.sortedBy { it.createdAt }
    }
    
    fun sendMessage(conversationId: String, senderId: String, content: String): Message {
        val message = Message(
            id = generateId(),
            conversationId = conversationId,
            senderId = senderId,
            content = content,
            createdAt = now,
            isRead = false
        )
        messages.add(message)
        
        // Update conversation
        val convIndex = conversations.indexOfFirst { it.id == conversationId }
        if (convIndex != -1) {
            conversations[convIndex] = conversations[convIndex].copy(
                lastMessage = message,
                lastMessageTime = message.createdAt
            )
        }
        
        return message
    }
    
    fun markMessageAsRead(messageId: String) {
        val index = messages.indexOfFirst { it.id == messageId }
        if (index != -1) {
            messages[index] = messages[index].copy(isRead = true)
        }
    }
    
    fun markConversationAsRead(conversationId: String) {
        messages
            .filter { it.conversationId == conversationId && !it.isRead }
            .forEach { message ->
                val index = messages.indexOf(message)
                if (index != -1) {
                    messages[index] = message.copy(isRead = true)
                }
            }
        
        // Update unread count
        val convIndex = conversations.indexOfFirst { it.id == conversationId }
        if (convIndex != -1) {
            conversations[convIndex] = conversations[convIndex].copy(unreadCount = 0)
        }
    }
    
    fun getUnreadConversationCount(): Int {
        return conversations.count { it.unreadCount > 0 }
    }
    
    fun createConversation(participantIds: List<String>, isGroup: Boolean = false, groupName: String? = null): Conversation {
        val conversation = Conversation(
            id = generateId(),
            participantIds = participantIds,
            participants = participantIds.mapNotNull { id -> MockUsers.users.find { it.id == id } },
            isGroup = isGroup,
            groupName = groupName,
            createdAt = now
        )
        conversations.add(conversation)
        return conversation
    }
}