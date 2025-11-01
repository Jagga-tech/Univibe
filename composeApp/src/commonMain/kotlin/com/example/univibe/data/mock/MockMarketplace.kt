package com.example.univibe.data.mock

import com.example.univibe.domain.models.*

object MockMarketplace {
    
    val items = listOf(
        MarketplaceItem(
            id = "mp1",
            title = "Calculus Textbook - 10th Edition",
            description = "Barely used calculus textbook for MATH 1910. Excellent condition with no highlighting or writing. Original price $250, selling for much less!",
            price = 80.0,
            category = MarketplaceCategory.TEXTBOOKS,
            condition = ItemCondition.LIKE_NEW,
            sellerId = "1",
            seller = MockUsers.users[0],
            images = emptyList(),
            location = "North Campus",
            isSold = false,
            isNegotiable = true,
            tags = listOf("Math", "Textbook", "MATH1910")
        ),
        MarketplaceItem(
            id = "mp2",
            title = "MacBook Pro 2020 - 13 inch",
            description = "MacBook Pro in great condition. 16GB RAM, 512GB SSD. Upgraded to newer model. Comes with charger and case.",
            price = 850.0,
            category = MarketplaceCategory.ELECTRONICS,
            condition = ItemCondition.GOOD,
            sellerId = "2",
            seller = MockUsers.users[1],
            images = emptyList(),
            location = "South Campus",
            isSold = false,
            isNegotiable = true,
            tags = listOf("MacBook", "Laptop", "Apple")
        ),
        MarketplaceItem(
            id = "mp3",
            title = "Ikea Desk and Chair Set",
            description = "Moving out sale! Desk and chair in good condition. Perfect for dorm or apartment. Easy to disassemble for transport.",
            price = 60.0,
            category = MarketplaceCategory.FURNITURE,
            condition = ItemCondition.GOOD,
            sellerId = "5",
            seller = MockUsers.users[4],
            images = emptyList(),
            location = "Off-Campus Apartments",
            isSold = false,
            isNegotiable = true,
            tags = listOf("Desk", "Chair", "Furniture", "Dorm")
        ),
        MarketplaceItem(
            id = "mp4",
            title = "Concert Tickets - Spring Fest",
            description = "2 tickets to Spring Fest concert. Can't make it anymore. Face value $40 each.",
            price = 70.0,
            category = MarketplaceCategory.TICKETS,
            condition = ItemCondition.NEW,
            sellerId = "8",
            seller = MockUsers.users[7],
            images = emptyList(),
            location = "Can meet on campus",
            isSold = false,
            isNegotiable = false,
            tags = listOf("Concert", "SpringFest", "Tickets")
        ),
        MarketplaceItem(
            id = "mp5",
            title = "Summer Sublet Available",
            description = "1BR in 3BR apartment. $600/month + utilities. Close to campus, furnished, parking available. May-August.",
            price = 600.0,
            category = MarketplaceCategory.HOUSING,
            condition = ItemCondition.NEW,
            sellerId = "6",
            seller = MockUsers.users[5],
            images = emptyList(),
            location = "College Avenue Apartments",
            isSold = false,
            isNegotiable = true,
            tags = listOf("Sublet", "Summer", "Housing", "Apartment")
        ),
        MarketplaceItem(
            id = "mp6",
            title = "Tutoring Services - Math & Physics",
            description = "Experienced tutor offering help in calculus, physics, and chemistry. $25/hour. Flexible schedule.",
            price = 25.0,
            category = MarketplaceCategory.SERVICES,
            condition = ItemCondition.NEW,
            sellerId = "1",
            seller = MockUsers.users[0],
            images = emptyList(),
            location = "Library or online",
            isSold = false,
            isNegotiable = false,
            tags = listOf("Tutoring", "Math", "Physics", "Academic")
        ),
        MarketplaceItem(
            id = "mp7",
            title = "Mini Fridge - Great Condition",
            description = "Perfect for dorm room. Works great, very clean. Moving and can't take it with me.",
            price = 40.0,
            category = MarketplaceCategory.FURNITURE,
            condition = ItemCondition.GOOD,
            sellerId = "9",
            seller = MockUsers.users[8],
            images = emptyList(),
            location = "East Campus Dorms",
            isSold = false,
            isNegotiable = true,
            tags = listOf("Fridge", "Dorm", "Appliance")
        ),
        MarketplaceItem(
            id = "mp8",
            title = "Physics Lab Manual - Spring 2024",
            description = "Official physics lab manual for spring semester. Barely opened, perfect condition.",
            price = 35.0,
            category = MarketplaceCategory.TEXTBOOKS,
            condition = ItemCondition.NEW,
            sellerId = "3",
            seller = MockUsers.users[2],
            images = emptyList(),
            location = "Library",
            isSold = false,
            isNegotiable = true,
            tags = listOf("Physics", "Lab", "Manual")
        ),
        MarketplaceItem(
            id = "mp9",
            title = "Winter Jacket - North Face",
            description = "Warm winter jacket in excellent condition. Perfect for campus winters. Size M.",
            price = 45.0,
            category = MarketplaceCategory.CLOTHING,
            condition = ItemCondition.LIKE_NEW,
            sellerId = "4",
            seller = MockUsers.users[3],
            images = emptyList(),
            location = "Central Campus",
            isSold = false,
            isNegotiable = true,
            tags = listOf("Jacket", "Winter", "Clothing")
        ),
        MarketplaceItem(
            id = "mp10",
            title = "Loft Bed Frame - Full Size",
            description = "Space-saving loft bed frame. Perfect for dorm rooms. Easily fits a desk underneath. Very sturdy.",
            price = 150.0,
            category = MarketplaceCategory.FURNITURE,
            condition = ItemCondition.GOOD,
            sellerId = "7",
            seller = MockUsers.users[6],
            images = emptyList(),
            location = "West Campus",
            isSold = false,
            isNegotiable = true,
            tags = listOf("Bed", "Loft", "Furniture", "Dorm")
        ),
        MarketplaceItem(
            id = "mp11",
            title = "AirPods Pro - Original",
            description = "Genuine Apple AirPods Pro with active noise cancellation. Minor scratches on case but working perfectly.",
            price = 120.0,
            category = MarketplaceCategory.ELECTRONICS,
            condition = ItemCondition.GOOD,
            sellerId = "8",
            seller = MockUsers.users[7],
            images = emptyList(),
            location = "Downtown campus store",
            isSold = false,
            isNegotiable = true,
            tags = listOf("AirPods", "Apple", "Electronics", "Headphones")
        ),
        MarketplaceItem(
            id = "mp12",
            title = "Biology Textbook - Campbell 12th Edition",
            description = "Campbell biology textbook for BIOL 2010. Some highlighting but in good condition.",
            price = 65.0,
            category = MarketplaceCategory.TEXTBOOKS,
            condition = ItemCondition.GOOD,
            sellerId = "2",
            seller = MockUsers.users[1],
            images = emptyList(),
            location = "Science Building",
            isSold = false,
            isNegotiable = true,
            tags = listOf("Biology", "Textbook", "Campbell")
        )
    )
    
    fun getItemById(id: String): MarketplaceItem? = items.find { it.id == id }
    
    fun getItemsByCategory(category: MarketplaceCategory): List<MarketplaceItem> {
        return items.filter { it.category == category && !it.isSold }
    }
    
    fun getAvailableItems(): List<MarketplaceItem> = items.filter { !it.isSold }
    
    fun searchItems(query: String): List<MarketplaceItem> {
        return items.filter { item ->
            !item.isSold && (
                item.title.contains(query, ignoreCase = true) ||
                item.description.contains(query, ignoreCase = true) ||
                item.tags.any { it.contains(query, ignoreCase = true) }
            )
        }
    }
    
    // TODO: Replace with actual API call to backend
    fun markItemAsSold(itemId: String) {
        // Implementation would update backend
    }
    
    // TODO: Replace with actual API call to backend
    fun updatePrice(itemId: String, newPrice: Double) {
        // Implementation would update backend
    }
}