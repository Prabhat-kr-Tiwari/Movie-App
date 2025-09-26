package com.prabhat.movieapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// --- Circle Composable (Slightly modified for clarity) ---
@Composable
fun ActivatingCircle(
    modifier: Modifier = Modifier,
    isActivated: Boolean,
    inactiveColor: Color = Color.DarkGray, // Changed inactive color
    activeColor: Color = Color.Blue,
    size: Dp = 40.dp,
    animationDuration: Int = 300
) {
    val targetColor = if (isActivated) activeColor else inactiveColor
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = animationDuration)
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(animatedColor)
    )
}

// --- Arrow Drawing Logic (extracted for reuse) ---
fun DrawScope.drawArrow(
    color: Color,
    strokeWidthPx: Float,
    arrowHeadSizePx: Float
) {
    val yCenter = size.height / 2

    // Line
    val lineStartX = 0f
    val lineEndX = size.width - arrowHeadSizePx // Stop line before arrowhead starts
    if (lineEndX > lineStartX) {
        drawLine(
            color = color,
            start = Offset(lineStartX, yCenter),
            end = Offset(lineEndX, yCenter),
            strokeWidth = strokeWidthPx,
            cap = StrokeCap.Round
        )
    }

    // Arrowhead (Triangle Outline)
    val tipX = size.width
    if (arrowHeadSizePx > 0) {
        val path = Path().apply {
            moveTo(lineEndX, yCenter - arrowHeadSizePx / 2) // Top base
            lineTo(tipX, yCenter)                           // Tip
            lineTo(lineEndX, yCenter + arrowHeadSizePx / 2) // Bottom base
            // Draw lines instead of filling
            // moveTo(lineEndX, yCenter - arrowHeadSizePx / 2) // Redundant if drawing lines
        }
        // Draw the two lines of the arrowhead
        drawLine(color, Offset(lineEndX, yCenter - arrowHeadSizePx / 2), Offset(tipX, yCenter), strokeWidth = strokeWidthPx, cap = StrokeCap.Round)
        drawLine(color, Offset(lineEndX, yCenter + arrowHeadSizePx / 2), Offset(tipX, yCenter), strokeWidth = strokeWidthPx, cap = StrokeCap.Round)
        // Alternatively: drawPath(path, color, style = Stroke(width = strokeWidthPx))
    }
}

// --- The Moving Arrow Composable ---
@Composable
fun ArrowComposable(
    modifier: Modifier = Modifier,
    color: Color = Color.Green,
    strokeWidth: Dp = 2.dp,
    arrowHeadSize: Dp = 8.dp // Slightly bigger head
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
    val arrowHeadSizePx = with(LocalDensity.current) { arrowHeadSize.toPx() }

    Canvas(modifier = modifier.height(arrowHeadSize * 2)) { // Ensure enough height
        drawArrow(
            color = color,
            strokeWidthPx = strokeWidthPx,
            arrowHeadSizePx = arrowHeadSizePx
        )
    }
}


