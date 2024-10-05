package com.example.movieapp.utils


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MarkerLoadingDialog(initialValue: Int, primaryColor: Color,secondaryColor: Color,circleRadius: Float,modifier: Modifier = Modifier) {


    Dialog(onDismissRequest = { /*TODO*/ }) {
        MarkersOnly(markerColor = primaryColor, circleRadius =circleRadius )

    }
}
@Composable
fun MarkersOnly(
    modifier: Modifier = Modifier,
    markerColor: Color,
    circleRadius: Float,
    minValue: Int = 0,
    maxValue: Int = 100,
    markerSize: Float = 4f, // Size of each marker
) {
    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val circleCenter = Offset(x = width / 2f, y = height / 2f)
            val outerRadius = circleRadius

            for (i in minValue..maxValue) {
                val color = markerColor
                val angleInDegrees = i * 360f / (maxValue - minValue).toFloat()
                val angleInRad = angleInDegrees * PI / 180f + PI / 2f

                // Calculate marker position
                val markerOffset = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleCenter.y).toFloat()
                )

                drawCircle(
                    color = color,
                    radius = markerSize.dp.toPx(),
                    center = markerOffset
                )
            }
        }
    }
}
