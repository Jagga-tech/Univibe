package com.example.univibe.domain.models

/**
 * Represents a job opportunity (internship, part-time, full-time, etc.).
 *
 * Jobs are posted by companies and organizations looking to hire students.
 *
 * @property id Unique identifier for the job
 * @property title Job title
 * @property company Company name
 * @property companyLogo Optional company logo URL
 * @property type Type of employment (full-time, internship, etc.)
 * @property category Job category
 * @property description Full job description
 * @property requirements List of job requirements
 * @property responsibilities List of key responsibilities
 * @property location Physical location (or remote)
 * @property isRemote Whether the job is remote
 * @property salary Optional salary/compensation information
 * @property postedDate When the job was posted
 * @property deadline Application deadline
 * @property contactEmail Email to apply or get more info
 * @property applyUrl Optional URL to apply directly
 * @property isActive Whether the job posting is still active
 * @property tags Searchable tags
 */
data class Job(
    val id: String,
    val title: String,
    val company: String,
    val companyLogo: String? = null,
    val type: JobType,
    val category: JobCategory,
    val description: String,
    val requirements: List<String> = emptyList(),
    val responsibilities: List<String> = emptyList(),
    val location: String,
    val isRemote: Boolean = false,
    val salary: String? = null,
    val postedDate: Long = 0L,
    val deadline: Long? = null,
    val contactEmail: String,
    val applyUrl: String? = null,
    val isActive: Boolean = true,
    val tags: List<String> = emptyList()
)

enum class JobType(val displayName: String) {
    FULL_TIME("Full-time"),
    PART_TIME("Part-time"),
    INTERNSHIP("Internship"),
    CO_OP("Co-op"),
    CONTRACT("Contract"),
    WORK_STUDY("Work Study")
}

enum class JobCategory(val displayName: String, val emoji: String) {
    TECHNOLOGY("Technology", "💻"),
    ENGINEERING("Engineering", "⚙️"),
    BUSINESS("Business", "💼"),
    RESEARCH("Research", "🔬"),
    TEACHING("Teaching", "📚"),
    HEALTHCARE("Healthcare", "🏥"),
    DESIGN("Design", "🎨"),
    MARKETING("Marketing", "📣"),
    ON_CAMPUS("On-Campus Jobs", "🎓"),
    OTHER("Other", "📌")
}