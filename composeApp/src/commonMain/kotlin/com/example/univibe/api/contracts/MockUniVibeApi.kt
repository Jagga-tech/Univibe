package com.example.univibe.api.contracts

import com.example.univibe.data.mock.*
import com.example.univibe.domain.models.*
import com.example.univibe.util.getCurrentTimeMillis

/**
 * One-file mock implementation of the UniVibe API surface.
 *
 * This provides concrete, in-memory logic for Posts, Users, Stories, Messages, and Events
 * by delegating to existing Mock* data sources. Use this when you want a single entry point
 * with "everything there" instead of scattered mock objects.
 */
class MockUniVibeApi : UniVibeApi {
    override val posts: PostsApi = PostsApiImpl
    override val users: UsersApi = UsersApiImpl
    override val stories: StoriesApi = StoriesApiImpl
    override val messages: MessagesApi = MessagesApiImpl
    override val events: EventsApi = EventsApiImpl

    private object PostsApiImpl : PostsApi {
        override suspend fun getFeed(page: Int, limit: Int): List<Post> {
            val start = page * limit
            return MockPosts.posts.drop(start).take(limit)
        }

        override suspend fun getPostById(id: String): Post? = MockPosts.getPostById(id)

        // Minimal mock: synthesize a Post without mutating the source mock list
        override suspend fun createPost(request: CreatePostRequest): Post = Post(
            id = "p_${getCurrentTimeMillis()}",
            authorId = request.authorId,
            author = MockUsers.getUserById(request.authorId) ?: MockUsers.users.first(),
            type = request.type,
            content = request.content,
            imageUrls = request.imageUrls,
            likeCount = 0,
            commentCount = 0,
            shareCount = 0,
            isLiked = false,
            isBookmarked = false,
            createdAt = getCurrentTimeMillis(),
            course = request.course,
            achievement = request.achievement,
            tags = request.tags
        )

        // Read-only mock dataset; return success without mutation
        override suspend fun likePost(postId: String): Boolean = true

        override suspend fun unlikePost(postId: String): Boolean = true
    }

    private object UsersApiImpl : UsersApi {
        override suspend fun getUser(id: String): User? = MockUsers.users.firstOrNull { it.id == id }

        override suspend fun searchUsers(query: String, limit: Int): List<User> {
            if (query.isBlank()) return emptyList()
            val q = query.trim().lowercase()
            return MockUsers.users.filter {
                it.username.lowercase().contains(q) || it.fullName.lowercase().contains(q)
            }.take(limit)
        }

        // No-op: return success
        override suspend fun follow(userId: String): Boolean = true

        override suspend fun unfollow(userId: String): Boolean = true
    }

    private object StoriesApiImpl : StoriesApi {
        override suspend fun getStoryGroups(): List<StoryGroup> = MockStories.getStoriesForViewing()

        override suspend fun getStoriesForUser(userId: String): List<Story> =
            MockStories.getStoriesForUser(userId)

        // Read-only: pretend success
        override suspend fun addReaction(storyId: String, reaction: StoryReaction): Boolean = true

        override suspend fun markViewed(storyId: String) { /* no-op */ }
    }

    private object MessagesApiImpl : MessagesApi {
        override suspend fun getConversations(page: Int, limit: Int): List<Conversation> {
            val start = page * limit
            return MockMessages.conversations.drop(start).take(limit)
        }

        override suspend fun getConversation(conversationId: String): Conversation? =
            MockMessages.getConversationById(conversationId)

        override suspend fun sendMessage(conversationId: String, content: String): Message =
            Message(
                id = "m_${getCurrentTimeMillis()}",
                conversationId = conversationId,
                senderId = MockUsers.users.first().id,
                sender = MockUsers.users.first(),
                content = content,
                createdAt = getCurrentTimeMillis(),
                isRead = false
            )
    }

    private object EventsApiImpl : EventsApi {
        override suspend fun getEvents(page: Int, limit: Int): List<Event> {
            val start = page * limit
            return MockEvents.events.drop(start).take(limit)
        }

        override suspend fun getEvent(id: String): Event? = MockEvents.getEventById(id)

        override suspend fun rsvp(eventId: String, attending: Boolean): Boolean = true
    }

    companion object {
        // Convenient singleton for callers who just need a default instance.
        val Default: UniVibeApi by lazy { MockUniVibeApi() }
    }
}
