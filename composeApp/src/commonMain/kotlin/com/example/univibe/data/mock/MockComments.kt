package com.example.univibe.data.mock

import com.example.univibe.domain.models.Comment

object MockComments {
    private val currentTime = 1698000000000L
    private val oneHour = 3600000L
    private val oneDay = 86400000L
    
    // Comments for different posts
    private val commentsMap = mapOf(
        "1" to listOf(
            Comment(
                id = "c1",
                postId = "1",
                authorId = "2",
                author = MockUsers.users[1],
                content = "Congrats! That's amazing! Your hard work really paid off 🎉",
                likeCount = 12,
                isLiked = false,
                createdAt = currentTime - (15 * 60000), // 15 mins ago
                replyCount = 2
            ),
            Comment(
                id = "c2",
                postId = "1",
                authorId = "3",
                author = MockUsers.users[2],
                content = "So proud of you! Want to study together for next semester?",
                likeCount = 8,
                isLiked = true,
                createdAt = currentTime - (20 * 60000), // 20 mins ago
                replyCount = 1
            ),
            Comment(
                id = "c3",
                postId = "1",
                authorId = "4",
                author = MockUsers.users[3],
                content = "Well deserved! I knew you could do it 💪",
                likeCount = 5,
                isLiked = false,
                createdAt = currentTime - (25 * 60000), // 25 mins ago
                replyCount = 0
            )
        ),
        "2" to listOf(
            Comment(
                id = "c4",
                postId = "2",
                authorId = "1",
                author = MockUsers.users[0],
                content = "I'm at the engineering library too! Room 204 if you want to join",
                likeCount = 15,
                isLiked = false,
                createdAt = currentTime - (30 * 60000),
                replyCount = 3
            ),
            Comment(
                id = "c5",
                postId = "2",
                authorId = "5",
                author = MockUsers.users[4],
                content = "Good luck on your exam! You got this! 🔥",
                likeCount = 7,
                isLiked = false,
                createdAt = currentTime - (35 * 60000),
                replyCount = 0
            )
        ),
        "3" to listOf(
            Comment(
                id = "c6",
                postId = "3",
                authorId = "6",
                author = MockUsers.users[5],
                content = "Beautiful shot! The lighting is perfect 📸",
                likeCount = 23,
                isLiked = true,
                createdAt = currentTime - oneHour,
                replyCount = 1
            ),
            Comment(
                id = "c7",
                postId = "3",
                authorId = "7",
                author = MockUsers.users[6],
                content = "This makes me want to spend more time at the lab!",
                likeCount = 9,
                isLiked = false,
                createdAt = currentTime - oneHour - (10 * 60000),
                replyCount = 0
            )
        ),
        "4" to listOf(
            Comment(
                id = "c8",
                postId = "4",
                authorId = "1",
                author = MockUsers.users[0],
                content = "Congratulations! I'd love to hear more about this. Are you looking for interns?",
                likeCount = 18,
                isLiked = false,
                createdAt = currentTime - (2 * oneHour),
                replyCount = 1
            ),
            Comment(
                id = "c9",
                postId = "4",
                authorId = "8",
                author = MockUsers.users[7],
                content = "This is so inspiring! What's your startup about?",
                likeCount = 12,
                isLiked = false,
                createdAt = currentTime - (2 * oneHour) - (15 * 60000),
                replyCount = 2
            ),
            Comment(
                id = "c10",
                postId = "4",
                authorId = "9",
                author = MockUsers.users[8],
                content = "Amazing opportunity! Best of luck with the accelerator program 🚀",
                likeCount = 6,
                isLiked = true,
                createdAt = currentTime - (2 * oneHour) - (30 * 60000),
                replyCount = 0
            )
        ),
        "5" to listOf(
            Comment(
                id = "c11",
                postId = "5",
                authorId = "2",
                author = MockUsers.users[1],
                content = "Can't wait to see it! Will definitely be there on Friday",
                likeCount = 14,
                isLiked = false,
                createdAt = currentTime - (3 * oneHour),
                replyCount = 1
            ),
            Comment(
                id = "c12",
                postId = "5",
                authorId = "9",
                author = MockUsers.users[8],
                content = "The poster looks amazing! Love the design 🎨",
                likeCount = 11,
                isLiked = false,
                createdAt = currentTime - (3 * oneHour) - (20 * 60000),
                replyCount = 0
            )
        )
    )
    
    fun getCommentsForPost(postId: String): List<Comment> {
        return commentsMap[postId] ?: emptyList()
    }
    
    fun addComment(comment: Comment): List<Comment> {
        // In real app, this would add to database
        return getCommentsForPost(comment.postId) + comment
    }
}