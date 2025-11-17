package com.example.univibe.util

import platform.posix.time

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
actual fun getCurrentTimeMillis(): Long {
    val seconds = time(null)
    return seconds.toLong() * 1000L
}
