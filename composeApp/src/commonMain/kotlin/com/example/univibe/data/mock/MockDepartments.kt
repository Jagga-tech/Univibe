package com.example.univibe.data.mock

import com.example.univibe.domain.models.*

object MockDepartments {
    
    val departments = listOf(
        Department(
            id = "dept1",
            name = "Computer Science",
            abbreviation = "CS",
            category = DepartmentCategory.ENGINEERING,
            description = "Leading department in computer science education and research. Programs in software engineering, AI, cybersecurity, and more.",
            building = "Engineering Building, Room 301",
            chairId = "1",
            chair = MockUsers.users[0],
            website = "cs.univibe.edu",
            email = "cs@univibe.edu",
            phone = "(555) 123-4567"
        ),
        Department(
            id = "dept2",
            name = "Mechanical Engineering",
            abbreviation = "ME",
            category = DepartmentCategory.ENGINEERING,
            description = "Innovative mechanical engineering programs with state-of-the-art labs and industry partnerships.",
            building = "Engineering Complex, Room 201",
            chairId = "2",
            chair = MockUsers.users[1],
            website = "me.univibe.edu",
            email = "me@univibe.edu",
            phone = "(555) 123-4568"
        ),
        Department(
            id = "dept3",
            name = "Biology",
            abbreviation = "BIO",
            category = DepartmentCategory.SCIENCES,
            description = "Comprehensive biology programs from molecular to ecological levels. Excellent research opportunities.",
            building = "Science Building, 3rd Floor",
            chairId = "3",
            chair = MockUsers.users[2],
            website = "bio.univibe.edu",
            email = "bio@univibe.edu",
            phone = "(555) 123-4569"
        ),
        Department(
            id = "dept4",
            name = "Business Administration",
            abbreviation = "BUS",
            category = DepartmentCategory.BUSINESS,
            description = "Preparing future business leaders with practical skills and theoretical knowledge.",
            building = "Business School, 2nd Floor",
            chairId = "4",
            chair = MockUsers.users[3],
            website = "business.univibe.edu",
            email = "business@univibe.edu",
            phone = "(555) 123-4570"
        ),
        Department(
            id = "dept5",
            name = "Psychology",
            abbreviation = "PSY",
            category = DepartmentCategory.SOCIAL_SCIENCES,
            description = "Understanding human behavior through research and clinical practice.",
            building = "Social Sciences Building, Room 401",
            chairId = "7",
            chair = MockUsers.users[6],
            website = "psych.univibe.edu",
            email = "psych@univibe.edu",
            phone = "(555) 123-4571"
        ),
        Department(
            id = "dept6",
            name = "English",
            abbreviation = "ENG",
            category = DepartmentCategory.HUMANITIES,
            description = "Literature, creative writing, and rhetoric. Develop critical thinking and communication skills.",
            building = "Humanities Building, Room 102",
            chairId = "5",
            chair = MockUsers.users[4],
            website = "english.univibe.edu",
            email = "english@univibe.edu",
            phone = "(555) 123-4572"
        ),
        Department(
            id = "dept7",
            name = "Physics",
            abbreviation = "PHY",
            category = DepartmentCategory.SCIENCES,
            description = "Exploring the fundamental laws of nature. From quantum mechanics to astrophysics.",
            building = "Physics Building, Room 305",
            chairId = "6",
            chair = MockUsers.users[5],
            website = "physics.univibe.edu",
            email = "physics@univibe.edu",
            phone = "(555) 123-4573"
        ),
        Department(
            id = "dept8",
            name = "Art & Design",
            abbreviation = "ART",
            category = DepartmentCategory.ARTS,
            description = "Studio arts, graphic design, and art history. Nurture your creativity in our modern facilities.",
            building = "Arts Building, 1st Floor",
            chairId = "8",
            chair = MockUsers.users[7],
            website = "art.univibe.edu",
            email = "art@univibe.edu",
            phone = "(555) 123-4574"
        )
    )
    
    fun getDepartmentById(id: String): Department? = departments.find { it.id == id }
    
    fun getDepartmentsByCategory(category: DepartmentCategory): List<Department> {
        return departments.filter { it.category == category }
    }
    
    fun getAllDepartments(): List<Department> = departments
}