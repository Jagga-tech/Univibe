package com.example.univibe.util

import java.util.UUID as JavaUUID

actual fun generateUUID(): String = JavaUUID.randomUUID().toString()