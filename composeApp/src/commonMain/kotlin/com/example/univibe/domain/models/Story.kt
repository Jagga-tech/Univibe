package com.example.univibe.domain.models

/**
 * Enumeration of supported story types.
 */
enum class StoryType {
    IMAGE,     // Static image with auto-advance
    VIDEO,     // Video that auto-advances when complete
    TEXT,      // Colored background with text overlay
    LINK       // Story with swipe-up link
}

/**
 * Enumeration of quick reactions users can send on stories.
 */
enum class StoryReaction(val emoji: String) {
    LOVE("❤️"),
    HAHA("😂"),
    WOW("😮"),
    SAD("😢"),
    CLAP("👏")
}

/**
 * Domain model representing a single story (image, video, text, or link).
 *
 * **Properties:**
 * - `id`: Unique identifier for the story
 * - `userId`: ID of the user who posted the story
 * - `user`: The User object (for convenience)
 * - `type`: Type of story (image, video, text, link)
 * - `imageUrl`: URL to story image (for IMAGE type)
 * - `videoUrl`: URL to story video (for VIDEO type)
 * - `text`: Text content (for TEXT type)
 * - `backgroundColor`: Background color for text stories (e.g., "#FF5733")
 * - `linkUrl`: URL for swipe-up links (for LINK type)
 * - `linkTitle`: Title for the link
 * - `createdAt`: Timestamp when story was created
 * - `expiresAt`: Timestamp when story expires (typically 24 hours later)
 * - `isViewed`: Whether the current user has viewed this story
 * - `reactions`: List of reactions from other users (emoji + user who reacted)
 * - `replyCount`: Number of replies/messages received
 */
data class Story(
    val id: String,
    val userId: String,
    val user: User,
    val type: StoryType = StoryType.IMAGE,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val text: String? = null,
    val backgroundColor: String? = null,  // Hex color for text stories
    val linkUrl: String? = null,
    val linkTitle: String? = null,
    val createdAt: Long,
    val expiresAt: Long,
    val isViewed: Boolean = false,
    val reactions: List<Pair<StoryReaction, User>> = emptyList(),
    val replyCount: Int = 0
)

/**
 * Grouped stories from a single user.
 * Used for efficient story viewing with multiple users' stories.
 *
 * **Properties:**
 * - `userId`: ID of the user who posted these stories
 * - `user`: The User object
 * - `stories`: All stories from this user (in chronological order)
 * - `hasUnviewedStories`: Whether this user has any unviewed stories
 */
data class StoryGroup(
    val userId: String,
    val user: User,
    val stories: List<Story>,
    val hasUnviewedStories: Boolean
)
