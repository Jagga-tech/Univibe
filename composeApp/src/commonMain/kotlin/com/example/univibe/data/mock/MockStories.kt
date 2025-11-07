package com.example.univibe.data.mock
import com.example.univibe.util.getCurrentTimeMillis

import com.example.univibe.domain.models.Story
import com.example.univibe.domain.models.StoryGroup
import com.example.univibe.domain.models.StoryType
import com.example.univibe.domain.models.StoryReaction

/**
 * Mock data provider for Stories.
 * Provides realistic story samples across all story types (image, video, text, link).
 * Stories have mixed read/unviewed states and span different time periods.
 */
object MockStories {
    // Using a fixed base time for consistency across platforms
    private val currentTime = 1698000000000L
    private val storyDuration = 86400000L // 24 hours
    
    /**
     * Comprehensive list of mock stories with diverse types and content.
     * Includes image stories, video stories, text stories, and link stories.
     */
    val stories = listOf(
        // ==================== User 1: Sarah (IMAGE + TEXT stories) ====================
        Story(
            id = "s1",
            userId = "1",
            user = MockUsers.users[0],
            type = StoryType.IMAGE,
            imageUrl = "https://images.unsplash.com/photo-1505228395891-9a51e7e86e81?w=400",
            createdAt = currentTime - 600000,     // 10 minutes ago
            expiresAt = currentTime + storyDuration - 600000,
            isViewed = false,
            replyCount = 3
        ),
        Story(
            id = "s2",
            userId = "1",
            user = MockUsers.users[0],
            type = StoryType.TEXT,
            text = "Just finished my morning coffee! ☕ How's everyone's day going?",
            backgroundColor = "#FF6B6B",  // Red
            createdAt = currentTime - 1200000,    // 20 minutes ago
            expiresAt = currentTime + storyDuration - 1200000,
            isViewed = false,
            reactions = listOf(
                StoryReaction.LOVE to MockUsers.users[1],
                StoryReaction.HAHA to MockUsers.users[2]
            ),
            replyCount = 5
        ),
        Story(
            id = "s3",
            userId = "1",
            user = MockUsers.users[0],
            type = StoryType.IMAGE,
            imageUrl = "https://images.unsplash.com/photo-1552664730-d307ca884978?w=400",
            createdAt = currentTime - 3600000,    // 1 hour ago
            expiresAt = currentTime + storyDuration - 3600000,
            isViewed = true,
            replyCount = 2
        ),
        Story(
            id = "s4",
            userId = "1",
            user = MockUsers.users[0],
            type = StoryType.LINK,
            imageUrl = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=400",
            linkUrl = "https://example.com/study-tips",
            linkTitle = "5 Study Tips for Midterms",
            createdAt = currentTime - 5400000,    // 1.5 hours ago
            expiresAt = currentTime + storyDuration - 5400000,
            isViewed = true,
            replyCount = 8
        ),

        // ==================== User 2: Marcus (VIDEO story) ====================
        Story(
            id = "s5",
            userId = "2",
            user = MockUsers.users[1],
            type = StoryType.VIDEO,
            videoUrl = "https://example.com/video.mp4",
            imageUrl = "https://images.unsplash.com/photo-1516787752417-989d5ce3eb3e?w=400",
            createdAt = currentTime - 1800000,    // 30 minutes ago
            expiresAt = currentTime + storyDuration - 1800000,
            isViewed = false,
            reactions = listOf(
                StoryReaction.WOW to MockUsers.users[0],
                StoryReaction.CLAP to MockUsers.users[3]
            ),
            replyCount = 12
        ),
        Story(
            id = "s6",
            userId = "2",
            user = MockUsers.users[1],
            type = StoryType.TEXT,
            text = "Game day is here! Let's go Vikings! 🏈",
            backgroundColor = "#4ECDC4",  // Teal
            createdAt = currentTime - 7200000,    // 2 hours ago
            expiresAt = currentTime + storyDuration - 7200000,
            isViewed = false,
            reactions = listOf(
                StoryReaction.CLAP to MockUsers.users[0],
                StoryReaction.LOVE to MockUsers.users[2],
                StoryReaction.HAHA to MockUsers.users[4]
            ),
            replyCount = 7
        ),
        Story(
            id = "s7",
            userId = "2",
            user = MockUsers.users[1],
            type = StoryType.IMAGE,
            imageUrl = "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=400",
            createdAt = currentTime - 10800000,   // 3 hours ago
            expiresAt = currentTime + storyDuration - 10800000,
            isViewed = true,
            replyCount = 4
        ),

        // ==================== User 3: Priya (TEXT + LINK stories) ====================
        Story(
            id = "s8",
            userId = "3",
            user = MockUsers.users[2],
            type = StoryType.TEXT,
            text = "New blog post is up! Check it out 📝",
            backgroundColor = "#FFD93D",  // Yellow
            createdAt = currentTime - 2400000,    // 40 minutes ago
            expiresAt = currentTime + storyDuration - 2400000,
            isViewed = false,
            replyCount = 15
        ),
        Story(
            id = "s9",
            userId = "3",
            user = MockUsers.users[2],
            type = StoryType.LINK,
            imageUrl = "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=400",
            linkUrl = "https://example.com/meditation",
            linkTitle = "Meditation App for Students",
            createdAt = currentTime - 4800000,    // 1.33 hours ago
            expiresAt = currentTime + storyDuration - 4800000,
            isViewed = false,
            reactions = listOf(
                StoryReaction.LOVE to MockUsers.users[0],
                StoryReaction.LOVE to MockUsers.users[1]
            ),
            replyCount = 6
        ),
        Story(
            id = "s10",
            userId = "3",
            user = MockUsers.users[2],
            type = StoryType.IMAGE,
            imageUrl = "https://images.unsplash.com/photo-1501747488641-7cc60eb901a7?w=400",
            createdAt = currentTime - 14400000,   // 4 hours ago
            expiresAt = currentTime + storyDuration - 14400000,
            isViewed = true,
            replyCount = 3
        ),

        // ==================== User 4: Alex (MIXED stories) ====================
        Story(
            id = "s11",
            userId = "4",
            user = MockUsers.users[3],
            type = StoryType.IMAGE,
            imageUrl = "https://images.unsplash.com/photo-1519904981063-b0cf448d479e?w=400",
            createdAt = currentTime - 900000,     // 15 minutes ago
            expiresAt = currentTime + storyDuration - 900000,
            isViewed = false,
            reactions = listOf(
                StoryReaction.WOW to MockUsers.users[0],
                StoryReaction.LOVE to MockUsers.users[1],
                StoryReaction.HAHA to MockUsers.users[2],
                StoryReaction.CLAP to MockUsers.users[4]
            ),
            replyCount = 20
        ),
        Story(
            id = "s12",
            userId = "4",
            user = MockUsers.users[3],
            type = StoryType.VIDEO,
            videoUrl = "https://example.com/workout.mp4",
            imageUrl = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400",
            createdAt = currentTime - 3600000,    // 1 hour ago
            expiresAt = currentTime + storyDuration - 3600000,
            isViewed = false,
            replyCount = 11
        ),
        Story(
            id = "s13",
            userId = "4",
            user = MockUsers.users[3],
            type = StoryType.TEXT,
            text = "Nothing beats a good gym session 💪",
            backgroundColor = "#6C5CE7",  // Purple
            createdAt = currentTime - 7200000,    // 2 hours ago
            expiresAt = currentTime + storyDuration - 7200000,
            isViewed = true,
            reactions = listOf(
                StoryReaction.CLAP to MockUsers.users[0],
                StoryReaction.LOVE to MockUsers.users[1]
            ),
            replyCount = 9
        ),

        // ==================== User 5: Jordan (IMAGE stories with reactions) ====================
        Story(
            id = "s14",
            userId = "5",
            user = MockUsers.users[4],
            type = StoryType.IMAGE,
            imageUrl = "https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400",
            createdAt = currentTime - 1200000,    // 20 minutes ago
            expiresAt = currentTime + storyDuration - 1200000,
            isViewed = false,
            reactions = listOf(
                StoryReaction.LOVE to MockUsers.users[0],
                StoryReaction.WOW to MockUsers.users[1]
            ),
            replyCount = 4
        ),
        Story(
            id = "s15",
            userId = "5",
            user = MockUsers.users[4],
            type = StoryType.TEXT,
            text = "Library is my happy place 📚✨",
            backgroundColor = "#A29BFE",  // Lavender
            createdAt = currentTime - 5400000,    // 1.5 hours ago
            expiresAt = currentTime + storyDuration - 5400000,
            isViewed = false,
            reactions = listOf(
                StoryReaction.LOVE to MockUsers.users[0],
                StoryReaction.LOVE to MockUsers.users[2],
                StoryReaction.LOVE to MockUsers.users[3]
            ),
            replyCount = 14
        ),
        Story(
            id = "s16",
            userId = "5",
            user = MockUsers.users[4],
            type = StoryType.LINK,
            imageUrl = "https://images.unsplash.com/photo-1493857671505-72967e2e2760?w=400",
            linkUrl = "https://example.com/book-club",
            linkTitle = "Join Our Book Club!",
            createdAt = currentTime - 10800000,   // 3 hours ago
            expiresAt = currentTime + storyDuration - 10800000,
            isViewed = true,
            replyCount = 18
        )
    )
    
