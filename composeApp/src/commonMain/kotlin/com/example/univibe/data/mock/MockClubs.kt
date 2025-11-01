package com.example.univibe.data.mock

import com.example.univibe.domain.models.*

object MockClubs {
    
    val clubs = listOf(
        Club(
            id = "c1",
            name = "AI & Machine Learning Club",
            description = "Explore the cutting edge of artificial intelligence and machine learning. Weekly workshops, guest speakers from industry, and collaborative projects. Open to all majors!",
            category = ClubCategory.TECHNOLOGY,
            logoUrl = null,
            presidentId = "1",
            president = MockUsers.users[0], // Sarah Chen
            memberCount = 156,
            members = MockUsers.users.take(5),
            isMember = true,
            isVerified = true,
            meetingSchedule = "Thursdays at 6:30 PM",
            meetingLocation = "Engineering Building, Room 342",
            socialMedia = ClubSocialMedia(
                instagram = "@univibe_aiml",
                website = "aimlclub.univibe.edu",
                email = "aiml@univibe.edu"
            ),
            upcomingEvents = MockEvents.events.take(2),
            recentPosts = MockPosts.posts.take(3),
            tags = listOf("AI", "MachineLearning", "Technology", "Programming")
        ),
        Club(
            id = "c2",
            name = "Robotics Team",
            description = "Design, build, and compete with robots! We compete in national competitions and work on exciting robotics projects. Engineering majors and hobbyists welcome.",
            category = ClubCategory.TECHNOLOGY,
            logoUrl = null,
            presidentId = "2",
            president = MockUsers.users[1], // Mike Johnson
            memberCount = 89,
            members = MockUsers.users.take(4),
            isMember = false,
            isVerified = true,
            meetingSchedule = "Tuesdays and Thursdays at 7:00 PM",
            meetingLocation = "Robotics Lab, Engineering Complex",
            socialMedia = ClubSocialMedia(
                instagram = "@univibe_robotics",
                website = "robotics.univibe.edu"
            ),
            upcomingEvents = emptyList(),
            recentPosts = MockPosts.posts.take(2),
            tags = listOf("Robotics", "Engineering", "Competition")
        ),
        Club(
            id = "c3",
            name = "Sustainability Initiative",
            description = "Making our campus greener one project at a time. Join us in environmental advocacy, sustainability projects, campus cleanups, and policy initiatives.",
            category = ClubCategory.VOLUNTEER,
            logoUrl = null,
            presidentId = "9",
            president = MockUsers.users[8], // Sophie Martin
            memberCount = 234,
            members = MockUsers.users.take(6),
            isMember = true,
            isVerified = true,
            meetingSchedule = "Wednesdays at 5:00 PM",
            meetingLocation = "Environmental Science Building",
            socialMedia = ClubSocialMedia(
                instagram = "@univibe_green",
                twitter = "@univibe_sustainability",
                email = "sustainability@univibe.edu"
            ),
            upcomingEvents = MockEvents.events.take(1),
            recentPosts = MockPosts.posts.take(2),
            tags = listOf("Environment", "Sustainability", "Climate", "Volunteer")
        ),
        Club(
            id = "c4",
            name = "Debate Society",
            description = "Sharpen your argumentation and public speaking skills. We compete in intercollegiate tournaments and host weekly practice debates on current issues.",
            category = ClubCategory.ACADEMIC,
            logoUrl = null,
            presidentId = "10",
            president = MockUsers.users[9], // James Wilson
            memberCount = 67,
            members = MockUsers.users.take(3),
            isMember = false,
            isVerified = true,
            meetingSchedule = "Mondays at 6:00 PM",
            meetingLocation = "Student Union, Room 204",
            socialMedia = ClubSocialMedia(
                email = "debate@univibe.edu"
            ),
            upcomingEvents = emptyList(),
            recentPosts = MockPosts.posts.take(1),
            tags = listOf("Debate", "PublicSpeaking", "Competition")
        ),
        Club(
            id = "c5",
            name = "Art Collective",
            description = "A community for student artists of all mediums. Monthly exhibitions, workshops, studio sessions, and collaborative projects. All skill levels welcome!",
            category = ClubCategory.ARTS,
            logoUrl = null,
            presidentId = "5",
            president = MockUsers.users[4], // Lisa Park
            memberCount = 178,
            members = MockUsers.users.take(5),
            isMember = false,
            isVerified = true,
            meetingSchedule = "Fridays at 4:00 PM",
            meetingLocation = "Art Studio, Arts Building",
            socialMedia = ClubSocialMedia(
                instagram = "@univibe_art",
                website = "artcollective.univibe.edu"
            ),
            upcomingEvents = MockEvents.events.take(1),
            recentPosts = MockPosts.posts.take(2),
            tags = listOf("Art", "Creative", "Exhibition", "Community")
        ),
        Club(
            id = "c6",
            name = "Jazz Ensemble",
            description = "University's premier jazz performance group. Weekly rehearsals, semester concerts, and opportunities to perform at campus events. Auditions required.",
            category = ClubCategory.ARTS,
            logoUrl = null,
            presidentId = "8",
            president = MockUsers.users[7], // Chris Taylor
            memberCount = 45,
            members = MockUsers.users.take(3),
            isMember = false,
            isVerified = true,
            meetingSchedule = "Mondays and Wednesdays at 7:30 PM",
            meetingLocation = "Music Hall, Performing Arts Center",
            socialMedia = ClubSocialMedia(
                instagram = "@univibe_jazz",
                website = "jazz.univibe.edu"
            ),
            upcomingEvents = MockEvents.events.filter { it.category == EventCategory.ARTS },
            recentPosts = emptyList(),
            tags = listOf("Music", "Jazz", "Performance")
        ),
        Club(
            id = "c7",
            name = "Entrepreneurship Society",
            description = "For aspiring entrepreneurs and business innovators. Startup workshops, pitch competitions, networking with founders, and mentorship opportunities.",
            category = ClubCategory.PROFESSIONAL,
            logoUrl = null,
            presidentId = "4",
            president = MockUsers.users[3], // Alex Rodriguez
            memberCount = 298,
            members = MockUsers.users.take(6),
            isMember = true,
            isVerified = true,
            meetingSchedule = "Thursdays at 6:00 PM",
            meetingLocation = "Business School, Room 101",
            socialMedia = ClubSocialMedia(
                instagram = "@univibe_entrepreneurs",
                website = "entrepreneurs.univibe.edu",
                email = "entrepreneurs@univibe.edu"
            ),
            upcomingEvents = MockEvents.events.filter { it.category == EventCategory.CAREER },
            recentPosts = MockPosts.posts.take(2),
            tags = listOf("Entrepreneurship", "Startup", "Business", "Networking")
        ),
        Club(
            id = "c8",
            name = "International Students Association",
            description = "Building a global community on campus. Cultural events, international festivals, peer support, and celebration of diversity.",
            category = ClubCategory.CULTURAL,
            logoUrl = null,
            presidentId = "1",
            president = MockUsers.users[0],
            memberCount = 412,
            members = MockUsers.users.take(7),
            isMember = false,
            isVerified = true,
            meetingSchedule = "First Sunday of each month at 3:00 PM",
            meetingLocation = "International Center",
            socialMedia = ClubSocialMedia(
                instagram = "@univibe_isa",
                email = "isa@univibe.edu"
            ),
            upcomingEvents = emptyList(),
            recentPosts = MockPosts.posts.take(1),
            tags = listOf("International", "Culture", "Community", "Diversity")
        ),
        Club(
            id = "c9",
            name = "Mental Health Advocates",
            description = "Promoting mental health awareness and supporting student wellbeing. Peer support groups, awareness campaigns, and resource sharing.",
            category = ClubCategory.VOLUNTEER,
            logoUrl = null,
            presidentId = "7",
            president = MockUsers.users[6], // Maya Patel
            memberCount = 189,
            members = MockUsers.users.take(4),
            isMember = false,
            isVerified = true,
            meetingSchedule = "Tuesdays at 5:30 PM",
            meetingLocation = "Counseling Center",
            socialMedia = ClubSocialMedia(
                instagram = "@univibe_mentalhealth",
                email = "mentalhealth@univibe.edu"
            ),
            upcomingEvents = emptyList(),
            recentPosts = MockPosts.posts.take(1),
            tags = listOf("MentalHealth", "Wellness", "Support", "Community")
        ),
        Club(
            id = "c10",
            name = "Physics Society",
            description = "For physics enthusiasts and majors. Guest lectures, lab tours, research presentations, and social events. Explore the universe together!",
            category = ClubCategory.ACADEMIC,
            logoUrl = null,
            presidentId = "6",
            president = MockUsers.users[5], // David Kim
            memberCount = 78,
            members = MockUsers.users.take(3),
            isMember = false,
            isVerified = false,
            meetingSchedule = "Thursdays at 5:00 PM",
            meetingLocation = "Physics Building, Room 215",
            socialMedia = ClubSocialMedia(
                email = "physics@univibe.edu"
            ),
            upcomingEvents = MockEvents.events.filter { it.category == EventCategory.ACADEMIC },
            recentPosts = emptyList(),
            tags = listOf("Physics", "Science", "Research", "Academic")
        )
    )
    
    fun getClubById(id: String): Club? = clubs.find { it.id == id }
    
    fun getClubsByCategory(category: ClubCategory): List<Club> {
        return clubs.filter { it.category == category }
    }
    
    fun getClubsByFilter(filter: ClubFilter): List<Club> {
        return when (filter) {
            ClubFilter.ALL -> clubs
            ClubFilter.JOINED -> clubs.filter { it.isMember }
            ClubFilter.POPULAR -> clubs.sortedByDescending { it.memberCount }
            ClubFilter.NEW -> clubs.sortedByDescending { it.createdAt }
            ClubFilter.RECOMMENDED -> clubs.take(5) // Mock recommendation
        }
    }
    
    fun getPopularClubs(): List<Club> {
        return clubs.sortedByDescending { it.memberCount }.take(5)
    }
    
    fun joinClub(clubId: String): Boolean {
        // In real app, would call API
        return true
    }
    
    fun leaveClub(clubId: String): Boolean {
        // In real app, would call API
        return true
    }
}