package com.example.univibe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform