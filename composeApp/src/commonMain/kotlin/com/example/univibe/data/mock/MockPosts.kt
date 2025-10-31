package com.example.univibe.data.mock

import com.example.univibe.domain.models.*

object MockPosts {
    // Using a fixed base time for consistency across platforms
    private val currentTime = 1698000000000L
    private val oneHour = 3600000L
    private val oneDay = 86400000L
    
    val posts = listOf(
        // Recent posts (last hour)
        Post(
            id = "1",
            authorId = "1",
            author = MockUsers.users[0],
            type = PostType.ACHIEVEMENT,
            content = "Just got an A on my Machine Learning final project! 🎉 All those late nights in the library finally paid off. Thanks to my amazing study group!",
            imageUrls = emptyList(),
            likeCount = 45,
            commentCount = 12,
            shareCount = 3,
            isLiked = false,
            createdAt = currentTime - (30 * 60000), // 30 mins ago
            course = "CS 4780 - Machine Learning",
            achievement = Achievement(
                type = AchievementType.GRADE,
                title = "Aced ML Project",
                description = "Scored 98% on final project"
            ),
            tags = listOf("MachineLearning", "CS", "Achievement")
        ),
        Post(
            id = "2",
            authorId = "2",
            author = MockUsers.users[1],
            type = PostType.TEXT,
            content = "Anyone else pulling an all-nighter at the engineering library? Need some study buddies for Thermodynamics exam tomorrow 📚",
            imageUrls = emptyList(),
            likeCount = 23,
            commentCount = 18,
            shareCount = 1,
            createdAt = currentTime - (45 * 60000), // 45 mins ago
            course = "ENGR 2210",
            tags = listOf("StudyGroup", "Engineering", "Thermodynamics")
        ),
        Post(
            id = "3",
            authorId = "3",
            author = MockUsers.users[2],
            type = PostType.IMAGE,
            content = "Beautiful sunset from the biology lab! Sometimes you need to step back and appreciate the view 🌅 #CampusLife",
            imageUrls = listOf("sunset1", "sunset2"),
            likeCount = 89,
            commentCount = 15,
            shareCount = 7,
            createdAt = currentTime - oneHour,
            tags = listOf("CampusLife", "Sunset", "Biology")
        ),
        
        // Posts from today
        Post(
            id = "4",
            authorId = "4",
            author = MockUsers.users[3],
            type = PostType.ACHIEVEMENT,
            content = "Excited to announce that our startup just got accepted into the university's accelerator program! 🚀 Looking for talented developers and designers to join the team.",
            imageUrls = emptyList(),
            likeCount = 156,
            commentCount = 34,
            shareCount = 12,
            createdAt = currentTime - (3 * oneHour),
            achievement = Achievement(
                type = AchievementType.MILESTONE,
                title = "Startup Accelerator",
                description = "Accepted into university accelerator"
            ),
            tags = listOf("Startup", "Entrepreneurship", "Hiring")
        ),
        Post(
            id = "5",
            authorId = "5",
            author = MockUsers.users[4],
            type = PostType.EVENT,
            content = "Art exhibition opening this Friday at 6 PM! Featuring works from 20 talented student artists. Free admission and refreshments 🎨",
            imageUrls = listOf("art_poster"),
            likeCount = 67,
            commentCount = 21,
            shareCount = 15,
            createdAt = currentTime - (5 * oneHour),
            tags = listOf("Art", "Exhibition", "Event")
        ),
        Post(
            id = "6",
            authorId = "6",
            author = MockUsers.users[5],
            type = PostType.QUESTION,
            content = "Quick poll: What's everyone's favorite late-night study spot on campus? Need some new places to try!",
            imageUrls = emptyList(),
            likeCount = 34,
            commentCount = 42,
            shareCount = 2,
            createdAt = currentTime - (6 * oneHour),
            tags = listOf("StudySpots", "Question", "Campus")
        ),
        Post(
            id = "7",
            authorId = "7",
            author = MockUsers.users[6],
            type = PostType.TEXT,
            content = "Reminder: Free mental health resources are available at the counseling center. Don't hesitate to reach out if you need support. You're not alone! 💚",
            imageUrls = emptyList(),
            likeCount = 123,
            commentCount = 28,
            shareCount = 34,
            createdAt = currentTime - (8 * oneHour),
            tags = listOf("MentalHealth", "Wellness", "Support")
        ),
        
        // Yesterday
        Post(
            id = "8",
            authorId = "8",
            author = MockUsers.users[7],
            type = PostType.IMAGE,
            content = "Jazz ensemble concert was incredible last night! Thanks to everyone who came out. Full house! 🎵🎹",
            imageUrls = listOf("concert1", "concert2", "concert3"),
            likeCount = 201,
            commentCount = 45,
            shareCount = 18,
            createdAt = currentTime - oneDay,
            tags = listOf("Music", "Concert", "Jazz")
        ),
        Post(
            id = "9",
            authorId = "9",
            author = MockUsers.users[8],
            type = PostType.ACHIEVEMENT,
            content = "Our sustainability club just secured funding for the campus composting program! Small steps towards a greener campus 🌱♻️",
            imageUrls = emptyList(),
            likeCount = 178,
            commentCount = 32,
            shareCount = 24,
            createdAt = currentTime - oneDay - oneHour,
            achievement = Achievement(
                type = AchievementType.CLUB,
                title = "Funding Secured",
                description = "Campus composting program approved"
            ),
            tags = listOf("Sustainability", "Environment", "Club")
        ),
        Post(
            id = "10",
            authorId = "10",
            author = MockUsers.users[9],
            type = PostType.TEXT,
            content = "Debate tournament was intense today! Shoutout to the opposing team for the great discussion on climate policy. This is what college is all about! 🎓",
            imageUrls = emptyList(),
            likeCount = 56,
            commentCount = 19,
            shareCount = 4,
            createdAt = currentTime - oneDay - (2 * oneHour),
            tags = listOf("Debate", "Competition", "Politics")
        ),
        
        // More varied posts
        Post(
            id = "11",
            authorId = "1",
            author = MockUsers.users[0],
            type = PostType.TEXT,
            content = "Looking for someone to trade shifts with at the dining hall next Tuesday. DM me if interested!",
            imageUrls = emptyList(),
            likeCount = 12,
            commentCount = 8,
            shareCount = 0,
            createdAt = currentTime - oneDay - (4 * oneHour),
            tags = listOf("WorkStudy", "ShiftTrade")
        ),
        Post(
            id = "12",
            authorId = "2",
            author = MockUsers.users[1],
            type = PostType.IMAGE,
            content = "Robotics team won first place at the regional competition! 🏆 So proud of everyone's hard work this semester.",
            imageUrls = listOf("robotics_trophy", "team_photo"),
            likeCount = 234,
            commentCount = 56,
            shareCount = 28,
            createdAt = currentTime - (2 * oneDay),
            achievement = Achievement(
                type = AchievementType.COMPETITION,
                title = "Robotics Champions",
                description = "First place at regional competition"
            ),
            tags = listOf("Robotics", "Competition", "Engineering")
        ),
        Post(
            id = "13",
            authorId = "3",
            author = MockUsers.users[2],
            type = PostType.TEXT,
            content = "Free yoga class tomorrow at 7 AM on the quad! All levels welcome. Bring your own mat 🧘‍♀️☀️",
            imageUrls = emptyList(),
            likeCount = 67,
            commentCount = 23,
            shareCount = 15,
            createdAt = currentTime - (2 * oneDay) - oneHour,
            tags = listOf("Yoga", "Wellness", "Exercise")
        ),
        Post(
            id = "14",
            authorId = "4",
            author = MockUsers.users[3],
            type = PostType.QUESTION,
            content = "What's the best place to get affordable business casual clothes near campus? Job interview next week!",
            imageUrls = emptyList(),
            likeCount = 34,
            commentCount = 27,
            shareCount = 3,
            createdAt = currentTime - (2 * oneDay) - (3 * oneHour),
            tags = listOf("Shopping", "Career", "Advice")
        ),
        Post(
            id = "15",
            authorId = "5",
            author = MockUsers.users[4],
            type = PostType.IMAGE,
            content = "New digital art piece finished! Working on my senior thesis exhibition. Would love some feedback 🎨✨",
            imageUrls = listOf("digital_art_1"),
            likeCount = 98,
            commentCount = 31,
            shareCount = 12,
            createdAt = currentTime - (3 * oneDay),
            tags = listOf("Art", "DigitalArt", "Thesis")
        )
    )
    
    fun getPostById(id: String): Post? = posts.find { it.id == id }
    fun getRecentPosts(count: Int = 10): List<Post> = posts.take(count)
    fun getPostsByUser(userId: String): List<Post> = posts.filter { it.authorId == userId }
}