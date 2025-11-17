package com.example.univibe.ui.utils

/**
 * Format time in seconds to MM:SS format
 */
fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    val secsPadded = secs.toString().padStart(2, '0')
    return "$mins:$secsPadded"
}

/**
 * Format time in seconds to MM:SS or SS format
 */
fun formatDuration(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    val secsPadded = secs.toString().padStart(2, '0')
    return if (mins > 0) {
        "$mins:$secsPadded"
    } else {
        "0:$secsPadded"
    }
}