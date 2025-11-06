package com.example.univibe.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Contextual

/**
 * Represents a student organization/club on campus.
 *
 * Clubs allow students to discover, join, and participate in student organizations.
 * Each club has a president, members, events, and a feed of posts.
 *
 * @property id Unique identifier for the club
 * @property name Display name of the club
 * @property description Full description of what the club does
 * @property category Type of club (academic, sports, arts, etc.)
 * @property logoUrl Optional URL to club logo/image
 * @property bannerUrl Optional URL to club banner
 * @property presidentId ID of the club president
 * @property president Full user object of the president
 * @property memberCount Total number of members
 * @property members List of club members
 * @property isMember Whether the current user is a member
 * @property isVerified Whether this club is officially verified
 * @property meetingSchedule Regular meeting time (e.g., "Thursdays at 6:30 PM")
 * @property meetingLocation Where the club meets
 * @property socialMedia Club's social media links
 * @property upcomingEvents Upcoming events hosted by the club
 * @property recentPosts Recent posts from the club
 * @property tags Searchable tags for the club
 * @property createdAt When the club was created
 */
@Serializable
data class Club(
    val id: String,
    val name: String,
    val description: String,
    val category: ClubCategory,
    val logoUrl: String? = null,
    val bannerUrl: String? = null,
    val presidentId: String,
    @Contextual
    val president: User,
    val memberCount: Int,
    @Contextual
    val members: List<User> = emptyList(),
    val isMember: Boolean = false,
    val isVerified: Boolean = false,
    val meetingSchedule: String? = null,
    val meetingLocation: String? = null,
    val socialMedia: ClubSocialMedia? = null,
    val upcomingEvents: List<Event> = emptyList(),
    val recentPosts: List<Post> = emptyList(),
    val tags: List<String> = emptyList(),
    val createdAt: Long = 0
)

/**
 * Club's social media and contact information
 */
@Serializable
data class ClubSocialMedia(
    val instagram: String? = null,
    val twitter: String? = null,
    val website: String? = null,
    val email: String? = null
)

/**
 * Categories for student organizations
 */
@Serializable
enum class ClubCategory(val displayName: String, val emoji: String) {
    ACADEMIC("Academic", "📚"),
    SPORTS("Sports & Recreation", "⚽"),
    ARTS("Arts & Culture", "🎨"),
    TECHNOLOGY("Technology", "💻"),
    VOLUNTEER("Volunteer & Service", "🤝"),
    PROFESSIONAL("Professional", "💼"),
    CULTURAL("Cultural & Identity", "🌍"),
    SOCIAL("Social & Special Interest", "🎉"),
    MEDIA("Media & Publications", "📰"),
    POLITICAL("Political & Advocacy", "🗳️"),
    OTHER("Other", "📌")
}

/**
 * Filter options for browsing clubs
 */
enum class ClubFilter {
    ALL,
    JOINED,
    POPULAR,
    NEW,
    RECOMMENDED
}