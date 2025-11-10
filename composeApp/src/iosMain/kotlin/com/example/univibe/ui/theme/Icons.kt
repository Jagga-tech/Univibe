package com.example.univibe.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

actual object PlatformIcons {
    actual val ErrorOutline: ImageVector = ImageVector.Builder(
        name = "error_outline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            moveTo(12f, 20f)
            curveTo(7.59f, 20f, 4f, 16.41f, 4f, 12f)
            curveTo(4f, 7.59f, 7.59f, 4f, 12f, 4f)
            curveTo(16.41f, 4f, 20f, 7.59f, 20f, 12f)
            curveTo(20f, 16.41f, 16.41f, 20f, 12f, 20f)
            moveTo(12f, 6f)
            curveTo(11.45f, 6f, 11f, 6.45f, 11f, 7f)
            lineTo(11f, 13f)
            curveTo(11f, 13.55f, 11.45f, 14f, 12f, 14f)
            curveTo(12.55f, 14f, 13f, 13.55f, 13f, 13f)
            lineTo(13f, 7f)
            curveTo(13f, 6.45f, 12.55f, 6f, 12f, 6f)
            moveTo(12f, 16f)
            curveTo(11.45f, 16f, 11f, 16.45f, 11f, 17f)
            curveTo(11f, 17.55f, 11.45f, 18f, 12f, 18f)
            curveTo(12.55f, 18f, 13f, 17.55f, 13f, 17f)
            curveTo(13f, 16.45f, 12.55f, 16f, 12f, 16f)
        }
    }.build()

    actual val Refresh: ImageVector = ImageVector.Builder(
        name = "refresh",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(17.65f, 6.35f)
            curveTo(16.2f, 4.9f, 14.21f, 4f, 12f, 4f)
            curveTo(7.58f, 4f, 4f, 7.58f, 4f, 12f)
            curveTo(4f, 16.42f, 7.58f, 20f, 12f, 20f)
            curveTo(15.73f, 20f, 18.84f, 17.45f, 19.73f, 14f)
            lineTo(17.65f, 14f)
            curveTo(16.83f, 16.33f, 14.61f, 18f, 12f, 18f)
            curveTo(8.69f, 18f, 6f, 15.31f, 6f, 12f)
            curveTo(6f, 8.69f, 8.69f, 6f, 12f, 6f)
            curveTo(13.66f, 6f, 15.14f, 6.69f, 16.22f, 7.78f)
            lineTo(13f, 11f)
            lineTo(20f, 11f)
            lineTo(20f, 4f)
            lineTo(17.65f, 6.35f)
        }
    }.build()

    actual val SearchOff: ImageVector = ImageVector.Builder(
        name = "search_off",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(19.8f, 22.6f)
            lineTo(4.2f, 3f)
            lineTo(3f, 4.2f)
            lineTo(9.5f, 10.7f)
            curveTo(9.2f, 11.1f, 9f, 11.6f, 9f, 12.1f)
            curveTo(9f, 14.3f, 10.7f, 16f, 12.9f, 16f)
            curveTo(13.4f, 16f, 13.9f, 15.8f, 14.3f, 15.5f)
            lineTo(20.9f, 22.1f)
            lineTo(19.8f, 22.6f)
            moveTo(12.9f, 14f)
            curveTo(11.8f, 14f, 10.9f, 13.1f, 10.9f, 12f)
            curveTo(10.9f, 11.8f, 10.9f, 11.6f, 11f, 11.4f)
            lineTo(14.2f, 14.6f)
            curveTo(14f, 14f, 13.8f, 14f, 12.9f, 14f)
            moveTo(22f, 20.7f)
            lineTo(20.7f, 22f)
            curveTo(20.3f, 20.9f, 19.8f, 19.8f, 19.2f, 18.9f)
            curveTo(17.5f, 21f, 15.3f, 22.5f, 12.9f, 22.5f)
            curveTo(7.1f, 22.5f, 2.5f, 17.9f, 2.5f, 12.1f)
            curveTo(2.5f, 8.8f, 4.7f, 6.1f, 7.7f, 5.3f)
            lineTo(5.5f, 3.1f)
            curveTo(1.9f, 4.4f, 0f, 8f, 0f, 12.1f)
            curveTo(0f, 19f, 5.9f, 24.5f, 12.9f, 24.5f)
            curveTo(16.2f, 24.5f, 19.2f, 23.3f, 21.5f, 21.3f)
            lineTo(22f, 20.7f)
        }
    }.build()

    actual val Add: ImageVector = ImageVector.Builder(
        name = "add",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(19f, 13f)
            lineTo(13f, 13f)
            lineTo(13f, 19f)
            lineTo(11f, 19f)
            lineTo(11f, 13f)
            lineTo(5f, 13f)
            lineTo(5f, 11f)
            lineTo(11f, 11f)
            lineTo(11f, 5f)
            lineTo(13f, 5f)
            lineTo(13f, 11f)
            lineTo(19f, 11f)
            lineTo(19f, 13f)
        }
    }.build()

    actual val MoreVert: ImageVector = ImageVector.Builder(
        name = "more_vert",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 8f)
            curveTo(10.9f, 8f, 10f, 8.9f, 10f, 10f)
            curveTo(10f, 11.1f, 10.9f, 12f, 12f, 12f)
            curveTo(13.1f, 12f, 14f, 11.1f, 14f, 10f)
            curveTo(14f, 8.9f, 13.1f, 8f, 12f, 8f)
            moveTo(12f, 14f)
            curveTo(10.9f, 14f, 10f, 14.9f, 10f, 16f)
            curveTo(10f, 17.1f, 10.9f, 18f, 12f, 18f)
            curveTo(13.1f, 18f, 14f, 17.1f, 14f, 16f)
            curveTo(14f, 14.9f, 13.1f, 14f, 12f, 14f)
            moveTo(12f, 2f)
            curveTo(10.9f, 2f, 10f, 2.9f, 10f, 4f)
            curveTo(10f, 5.1f, 10.9f, 6f, 12f, 6f)
            curveTo(13.1f, 6f, 14f, 5.1f, 14f, 4f)
            curveTo(14f, 2.9f, 13.1f, 2f, 12f, 2f)
        }
    }.build()

    actual val Favorite: ImageVector = ImageVector.Builder(
        name = "favorite",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 21.35f)
            lineTo(10.55f, 20.03f)
            curveTo(5.4f, 15.36f, 2f, 12.28f, 2f, 8.5f)
            curveTo(2f, 5.42f, 4.42f, 3f, 7.5f, 3f)
            curveTo(9.24f, 3f, 10.91f, 3.81f, 12f, 5.09f)
            curveTo(13.09f, 3.81f, 14.76f, 3f, 16.5f, 3f)
            curveTo(19.58f, 3f, 22f, 5.42f, 22f, 8.5f)
            curveTo(22f, 12.28f, 18.6f, 15.36f, 13.45f, 20.03f)
            lineTo(12f, 21.35f)
        }
    }.build()

    actual val FavoriteBorder: ImageVector = ImageVector.Builder(
        name = "favorite_border",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = null,
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(20.84f, 4.61f)
            curveTo(20.02f, 3.78f, 18.85f, 3.31f, 17.55f, 3.31f)
            curveTo(16.01f, 3.31f, 14.57f, 4.06f, 13.61f, 5.28f)
            lineTo(12f, 7.33f)
            lineTo(10.39f, 5.28f)
            curveTo(9.43f, 4.06f, 7.99f, 3.31f, 6.45f, 3.31f)
            curveTo(5.15f, 3.31f, 3.98f, 3.78f, 3.16f, 4.61f)
            curveTo(1.5f, 6.3f, 1.5f, 8.99f, 3.39f, 11.26f)
            lineTo(12f, 20.84f)
            lineTo(20.61f, 11.26f)
            curveTo(22.5f, 8.99f, 22.5f, 6.3f, 20.84f, 4.61f)
        }
    }.build()

    actual val ChatBubbleOutline: ImageVector = ImageVector.Builder(
        name = "chat_bubble_outline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(20f, 2f)
            lineTo(4f, 2f)
            curveTo(2.9f, 2f, 2f, 2.9f, 2f, 4f)
            lineTo(2f, 18f)
            curveTo(2f, 19.1f, 2.9f, 20f, 4f, 20f)
            lineTo(18f, 20f)
            lineTo(22f, 24f)
            lineTo(22f, 4f)
            curveTo(22f, 2.9f, 21.1f, 2f, 20f, 2f)
            moveTo(20f, 18f)
            lineTo(4f, 18f)
            lineTo(4f, 4f)
            lineTo(20f, 4f)
            lineTo(20f, 18f)
        }
    }.build()

    actual val IosShare: ImageVector = ImageVector.Builder(
        name = "share",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(18f, 16.08f)
            curveTo(17.24f, 16.08f, 16.56f, 16.35f, 16.04f, 16.78f)
            lineTo(8.91f, 13.69f)
            curveTo(8.96f, 13.47f, 9f, 13.24f, 9f, 13f)
            curveTo(9f, 12.76f, 8.96f, 12.53f, 8.91f, 12.31f)
            lineTo(15.96f, 9.22f)
            curveTo(16.5f, 9.63f, 17.23f, 9.92f, 18f, 9.92f)
            curveTo(19.86f, 9.92f, 21.4f, 8.38f, 21.4f, 6.52f)
            curveTo(21.4f, 4.66f, 19.86f, 3.12f, 18f, 3.12f)
            curveTo(16.14f, 3.12f, 14.6f, 4.66f, 14.6f, 6.52f)
            curveTo(14.6f, 6.76f, 14.64f, 6.99f, 14.69f, 7.21f)
            lineTo(7.64f, 10.3f)
            curveTo(7.1f, 9.89f, 6.37f, 9.6f, 5.6f, 9.6f)
            curveTo(3.74f, 9.6f, 2.2f, 11.14f, 2.2f, 13f)
            curveTo(2.2f, 14.86f, 3.74f, 16.4f, 5.6f, 16.4f)
            curveTo(6.37f, 16.4f, 7.1f, 16.11f, 7.64f, 15.7f)
            lineTo(14.69f, 18.79f)
            curveTo(14.64f, 19.01f, 14.6f, 19.24f, 14.6f, 19.48f)
            curveTo(14.6f, 21.34f, 16.14f, 22.88f, 18f, 22.88f)
            curveTo(19.86f, 22.88f, 21.4f, 21.34f, 21.4f, 19.48f)
            curveTo(21.4f, 17.62f, 19.86f, 16.08f, 18f, 16.08f)
        }
    }.build()

    actual val Person: ImageVector = ImageVector.Builder(
        name = "person",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 12f)
            curveTo(13.66f, 12f, 15f, 10.66f, 15f, 9f)
            curveTo(15f, 7.34f, 13.66f, 6f, 12f, 6f)
            curveTo(10.34f, 6f, 9f, 7.34f, 9f, 9f)
            curveTo(9f, 10.66f, 10.34f, 12f, 12f, 12f)
            moveTo(12f, 14f)
            curveTo(10.67f, 14f, 8f, 14.75f, 8f, 16.14f)
            lineTo(8f, 18f)
            lineTo(16f, 18f)
            lineTo(16f, 16.14f)
            curveTo(16f, 14.75f, 13.33f, 14f, 12f, 14f)
        }
    }.build()

    actual val Check: ImageVector = ImageVector.Builder(
        name = "check",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(9f, 16.17f)
            lineTo(4.83f, 12f)
            lineTo(3.41f, 13.41f)
            lineTo(9f, 19f)
            lineTo(21f, 7f)
            lineTo(19.59f, 5.59f)
            lineTo(9f, 16.17f)
        }
    }.build()

    actual val Close: ImageVector = ImageVector.Builder(
        name = "close",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(19f, 6.41f)
            lineTo(17.59f, 5f)
            lineTo(12f, 10.59f)
            lineTo(6.41f, 5f)
            lineTo(5f, 6.41f)
            lineTo(10.59f, 12f)
            lineTo(5f, 17.59f)
            lineTo(6.41f, 19f)
            lineTo(12f, 13.41f)
            lineTo(17.59f, 19f)
            lineTo(19f, 17.59f)
            lineTo(13.41f, 12f)
            lineTo(19f, 6.41f)
        }
    }.build()

    actual val ChatBubble: ImageVector = ImageVector.Builder(
        name = "chat_bubble",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(20f, 2f)
            lineTo(4f, 2f)
            curveTo(2.9f, 2f, 2f, 2.9f, 2f, 4f)
            lineTo(2f, 18f)
            curveTo(2f, 19.1f, 2.9f, 20f, 4f, 20f)
            lineTo(18f, 20f)
            lineTo(22f, 24f)
            lineTo(22f, 4f)
            curveTo(22f, 2.9f, 21.1f, 2f, 20f, 2f)
        }
    }.build()

    actual val Tag: ImageVector = ImageVector.Builder(
        name = "tag",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 2f)
            lineTo(6.5f, 6.5f)
            curveTo(6.08f, 6.92f, 6f, 7.55f, 6.35f, 8.04f)
            lineTo(9.88f, 14f)
            lineTo(5f, 14f)
            curveTo(3.9f, 14f, 3f, 14.9f, 3f, 16f)
            lineTo(3f, 20f)
            curveTo(3f, 21.1f, 3.9f, 22f, 5f, 22f)
            lineTo(19f, 22f)
            curveTo(20.1f, 22f, 21f, 21.1f, 21f, 20f)
            lineTo(21f, 12f)
            lineTo(12f, 2f)
            moveTo(18f, 20f)
            lineTo(5f, 20f)
            lineTo(5f, 16f)
            lineTo(18f, 16f)
            lineTo(18f, 20f)
        }
    }.build()

    actual val Star: ImageVector = ImageVector.Builder(
        name = "star",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 17.27f)
            lineTo(18.18f, 21f)
            lineTo(16.54f, 14.83f)
            lineTo(22f, 10.5f)
            lineTo(15.81f, 10.13f)
            lineTo(12f, 4f)
            lineTo(8.19f, 10.13f)
            lineTo(2f, 10.5f)
            lineTo(7.46f, 14.83f)
            lineTo(5.82f, 21f)
            lineTo(12f, 17.27f)
        }
    }.build()

    actual val Email: ImageVector = ImageVector.Builder(
        name = "email",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(20f, 4f)
            lineTo(4f, 4f)
            curveTo(2.9f, 4f, 2f, 4.9f, 2f, 6f)
            lineTo(2f, 18f)
            curveTo(2f, 19.1f, 2.9f, 20f, 4f, 20f)
            lineTo(20f, 20f)
            curveTo(21.1f, 20f, 22f, 19.1f, 22f, 18f)
            lineTo(22f, 6f)
            curveTo(22f, 4.9f, 21.1f, 4f, 20f, 4f)
            moveTo(20f, 6f)
            lineTo(12f, 11f)
            lineTo(4f, 6f)
            lineTo(20f, 6f)
        }
    }.build()

    actual val CalendarToday: ImageVector = ImageVector.Builder(
        name = "calendar_today",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(19f, 3f)
            lineTo(18f, 3f)
            lineTo(18f, 2f)
            lineTo(16f, 2f)
            lineTo(16f, 3f)
            lineTo(8f, 3f)
            lineTo(8f, 2f)
            lineTo(6f, 2f)
            lineTo(6f, 3f)
            lineTo(5f, 3f)
            curveTo(3.89f, 3f, 3f, 3.9f, 3f, 5f)
            lineTo(3f, 19f)
            curveTo(3f, 20.1f, 3.89f, 21f, 5f, 21f)
            lineTo(19f, 21f)
            curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
            lineTo(21f, 5f)
            curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
            moveTo(19f, 19f)
            lineTo(5f, 19f)
            lineTo(5f, 8f)
            lineTo(19f, 8f)
            lineTo(19f, 19f)
        }
    }.build()

    actual val Lock: ImageVector = ImageVector.Builder(
        name = "lock",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(18f, 8f)
            lineTo(17f, 8f)
            lineTo(17f, 6f)
            curveTo(17f, 3.24f, 14.76f, 1f, 12f, 1f)
            curveTo(9.24f, 1f, 7f, 3.24f, 7f, 6f)
            lineTo(7f, 8f)
            lineTo(6f, 8f)
            curveTo(4.9f, 8f, 4f, 8.9f, 4f, 10f)
            lineTo(4f, 20f)
            curveTo(4f, 21.1f, 4.9f, 22f, 6f, 22f)
            lineTo(18f, 22f)
            curveTo(19.1f, 22f, 20f, 21.1f, 20f, 20f)
            lineTo(20f, 10f)
            curveTo(20f, 8.9f, 19.1f, 8f, 18f, 8f)
            moveTo(9f, 6f)
            curveTo(9f, 4.34f, 10.34f, 3f, 12f, 3f)
            curveTo(13.66f, 3f, 15f, 4.34f, 15f, 6f)
            lineTo(15f, 8f)
            lineTo(9f, 8f)
            lineTo(9f, 6f)
            moveTo(12f, 17f)
            curveTo(11.45f, 17f, 11f, 16.55f, 11f, 16f)
            curveTo(11f, 15.45f, 11.45f, 15f, 12f, 15f)
            curveTo(12.55f, 15f, 13f, 15.45f, 13f, 16f)
            curveTo(13f, 16.55f, 12.55f, 17f, 12f, 17f)
        }
    }.build()

    actual val Delete: ImageVector = ImageVector.Builder(
        name = "delete",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(6f, 19f)
            curveTo(6f, 20.1f, 6.9f, 21f, 8f, 21f)
            lineTo(16f, 21f)
            curveTo(17.1f, 21f, 18f, 20.1f, 18f, 19f)
            lineTo(18f, 7f)
            lineTo(6f, 7f)
            lineTo(6f, 19f)
            moveTo(19f, 4f)
            lineTo(15.5f, 4f)
            lineTo(14.5f, 3f)
            lineTo(9.5f, 3f)
            lineTo(8.5f, 4f)
            lineTo(5f, 4f)
            lineTo(5f, 6f)
            lineTo(19f, 6f)
            lineTo(19f, 4f)
        }
    }.build()

    actual val Visibility: ImageVector = ImageVector.Builder(
        name = "visibility",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 4.5f)
            curveTo(7f, 4.5f, 2.73f, 7.61f, 1f, 12f)
            curveTo(2.73f, 16.39f, 7f, 19.5f, 12f, 19.5f)
            curveTo(17f, 19.5f, 21.27f, 16.39f, 23f, 12f)
            curveTo(21.27f, 7.61f, 17f, 4.5f, 12f, 4.5f)
            moveTo(12f, 17f)
            curveTo(9.24f, 17f, 7f, 14.76f, 7f, 12f)
            curveTo(7f, 9.24f, 9.24f, 7f, 12f, 7f)
            curveTo(14.76f, 7f, 17f, 9.24f, 17f, 12f)
            curveTo(17f, 14.76f, 14.76f, 17f, 12f, 17f)
            moveTo(12f, 9f)
            curveTo(10.34f, 9f, 9f, 10.34f, 9f, 12f)
            curveTo(9f, 13.66f, 10.34f, 15f, 12f, 15f)
            curveTo(13.66f, 15f, 15f, 13.66f, 15f, 12f)
            curveTo(15f, 10.34f, 13.66f, 9f, 12f, 9f)
        }
    }.build()

    actual val VisibilityOff: ImageVector = ImageVector.Builder(
        name = "visibility_off",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 7f)
            curveTo(6.48f, 7f, 2.25f, 9.97f, 0.98f, 14.26f)
            curveTo(0.33f, 16.07f, 0.33f, 17.93f, 0.98f, 19.74f)
            curveTo(2.25f, 24.03f, 6.48f, 27f, 12f, 27f)
            curveTo(17.52f, 27f, 21.75f, 24.03f, 23.02f, 19.74f)
            curveTo(23.67f, 17.93f, 23.67f, 16.07f, 23.02f, 14.26f)
            curveTo(21.75f, 9.97f, 17.52f, 7f, 12f, 7f)
            moveTo(12f, 24.5f)
            curveTo(8.04f, 24.5f, 4.56f, 22.35f, 3.42f, 19.18f)
            curveTo(3.03f, 18.08f, 3.03f, 16.92f, 3.42f, 15.82f)
            curveTo(4.56f, 12.65f, 8.04f, 10.5f, 12f, 10.5f)
            curveTo(15.96f, 10.5f, 19.44f, 12.65f, 20.58f, 15.82f)
            curveTo(20.97f, 16.92f, 20.97f, 18.08f, 20.58f, 19.18f)
            curveTo(19.44f, 22.35f, 15.96f, 24.5f, 12f, 24.5f)
            moveTo(12f, 12.5f)
            curveTo(10.07f, 12.5f, 8.5f, 14.07f, 8.5f, 16f)
            curveTo(8.5f, 17.93f, 10.07f, 19.5f, 12f, 19.5f)
            curveTo(13.93f, 19.5f, 15.5f, 17.93f, 15.5f, 16f)
            curveTo(15.5f, 14.07f, 13.93f, 12.5f, 12f, 12.5f)
        }
    }.build()

    actual val VideoCall: ImageVector = ImageVector.Builder(
        name = "video_call",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(17f, 10.5f)
            verticalLineTo(7f)
            curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
            horizontalLineTo(4f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            verticalLineToRelative(10f)
            curveToRelative(0f, 0.55f, 0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(12f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            verticalLineToRelative(-3.5f)
            lineToRelative(4f, 4f)
            verticalLineToRelative(-11f)
            close()
        }
    }.build()

    actual val PlayCircleFilled: ImageVector = ImageVector.Builder(
        name = "play_circle_filled",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            moveTo(10f, 16.5f)
            verticalLineTo(7.5f)
            lineTo(16f, 12f)
            lineTo(10f, 16.5f)
        }
    }.build()

    actual val CheckCircle: ImageVector = ImageVector.Builder(
        name = "check_circle",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            moveTo(10f, 17f)
            lineTo(5f, 12f)
            lineTo(6.41f, 10.59f)
            lineTo(10f, 14.17f)
            lineTo(17.59f, 6.58f)
            lineTo(19f, 8f)
            lineTo(10f, 17f)
        }
    }.build()

    actual val Edit: ImageVector = ImageVector.Builder(
        name = "edit",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(3f, 17.25f)
            verticalLineTo(21f)
            horizontalLineTo(6.75f)
            lineTo(17.81f, 9.94f)
            lineTo(14.06f, 6.19f)
            lineTo(3f, 17.25f)
            moveTo(20.71f, 7.04f)
            curveTo(20.71f, 5.71f, 19.38f, 4.38f, 18.04f, 4.38f)
            lineTo(16.66f, 5.76f)
            lineTo(20.71f, 9.81f)
            verticalLineTo(7.04f)
        }
    }.build()

    actual val Phone: ImageVector = ImageVector.Builder(
        name = "phone",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(17f, 10.5f)
            verticalLineTo(7f)
            curveTo(17f, 5.9f, 16.1f, 5f, 15f, 5f)
            horizontalLineTo(9f)
            curveTo(7.9f, 5f, 7f, 5.9f, 7f, 7f)
            verticalLineTo(17f)
            curveTo(7f, 18.1f, 7.9f, 19f, 9f, 19f)
            horizontalLineTo(15f)
            curveTo(16.1f, 19f, 17f, 18.1f, 17f, 17f)
            verticalLineTo(12f)
            horizontalLineTo(15f)
            verticalLineTo(17f)
            horizontalLineTo(9f)
            verticalLineTo(7f)
            horizontalLineTo(15f)
            verticalLineTo(10.5f)
            horizontalLineTo(17f)
        }
    }.build()

    actual val Language: ImageVector = ImageVector.Builder(
        name = "language",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(11.99f, 2f)
            curveTo(6.47f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.47f, 22f, 11.99f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 11.99f, 2f)
            moveTo(19.89f, 12.1f)
            horizontalLineTo(16.64f)
            curveTo(16.48f, 13.86f, 15.99f, 15.54f, 15.23f, 17.06f)
            curveTo(17.66f, 15.93f, 19.44f, 14.24f, 19.89f, 12.1f)
            moveTo(12f, 19.96f)
            curveTo(11.06f, 18.74f, 10.4f, 17.35f, 10.16f, 15.8f)
            horizontalLineTo(13.84f)
            curveTo(13.6f, 17.35f, 12.93f, 18.74f, 12f, 19.96f)
            moveTo(4.26f, 14.23f)
            curveTo(4.1f, 13.24f, 4f, 12.22f, 4f, 12f)
            curveTo(4f, 11.78f, 4.1f, 10.76f, 4.26f, 9.77f)
            horizontalLineTo(7.36f)
            curveTo(7.24f, 10.74f, 7.18f, 11.71f, 7.18f, 12f)
            curveTo(7.18f, 12.29f, 7.24f, 13.26f, 7.36f, 14.23f)
            horizontalLineTo(4.26f)
            moveTo(4.11f, 6.94f)
            curveTo(5.85f, 5.1f, 8.22f, 3.8f, 11f, 3.44f)
            verticalLineTo(8.2f)
            curveTo(9.69f, 8.7f, 8.5f, 9.63f, 7.65f, 10.75f)
            horizontalLineTo(4.11f)
            curveTo(4.37f, 9.25f, 4.7f, 7.95f, 5.08f, 6.94f)
            moveTo(4.11f, 17.06f)
            curveTo(4.37f, 18.75f, 5.08f, 20.05f, 5.08f, 20.05f)
            curveTo(5.85f, 18.9f, 8.22f, 17.6f, 11f, 17.24f)
            verticalLineTo(20.56f)
            curveTo(8.22f, 20.2f, 5.85f, 18.9f, 4.11f, 17.06f)
            moveTo(15.23f, 6.94f)
            curveTo(15.99f, 8.46f, 16.48f, 10.14f, 16.64f, 11.9f)
            horizontalLineTo(19.89f)
            curveTo(19.44f, 9.76f, 17.66f, 8.07f, 15.23f, 6.94f)
            moveTo(12f, 4.04f)
            curveTo(12.94f, 5.26f, 13.6f, 6.65f, 13.84f, 8.2f)
            horizontalLineTo(10.16f)
            curveTo(10.4f, 6.65f, 11.06f, 5.26f, 12f, 4.04f)
            moveTo(7.65f, 13.25f)
            curveTo(8.5f, 14.37f, 9.69f, 15.3f, 11f, 15.8f)
            verticalLineTo(20.56f)
            curveTo(8.22f, 20.2f, 5.85f, 18.9f, 4.11f, 17.06f)
            horizontalLineTo(7.65f)
            verticalLineTo(13.25f)
            moveTo(16.64f, 12.1f)
            horizontalLineTo(19.89f)
            curveTo(19.44f, 14.24f, 17.66f, 15.93f, 15.23f, 17.06f)
            curveTo(15.99f, 15.54f, 16.48f, 13.86f, 16.64f, 12.1f)
        }
    }.build()

    actual val NotificationsNone: ImageVector = ImageVector.Builder(
        name = "notifications_none",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 22f)
            curveTo(13.1f, 22f, 14f, 21.9f, 14f, 21f)
            horizontalLineTo(10f)
            curveTo(10f, 21.9f, 10.9f, 22f, 12f, 22f)
            moveTo(18f, 16f)
            verticalLineTo(11f)
            curveTo(18f, 7.93f, 16.36f, 5.36f, 13.5f, 4.68f)
            verticalLineTo(4f)
            curveTo(13.5f, 3.17f, 12.83f, 2.5f, 12f, 2.5f)
            curveTo(11.17f, 2.5f, 10.5f, 3.17f, 10.5f, 4f)
            verticalLineTo(4.68f)
            curveTo(7.63f, 5.36f, 6f, 7.92f, 6f, 11f)
            verticalLineTo(16f)
            lineTo(4f, 18f)
            verticalLineTo(19f)
            horizontalLineTo(20f)
            verticalLineTo(18f)
            lineTo(18f, 16f)
            moveTo(16f, 17f)
            horizontalLineTo(8f)
            verticalLineTo(11f)
            curveTo(8f, 8.52f, 9.51f, 6.5f, 12f, 6.5f)
            curveTo(14.49f, 6.5f, 16f, 8.52f, 16f, 11f)
            verticalLineTo(17f)
        }
    }.build()

    actual val Notifications: ImageVector = ImageVector.Builder(
        name = "notifications",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 22f)
            curveTo(13.1f, 22f, 14f, 21.1f, 14f, 20f)
            horizontalLineTo(10f)
            curveTo(10f, 21.1f, 10.9f, 22f, 12f, 22f)
            moveTo(18f, 16f)
            verticalLineTo(11f)
            curveTo(18f, 7.93f, 16.36f, 5.36f, 13.5f, 4.68f)
            verticalLineTo(4f)
            curveTo(13.5f, 3.17f, 12.83f, 2.5f, 12f, 2.5f)
            curveTo(11.17f, 2.5f, 10.5f, 3.17f, 10.5f, 4f)
            verticalLineTo(4.68f)
            curveTo(7.63f, 5.36f, 6f, 7.92f, 6f, 11f)
            verticalLineTo(16f)
            lineTo(4f, 18f)
            verticalLineTo(19f)
            horizontalLineTo(20f)
            verticalLineTo(18f)
            lineTo(18f, 16f)
        }
    }.build()

    actual val VpnKey: ImageVector = ImageVector.Builder(
        name = "vpn_key",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12.65f, 10f)
            curveTo(12.88f, 9.85f, 13.05f, 9.6f, 13.05f, 9.33f)
            curveTo(13.05f, 8.61f, 12.45f, 8f, 11.75f, 8f)
            curveTo(11.05f, 8f, 10.45f, 8.6f, 10.45f, 9.3f)
            curveTo(10.45f, 10f, 11.05f, 10.6f, 11.75f, 10.6f)
            curveTo(11.96f, 10.6f, 12.15f, 10.54f, 12.31f, 10.46f)
            lineTo(13.56f, 11.71f)
            lineTo(12f, 13.27f)
            verticalLineTo(21f)
            horizontalLineTo(2f)
            verticalLineTo(9f)
            curveTo(2f, 7.9f, 2.9f, 7f, 4f, 7f)
            horizontalLineTo(11f)
            lineTo(12.65f, 10f)
            moveTo(22f, 8.82f)
            curveTo(21.4f, 7.74f, 20.3f, 7f, 19f, 7f)
            curveTo(17.34f, 7f, 16f, 8.34f, 16f, 10f)
            curveTo(16f, 11.66f, 17.34f, 13f, 19f, 13f)
            curveTo(20.3f, 13f, 21.4f, 12.26f, 22f, 11.18f)
            verticalLineTo(22f)
            horizontalLineTo(14f)
            verticalLineTo(20f)
            horizontalLineTo(20f)
            verticalLineTo(8.82f)
        }
    }.build()

    actual val Security: ImageVector = ImageVector.Builder(
        name = "security",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 1f)
            lineTo(3f, 5f)
            verticalLineTo(11f)
            curveTo(3f, 16.55f, 6.84f, 21.74f, 12f, 23f)
            curveTo(17.16f, 21.74f, 21f, 16.55f, 21f, 11f)
            verticalLineTo(5f)
            lineTo(12f, 1f)
            moveTo(12f, 5f)
            curveTo(15.31f, 5f, 18f, 7.69f, 18f, 11f)
            curveTo(18f, 14.31f, 15.31f, 17f, 12f, 17f)
            curveTo(8.69f, 17f, 6f, 14.31f, 6f, 11f)
            curveTo(6f, 7.69f, 8.69f, 5f, 12f, 5f)
            moveTo(12f, 7f)
            curveTo(9.79f, 7f, 8f, 8.79f, 8f, 11f)
            curveTo(8f, 13.21f, 9.79f, 15f, 12f, 15f)
            curveTo(14.21f, 15f, 16f, 13.21f, 16f, 11f)
            curveTo(16f, 8.79f, 14.21f, 7f, 12f, 7f)
        }
    }.build()

    actual val MailOutline: ImageVector = ImageVector.Builder(
        name = "mail_outline",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(20f, 4f)
            horizontalLineTo(4f)
            curveTo(2.9f, 4f, 2f, 4.9f, 2f, 6f)
            verticalLineTo(18f)
            curveTo(2f, 19.1f, 2.9f, 20f, 4f, 20f)
            horizontalLineTo(20f)
            curveTo(21.1f, 20f, 22f, 19.1f, 22f, 18f)
            verticalLineTo(6f)
            curveTo(22f, 4.9f, 21.1f, 4f, 20f, 4f)
            moveTo(20f, 6f)
            lineTo(12f, 13f)
            lineTo(4f, 6f)
            horizontalLineTo(20f)
            moveTo(20f, 18f)
            horizontalLineTo(4f)
            verticalLineTo(8f)
            lineTo(12f, 14f)
            lineTo(20f, 8f)
            verticalLineTo(18f)
        }
    }.build()

    actual val Schedule: ImageVector = ImageVector.Builder(
        name = "schedule",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(19f, 3f)
            horizontalLineTo(5f)
            curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
            verticalLineTo(19f)
            curveTo(3f, 20.1f, 3.9f, 21f, 5f, 21f)
            horizontalLineTo(19f)
            curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
            verticalLineTo(5f)
            curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
            moveTo(19f, 19f)
            horizontalLineTo(5f)
            verticalLineTo(5f)
            horizontalLineTo(19f)
            verticalLineTo(19f)
            moveTo(12f, 7f)
            curveTo(11.4f, 7f, 11f, 7.4f, 11f, 8f)
            verticalLineTo(13f)
            curveTo(11f, 13.6f, 11.4f, 14f, 12f, 14f)
            curveTo(12.6f, 14f, 13f, 13.6f, 13f, 13f)
            verticalLineTo(8f)
            curveTo(13f, 7.4f, 12.6f, 7f, 12f, 7f)
            moveTo(16f, 10f)
            curveTo(15.4f, 10f, 15f, 10.4f, 15f, 11f)
            verticalLineTo(16f)
            curveTo(15f, 16.6f, 15.4f, 17f, 16f, 17f)
            curveTo(16.6f, 17f, 17f, 16.6f, 17f, 16f)
            verticalLineTo(11f)
            curveTo(17f, 10.4f, 16.6f, 10f, 16f, 10f)
            moveTo(8f, 11f)
            curveTo(7.4f, 11f, 7f, 11.4f, 7f, 12f)
            verticalLineTo(16f)
            curveTo(7f, 16.6f, 7.4f, 17f, 8f, 17f)
            curveTo(8.6f, 17f, 9f, 16.6f, 9f, 16f)
            verticalLineTo(12f)
            curveTo(9f, 11.4f, 8.6f, 11f, 8f, 11f)
        }
    }.build()

    actual val Place: ImageVector = ImageVector.Builder(
        name = "place",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 2f)
            curveTo(8.13f, 2f, 5f, 5.13f, 5f, 9f)
            curveTo(5f, 14.25f, 12f, 22f, 12f, 22f)
            curveTo(12f, 22f, 19f, 14.25f, 19f, 9f)
            curveTo(19f, 5.13f, 15.87f, 2f, 12f, 2f)
            moveTo(12f, 11.5f)
            curveTo(10.6f, 11.5f, 9.5f, 10.4f, 9.5f, 9f)
            curveTo(9.5f, 7.6f, 10.6f, 6.5f, 12f, 6.5f)
            curveTo(13.4f, 6.5f, 14.5f, 7.6f, 14.5f, 9f)
            curveTo(14.5f, 10.4f, 13.4f, 11.5f, 12f, 11.5f)
        }
    }.build()

    actual val PhotoCamera: ImageVector = ImageVector.Builder(
        name = "photo_camera",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(21f, 19f)
            verticalLineTo(5f)
            curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
            horizontalLineTo(5f)
            curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
            verticalLineTo(19f)
            curveTo(3f, 20.1f, 3.9f, 21f, 5f, 21f)
            horizontalLineTo(19f)
            curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
            moveTo(12f, 6f)
            curveTo(14.76f, 6f, 17f, 8.24f, 17f, 11f)
            curveTo(17f, 13.76f, 14.76f, 16f, 12f, 16f)
            curveTo(9.24f, 16f, 7f, 13.76f, 7f, 11f)
            curveTo(7f, 8.24f, 9.24f, 6f, 12f, 6f)
            moveTo(12f, 14f)
            curveTo(13.66f, 14f, 15f, 12.66f, 15f, 11f)
            curveTo(15f, 9.34f, 13.66f, 8f, 12f, 8f)
            curveTo(10.34f, 8f, 9f, 9.34f, 9f, 11f)
            curveTo(9f, 12.66f, 10.34f, 14f, 12f, 14f)
            moveTo(18f, 5f)
            curveTo(18.55f, 5f, 19f, 4.55f, 19f, 4f)
            curveTo(19f, 3.45f, 18.55f, 3f, 18f, 3f)
            curveTo(17.45f, 3f, 17f, 3.45f, 17f, 4f)
            curveTo(17f, 4.55f, 17.45f, 5f, 18f, 5f)
        }
    }.build()

    actual val Verified: ImageVector = ImageVector.Builder(
        name = "verified",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 2f)
            curveTo(9.24f, 2f, 6.55f, 3.08f, 4.46f, 4.91f)
            lineTo(3.05f, 6.32f)
            curveTo(2.32f, 7.05f, 2.32f, 8.24f, 3.05f, 8.97f)
            lineTo(11.07f, 16.99f)
            curveTo(11.67f, 17.59f, 12.67f, 17.59f, 13.27f, 16.99f)
            lineTo(22.36f, 7.9f)
            curveTo(23.09f, 7.17f, 23.09f, 5.98f, 22.36f, 5.25f)
            lineTo(20.95f, 3.84f)
            curveTo(18.86f, 2.01f, 15.76f, 1.5f, 12.76f, 2.08f)
            curveTo(12.5f, 2.13f, 12.25f, 2f, 12f, 2f)
            moveTo(12.21f, 8.23f)
            lineTo(10f, 10.44f)
            lineTo(8.79f, 9.23f)
            curveTo(8.39f, 8.83f, 7.73f, 8.83f, 7.33f, 9.23f)
            curveTo(6.93f, 9.63f, 6.93f, 10.29f, 7.33f, 10.69f)
            lineTo(9.21f, 12.57f)
            curveTo(9.61f, 12.97f, 10.27f, 12.97f, 10.67f, 12.57f)
            lineTo(13.9f, 9.34f)
            curveTo(14.3f, 8.94f, 14.3f, 8.28f, 13.9f, 7.88f)
            curveTo(13.5f, 7.48f, 12.61f, 7.63f, 12.21f, 8.23f)
        }
    }.build()

    actual val Laptop: ImageVector = ImageVector.Builder(
        name = "laptop",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(20f, 3f)
            horizontalLineTo(4f)
            curveTo(2.9f, 3f, 2f, 3.9f, 2f, 5f)
            verticalLineTo(14f)
            curveTo(2f, 15.1f, 2.9f, 16f, 4f, 16f)
            horizontalLineTo(20f)
            curveTo(21.1f, 16f, 22f, 15.1f, 22f, 14f)
            verticalLineTo(5f)
            curveTo(22f, 3.9f, 21.1f, 3f, 20f, 3f)
            moveTo(20f, 14f)
            horizontalLineTo(4f)
            verticalLineTo(5f)
            horizontalLineTo(20f)
            verticalLineTo(14f)
            moveTo(19f, 17f)
            horizontalLineTo(5f)
            curveTo(4.45f, 17f, 4f, 17.45f, 4f, 18f)
            verticalLineTo(19f)
            curveTo(4f, 19.55f, 4.45f, 20f, 5f, 20f)
            horizontalLineTo(19f)
            curveTo(19.55f, 20f, 20f, 19.55f, 20f, 19f)
            verticalLineTo(18f)
            curveTo(20f, 17.45f, 19.55f, 17f, 19f, 17f)
        }
    }.build()

    actual val Work: ImageVector = ImageVector.Builder(
        name = "work",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(20f, 6f)
            horizontalLineTo(16f)
            verticalLineTo(4f)
            curveTo(16f, 2.9f, 15.1f, 2f, 14f, 2f)
            horizontalLineTo(10f)
            curveTo(8.9f, 2f, 8f, 2.9f, 8f, 4f)
            verticalLineTo(6f)
            horizontalLineTo(4f)
            curveTo(2.9f, 6f, 2f, 6.9f, 2f, 8f)
            verticalLineTo(20f)
            curveTo(2f, 21.1f, 2.9f, 22f, 4f, 22f)
            horizontalLineTo(20f)
            curveTo(21.1f, 22f, 22f, 21.1f, 22f, 20f)
            verticalLineTo(8f)
            curveTo(22f, 6.9f, 21.1f, 6f, 20f, 6f)
            moveTo(10f, 4f)
            horizontalLineTo(14f)
            verticalLineTo(6f)
            horizontalLineTo(10f)
            verticalLineTo(4f)
            moveTo(20f, 20f)
            horizontalLineTo(4f)
            verticalLineTo(8f)
            horizontalLineTo(20f)
            verticalLineTo(20f)
            moveTo(12f, 10f)
            curveTo(10.34f, 10f, 9f, 11.34f, 9f, 13f)
            curveTo(9f, 14.66f, 10.34f, 16f, 12f, 16f)
            curveTo(13.66f, 16f, 15f, 14.66f, 15f, 13f)
            curveTo(15f, 11.34f, 13.66f, 10f, 12f, 10f)
        }
    }.build()

    actual val AttachMoney: ImageVector = ImageVector.Builder(
        name = "attach_money",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(11.8f, 10.9f)
            curveTo(10.5f, 10.9f, 9.5f, 11.9f, 9.5f, 13.1f)
            curveTo(9.5f, 14.3f, 10.5f, 15.3f, 11.8f, 15.3f)
            curveTo(13.1f, 15.3f, 14.1f, 14.3f, 14.1f, 13.1f)
            curveTo(14.1f, 11.9f, 13.1f, 10.9f, 11.8f, 10.9f)
            moveTo(19.5f, 4f)
            horizontalLineTo(4.5f)
            curveTo(3.1f, 4f, 2f, 5.1f, 2f, 6.5f)
            verticalLineTo(17.5f)
            curveTo(2f, 18.9f, 3.1f, 20f, 4.5f, 20f)
            horizontalLineTo(19.5f)
            curveTo(20.9f, 20f, 22f, 18.9f, 22f, 17.5f)
            verticalLineTo(6.5f)
            curveTo(22f, 5.1f, 20.9f, 4f, 19.5f, 4f)
            moveTo(19.5f, 17.5f)
            horizontalLineTo(4.5f)
            verticalLineTo(6.5f)
            horizontalLineTo(19.5f)
            verticalLineTo(17.5f)
            moveTo(11.5f, 1f)
            lineTo(11.5f, 3f)
            lineTo(12.5f, 3f)
            lineTo(12.5f, 1f)
            moveTo(11.5f, 21f)
            lineTo(11.5f, 23f)
            lineTo(12.5f, 23f)
            lineTo(12.5f, 21f)
        }
    }.build()

    actual val MusicNote: ImageVector = ImageVector.Builder(
        name = "music_note",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(12f, 3f)
            verticalLineTo(12.26f)
            curveTo(11.5f, 12.09f, 10.78f, 12f, 10f, 12f)
            curveTo(7.67f, 12f, 6f, 13.67f, 6f, 16f)
            curveTo(6f, 18.33f, 7.67f, 20f, 10f, 20f)
            curveTo(12.33f, 20f, 14f, 18.33f, 14f, 16f)
            verticalLineTo(8f)
            horizontalLineTo(18f)
            verticalLineTo(3f)
            horizontalLineTo(12f)
        }
    }.build()

    actual val Camera: ImageVector = ImageVector.Builder(
        name = "camera",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(21f, 19f)
            verticalLineTo(5f)
            curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
            horizontalLineTo(5f)
            curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
            verticalLineTo(19f)
            curveTo(3f, 20.1f, 3.9f, 21f, 5f, 21f)
            horizontalLineTo(19f)
            curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
            moveTo(12f, 6f)
            curveTo(14.76f, 6f, 17f, 8.24f, 17f, 11f)
            curveTo(17f, 13.76f, 14.76f, 16f, 12f, 16f)
            curveTo(9.24f, 16f, 7f, 13.76f, 7f, 11f)
            curveTo(7f, 8.24f, 9.24f, 6f, 12f, 6f)
            moveTo(12f, 14f)
            curveTo(13.66f, 14f, 15f, 12.66f, 15f, 11f)
            curveTo(15f, 9.34f, 13.66f, 8f, 12f, 8f)
            curveTo(10.34f, 8f, 9f, 9.34f, 9f, 11f)
            curveTo(9f, 12.66f, 10.34f, 14f, 12f, 14f)
            moveTo(18f, 5f)
            curveTo(18.55f, 5f, 19f, 4.55f, 19f, 4f)
            curveTo(19f, 3.45f, 18.55f, 3f, 18f, 3f)
            curveTo(17.45f, 3f, 17f, 3.45f, 17f, 4f)
            curveTo(17f, 4.55f, 17.45f, 5f, 18f, 5f)
        }
    }.build()

    actual val Image: ImageVector = ImageVector.Builder(
        name = "image",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(21f, 19f)
            verticalLineTo(5f)
            curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
            horizontalLineTo(5f)
            curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
            verticalLineTo(19f)
            curveTo(3f, 20.1f, 3.9f, 21f, 5f, 21f)
            horizontalLineTo(19f)
            curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
            moveTo(8.5f, 13.5f)
            lineTo(11f, 16.51f)
            lineTo(14.5f, 12f)
            lineTo(19f, 18f)
            verticalLineTo(5f)
            horizontalLineTo(5f)
            verticalLineTo(19f)
            lineTo(8.5f, 13.5f)
        }
    }.build()

    actual val TextFields: ImageVector = ImageVector.Builder(
        name = "text_fields",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            pathFillType = androidx.compose.ui.graphics.PathFillType.NonZero
        ) {
            moveTo(2.5f, 4f)
            verticalLineTo(3f)
            horizontalLineTo(5.5f)
            verticalLineTo(4f)
            horizontalLineTo(4f)
            verticalLineTo(7f)
            horizontalLineTo(3f)
            verticalLineTo(4f)
            horizontalLineTo(2.5f)
            moveTo(21f, 4f)
            verticalLineTo(3f)
            horizontalLineTo(20f)
            verticalLineTo(7f)
            horizontalLineTo(19f)
            verticalLineTo(4f)
            horizontalLineTo(17.5f)
            verticalLineTo(3f)
            horizontalLineTo(20.5f)
            verticalLineTo(4f)
            horizontalLineTo(21f)
            moveTo(3f, 14f)
            verticalLineTo(10f)
            horizontalLineTo(4f)
            verticalLineTo(13f)
            horizontalLineTo(5.5f)
            verticalLineTo(10f)
            horizontalLineTo(6.5f)
            verticalLineTo(14f)
            horizontalLineTo(3f)
            moveTo(20f, 10f)
            verticalLineTo(14f)
            horizontalLineTo(17.5f)
            verticalLineTo(13f)
            horizontalLineTo(19f)
            verticalLineTo(10f)
            horizontalLineTo(20f)
            moveTo(8.5f, 11f)
            verticalLineTo(19f)
            horizontalLineTo(14f)
            verticalLineTo(18f)
            horizontalLineTo(9.5f)
            verticalLineTo(15.5f)
            horizontalLineTo(13.5f)
            verticalLineTo(14.5f)
            horizontalLineTo(9.5f)
            verticalLineTo(11f)
            horizontalLineTo(14f)
            verticalLineTo(10f)
            horizontalLineTo(8.5f)
        }
    }.build()
}
