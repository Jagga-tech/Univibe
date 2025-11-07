package com.example.univibe.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ReelDraft(
    val id: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val videoDuration: Int, // in seconds
    val caption: String = "",
    val hashtags: List<String> = emptyList(),
    val textOverlays: List<ReelTextOverlay> = emptyList(),
    val stickerElements: List<ReelStickerElement> = emptyList(),
    val selectedMusic: MusicTrack? = null,
    val selectedSpeed: Float = 1f, // 0.5x, 1x, 1.5x, 2x
    val selectedFilter: ReelFilter = ReelFilter.NONE,
    val trimStart: Float = 0f,
    val trimEnd: Float = videoDuration.toFloat(),
    val brightness: Int = 0, // -100 to +100
    val contrast: Int = 0,   // -100 to +100
    val saturation: Int = 0  // -100 to +100
)

@Serializable
data class ReelTextOverlay(
    val id: String,
    val text: String,
    val style: TextStyle = TextStyle.MODERN,
    val color: String = "#FFFFFF", // hex color
    val fontSize: Int = 32,
    val positionX: Float = 0.5f, // normalized 0-1
    val positionY: Float = 0.7f, // normalized 0-1
    val rotation: Float = 0f
)

@Serializable
data class ReelStickerElement(
    val id: String,
    val emoji: String,
    val size: Float = 1f,
    val positionX: Float = 0.5f,
    val positionY: Float = 0.5f,
    val rotation: Float = 0f,
    val opacity: Float = 1f
)

@Serializable
data class MusicTrack(
    val id: String,
    val name: String,
    val artist: String,
    val duration: Int, // in seconds
    val coverUrl: String? = null,
    val audioUrl: String = ""
)

@Serializable
enum class ReelFilter {
    NONE,
    WARM,
    COOL,
    VINTAGE,
    GRAYSCALE,
    SEPIA,
    VIVID,
    BRIGHT,
    CINEMATIC
}

@Serializable
enum class TextStyle {
    MODERN,
    BOLD,
    ITALIC,
    OUTLINED,
    SHADOW,
    CLASSIC
}

object StickerCategoriesReel {
    val hearts = listOf(
        "❤️", "🧡", "💛", "💚", 
        "💙", "💜", "🖤", "🤍"
    )
    
    val faces = listOf(
        "😂", "🤣", "😍", "😘",
        "😎", "🥰", "😌", "😭"
    )
    
    val hands = listOf(
        "👋", "🙌", "👏", "✌️",
        "🤘", "🙏", "💪", "✊"
    )
    
    val party = listOf(
        "🎉", "🎊", "🎈", "🎁",
        "🎀", "⭐", "✨", "🌟"
    )
    
    val misc = listOf(
        "📸", "📷", "🎬", "🎭",
        "🎨", "🎪", "🎯", "🎲"
    )
    
    val music = listOf(
        "🎵", "🎶", "🎤", "🎧",
        "🎸", "🎹", "🎺", "🥁"
    )
    
    val all = mapOf(
        "Hearts" to hearts,
        "Faces" to faces,
        "Hands" to hands,
        "Party" to party,
        "Music" to music,
        "Misc" to misc
    )
}

val DEFAULT_MUSIC_TRACKS = listOf(
    MusicTrack(
        id = "music_1",
        name = "Campus Vibes",
        artist = "UniVibe Collective",
        duration = 45,
        coverUrl = "https://picsum.photos/seed/music1/100/100",
        audioUrl = "https://example.com/music1.mp3"
    ),
    MusicTrack(
        id = "music_2",
        name = "College Energy",
        artist = "Student Beats",
        duration = 52,
        coverUrl = "https://picsum.photos/seed/music2/100/100",
        audioUrl = "https://example.com/music2.mp3"
    ),
    MusicTrack(
        id = "music_3",
        name = "Campus Tales",
        artist = "The Rhythm Squad",
        duration = 38,
        coverUrl = "https://picsum.photos/seed/music3/100/100",
        audioUrl = "https://example.com/music3.mp3"
    ),
    MusicTrack(
        id = "music_4",
        name = "Study Break",
        artist = "Calm Campus",
        duration = 55,
        coverUrl = "https://picsum.photos/seed/music4/100/100",
        audioUrl = "https://example.com/music4.mp3"
    ),
    MusicTrack(
        id = "music_5",
        name = "Late Night Sessions",
        artist = "Night Owls",
        duration = 48,
        coverUrl = "https://picsum.photos/seed/music5/100/100",
        audioUrl = "https://example.com/music5.mp3"
    ),
    MusicTrack(
        id = "music_6",
        name = "Festival Mode",
        artist = "Event Masters",
        duration = 42,
        coverUrl = "https://picsum.photos/seed/music6/100/100",
        audioUrl = "https://example.com/music6.mp3"
    )
)