    /**
     * Get all story groups grouped by user.
     * Returns users sorted by unviewed stories first.
     */
    val storyGroups: List<StoryGroup>
        get() = stories
            .groupBy { it.userId }
            .map { (userId, userStories) ->
                StoryGroup(
                    userId = userId,
                    user = userStories.first().user,
                    stories = userStories.sortedBy { it.createdAt },
                    hasUnviewedStories = userStories.any { !it.isViewed }
                )
            }
            .sortedWith(compareBy({ !it.hasUnviewedStories }, { it.user.fullName }))
    
    /**
     * Get all stories for a specific user, sorted by creation time.
     *
     * @param userId The ID of the user
     * @return List of stories from this user in chronological order
     */
    fun getStoriesForUser(userId: String): List<Story> {
        return stories
            .filter { it.userId == userId }
            .sortedBy { it.createdAt }
    }
    
    /**
     * Get a specific story by ID.
     *
     * @param storyId The ID of the story
     * @return The story if found, null otherwise
     */
    fun getStoryById(storyId: String): Story? {
        return stories.find { it.id == storyId }
    }
    
    /**
     * Mark a story as viewed.
     * Updates the mock data in-place for testing.
     *
     * @param storyId The ID of the story to mark as viewed
     */
    fun markStoryAsViewed(storyId: String) {
        val index = stories.indexOfFirst { it.id == storyId }
        if (index >= 0) {
            val story = stories[index]
            stories.forEachIndexed { idx, s ->
                if (s.id == storyId) {
                    // In a real app, this would be a repository update
                    // For mock data, we just track it
                }
            }
        }
    }
    
    /**
     * Add a reaction to a story.
     *
     * @param storyId The ID of the story
     * @param reaction The reaction emoji
     * @param userId The ID of the user reacting
     */
    fun addReactionToStory(storyId: String, reaction: StoryReaction, userId: String) {
        val story = getStoryById(storyId)
        val user = MockUsers.getUserById(userId)
        if (story != null && user != null) {
            // In a real app, this would be persisted to a database
            // For mock data, we just acknowledge the action
        }
    }
    
    /**
     * Get stories ordered for viewing (unviewed first, then viewed).
     */
    fun getStoriesForViewing(): List<StoryGroup> {
        return storyGroups
    }
    
    /**
     * Get all active (non-expired) stories.
     */
    fun getActiveStories(): List<Story> {
        val now = getCurrentTimeMillis()
        return stories.filter { it.expiresAt > now }
    }
}
