package com.example.univibe.domain.models

import kotlinx.serialization.Serializable

/**
 * Shared enumeration of text styles available in story and reel editors.
 */
@Serializable
enum class TextStyle {
    MODERN,
    BOLD,
    ITALIC,
    OUTLINED,
    SHADOW,
    CLASSIC
}
