package com.example.univibe

import com.example.univibe.domain.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.json.Json

class SerializationRoundTripTest {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = false
    }

    @Test
    fun user_round_trip() {
        val u = User(
            id = "u1",
            username = "alex",
            fullName = "Alex Doe",
            email = "alex@example.com",
            avatarUrl = null,
            bio = "CS Student",
            major = "Computer Science",
            graduationYear = 2026,
            interests = listOf("ai", "ml"),
            followersCount = 10,
            followingCount = 5,
            isOnline = false,
            isVerified = false,
            joinedDate = 1704067200000
        )
        val encoded = json.encodeToString(User.serializer(), u)
        val decoded = json.decodeFromString(User.serializer(), encoded)
        assertEquals(u, decoded)
    }

    @Test
    fun post_round_trip() {
        val author = User(
            id = "u2",
            username = "sam",
            fullName = "Sam Roe",
            email = "sam@example.com"
        )
        val p = Post(
            id = "p1",
            authorId = author.id,
            author = author,
            type = PostType.TEXT,
            content = "Hello UniVibe!",
            imageUrls = emptyList(),
            likeCount = 3,
            commentCount = 1,
            shareCount = 0,
            isLiked = false,
            isBookmarked = false,
            createdAt = 1704067200000,
            course = null,
            achievement = null,
            tags = listOf("intro")
        )
        val encoded = json.encodeToString(Post.serializer(), p)
        val decoded = json.decodeFromString(Post.serializer(), encoded)
        assertEquals(p, decoded)
    }

    @Test
    fun comment_round_trip() {
        val user = User(
            id = "u3",
            username = "rita",
            fullName = "Rita V",
            email = "rita@example.com"
        )
        val c = Comment(
            id = "c1",
            postId = "p1",
            authorId = user.id,
            author = user,
            content = "Nice!",
            likeCount = 2,
            isLiked = false,
            createdAt = 1704067200000,
            replyCount = 0
        )
        val encoded = json.encodeToString(Comment.serializer(), c)
        val decoded = json.decodeFromString(Comment.serializer(), encoded)
        assertEquals(c, decoded)
    }

    @Test
    fun story_round_trip() {
        val user = User(
            id = "u4",
            username = "kai",
            fullName = "Kai L",
            email = "kai@example.com"
        )
        val s = Story(
            id = "s1",
            userId = user.id,
            user = user,
            type = StoryType.IMAGE,
            imageUrl = "https://example.com/img.png",
            videoUrl = null,
            text = null,
            backgroundColor = null,
            linkUrl = null,
            linkTitle = null,
            createdAt = 1704067200000,
            expiresAt = 1704153600000,
            isViewed = false,
            reactions = emptyList(),
            replyCount = 0
        )
        val encoded = json.encodeToString(Story.serializer(), s)
        val decoded = json.decodeFromString(Story.serializer(), encoded)
        assertEquals(s, decoded)
    }
}
