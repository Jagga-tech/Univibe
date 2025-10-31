package com.example.univibe.data.mock

import com.example.univibe.domain.models.User

object MockUsers {
    val users = listOf(
        User(
            id = "1",
            username = "sarah.chen",
            fullName = "Sarah Chen",
            email = "sarah.chen@university.edu",
            avatarUrl = null,
            bio = "CS major | AI enthusiast | Coffee addict ☕",
            major = "Computer Science",
            graduationYear = 2025,
            interests = listOf("AI", "Machine Learning", "Web Dev"),
            followersCount = 245,
            followingCount = 180,
            isOnline = true,
            isVerified = true
        ),
        User(
            id = "2",
            username = "mike.johnson",
            fullName = "Mike Johnson",
            email = "mike.j@university.edu",
            avatarUrl = null,
            bio = "Engineering student | Robotics team captain 🤖",
            major = "Mechanical Engineering",
            graduationYear = 2024,
            interests = listOf("Robotics", "3D Printing", "CAD"),
            followersCount = 312,
            followingCount = 201,
            isOnline = true
        ),
        User(
            id = "3",
            username = "emma.williams",
            fullName = "Emma Williams",
            email = "emma.w@university.edu",
            avatarUrl = null,
            bio = "Biology major | Pre-med | Yoga instructor 🧘‍♀️",
            major = "Biology",
            graduationYear = 2026,
            interests = listOf("Medicine", "Research", "Wellness"),
            followersCount = 189,
            followingCount = 156
        ),
        User(
            id = "4",
            username = "alex.rodriguez",
            fullName = "Alex Rodriguez",
            email = "alex.r@university.edu",
            avatarUrl = null,
            bio = "Business major | Entrepreneur | Startup founder 🚀",
            major = "Business Administration",
            graduationYear = 2025,
            interests = listOf("Entrepreneurship", "Finance", "Marketing"),
            followersCount = 428,
            followingCount = 267,
            isOnline = true,
            isVerified = true
        ),
        User(
            id = "5",
            username = "lisa.park",
            fullName = "Lisa Park",
            email = "lisa.p@university.edu",
            avatarUrl = null,
            bio = "Art major | Digital designer | Gallery curator 🎨",
            major = "Fine Arts",
            graduationYear = 2024,
            interests = listOf("Design", "Photography", "Digital Art"),
            followersCount = 356,
            followingCount = 289
        ),
        User(
            id = "6",
            username = "david.kim",
            fullName = "David Kim",
            email = "david.k@university.edu",
            avatarUrl = null,
            bio = "Physics major | Research assistant | Amateur astronomer 🔭",
            major = "Physics",
            graduationYear = 2025,
            interests = listOf("Astrophysics", "Research", "Mathematics"),
            followersCount = 167,
            followingCount = 143,
            isOnline = true
        ),
        User(
            id = "7",
            username = "maya.patel",
            fullName = "Maya Patel",
            email = "maya.p@university.edu",
            avatarUrl = null,
            bio = "Psychology major | Mental health advocate | Peer counselor 💚",
            major = "Psychology",
            graduationYear = 2026,
            interests = listOf("Mental Health", "Counseling", "Wellness"),
            followersCount = 298,
            followingCount = 234
        ),
        User(
            id = "8",
            username = "chris.taylor",
            fullName = "Chris Taylor",
            email = "chris.t@university.edu",
            avatarUrl = null,
            bio = "Music major | Jazz pianist | Composer 🎹",
            major = "Music",
            graduationYear = 2024,
            interests = listOf("Jazz", "Composition", "Performance"),
            followersCount = 412,
            followingCount = 345,
            isVerified = true
        ),
        User(
            id = "9",
            username = "sophie.martin",
            fullName = "Sophie Martin",
            email = "sophie.m@university.edu",
            avatarUrl = null,
            bio = "Environmental Science | Sustainability club president 🌍",
            major = "Environmental Science",
            graduationYear = 2025,
            interests = listOf("Sustainability", "Climate", "Conservation"),
            followersCount = 223,
            followingCount = 189,
            isOnline = true
        ),
        User(
            id = "10",
            username = "james.wilson",
            fullName = "James Wilson",
            email = "james.w@university.edu",
            avatarUrl = null,
            bio = "History major | Debate team | Future lawyer ⚖️",
            major = "History",
            graduationYear = 2024,
            interests = listOf("Law", "Politics", "Debate"),
            followersCount = 334,
            followingCount = 278
        )
    )
    
    fun getUserById(id: String): User? = users.find { it.id == id }
    fun getRandomUser(): User = users.random()
    fun getRandomUsers(count: Int): List<User> = users.shuffled().take(count)
}