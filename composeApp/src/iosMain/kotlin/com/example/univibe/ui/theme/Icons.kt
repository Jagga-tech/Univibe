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
}
