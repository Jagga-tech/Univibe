package com.example.univibe.domain.models

/**
 * Represents an item for sale in the student marketplace.
 *
 * Students can buy and sell items like textbooks, furniture, electronics, etc.
 *
 * @property id Unique identifier for the listing
 * @property title Title of the item
 * @property description Full description
 * @property price Price in dollars
 * @property category Item category
 * @property condition Condition of the item
 * @property sellerId ID of the seller
 * @property seller Full user object of the seller
 * @property images List of image URLs
 * @property location Where the item is located/can be picked up
 * @property isSold Whether the item has been sold
 * @property isNegotiable Whether the price is negotiable
 * @property createdAt When the listing was created
 * @property tags Searchable tags
 */
data class MarketplaceItem(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: MarketplaceCategory,
    val condition: ItemCondition,
    val sellerId: String,
    val seller: User,
    val images: List<String> = emptyList(),
    val location: String,
    val isSold: Boolean = false,
    val isNegotiable: Boolean = true,
    val createdAt: Long = 0L,
    val tags: List<String> = emptyList()
)

enum class MarketplaceCategory(val displayName: String, val emoji: String) {
    TEXTBOOKS("Textbooks", "📚"),
    ELECTRONICS("Electronics", "💻"),
    FURNITURE("Furniture", "🛋️"),
    CLOTHING("Clothing", "👕"),
    TICKETS("Tickets & Events", "🎫"),
    HOUSING("Housing & Sublets", "🏠"),
    SERVICES("Services", "🔧"),
    OTHER("Other", "📦")
}

enum class ItemCondition(val displayName: String) {
    NEW("New"),
    LIKE_NEW("Like New"),
    GOOD("Good"),
    FAIR("Fair"),
    POOR("Poor")
}