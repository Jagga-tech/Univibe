package com.example.univibe.data.mock

import com.example.univibe.domain.models.Story
import com.example.univibe.domain.models.StoryGroup

object MockStories {
    // Using a fixed base time for consistency across platforms
    private val currentTime = 1698000000000L
    private val storyDuration = 86400000L // 24 hours
    
    val stories = listOf(
        Story(
            id = "s1",
            userId = "1",
            user = MockUsers.users[0],
            imageUrl = "story_1",
            createdAt = currentTime - 3600000, // 1 hour ago
            expiresAt = currentTime + storyDuration - 3600000,
            isViewed = false
        ),
        Story(
            id = "s2",
            userId = "2",
            user = MockUsers.users[1],
            imageUrl = "story_2",
            createdAt = currentTime - 7200000, // 2 hours ago
            expiresAt = currentTime + storyDuration - 7200000,
            isViewed = false
        ),
        Story(
            id = "s3",
            userId = "3",
            user = MockUsers.users[2],
            imageUrl = "story_3",
            createdAt = currentTime - 10800000, // 3 hours ago
            expiresAt = currentTime + storyDuration - 10800000,
            isViewed = true
        ),
        Story(
            id = "s4",
            userId = "4",
            user = MockUsers.users[3],
            imageUrl = "story_4",
            createdAt = currentTime - 14400000, // 4 hours ago
            expiresAt = currentTime + storyDuration - 14400000,
            isViewed = false
        ),
        Story(
            id = "s5",
            userId = "5",
            user = MockUsers.users[4],
            imageUrl = "story_5",
            createdAt = currentTime - 18000000, // 5 hours ago
            expiresAt = currentTime + storyDuration - 18000000,
            isViewed = false
        )
    )
    
    val storyGroups = stories.groupBy { it.userId }.map { (userId, userStories) ->
        StoryGroup(
            userId = userId,
            user = userStories.first().user,
            stories = userStories,
            hasUnviewedStories = userStories.any { !it.isViewed }
        )
    }
}