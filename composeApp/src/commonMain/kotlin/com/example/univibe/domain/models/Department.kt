package com.example.univibe.domain.models

/**
 * Represents an academic department at the university.
 *
 * Departments allow students to browse academic programs and contact department leadership.
 *
 * @property id Unique identifier for the department
 * @property name Display name of the department
 * @property abbreviation Short abbreviation (e.g., "CS", "BIO")
 * @property category Department category/field
 * @property description Full description of the department
 * @property building Physical location/building
 * @property chairId ID of the department chair
 * @property chair Full user object of the department chair
 * @property website Optional department website
 * @property email Contact email
 * @property phone Contact phone number
 * @property logoUrl Optional department logo
 */
data class Department(
    val id: String,
    val name: String,
    val abbreviation: String,
    val category: DepartmentCategory,
    val description: String,
    val building: String,
    val chairId: String,
    val chair: User,
    val website: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val logoUrl: String? = null
)

enum class DepartmentCategory(val displayName: String, val emoji: String) {
    ENGINEERING("Engineering", "⚙️"),
    SCIENCES("Sciences", "🔬"),
    HUMANITIES("Humanities", "📚"),
    SOCIAL_SCIENCES("Social Sciences", "🌍"),
    BUSINESS("Business", "💼"),
    ARTS("Arts", "🎨"),
    HEALTH("Health Sciences", "🏥"),
    EDUCATION("Education", "🎓"),
    LAW("Law", "⚖️"),
    OTHER("Other", "📌")
}