package com.example.univibe.util

import kotlin.random.Random

actual fun generateUUID(): String {
    val uuid = ByteArray(16)
    Random.nextBytes(uuid)
    
    // Set version to 4 (random)
    uuid[6] = (uuid[6].toInt() and 0x0f or 0x40).toByte()
    // Set variant to RFC 4122
    uuid[8] = (uuid[8].toInt() and 0x3f or 0x80).toByte()
    
    return buildString {
        uuid.forEachIndexed { index, byte ->
            when (index) {
                4, 6, 8, 10 -> append('-')
            }
            append(byte.toInt().and(0xff).toString(16).padStart(2, '0'))
        }
    }
}