package com.example.univibe.data.mock

import com.example.univibe.domain.models.StudySession
import com.example.univibe.domain.models.SessionLocation
import com.example.univibe.domain.models.LocationType
import com.example.univibe.domain.models.SessionFilter
import kotlin.system.getTimeMillis

/**
 * Mock data for Study Sessions feature.
 * Contains realistic study sessions with varied topics, times, and participants.
 */
object MockStudySessions {
    private val currentTime = getTimeMillis()
    private val oneHour = 3600000L
    private val oneDay = 86400000L
    
    val sessions = listOf(
        // Session 1: CS 4780 Final Exam Prep - Library, 2 hours from now
        StudySession(
            id = "ss1",
            title = "CS 4780 Final Exam Prep",
            course = "CS 4780",
            subject = "Machine Learning",
            description = "Going over past exams and practice problems for the ML final. Bring your questions! We'll focus on neural networks, backpropagation, and gradient descent.",
            hostId = "1",
            host = MockUsers.users[0], // Sarah Chen
            location = SessionLocation(
                type = LocationType.LIBRARY,
                name = "Engineering Library",
                details = "3rd Floor Study Room 301"
            ),
            startTime = currentTime + (2 * oneHour),
            endTime = currentTime + (5 * oneHour),
            maxParticipants = 6,
            currentParticipants = 4,
            participants = listOf(
                MockUsers.users[0],
                MockUsers.users[1],
                MockUsers.users[5],
                MockUsers.users[6]
            ),
            isJoined = false,
            tags = listOf("MachineLearning", "FinalExam", "CS", "Urgent")
        ),
        
        // Session 2: Thermodynamics Problem Set - Student Center, tomorrow
        StudySession(
            id = "ss2",
            title = "Thermodynamics Problem Set 8",
            course = "ENGR 2210",
            subject = "Thermodynamics",
            description = "Working through problem set 8 together. Focus on entropy, heat cycles, and the second law. Bring calculators!",
            hostId = "2",
            host = MockUsers.users[1], // Mike Johnson
            location = SessionLocation(
                type = LocationType.STUDENT_CENTER,
                name = "Student Union",
                details = "2nd floor study lounge"
            ),
            startTime = currentTime + oneDay,
            endTime = currentTime + oneDay + (2 * oneHour),
            maxParticipants = 5,
            currentParticipants = 3,
            participants = listOf(
                MockUsers.users[1],
                MockUsers.users[2],
                MockUsers.users[4]
            ),
            isJoined = false,
            tags = listOf("Engineering", "Thermodynamics", "ProblemSet")
        ),
        
        // Session 3: Organic Chemistry Study Group - Science Library, 2 days
        StudySession(
            id = "ss3",
            title = "Organic Chemistry Study Group",
            course = "CHEM 2510",
            subject = "Organic Chemistry",
            description = "Weekly organic chem study session. Going over reaction mechanisms, nomenclature, and synthesis strategies. Perfect for beginners!",
            hostId = "3",
            host = MockUsers.users[2], // Emma Williams
            location = SessionLocation(
                type = LocationType.LIBRARY,
                name = "Science Library",
                details = "Group Study Room B"
            ),
            startTime = currentTime + (2 * oneDay),
            endTime = currentTime + (2 * oneDay) + (3 * oneHour),
            maxParticipants = 8,
            currentParticipants = 6,
            participants = listOf(
                MockUsers.users[2],
                MockUsers.users[3],
                MockUsers.users[4],
                MockUsers.users[7],
                MockUsers.users[8],
                MockUsers.users[9]
            ),
            isJoined = false,
            tags = listOf("Chemistry", "OrganicChem", "Weekly", "Popular")
        ),
        
        // Session 4: Microeconomics Midterm Review - Online, 3 days
        StudySession(
            id = "ss4",
            title = "Microeconomics Midterm Review",
            course = "ECON 1110",
            subject = "Microeconomics",
            description = "Reviewing supply & demand curves, elasticity, consumer surplus, and market structures for the midterm. Zoom link in chat!",
            hostId = "4",
            host = MockUsers.users[3], // Alex Rodriguez
            location = SessionLocation(
                type = LocationType.ONLINE,
                name = "Zoom Meeting",
                details = "Link will be shared in chat"
            ),
            startTime = currentTime + (3 * oneDay),
            endTime = currentTime + (3 * oneDay) + (2 * oneHour),
            maxParticipants = 15,
            currentParticipants = 9,
            participants = listOf(
                MockUsers.users[3],
                MockUsers.users[0],
                MockUsers.users[1],
                MockUsers.users[5],
                MockUsers.users[6],
                MockUsers.users[7],
                MockUsers.users[8],
                MockUsers.users[9],
                MockUsers.users[2]
            ),
            isJoined = false,
            tags = listOf("Economics", "Midterm", "Online", "Review")
        ),
        
        // Session 5: Calculus III Help Session - Classroom, 4 days
        StudySession(
            id = "ss5",
            title = "Calculus III Help Session",
            course = "MATH 2940",
            subject = "Multivariable Calculus",
            description = "Need help with partial derivatives, multiple integrals, and vector calculus? Come join us for a collaborative learning session!",
            hostId = "5",
            host = MockUsers.users[4], // Lisa Park
            location = SessionLocation(
                type = LocationType.CLASSROOM,
                name = "Math Building",
                details = "Room 215"
            ),
            startTime = currentTime + (4 * oneDay),
            endTime = currentTime + (4 * oneDay) + (2 * oneHour),
            maxParticipants = 10,
            currentParticipants = 5,
            participants = listOf(
                MockUsers.users[4],
                MockUsers.users[0],
                MockUsers.users[5],
                MockUsers.users[6],
                MockUsers.users[8]
            ),
            isJoined = false,
            tags = listOf("Math", "Calculus", "Help", "HelpSession")
        ),
        
        // Session 6: Python Programming Workshop - Online Discord, 5 days
        StudySession(
            id = "ss6",
            title = "Python Programming Workshop",
            course = "CS 1110",
            subject = "Intro to Programming",
            description = "Beginner-friendly Python workshop! Working on loops, functions, and data structures. No experience needed!",
            hostId = "1",
            host = MockUsers.users[0], // Sarah Chen
            location = SessionLocation(
                type = LocationType.ONLINE,
                name = "Discord Server",
                details = "Channel: #python-workshop"
            ),
            startTime = currentTime + (5 * oneDay),
            endTime = currentTime + (5 * oneDay) + (3 * oneHour),
            maxParticipants = 20,
            currentParticipants = 12,
            participants = listOf(
                MockUsers.users[0],
                MockUsers.users[1],
                MockUsers.users[2],
                MockUsers.users[3],
                MockUsers.users[4],
                MockUsers.users[5],
                MockUsers.users[6],
                MockUsers.users[7],
                MockUsers.users[8],
                MockUsers.users[9],
                MockUsers.users[1],
                MockUsers.users[2]
            ),
            isJoined = false,
            tags = listOf("Programming", "Python", "Beginner", "Workshop", "Free")
        ),
        
        // Session 7: Renaissance Art History Paper - Coffee Shop, 6 days
        StudySession(
            id = "ss7",
            title = "Art History Paper Discussion",
            course = "ARTH 2800",
            subject = "Renaissance Art",
            description = "Discussing our upcoming papers on Renaissance art and culture. Bring your outlines, drafts, and coffee! Casual atmosphere.",
            hostId = "5",
            host = MockUsers.users[4], // Lisa Park
            location = SessionLocation(
                type = LocationType.COFFEE_SHOP,
                name = "Campus Coffee",
                details = "Back corner tables - look for the study group flag"
            ),
            startTime = currentTime + (6 * oneDay),
            endTime = currentTime + (6 * oneDay) + (2 * oneHour),
            maxParticipants = 6,
            currentParticipants = 4,
            participants = listOf(
                MockUsers.users[4],
                MockUsers.users[7],
                MockUsers.users[8],
                MockUsers.users[9]
            ),
            isJoined = false,
            tags = listOf("Art", "Writing", "Renaissance", "Casual")
        )
    )
    
