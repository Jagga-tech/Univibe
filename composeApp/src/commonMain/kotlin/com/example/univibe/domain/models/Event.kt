package com.example.univibe.domain.models

import kotlinx.serialization.Serializable
import kotlin.system.getTimeMillis

/**
 * Represents a campus event that users can discover and RSVP to.
 *
 * Events are core to campus social and academic life, allowing students to:
 * - Discover campus activities and events
 * - RSVP and track attendance
 * - Connect with peers through shared interests
 *
 * @property id Unique identifier for the event
 * @property title Display name of the event
 * @property description Detailed description of what to expect
 * @property category Type of event (social, academic, sports, etc.)
 * @property organizerId ID of the user who created the event
 * @property organizer Full user object of the organizer
 * @property location Physical or virtual location details
 * @property startTime Event start time in milliseconds since epoch
 * @property endTime Event end time in milliseconds since epoch
 * @property imageUrl Optional banner/thumbnail image URL
 * @property maxAttendees Maximum capacity (null for unlimited)
 * @property currentAttendees Current number of RSVPs
 * @property attendees List of users who have RSVP'd
 * @property isRSVPed Whether the current user has RSVP'd to this event
 * @property isFeatured Whether this event should be prominently displayed
 * @property tags Topic tags for discoverability
 * @property createdAt When the event was created
 */
@Serializable
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val category: EventCategory,
    val organizerId: String,
    val organizer: User,
    val location: EventLocation,
    val startTime: Long,
    val endTime: Long,
    val imageUrl: String? = null,
    val maxAttendees: Int? = null,
    val currentAttendees: Int = 0,
    val attendees: List<User> = emptyList(),
    val isRSVPed: Boolean = false,
    val isFeatured: Boolean = false,
    val tags: List<String> = emptyList(),
    val createdAt: Long = getTimeMillis()
) {
    /**
     * Get human-readable event duration
     */
    val durationMinutes: Long
        get() = (endTime - startTime) / 60000
    
    /**
     * Check if event is happening today
     */
    val isToday: Boolean
        get() {
            val now = getTimeMillis()
            val oneDayMs = 86400000L
            return startTime > now && startTime < now + oneDayMs
        }
    
    /**
     * Check if event is happening within this week
     */
    val isThisWeek: Boolean
        get() {
            val now = getTimeMillis()
            val oneWeekMs = 7 * 86400000L
            return startTime > now && startTime < now + oneWeekMs
        }
    
    /**
     * Get remaining capacity for the event
     */
    val remainingCapacity: Int?
        get() = maxAttendees?.let { it - currentAttendees }
    
    /**
     * Check if event is fully booked
     */
    val isFull: Boolean
        get() = maxAttendees?.let { currentAttendees >= it } ?: false
}

/**
 * Categories for campus events with emoji for visual identification
 */
@Serializable
enum class EventCategory(val displayName: String, val emoji: String) {
    SOCIAL("Social", "🎉"),
    ACADEMIC("Academic", "📚"),
    SPORTS("Sports", "⚽"),
    ARTS("Arts & Culture", "🎨"),
    CLUB("Club Meeting", "👥"),
    CAREER("Career", "💼"),
    WORKSHOP("Workshop", "🛠️"),
    VOLUNTEER("Volunteer", "🤝"),
    OTHER("Other", "📌")
}

/**
 * Location information for an event
 *
 * Supports both physical campus locations and virtual events (Zoom, etc.)
 *
 * @property name Display name of the location
 * @property address Street address for physical events
 * @property building Campus building name
 * @property room Specific room number
 * @property isVirtual Whether this is an online event
 * @property virtualLink Link to virtual meeting (Zoom, Teams, etc.)
 */
@Serializable
data class EventLocation(
    val name: String,
    val address: String? = null,
    val building: String? = null,
    val room: String? = null,
    val isVirtual: Boolean = false,
    val virtualLink: String? = null
) {
    /**
     * Get full location string for display
     */
    val displayLocation: String
        get() {
            return if (isVirtual) {
                name
            } else {
                val parts = listOf(room, building, name).filterNotNull()
                parts.joinToString(", ")
            }
        }
}

/**
 * Filter options for browsing events
 */
@Serializable
enum class EventFilter {
    ALL,
    TODAY,
    THIS_WEEK,
    THIS_MONTH,
    RSVPED,
    FEATURED
}

// Extension functions for common queries

/**
 * Check if the current user (given by id) is attending this event
 */
fun Event.userIsAttending(userId: String): Boolean =
    attendees.any { it.id == userId }

/**
 * Get formatted duration string for display
 */
fun Event.getDurationString(): String {
    val hours = durationMinutes / 60
    val mins = durationMinutes % 60
    return when {
        hours > 0 && mins > 0 -> "${hours}h ${mins}m"
        hours > 0 -> "${hours}h"
        else -> "${mins}m"
    }
}

/**
 * Get time until event in human-readable format
 */
fun Event.getTimeUntilString(): String {
    val now = getTimeMillis()
    val diff = startTime - now
    
    return when {
        diff < 0 -> "Ended"
        diff < 60000 -> "Starting now"
        diff < 3600000 -> "In ${diff / 60000}m"
        diff < 86400000 -> "In ${diff / 3600000}h"
        diff < 172800000 -> "Tomorrow"
        else -> "In ${diff / 86400000}d"
    }
}