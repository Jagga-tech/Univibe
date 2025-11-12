package com.example.univibe.data.repository.mock

import com.example.univibe.data.mock.MockNotifications
import com.example.univibe.domain.models.Notification
import com.example.univibe.domain.repository.NotificationRepository

/**
 * Mock-backed implementation of NotificationRepository.
 * Uses in-memory list from MockNotifications. Suitable for KMP common code.
 */
class MockNotificationRepository : NotificationRepository {
    override suspend fun getAll(): List<Notification> {
        // Return a snapshot copy to avoid external mutation
        return MockNotifications.notifications.toList()
    }

    override suspend fun markAsRead(id: String) {
        val n = MockNotifications.notifications.firstOrNull { it.id == id } ?: return
        val idx = MockNotifications.notifications.indexOf(n)
        if (idx >= 0) {
            MockNotifications.notifications[idx] = n.copy(isRead = true)
        }
    }

    override suspend fun markAllAsRead() {
        for (i in MockNotifications.notifications.indices) {
            val n = MockNotifications.notifications[i]
            if (!n.isRead) MockNotifications.notifications[i] = n.copy(isRead = true)
        }
    }

    override suspend fun delete(id: String) {
        MockNotifications.notifications.removeAll { it.id == id }
    }
}
