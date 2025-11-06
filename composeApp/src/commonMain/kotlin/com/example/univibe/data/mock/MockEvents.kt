package com.example.univibe.data.mock

import com.example.univibe.domain.models.*

/**
 * Mock events data provider for testing and demonstration
 *
 * Contains 10 realistic campus events spanning diverse categories:
 * - Social: Welcome party
 * - Academic: Lectures and workshops
 * - Sports: Basketball game
 * - Arts: Exhibitions and concerts
 * - Career: Job fairs and recruitment
 * - Club: Sustainability meeting
 * - Volunteer: Community service
 *
 * Integration Points:
 * All functions can be easily replaced with API calls:
 * - Replace MockUsers references with API user lookups
 * - Replace list filters with backend database queries
 * - Add pagination for large datasets
 */
object MockEvents {
    // Using a fixed timestamp for mock data (represents a baseline "now" for consistent behavior)
    private val currentTime = 1704067200000L // January 1, 2024 00:00:00 UTC
    private val oneHour = 3600000L
    private val oneDay = 86400000L
    
    /**
     * 10 realistic campus events with diverse categories and timings
     */
    val events = listOf(
        Event(
            id = "e1",
            title = "Spring Semester Welcome Party",
            description = "Join us for the biggest welcome party of the semester! Free food, music, games, and a chance to meet new friends. DJ performance starts at 9 PM!",
            category = EventCategory.SOCIAL,
            organizerId = "4",
            organizer = MockUsers.users[3], // Alex Rodriguez
            location = EventLocation(
                name = "Student Union Ballroom",
                building = "Student Union",
                room = "Main Ballroom",
                isVirtual = false
            ),
            startTime = currentTime + (3 * oneHour), // 3 hours from now
            endTime = currentTime + (7 * oneHour),
            imageUrl = "party_banner",
            maxAttendees = 500,
            currentAttendees = 234,
            attendees = MockUsers.users.take(5),
            isRSVPed = false,
            isFeatured = true,
            tags = listOf("Party", "Social", "Music", "Food")
        ),
        Event(
            id = "e2",
            title = "Introduction to Machine Learning Workshop",
            description = "Learn the fundamentals of ML with hands-on coding exercises. Bring your laptop! We'll cover supervised learning, neural networks, and practical applications.",
            category = EventCategory.WORKSHOP,
            organizerId = "1",
            organizer = MockUsers.users[0], // Sarah Chen
            location = EventLocation(
                name = "Engineering Building",
                building = "Duffield Hall",
                room = "Room 342",
                isVirtual = false
            ),
            startTime = currentTime + oneDay, // Tomorrow
            endTime = currentTime + oneDay + (3 * oneHour),
            imageUrl = "ml_workshop",
            maxAttendees = 30,
            currentAttendees = 18,
            attendees = MockUsers.users.take(3),
            isRSVPed = true,
            isFeatured = true,
            tags = listOf("MachineLearning", "Workshop", "AI", "Programming")
        ),
        Event(
            id = "e3",
            title = "Basketball Game: Tigers vs. Eagles",
            description = "Come support our Tigers basketball team! Student section tickets are free with ID. Game starts at 7 PM, doors open at 6:30 PM.",
            category = EventCategory.SPORTS,
            organizerId = "2",
            organizer = MockUsers.users[1], // Mike Johnson
            location = EventLocation(
                name = "University Arena",
                building = "Athletic Complex",
                isVirtual = false
            ),
            startTime = currentTime + (2 * oneDay),
            endTime = currentTime + (2 * oneDay) + (2 * oneHour),
            imageUrl = "basketball_game",
            maxAttendees = null, // Unlimited
            currentAttendees = 456,
            attendees = MockUsers.users.take(4),
            isRSVPed = false,
            isFeatured = true,
            tags = listOf("Basketball", "Sports", "Tigers")
        ),
        Event(
            id = "e4",
            title = "Art Exhibition: Student Showcase",
            description = "Annual student art exhibition featuring paintings, sculptures, photography, and digital art from talented student artists. Wine and cheese reception at opening.",
            category = EventCategory.ARTS,
            organizerId = "5",
            organizer = MockUsers.users[4], // Lisa Park
            location = EventLocation(
                name = "University Art Gallery",
                building = "Arts Building",
                isVirtual = false
            ),
            startTime = currentTime + (3 * oneDay),
            endTime = currentTime + (3 * oneDay) + (4 * oneHour),
            imageUrl = "art_exhibition",
            maxAttendees = 200,
            currentAttendees = 89,
            attendees = MockUsers.users.take(3),
            isRSVPed = false,
            isFeatured = false,
            tags = listOf("Art", "Exhibition", "Gallery")
        ),
        Event(
            id = "e5",
            title = "Tech Career Fair 2025",
            description = "Meet recruiters from top tech companies! Bring your resume. Companies include Google, Microsoft, Amazon, Apple, and 50+ startups. Business casual attire recommended.",
            category = EventCategory.CAREER,
            organizerId = "4",
            organizer = MockUsers.users[3], // Alex Rodriguez
            location = EventLocation(
                name = "Career Services Center",
                building = "Student Services Building",
                isVirtual = false
            ),
            startTime = currentTime + (4 * oneDay),
            endTime = currentTime + (4 * oneDay) + (6 * oneHour),
            imageUrl = "career_fair",
            maxAttendees = 1000,
            currentAttendees = 567,
            attendees = MockUsers.users.take(5),
            isRSVPed = true,
            isFeatured = true,
            tags = listOf("Career", "TechJobs", "Networking", "Recruitment")
        ),
        Event(
            id = "e6",
            title = "Sustainability Club Meeting",
            description = "Monthly meeting to discuss campus sustainability initiatives. This month: planning the Earth Day festival and reviewing composting program progress.",
            category = EventCategory.CLUB,
            organizerId = "9",
            organizer = MockUsers.users[8], // Sophie Martin
            location = EventLocation(
                name = "Environmental Science Building",
                building = "ESB",
                room = "Room 101",
                isVirtual = false
            ),
            startTime = currentTime + (5 * oneDay),
            endTime = currentTime + (5 * oneDay) + oneHour,
            imageUrl = null,
            maxAttendees = 50,
            currentAttendees = 23,
            attendees = MockUsers.users.take(3),
            isRSVPed = false,
            isFeatured = false,
            tags = listOf("Sustainability", "Environment", "Club")
        ),
        Event(
            id = "e7",
            title = "Virtual Resume Workshop",
            description = "Online workshop on crafting the perfect resume for tech internships. Learn about ATS optimization, formatting tips, and what recruiters look for.",
            category = EventCategory.WORKSHOP,
            organizerId = "1",
            organizer = MockUsers.users[0], // Sarah Chen
            location = EventLocation(
                name = "Zoom Meeting",
                isVirtual = true,
                virtualLink = "https://zoom.us/j/example"
            ),
            startTime = currentTime + (6 * oneDay),
            endTime = currentTime + (6 * oneDay) + (2 * oneHour),
            imageUrl = null,
            maxAttendees = 100,
            currentAttendees = 45,
            attendees = MockUsers.users.take(3),
            isRSVPed = false,
            isFeatured = false,
            tags = listOf("Resume", "Career", "Workshop", "Online")
        ),
        Event(
            id = "e8",
            title = "Community Food Drive",
            description = "Help us collect food for local families in need. Bring non-perishable items or volunteer to help sort donations. Every contribution makes a difference!",
            category = EventCategory.VOLUNTEER,
            organizerId = "7",
            organizer = MockUsers.users[6], // Maya Patel
            location = EventLocation(
                name = "Community Center",
                address = "123 Main Street",
                isVirtual = false
            ),
            startTime = currentTime + (7 * oneDay),
            endTime = currentTime + (7 * oneDay) + (5 * oneHour),
            imageUrl = null,
            maxAttendees = null,
            currentAttendees = 67,
            attendees = MockUsers.users.take(4),
            isRSVPed = false,
            isFeatured = false,
            tags = listOf("Volunteer", "Community", "FoodDrive")
        ),
        Event(
            id = "e9",
            title = "Jazz Ensemble Spring Concert",
            description = "The University Jazz Ensemble performs classic and contemporary jazz pieces. Featuring student soloists and special guest artist.",
            category = EventCategory.ARTS,
            organizerId = "8",
            organizer = MockUsers.users[7], // Chris Taylor
            location = EventLocation(
                name = "Music Hall",
                building = "Performing Arts Center",
                room = "Main Auditorium",
                isVirtual = false
            ),
            startTime = currentTime + (8 * oneDay),
            endTime = currentTime + (8 * oneDay) + (2 * oneHour),
            imageUrl = "jazz_concert",
            maxAttendees = 300,
            currentAttendees = 156,
            attendees = MockUsers.users.take(4),
            isRSVPed = false,
            isFeatured = true,
            tags = listOf("Music", "Jazz", "Concert", "Performance")
        ),
        Event(
            id = "e10",
            title = "Intro to Quantum Computing Lecture",
            description = "Guest lecture by Dr. Jane Smith from IBM Research on the fundamentals of quantum computing and its potential applications.",
            category = EventCategory.ACADEMIC,
            organizerId = "6",
            organizer = MockUsers.users[5], // David Kim
            location = EventLocation(
                name = "Physics Building",
                building = "Clark Hall",
                room = "Lecture Hall A",
                isVirtual = false
            ),
            startTime = currentTime + (9 * oneDay),
            endTime = currentTime + (9 * oneDay) + oneHour,
            imageUrl = null,
            maxAttendees = 150,
            currentAttendees = 89,
            attendees = MockUsers.users.take(3),
            isRSVPed = false,
            isFeatured = false,
            tags = listOf("Physics", "QuantumComputing", "Lecture", "Academic")
        )
    )
    
