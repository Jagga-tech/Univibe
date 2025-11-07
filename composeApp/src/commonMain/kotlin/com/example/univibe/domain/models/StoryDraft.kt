package com.example.univibe.domain.models

import kotlinx.serialization.Serializable
import com.example.univibe.util.getCurrentTimeMillis

/**
 * Enumeration of available story filters.
 */
@Serializable
enum class StoryFilter {
    NONE,
    WARM,
    COOL,
    VINTAGE,
    GRAYSCALE,
    SEPIA,
    VIVID,
    BRIGHT
}

/**
 * Data class representing a text element on a story.
 *
 * **Properties:**
 * - `text`: The text content
 * - `style`: The text styling (Modern, Bold, Italic, etc.)
 * - `color`: Text color in hex format (e.g., "#FFFFFF")
 * - `backgroundColor`: Optional background color for text (e.g., "#000000")
 * - `fontSize`: Font size in dp
 * - `x`: X position on canvas (0-1, where 1 = screen width)
 * - `y`: Y position on canvas (0-1, where 1 = screen height)
 * - `rotation`: Rotation angle in degrees
 * - `isVisible`: Whether this text element is visible
 */
@Serializable
data class TextElement(
    val id: String,
    val text: String,
    val style: TextStyle = TextStyle.MODERN,
    val color: String = "#FFFFFF",
    val backgroundColor: String? = null,
    val fontSize: Float = 32f,
    val x: Float = 0.5f,
    val y: Float = 0.5f,
    val rotation: Float = 0f,
    val isVisible: Boolean = true
)

/**
 * Data class representing a sticker/emoji on a story.
 *
 * **Properties:**
 * - `id`: Unique identifier
 * - `emoji`: The emoji character
 * - `size`: Size in dp
 * - `x`: X position on canvas (0-1)
 * - `y`: Y position on canvas (0-1)
 * - `rotation`: Rotation angle in degrees
 * - `opacity`: Opacity from 0-1
 * - `isVisible`: Whether visible
 */
@Serializable
data class StickerElement(
    val id: String,
    val emoji: String,
    val size: Float = 48f,
    val x: Float = 0.5f,
    val y: Float = 0.5f,
    val rotation: Float = 0f,
    val opacity: Float = 1f,
    val isVisible: Boolean = true
)

/**
 * Draft story model representing a story being created or edited.
 * Used to store in-progress story data before publishing.
 *
 * **Properties:**
 * - `id`: Unique identifier for this draft
 * - `imageUri`: Local file path or URI to the selected image
 * - `imageUrl`: Uploaded image URL (null until published)
 * - `type`: Type of story (IMAGE, TEXT, etc.)
 * - `textElements`: List of text overlays on the story
 * - `stickerElements`: List of stickers/emojis on the story
 * - `filter`: Applied filter effect
 * - `brightness`: Brightness adjustment (-100 to 100)
 * - `contrast`: Contrast adjustment (-100 to 100)
 * - `saturation`: Saturation adjustment (-100 to 100)
 * - `linkUrl`: Optional link for swipe-up link story
 * - `linkTitle`: Title for the link
 * - `duration`: How long story appears (in seconds, default 5)
 * - `createdAt`: Timestamp when draft was created
 * - `lastModifiedAt`: Timestamp of last edit
 * - `isPublished`: Whether this story has been published
 */
@Serializable
data class StoryDraft(
    val id: String,
    val imageUri: String? = null,
    val imageUrl: String? = null,
    val type: StoryType = StoryType.IMAGE,
    val textElements: List<TextElement> = emptyList(),
    val stickerElements: List<StickerElement> = emptyList(),
    val filter: StoryFilter = StoryFilter.NONE,
    val brightness: Int = 0,
    val contrast: Int = 0,
    val saturation: Int = 0,
    val linkUrl: String? = null,
    val linkTitle: String? = null,
    val duration: Int = 5,
    val createdAt: Long = 0L, // Use 0L as placeholder, set at creation time
    val lastModifiedAt: Long = 0L, // Use 0L as placeholder, set at creation time
    val isPublished: Boolean = false
)

/**
 * Helper function to get common emoji stickers for stories.
 */
object StickerCategories {
    val hearts = listOf("❤️", "🧡", "💛", "💚", "💙", "💜", "🖤", "🤍")
    val faces = listOf("😂", "🤣", "😍", "😘", "😎", "🥰", "😌", "😭")
    val hands = listOf("👋", "🙌", "👏", "✌️", "🤘", "🙏", "💪", "✊")
    val party = listOf("🎉", "🎊", "🎈", "🎁", "🎀", "⭐", "✨", "🌟")
    val misc = listOf("📸", "📷", "🎬", "🎭", "🎨", "🎪", "🎯", "🎲")

    fun getAll(): List<String> = hearts + faces + hands + party + misc
}
