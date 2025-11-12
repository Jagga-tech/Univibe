package com.example.univibe.api.contracts

import com.example.univibe.domain.models.*

/**
 * Posts API contract for UniVibe. Mock-first, backend can implement to this.
 */
interface PostsApi {
    suspend fun getFeed(page: Int, limit: Int): List<Post>
    suspend fun getPostById(id: String): Post?
    suspend fun createPost(request: CreatePostRequest): Post
    suspend fun likePost(postId: String): Boolean
    suspend fun unlikePost(postId: String): Boolean
}

/** Request payload for creating a post */
data class CreatePostRequest(
    val authorId: String,
    val type: PostType,
    val content: String,
    val imageUrls: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val course: String? = null,
    val achievement: Achievement? = null
)

/** Users API contract */
interface UsersApi {
    suspend fun getUser(id: String): User?
    suspend fun searchUsers(query: String, limit: Int = 20): List<User>
    suspend fun follow(userId: String): Boolean
    suspend fun unfollow(userId: String): Boolean
}

/** Stories API contract */
interface StoriesApi {
    suspend fun getStoryGroups(): List<StoryGroup>
    suspend fun getStoriesForUser(userId: String): List<Story>
    suspend fun addReaction(storyId: String, reaction: StoryReaction): Boolean
    suspend fun markViewed(storyId: String)
}

/** Messages API contract */
interface MessagesApi {
    suspend fun getConversations(page: Int, limit: Int): List<Conversation>
    suspend fun getConversation(conversationId: String): Conversation?
    suspend fun sendMessage(conversationId: String, content: String): Message
}

/** Events API contract */
interface EventsApi {
    suspend fun getEvents(page: Int, limit: Int): List<Event>
    suspend fun getEvent(id: String): Event?
    suspend fun rsvp(eventId: String, attending: Boolean): Boolean
}

/** Aggregate API surface for convenience and DI */
interface UniVibeApi {
    val posts: PostsApi
    val users: UsersApi
    val stories: StoriesApi
    val messages: MessagesApi
    val events: EventsApi
}
