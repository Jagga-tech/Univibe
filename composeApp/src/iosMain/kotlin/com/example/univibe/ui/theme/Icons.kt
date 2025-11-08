package com.example.univibe.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

actual object PlatformIcons {
    // Fallback icons for iOS - using custom vector paths
    actual val ErrorOutline: ImageVector = ImageVector.Builder(
        name = "ErrorOutline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = Color.White) {
            moveTo(1f, 21f)
            horizontalLineTo(23f)
            lineTo(12f, 2f)
            close()
        }
    }.build()
    
    actual val Refresh: ImageVector = ImageVector.Builder(
        name = "Refresh",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = Color.White) {
            moveTo(1f, 4f)
            verticalLineTo(10f)
            horizontalLineTo(7f)
            moveTo(23f, 20f)
            verticalLineTo(14f)
            horizontalLineTo(17f)
        }
    }.build()
    
    actual val SearchOff: ImageVector = ImageVector.Builder(
        name = "SearchOff",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = Color.White) {
            moveTo(19f, 6.4f)
            lineTo(6.4f, 19f)
        }
    }.build()
}