    /**
     * Get session by ID.
     */
    fun getSessionById(id: String): StudySession? = sessions.find { it.id == id }
    
    /**
     * Get all upcoming sessions sorted by start time.
     */
    fun getUpcomingSessions(): List<StudySession> {
        return sessions
            .filter { it.startTime > getTimeMillis() }
            .sortedBy { it.startTime }
    }
    
    /**
     * Get sessions filtered by various criteria.
     */
    fun getSessionsByFilter(filter: SessionFilter): List<StudySession> {
        val now = getTimeMillis()
        val oneDayMs = 86400000L
        val oneWeekMs = 7 * oneDayMs
        
        return when (filter) {
            SessionFilter.ALL -> getUpcomingSessions()
            SessionFilter.TODAY -> sessions.filter { 
                it.startTime > now && it.startTime < now + oneDayMs 
            }.sortedBy { it.startTime }
            SessionFilter.THIS_WEEK -> sessions.filter { 
                it.startTime > now && it.startTime < now + oneWeekMs 
            }.sortedBy { it.startTime }
            SessionFilter.MY_COURSES -> sessions.filter { 
                it.course == "CS 4780" || it.course == "CS 1110" // Mock: Sarah's courses
            }.sortedBy { it.startTime }
            SessionFilter.ONLINE_ONLY -> sessions.filter { 
                it.location.type == LocationType.ONLINE 
            }.sortedBy { it.startTime }
        }
    }
    
