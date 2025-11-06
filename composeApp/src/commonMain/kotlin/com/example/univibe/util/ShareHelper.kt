package com.example.univibe.util

import com.example.univibe.domain.models.*

object ShareHelper {
    
    fun sharePost(post: Post): String {
        return """
            Check out this post on UniVibe!
            
            ${post.content}
            
            By ${post.author.fullName}
        """.trimIndent()
    }
    
    fun shareEvent(event: Event): String {
        return """
            📅 ${event.title}
            
            ${event.description}
            
            📍 ${event.location.name}
            🕐 ${formatEventDate(event.startTime)}
            
            Join me at this event!
        """.trimIndent()
    }
    
    fun shareClub(club: Club): String {
        return """
            Join ${club.name} on UniVibe!
            
            ${club.description}
            
            ${club.memberCount} members
        """.trimIndent()
    }
    
    fun shareStudySession(session: StudySession): String {
        return """
            📚 Study Session: ${session.title}
            
            ${session.course} - ${session.subject}
            
            📍 ${session.location.name}
            🕐 ${formatSessionDate(session.startTime)}
            
            ${session.currentParticipants}/${session.maxParticipants} participants
        """.trimIndent()
    }
    
    fun shareDepartment(department: Department): String {
        return """
            ${department.name} (${department.abbreviation})
            
            ${department.description}
            
            📍 ${department.building}
            📧 ${department.email}
        """.trimIndent()
    }
    
    fun shareMarketplaceItem(item: MarketplaceItem): String {
        return """
            💰 ${item.title}
            
            Price: $${item.price}
            Condition: ${item.condition.displayName}
            
            ${item.description}
            
            📍 ${item.location}
        """.trimIndent()
    }
    
    fun shareJob(job: Job): String {
        return """
            💼 ${job.title} at ${job.company}
            
            ${job.type.displayName}
            ${if (job.salary != null) "💰 ${job.salary}" else ""}
            
            ${job.description}
            
            📧 Apply: ${job.contactEmail}
        """.trimIndent()
    }
    
    private fun formatEventDate(timestamp: Long): String {
        // Simple format - in real app use proper date formatter
        return "Soon"
    }
    
    private fun formatSessionDate(timestamp: Long): String {
        // Simple format - in real app use proper date formatter
        return "Soon"
    }
}