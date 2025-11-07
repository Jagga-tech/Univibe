package com.example.univibe.util

/**
 * Cross-platform system utility functions.
 * Platform-specific implementations use expect/actual declarations.
 */
expect fun getCurrentTimeMillis(): Long

/**
 * Generates a unique ID based on current time and random number
 */
fun generateUniqueId(prefix: String = ""): String {
    val timestamp = getCurrentTimeMillis()
    val random = (0..9999).random()
    return if (prefix.isNotEmpty()) "$prefix${timestamp}_$random" else "${timestamp}_$random"
}
