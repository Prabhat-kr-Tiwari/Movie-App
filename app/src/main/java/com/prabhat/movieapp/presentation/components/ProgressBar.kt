package com.prabhat.movieapp.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec // Import AnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prabhat.movieapp.presentation.screen.downloads.ThemeAnnotation
import com.prabhat.movieapp.ui.theme.GreyLight
import kotlin.math.abs // Import abs for float comparison

// --- Helper functions remain the same ---
fun increaseProgress(currentValue: Int, onValueChange: (Int) -> Unit, context: Context) {
    if (currentValue < 10) {
        val newValue = currentValue + 1
        onValueChange(newValue)
    } else {
        Toast.makeText(context, "Cannot increase further", Toast.LENGTH_SHORT).show()
    }
}
fun decreaseProgress(currentValue: Int, onValueChange: (Int) -> Unit, context: Context) {
    if (currentValue > 0) {
        val newValue = currentValue - 1
        onValueChange(newValue)
    } else {
        Toast.makeText(context, "Cannot decrease further", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun CustomLinearProgressBar(
    modifier: Modifier = Modifier,
    progressCount: Int,
    backgroundColor:Color,
    foreGroundColor:Color,
    showProgressPercentage: Boolean=false,
    // Callback to report animation status
    onAnimationRunning: (Boolean) -> Unit
) {
    val targetFloatProgress = when (progressCount.coerceIn(0, 10)) {
        // ... (cases remain the same) ...
        0 -> 0.0f
        1 -> 0.1f
        2 -> 0.2f
        3 -> 0.3f
        4 -> 0.4f
        5 -> 0.5f
        6 -> 0.6f
        7 -> 0.7f
        8 -> 0.8f
        9 -> 0.9f
        10 -> 1.0f
        else -> 0.0f
    }

    // Define the animation spec separately
    val animationSpec: AnimationSpec<Float> = tween(
        durationMillis = 1000,
        delayMillis = 200, // You might remove delay if you want immediate start
        easing = LinearOutSlowInEasing
    )

    val animatedProgress by animateFloatAsState(
        targetValue = targetFloatProgress,
        animationSpec = animationSpec,
        label = "ProgressBarAnimation"
        // 'finishedListener' is another option, but checking values is often simpler here
    )

    // Determine if the animation is currently running
    // Use a small tolerance (epsilon) for float comparison
    val epsilon = 0.001f
    val isRunning = abs(animatedProgress - targetFloatProgress) > epsilon

    // Use LaunchedEffect to call the callback only when isRunning CHANGES
    LaunchedEffect(isRunning) {
        onAnimationRunning(isRunning)
    }

    // --- UI remains the same ---
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(animatedProgress) // Still use animatedProgress for visuals
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (showProgressPercentage){

                Text(
                    text = "${(targetFloatProgress * 100).toInt()}%", // Show target percentage
                    fontSize = 12.sp
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        ) {
            Box( // Background
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(9.dp))
                    .background(backgroundColor) // Use your theme colors
            )
            Box( // Foreground
                modifier = Modifier
                    .fillMaxWidth(animatedProgress) // Still use animatedProgress for visuals
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(9.dp))
                    .background(foreGroundColor) // Use your theme colors
                    .animateContentSize()
            )
        }
    }
}


@ThemeAnnotation
@Composable
fun Control(modifier: Modifier = Modifier) {
    var progressCount by remember { mutableIntStateOf(0) }
    // State to track if the animation is running
    var isAnimating by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(start = 30.dp, end = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomLinearProgressBar(
            modifier = Modifier.padding(top = 100.dp),
            progressCount = progressCount,
            backgroundColor = GreyLight,
            foreGroundColor=Red,
            showProgressPercentage = true,
            // Update the isAnimating state based on the callback
            onAnimationRunning = { running ->
                isAnimating = running
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Decrease button - enabled only when not animating
            OutlinedButton(
                onClick = {
                    decreaseProgress(
                        currentValue = progressCount,
                        onValueChange = { newValue -> progressCount = newValue },
                        context = context
                    )
                },
                // Disable button if animation is running
                enabled = !isAnimating
            ) {
                Text(text = "Decrease")
            }

            // Increase Button - enabled only when not animating
            Button(
                onClick = {
                    increaseProgress(
                        currentValue = progressCount,
                        onValueChange = { newValue -> progressCount = newValue },
                        context = context
                    )
                },
                // Disable button if animation is running
                enabled = !isAnimating
            ) {
                Text(text = "Increase")
            }
        }
    }
}