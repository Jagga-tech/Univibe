package com.example.univibe.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Contextual

/**
 * Complete study session model representing an academic collaboration group.
 * Students can create, join, and browse study sessions for collaborative learning.
 */
@Serializable
data class StudySession(
    val id: String,
    val title: String,
    val course: String,
    val subject: String,
    val description: String,
    val hostId: String,
    @Contextual
    val host: User,
    val location: SessionLocation,
    val startTime: Long,
    val endTime: Long,
    val maxParticipants: Int,
    val currentParticipants: Int,
    @Contextual
    val participants: List<User> = emptyList(),
    val isJoined: Boolean = false,
    val createdAt: Long = 0,
    val tags: List<String> = emptyList()
) {
    /**
     * Calculates remaining spots in the session.
     */
    val spotsRemaining: Int get() = maxParticipants - currentParticipants
    
    /**
     * Checks if session is at capacity.
     */
    val isFull: Boolean get() = currentParticipants >= maxParticipants
    
    /**
     * Checks if session is happening soon (within next 24 hours).
     * Pass currentTime from System.currentTimeMillis() externally.
     */
    fun isUpcoming(currentTime: Long): Boolean = startTime - currentTime <= 86400000L
    
    /**
     * Formats duration as readable string (e.g., "2h 30m").
     */
    fun getDurationString(): String {
        val durationMs = endTime - startTime
        val hours = durationMs / 3600000L
        val minutes = (durationMs % 3600000L) / 60000L
        return when {
            hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
            hours > 0 -> "${hours}h"
            else -> "${minutes}m"
        }
    }
    
    /**
     * Checks if user has already joined the session.
     */
    fun userHasJoined(userId: String): Boolean {
        return participants.any { it.id == userId }
    }
}

/**
 * Session location information - can be physical or online.
 */
@Serializable
data class SessionLocation(
    val type: LocationType,
    val name: String,
    val details: String? = null
) {
    /**
     * Gets location display string for UI.
     */
    fun getDisplayString(): String = "$name${if (!details.isNullOrEmpty()) " • $details" else ""}"
}

/**
 * Enum for different location types.
 */
@Serializable
enum class LocationType {
    LIBRARY,
    CLASSROOM,
    COFFEE_SHOP,
    ONLINE,
    DORM,
    STUDENT_CENTER,
    OTHER
}

/**
 * Filter options for browsing study sessions.
 */
enum class SessionFilter {
    ALL,
    TODAY,
    THIS_WEEK,
    MY_COURSES,
    ONLINE_ONLY
}