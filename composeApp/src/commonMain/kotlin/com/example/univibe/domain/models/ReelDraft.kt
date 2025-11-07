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
