package com.example.univibe.ui.utils

/**
 * Format time in seconds to MM:SS format
 */
fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%d:%02d", mins, secs)
}

/**
 * Format time in seconds to MM:SS or SS format
 */
fun formatDuration(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return if (mins > 0) {
        String.format("%d:%02d", mins, secs)
    } else {
        String.format("0:%02d", secs)
    }
}