// --- Main Animation Composable ---
@Composable
fun MovingArrowActivationAnimation(
    modifier: Modifier = Modifier,
    numberOfCircles: Int = 4,
    circleSize: Dp = 40.dp,
    spacing: Dp = 50.dp, // Spacing between circle centers
    arrowWidth: Dp = 30.dp, // Visual width of the arrow graphic
    arrowColor: Color = Color(0xFF4CAF50), // Greenish
    inactiveCircleColor: Color = Color.DarkGray,
    activeCircleColor: Color = MaterialTheme.colorScheme.primary,
    travelDurationPerSegmentMillis: Int = 800,
    initialDelayMillis: Long = 500L,
    restartDelayMillis: Long = 1500L // Delay before animation loops
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    // Convert Dp to Px
    val circleSizePx = remember(density, circleSize) { with(density) { circleSize.toPx() } }
    val spacingPx = remember(density, spacing) { with(density) { spacing.toPx() } }
    val arrowWidthPx = remember(density, arrowWidth) { with(density) { arrowWidth.toPx() } }

    // State for the arrow's leading tip X position (in Pixels)
    val arrowTipX = remember { Animatable(0f) }

    // State to hold the calculated center X positions of the circles (in Pixels)
    var circleCenterPositions by remember { mutableStateOf<List<Float>>(emptyList()) }
    var layoutWidth by remember { mutableStateOf(0) }
    var totalArrangementWidth by remember { mutableStateOf(0f)}

    // Calculate positions once the layout size is known
    val calculatePositions = { availableWidth: Int ->
        if (availableWidth <= 0 || numberOfCircles <= 0) {
            circleCenterPositions = emptyList()
            totalArrangementWidth = 0f
            0f // Return start offset
        } else {
            val requiredWidth = (numberOfCircles - 1) * spacingPx + circleSizePx
            totalArrangementWidth = requiredWidth
            val startPadding = (availableWidth - requiredWidth).coerceAtLeast(0f) / 2f
            val positions = List(numberOfCircles) { i ->
                startPadding + (i * spacingPx) + (circleSizePx / 2f)
            }
            circleCenterPositions = positions
            startPadding // Return the calculated start offset
        }
    }

    // Animatable needs a stable target, calculate initial position
    LaunchedEffect(layoutWidth, numberOfCircles) {
        val startPadding = calculatePositions(layoutWidth)
        // Set initial position *before* the first circle's visible area
        // Position arrow tip slightly before the visual start of the circles
        val initialX = startPadding + circleSizePx / 2f - spacingPx / 2f // Example starting point
        arrowTipX.snapTo(initialX.coerceAtLeast(0f)) // Use snapTo to set without animation
    }

    // The Animation Driver
    LaunchedEffect(key1 = numberOfCircles, key2 = circleCenterPositions) {
        if (circleCenterPositions.isEmpty()) return@LaunchedEffect // Wait for positions

        // Initial starting position (ensure it's set before animation begins)
        val initialX = circleCenterPositions.first() - spacingPx / 2f
        arrowTipX.snapTo(initialX.coerceAtLeast(0f))

        // Loop the animation
        while (true) {
            delay(initialDelayMillis)

            // Animate through each segment
            for (i in 0 until numberOfCircles) {
                val targetX = circleCenterPositions[i]
                scope.launch { // Launch animation, don't block the loop
                    arrowTipX.animateTo(
                        targetValue = targetX,
                        animationSpec = tween(
                            durationMillis = travelDurationPerSegmentMillis,
                            easing = LinearEasing // Constant speed
                        )
                    )
                }
                // Small delay allows the circle color animation to start smoothly
                // Adjust if needed, or remove if segment duration is long enough
                delay( (travelDurationPerSegmentMillis * 0.1f).toLong().coerceAtMost(100L) )

                // If not the last circle, wait for the travel time before next target
                if (i < numberOfCircles - 1) {
                    delay(travelDurationPerSegmentMillis - 100L) // Wait remainder of segment time
                } else {
                    delay(travelDurationPerSegmentMillis / 2L) // Wait a bit after reaching the last one
                }

            }

            // Wait before restarting
            delay(restartDelayMillis)

            // Reset arrow position smoothly or snap
            // arrowTipX.snapTo(initialX.coerceAtLeast(0f)) // Instant reset
            arrowTipX.animateTo(initialX.coerceAtLeast(0f), tween(300)) // Smooth reset
            delay(300) // Wait for reset animation
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth() // Take available width
            .onSizeChanged { layoutWidth = it.width }
            .height(IntrinsicSize.Min) // Height based on content (primarily circle size)
            .padding(vertical = 16.dp) // Add some vertical padding
    ) {
        if (circleCenterPositions.isNotEmpty()) {
            val startPaddingPx = circleCenterPositions.first() - circleSizePx / 2f

            // Draw Circles at fixed positions
            circleCenterPositions.forEachIndexed { index, centerX ->
                // Determine if the circle should be active
                // Activate slightly before the arrow tip *exactly* reaches the center for better visual feel
                val activationThreshold = centerX - (arrowWidthPx * 0.1f) // Example threshold
                val isActivated = arrowTipX.value >= activationThreshold

                ActivatingCircle(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = (centerX - circleSizePx / 2f).roundToInt(),
                                y = 0 // Align circles vertically at the top of the Box content area
                            )
                        },
                    isActivated = isActivated,
                    inactiveColor = inactiveCircleColor,
                    activeColor = activeCircleColor,
                    size = circleSize
                )
            }

            // Draw the Moving Arrow
            // Offset the arrow so its *tip* aligns with arrowTipX.value
            ArrowComposable(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (arrowTipX.value - arrowWidthPx).roundToInt(), // Position based on tip
                            y = (circleSizePx / 2f).roundToInt() // Center arrow vertically with circles
                        )
                    }
                    .width(arrowWidth), // Control the visual size of the arrow graphic
                color = arrowColor
            )
        }
    }
}


// --- Preview ---
@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewMovingArrowActivationAnimation() {
    MaterialTheme {
        MovingArrowActivationAnimation(
            numberOfCircles = 5,
            travelDurationPerSegmentMillis = 1000,
            spacing = 65.dp,
            circleSize = 35.dp
        )
    }
}