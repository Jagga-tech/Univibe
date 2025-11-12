package com.example.univibe.config

/**
 * Centralized feature flags for gating work-in-progress features at runtime.
 * These are compile-time constants so they can be referenced in expect/actual or previews.
 */
object FeatureFlags {
    const val ENABLE_STORIES: Boolean = true
    const val ENABLE_REELS: Boolean = true
    const val ENABLE_MARKETPLACE: Boolean = false // Coming soon
    const val ENABLE_JOBS: Boolean = true
    const val ENABLE_CLUBS: Boolean = true
}
