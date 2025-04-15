package com.tomiappdevelopment.goalstracker.presentation.core.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


@Composable
fun ProgressRings(
    progress1: Pair<Float, Float>,
    progress2: Pair<Float, Float>,
    modifier: Modifier = Modifier
) {
    val progress1Ratio = (progress1.first / progress1.second).coerceIn(0f, 1f)
    val progress2Ratio = (progress2.first / progress2.second).coerceIn(0f, 1f)

    val animatedProgress1 by animateFloatAsState(targetValue = progress1Ratio)
    val animatedProgress2 by animateFloatAsState(targetValue = progress2Ratio)

    // Extract theme colors here
    val primary = MaterialTheme.colorScheme.primary
    val primaryBackground = primary.copy(alpha = 0.3f)
    val secondary = MaterialTheme.colorScheme.secondary
    val secondaryBackground = secondary.copy(alpha = 0.3f)

    Canvas(modifier = modifier.size(120.dp)) {
        val stroke = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)

        // Ring 1 (inner)
        drawArc(
            color = primaryBackground,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke
        )
        drawArc(
            color = primary,
            startAngle = -90f,
            sweepAngle = 360f * animatedProgress1,
            useCenter = false,
            style = stroke
        )

        // Ring 2 (outer)
        drawArc(
            color = secondaryBackground,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke,
            topLeft = Offset(-12.dp.toPx(), -12.dp.toPx()),
            size = Size(size.width + 24.dp.toPx(), size.height + 24.dp.toPx())
        )
        drawArc(
            color = secondary,
            startAngle = -90f,
            sweepAngle = 360f * animatedProgress2,
            useCenter = false,
            style = stroke,
            topLeft = Offset(-12.dp.toPx(), -12.dp.toPx()),
            size = Size(size.width + 24.dp.toPx(), size.height + 24.dp.toPx())
        )
    }
}