    /**
     * Get sessions by subject/course.
     */
    fun getSessionsBySubject(subject: String): List<StudySession> {
        return sessions.filter { it.subject.equals(subject, ignoreCase = true) }
            .sortedBy { it.startTime }
    }
    
    /**
     * Get sessions by location type.
     */
    fun getSessionsByLocation(locationType: LocationType): List<StudySession> {
        return sessions.filter { it.location.type == locationType }
            .sortedBy { it.startTime }
    }
    
    /**
     * Search sessions by title or description.
     */
    fun searchSessions(query: String): List<StudySession> {
        val lowerQuery = query.lowercase()
        return sessions.filter {
            it.title.lowercase().contains(lowerQuery) ||
            it.description.lowercase().contains(lowerQuery) ||
            it.subject.lowercase().contains(lowerQuery) ||
            it.course.lowercase().contains(lowerQuery)
        }.sortedBy { it.startTime }
    }
    
    /**
     * Get sessions hosted by specific user.
     */
    fun getSessionsByHost(hostId: String): List<StudySession> {
        return sessions.filter { it.hostId == hostId }
            .sortedBy { it.startTime }
    }
    
    /**
     * Get sessions joined by specific user.
     */
    fun getSessionsJoinedByUser(userId: String): List<StudySession> {
        return sessions.filter { session ->
            session.participants.any { it.id == userId }
        }.sortedBy { it.startTime }
    }
    
    /**
     * Join a study session (mock implementation).
     */
    fun joinSession(sessionId: String, userId: String): Boolean {
        // In real app, would call API and update local state
        return true
    }
    
    /**
     * Leave a study session (mock implementation).
     */
    fun leaveSession(sessionId: String, userId: String): Boolean {
        // In real app, would call API and update local state
        return true
    }
    
    /**
     * Create a new study session (mock implementation).
     */
    fun createSession(session: StudySession): StudySession {
        // In real app, would call API and persist to database
        return session
    }
    
    /**
     * Update a study session (mock implementation).
     */
    fun updateSession(sessionId: String, session: StudySession): StudySession {
        // In real app, would call API and persist to database
        return session
    }
    
    /**
     * Cancel a study session (mock implementation).
     */
    fun cancelSession(sessionId: String): Boolean {
        // In real app, would call API and persist to database
        return true
    }
    
    /**
     * Get all available subjects.
     */
    fun getAllSubjects(): List<String> {
        return sessions.map { it.subject }.distinct().sorted()
    }
    
    /**
     * Get popular tags.
     */
    fun getPopularTags(): List<String> {
        return sessions.flatMap { it.tags }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .map { it.first }
            .take(10)
    }
}