    // ═══════════════════════════════════════════════════════════════
    // QUERY FUNCTIONS
    // ═══════════════════════════════════════════════════════════════
    
    /**
     * Get specific event by ID
     */
    fun getEventById(id: String): Event? = events.find { it.id == id }
    
    /**
     * Get all upcoming events sorted by start time
     */
    fun getUpcomingEvents(): List<Event> {
        return events
            .filter { it.startTime > currentTime }
            .sortedBy { it.startTime }
    }
    
    /**
     * Get featured events (promoted by organizers)
     */
    fun getFeaturedEvents(): List<Event> {
        return events.filter { it.isFeatured && it.startTime > currentTime }
    }
    
    /**
     * Get events by specific category
     */
    fun getEventsByCategory(category: EventCategory): List<Event> {
        return events.filter { it.category == category && it.startTime > currentTime }
    }
    
    /**
     * Apply filter to events
     *
     * Supports filtering by:
     * - ALL: All upcoming events
     * - TODAY: Events happening today
     * - THIS_WEEK: Events within 7 days
     * - THIS_MONTH: Events within 30 days
     * - RSVPED: Events user has RSVP'd to
     * - FEATURED: Featured/promoted events
     */
    fun getEventsByFilter(filter: EventFilter): List<Event> {
        val oneDayMs = 86400000L
        val oneWeekMs = 7 * oneDayMs
        val oneMonthMs = 30 * oneDayMs
        
        return when (filter) {
            EventFilter.ALL -> getUpcomingEvents()
            EventFilter.TODAY -> events.filter { 
                it.startTime > currentTime && it.startTime < currentTime + oneDayMs 
            }.sortedBy { it.startTime }
            EventFilter.THIS_WEEK -> events.filter { 
                it.startTime > currentTime && it.startTime < currentTime + oneWeekMs 
            }.sortedBy { it.startTime }
            EventFilter.THIS_MONTH -> events.filter { 
                it.startTime > currentTime && it.startTime < currentTime + oneMonthMs 
            }.sortedBy { it.startTime }
            EventFilter.RSVPED -> events.filter { it.isRSVPed }.sortedBy { it.startTime }
            EventFilter.FEATURED -> getFeaturedEvents()
        }
    }
    
