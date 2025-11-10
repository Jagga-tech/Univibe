package com.example.univibe.ui.utils

import androidx.compose.ui.graphics.Color

/**
 * Parse color from hex string (e.g., "#FF0000" or "#F00")
 */
fun parseHexColor(hexColor: String): Color {
    val hex = hexColor.removePrefix("#")
    return when (hex.length) {
        6 -> {
            val color = hex.toLong(16)
            Color(
                red = ((color shr 16) and 0xFF) / 255f,
                green = ((color shr 8) and 0xFF) / 255f,
                blue = (color and 0xFF) / 255f,
                alpha = 1f
            )
        }
        8 -> {
            val color = hex.toLong(16)
            Color(
                red = ((color shr 16) and 0xFF) / 255f,
                green = ((color shr 8) and 0xFF) / 255f,
                blue = (color and 0xFF) / 255f,
                alpha = ((color shr 24) and 0xFF) / 255f
            )
        }
        else -> Color.Black
    }
}