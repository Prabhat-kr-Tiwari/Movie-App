package com.prabhat.movieapp.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberedStepIndicator(
    totalSteps: Int,
    currentStep: Int
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        repeat(totalSteps) { index ->
            val stepNumber = index + 1
            val isActive = stepNumber == currentStep
            val isCompleted = stepNumber < currentStep

            val circleColor by animateColorAsState(
                targetValue = when {
                    isActive -> Color.Black
                    isCompleted -> Color.Red
                    else -> Color.LightGray
                },
                animationSpec = tween(300),
                label = ""
            )

            val circleSize by animateDpAsState(
                targetValue = if (isActive) 32.dp else 26.dp,
                animationSpec = tween(300),
                label = ""
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ----------------- CIRCLE -----------------
                Box(
                    modifier = Modifier
                        .size(circleSize)
                        .clip(CircleShape)
                        .background(circleColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stepNumber.toString(),
                        color = Color.White,
                        fontSize = if (isActive) 16.sp else 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // ----------------- TEXT UNDER CIRCLE -----------------
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stepNumber.toString(),
                    color = if (isActive) Color.Black else Color.Gray,
                    fontSize = 12.sp
                )
            }

            // ----------------- CONNECTING LINES -----------------
            if (index < totalSteps - 1) {
                val lineColor by animateColorAsState(
                    targetValue = if (isCompleted) Color.Red else Color.LightGray,
                    tween(600),
                    label = ""
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(3.dp)
                        .background(lineColor)
                )
            }
        }
    }
}
@Composable
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
fun NumberedStepIndicatorPreview() {
    NumberedStepIndicator(
        totalSteps = 5,
        currentStep = 3
    )
}