    /**
     * Search events by multiple fields
     *
     * Searches across:
     * - Event title
     * - Event description
     * - Location name
     * - Category display name
     * - Tags
     *
     * Search is case-insensitive and matches partial strings
     */
    fun searchEvents(query: String): List<Event> {
        if (query.isEmpty()) return getUpcomingEvents()
        
        val lowerQuery = query.lowercase()
        return getUpcomingEvents().filter { event ->
            event.title.lowercase().contains(lowerQuery) ||
            event.description.lowercase().contains(lowerQuery) ||
            event.location.name.lowercase().contains(lowerQuery) ||
            event.category.displayName.lowercase().contains(lowerQuery) ||
            event.tags.any { it.lowercase().contains(lowerQuery) }
        }
    }
    
    /**
     * Get events by organizer
     */
    fun getEventsByOrganizer(organizerId: String): List<Event> {
        return events.filter { it.organizerId == organizerId && it.startTime > currentTime }
    }
    
    /**
     * Get events attended/RSVP'd by a user
     */
    fun getEventsRsvpdByUser(userId: String): List<Event> {
        return events.filter { event ->
            event.attendees.any { it.id == userId } || event.isRSVPed
        }.sortedBy { it.startTime }
    }
    
    /**
     * Get all unique event categories with events
     */
    fun getAllCategories(): List<EventCategory> {
        return EventCategory.values().filter { category ->
            events.any { it.category == category && it.startTime > currentTime }
        }
    }
    
