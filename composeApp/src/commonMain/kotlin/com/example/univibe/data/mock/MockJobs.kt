package com.example.univibe.data.mock

import com.example.univibe.domain.models.*

object MockJobs {
    private val oneDay = 86400000L
    
    val jobs = listOf(
        Job(
            id = "job1",
            title = "Software Engineer Intern",
            company = "Google",
            companyLogo = "🔵",
            type = JobType.INTERNSHIP,
            category = JobCategory.TECHNOLOGY,
            description = "Join Google's engineering team for a challenging summer internship. Work on real products used by millions.",
            requirements = listOf(
                "Currently enrolled in Computer Science or related field",
                "Strong programming skills in Java, Python, or C++",
                "GPA 3.5 or higher",
                "Available for summer 2024"
            ),
            responsibilities = listOf(
                "Develop and test new features",
                "Collaborate with senior engineers",
                "Participate in code reviews",
                "Attend technical talks and networking events"
            ),
            location = "Mountain View, CA",
            isRemote = false,
            salary = "$7,000 - $8,500/month",
            postedDate = 1700000000000L - (7 * oneDay),
            deadline = 1700000000000L + (14 * oneDay),
            contactEmail = "careers@google.com",
            isActive = true,
            tags = listOf("Tech", "Internship", "Summer", "Engineering")
        ),
        Job(
            id = "job2",
            title = "Data Analyst - Part-time",
            company = "Microsoft",
            companyLogo = "⬛",
            type = JobType.PART_TIME,
            category = JobCategory.TECHNOLOGY,
            description = "Help Microsoft analyze user data and generate insights. Perfect for students looking for flexible work.",
            requirements = listOf(
                "Excel and SQL proficiency",
                "Basic statistics knowledge",
                "Attention to detail"
            ),
            responsibilities = listOf(
                "Analyze datasets and create reports",
                "Support business intelligence team",
                "Data visualization and presentation"
            ),
            location = "Seattle, WA",
            isRemote = true,
            salary = "$20/hour",
            postedDate = 1700000000000L - (3 * oneDay),
            deadline = null,
            contactEmail = "jobs@microsoft.com",
            isActive = true,
            tags = listOf("Tech", "Data", "Part-time", "Remote")
        ),
        Job(
            id = "job3",
            title = "UX/UI Design Internship",
            company = "Apple",
            companyLogo = "🍎",
            type = JobType.INTERNSHIP,
            category = JobCategory.DESIGN,
            description = "Shape the future of Apple's products by designing beautiful, intuitive user experiences.",
            requirements = listOf(
                "Portfolio demonstrating design skills",
                "Proficiency in Figma or similar tools",
                "Understanding of design principles",
                "Passion for user-centered design"
            ),
            responsibilities = listOf(
                "Create wireframes and prototypes",
                "Conduct user research",
                "Collaborate with product teams",
                "Iterate on designs based on feedback"
            ),
            location = "Cupertino, CA",
            isRemote = false,
            salary = "$6,500 - $7,500/month",
            postedDate = 1700000000000L - (2 * oneDay),
            deadline = 1700000000000L + (21 * oneDay),
            contactEmail = "design-careers@apple.com",
            isActive = true,
            tags = listOf("Design", "Internship", "UX/UI", "Summer")
        ),
        Job(
            id = "job4",
            title = "Campus Ambassador",
            company = "UniVibe (On-Campus)",
            companyLogo = "🎓",
            type = JobType.WORK_STUDY,
            category = JobCategory.ON_CAMPUS,
            description = "Represent UniVibe on campus! Help promote the platform and gather student feedback.",
            requirements = listOf(
                "Enrolled full-time at the university",
                "Good communication skills",
                "2-3 hours availability per week"
            ),
            responsibilities = listOf(
                "Promote UniVibe to fellow students",
                "Gather feedback and suggestions",
                "Attend campus events",
                "Report on campus engagement"
            ),
            location = "On Campus",
            isRemote = false,
            salary = "$15/hour",
            postedDate = 1700000000000L - oneDay,
            deadline = null,
            contactEmail = "campus@univibe.edu",
            isActive = true,
            tags = listOf("On-Campus", "Work-Study", "Part-time")
        ),
        Job(
            id = "job5",
            title = "Research Assistant - Psychology",
            company = "University Psychology Department",
            companyLogo = "🧠",
            type = JobType.PART_TIME,
            category = JobCategory.RESEARCH,
            description = "Assist faculty with ongoing psychology research projects. Great for students interested in research.",
            requirements = listOf(
                "Psychology major or related field",
                "Research experience preferred",
                "Attention to detail",
                "Available 10-15 hours/week"
            ),
            responsibilities = listOf(
                "Conduct participant interviews",
                "Input and analyze data",
                "Literature review",
                "Assist with manuscript preparation"
            ),
            location = "On Campus - Psychology Building",
            isRemote = false,
            salary = "$16/hour",
            postedDate = 1700000000000L - (4 * oneDay),
            deadline = null,
            contactEmail = "psych-research@univibe.edu",
            isActive = true,
            tags = listOf("Research", "Psychology", "Part-time", "Academic")
        ),
        Job(
            id = "job6",
            title = "Marketing Co-op",
            company = "Amazon",
            companyLogo = "📦",
            type = JobType.CO_OP,
            category = JobCategory.MARKETING,
            description = "Develop marketing campaigns for Amazon's student-focused initiatives. 4-month co-op program.",
            requirements = listOf(
                "Marketing or Business major",
                "Social media experience",
                "Creative thinking",
                "Available for 4-month co-op"
            ),
            responsibilities = listOf(
                "Create marketing content",
                "Manage social media accounts",
                "Analyze campaign performance",
                "Collaborate with marketing team"
            ),
            location = "Seattle, WA",
            isRemote = true,
            salary = "$22/hour",
            postedDate = 1700000000000L - (5 * oneDay),
            deadline = 1700000000000L + (15 * oneDay),
            contactEmail = "careers@amazon.com",
            isActive = true,
            tags = listOf("Marketing", "Co-op", "Remote", "Business")
        ),
        Job(
            id = "job7",
            title = "Mechanical Engineering Intern",
            company = "Tesla",
            companyLogo = "⚡",
            type = JobType.INTERNSHIP,
            category = JobCategory.ENGINEERING,
            description = "Work on next-generation vehicle design and manufacturing. Summer internship with potential full-time conversion.",
            requirements = listOf(
                "Mechanical Engineering student",
                "CAD experience (AutoCAD, SolidWorks)",
                "Strong math and physics background",
                "Available full-time summer"
            ),
            responsibilities = listOf(
                "Design mechanical components",
                "Run simulations and analysis",
                "Prototype testing",
                "Collaborate with engineering teams"
            ),
            location = "Fremont, CA",
            isRemote = false,
            salary = "$8,000 - $9,000/month",
            postedDate = 1700000000000L - (6 * oneDay),
            deadline = 1700000000000L + (10 * oneDay),
            contactEmail = "internships@tesla.com",
            isActive = true,
            tags = listOf("Engineering", "Internship", "Manufacturing", "Summer")
        ),
        Job(
            id = "job8",
            title = "Business Development Manager - Contract",
            company = "Startup Hub",
            companyLogo = "🚀",
            type = JobType.CONTRACT,
            category = JobCategory.BUSINESS,
            description = "Help a fast-growing startup identify new business opportunities and partnerships.",
            requirements = listOf(
                "Business or MBA student",
                "Networking skills",
                "Entrepreneurial mindset",
                "Flexible schedule"
            ),
            responsibilities = listOf(
                "Identify potential partners",
                "Conduct market research",
                "Prepare business proposals",
                "Attend networking events"
            ),
            location = "Innovation District",
            isRemote = true,
            salary = "$25-30/hour (Contract)",
            postedDate = 1700000000000L - (5 * oneDay),
            deadline = null,
            contactEmail = "hiring@startuphub.com",
            isActive = true,
            tags = listOf("Business", "Contract", "Startup", "Remote")
        ),
        Job(
            id = "job9",
            title = "Teaching Assistant - Computer Science",
            company = "Computer Science Department",
            companyLogo = "💻",
            type = JobType.WORK_STUDY,
            category = JobCategory.TEACHING,
            description = "Help students learn computer science! Grade assignments, hold office hours, and lead study sessions.",
            requirements = listOf(
                "CS major with strong grades",
                "Previous CS coursework completed",
                "Good communication skills",
                "Available 8-10 hours/week"
            ),
            responsibilities = listOf(
                "Hold office hours",
                "Grade assignments and exams",
                "Lead recitation sessions",
                "Help with programming projects"
            ),
            location = "On Campus - Engineering Building",
            isRemote = false,
            salary = "$18/hour",
            postedDate = 1700000000000L - (3 * oneDay),
            deadline = null,
            contactEmail = "cs-dept@univibe.edu",
            isActive = true,
            tags = listOf("Teaching", "TA", "On-Campus", "Work-Study")
        ),
        Job(
            id = "job10",
            title = "Healthcare Research Coordinator",
            company = "University Hospital",
            companyLogo = "🏥",
            type = JobType.PART_TIME,
            category = JobCategory.HEALTHCARE,
            description = "Coordinate clinical research projects. Excellent experience for pre-med and health science students.",
            requirements = listOf(
                "Pre-med, nursing, or health sciences student",
                "Strong organizational skills",
                "HIPAA compliance understanding",
                "Available 12-15 hours/week"
            ),
            responsibilities = listOf(
                "Recruit study participants",
                "Conduct consent process",
                "Collect patient data",
                "Maintain research records"
            ),
            location = "University Hospital",
            isRemote = false,
            salary = "$17/hour",
            postedDate = 1700000000000L - (2 * oneDay),
            deadline = null,
            contactEmail = "research@hospital.edu",
            isActive = true,
            tags = listOf("Healthcare", "Research", "Part-time", "Medical")
        )
    )
    
    fun getJobById(id: String): Job? = jobs.find { it.id == id }
    
    fun getActiveJobs(): List<Job> = jobs.filter { it.isActive }
    
    fun getJobsByType(type: JobType): List<Job> = jobs.filter { it.type == type && it.isActive }
    
    fun getJobsByCategory(category: JobCategory): List<Job> = jobs.filter { it.category == category && it.isActive }
    
    fun searchJobs(query: String): List<Job> {
        return jobs.filter { job ->
            job.isActive && (
                job.title.contains(query, ignoreCase = true) ||
                job.company.contains(query, ignoreCase = true) ||
                job.description.contains(query, ignoreCase = true) ||
                job.tags.any { it.contains(query, ignoreCase = true) }
            )
        }
    }
    
    fun getRecentJobs(days: Int = 7): List<Job> {
        val cutoffTime = 1700000000000L - (days * 86400000L)
        return jobs.filter { it.isActive && it.postedDate >= cutoffTime }
    }
    
    // TODO: Replace with actual API call to backend
    fun applyForJob(jobId: String, userId: String) {
        // Implementation would save application to backend
    }
}