package com.example.univibe.util

/**
 * Cross-platform UUID generation wrapper
 */
expect fun generateUUID(): String

/**
 * Convenience object for UUID operations
 */
object UUID {
    fun randomUUID(): String = generateUUID()
}