    /**
     * Get popular event tags
     */
    fun getPopularTags(): List<String> {
        return events
            .flatMap { it.tags }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .map { it.key }
    }
    
    /**
     * Get events by location type (virtual vs physical)
     */
    fun getEventsByLocationType(isVirtual: Boolean): List<Event> {
        return events.filter { 
            it.location.isVirtual == isVirtual && it.startTime > currentTime 
        }.sortedBy { it.startTime }
    }
    
    // ═══════════════════════════════════════════════════════════════
    // ACTION FUNCTIONS
    // ═══════════════════════════════════════════════════════════════
    
    /**
     * RSVP to an event
     *
     * In production: Calls API to register user for event
     * Handles capacity checking and duplicate prevention
     */
    fun rsvpToEvent(eventId: String): Boolean {
        // TODO: Replace with API call
        // POST /api/events/{eventId}/rsvp
        // Response: { success: boolean, attendees: Int }
        return true
    }
    
    /**
     * Cancel RSVP to an event
     *
     * In production: Calls API to unregister user from event
     * Updates attendee count and capacity
     */
    fun cancelRSVP(eventId: String): Boolean {
        // TODO: Replace with API call
        // DELETE /api/events/{eventId}/rsvp
        // Response: { success: boolean }
        return true
    }
    
    /**
     * Create a new event
     *
     * In production: Calls API to create event in database
     * Handles validation, image uploads, and organizer assignment
     */
    fun createEvent(event: Event): Event {
        // TODO: Replace with API call
        // POST /api/events
        // Body: event data
        // Response: created event with ID from server
        return event
    }
    
    /**
     * Update existing event
     *
     * In production: Calls API to update event details
     * Only event organizer can update
     */
    fun updateEvent(eventId: String, updates: Map<String, Any>): Boolean {
        // TODO: Replace with API call
        // PATCH /api/events/{eventId}
        // Body: updates
        // Response: { success: boolean }
        return true
    }
    
    /**
     * Cancel an event
     *
     * In production: Calls API to cancel event
     * Notifies all attendees
     */
    fun cancelEvent(eventId: String): Boolean {
        // TODO: Replace with API call
        // DELETE /api/events/{eventId}
        // Response: { success: boolean }
        return true
    }
    
    /**
     * Share event (for social features)
     *
     * In production: Creates shareable link, posts to social feed, or sends invites
     */
    fun shareEvent(eventId: String): String {
        // TODO: Replace with API call or share URL generation
        // GET /api/events/{eventId}/share-link
        // Response: { shareUrl: String }
        return "https://univibe.app/events/$eventId"
    }
}