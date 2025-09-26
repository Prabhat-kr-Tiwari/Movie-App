package com.prabhat.movieapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

// --- Reusable Composables ---

@Composable
fun AnimatedCircle(
    isActive: Boolean,
    inactiveColor: Color = Color.Black,
    activeColor: Color = Color.Blue, // Like the image
    size: Dp = 40.dp,
    animationDuration: Int = 500
) {
    val targetColor = if (isActive) activeColor else inactiveColor
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = animationDuration)
    )

 /*   Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(animatedColor)
    )*/
    Box(
        modifier = Modifier
            .size(size) // <-- Makes the Box square (width = height = size)
            .clip(CircleShape) // <-- Clips the square area into a circle
            .background(animatedColor)
    )
}

@Composable
fun AnimatedArrow(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    inactiveColor: Color = Color.LightGray,
    activeColor: Color = Color.Green, // Like the image
    strokeWidth: Dp = 2.dp,
    arrowHeadSize: Dp = 6.dp,
    animationDuration: Int = 500
) {
    val targetColor = if (isActive) activeColor else inactiveColor
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = animationDuration)
    )

    Canvas(modifier = modifier.height(arrowHeadSize * 2)) { // Ensure enough height for arrowhead
        val yCenter = size.height / 2
        val arrowHeadPx = arrowHeadSize.toPx()
        val strokeWidthPx = strokeWidth.toPx()

        // Line
        val lineEndX = size.width - arrowHeadPx // Stop line before arrowhead starts
        if (lineEndX > 0f) { // Draw line only if there's space
            drawLine(
                color = animatedColor,
                start = Offset(0f, yCenter),
                end = Offset(lineEndX, yCenter),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round // Optional: Rounded ends for the line
            )
        }


        // Arrowhead (Triangle)
        val path = Path().apply {
            moveTo(lineEndX, yCenter - arrowHeadPx / 2) // Top point
            lineTo(size.width, yCenter)                 // Tip
            lineTo(lineEndX, yCenter + arrowHeadPx / 2) // Bottom point
            // Optional: Close the path if you want a filled arrowhead,
            // but for just lines, this is fine. For filled: close()
        }
        // Use drawPath for filled, or drawLine segments for outline
        drawLine(animatedColor, Offset(lineEndX, yCenter - arrowHeadPx / 2), Offset(size.width, yCenter), strokeWidth = strokeWidthPx)
        drawLine(animatedColor, Offset(lineEndX, yCenter + arrowHeadPx / 2), Offset(size.width, yCenter), strokeWidth = strokeWidthPx)

        // Alternative: Filled Arrowhead
        // drawPath(path = path, color = animatedColor)
    }
}


// --- Main Animation Composable ---

@Composable
fun SequentialCircleArrowAnimation(
    numberOfSteps: Int = 4, // Total number of circles
    stepDurationMillis: Long = 1000L,
    initialDelayMillis: Long = 500L,
    circleSize: Dp = 40.dp,
    arrowWidth: Dp = 50.dp,
    spacing: Dp = 10.dp,
    inactiveCircleColor: Color = Color.Black,
    activeCircleColor: Color = MaterialTheme.colorScheme.primary, // Use theme color
    inactiveArrowColor: Color = Color.LightGray,
    activeArrowColor: Color = Color(0xFF4CAF50) // Greenish
) {
    // State to track the current completed step. -1 means nothing active yet.
    var currentStep by remember { mutableStateOf(-1) }

    // Trigger the animation sequence when the composable enters the composition
    LaunchedEffect(key1 = numberOfSteps) { // Re-launch if number of steps changes
        delay(initialDelayMillis) // Wait a bit before starting
        for (step in 0 until numberOfSteps) {
            currentStep = step
            delay(stepDurationMillis)
        }
        // Optional: Reset after a while
        // delay(stepDurationMillis * 2)
        // currentStep = -1
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        // horizontalArrangement = Arrangement.spacedBy(spacing) // Canvas doesn't space well this way
        modifier = Modifier.padding(16.dp)
    ) {
        for (i in 0 until numberOfSteps) {
            // --- Circle ---
            AnimatedCircle(
                isActive = i <= currentStep, // Circle is active if it's the current step or earlier
                inactiveColor = inactiveCircleColor,
                activeColor = activeCircleColor,
                size = circleSize
            )

            // --- Spacer and Arrow (if not the last circle) ---
            if (i < numberOfSteps - 1) {
                Spacer(modifier = Modifier.width(spacing))
                AnimatedArrow(
                    modifier = Modifier.width(arrowWidth),
                    isActive = i < currentStep, // Arrow is active if the *next* step has started
                    inactiveColor = inactiveArrowColor,
                    activeColor = activeArrowColor,
                    strokeWidth = 2.dp,
                    arrowHeadSize = 6.dp
                )
                Spacer(modifier = Modifier.width(spacing))
            }
        }
    }
}

// --- Preview ---

@Preview(showBackground = true)
@Composable
fun PreviewSequentialCircleArrowAnimation() {
    MaterialTheme { // Recommended to wrap in MaterialTheme for previews
        SequentialCircleArrowAnimation(
            numberOfSteps = 4,
            stepDurationMillis = 1200L
        )
    }
}