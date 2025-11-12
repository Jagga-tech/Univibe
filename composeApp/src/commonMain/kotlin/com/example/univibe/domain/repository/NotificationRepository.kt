package com.example.univibe.domain.repository

import com.example.univibe.domain.models.Notification

/**
 * Frontend-first repository abstraction for notifications.
 * Backed by mocks for now; can be swapped with real data later without UI changes.
 */
interface NotificationRepository {
    suspend fun getAll(): List<Notification>
    suspend fun markAsRead(id: String)
    suspend fun markAllAsRead()
    suspend fun delete(id: String)
}
