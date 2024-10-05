package com.example.movieapp.utils

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.prabhat.movieapp.presentation.screen.signUpScreen.progressFlow
import com.prabhat.movieapp.ui.theme.darkGray
import com.prabhat.movieapp.ui.theme.gray
import com.prabhat.movieapp.ui.theme.orange
import com.prabhat.movieapp.ui.theme.white
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomLoadingDialog(
    lineHeight:Int,
    isClicked:Boolean,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    circleRadius: Float,
    showPercentage: Boolean,
    showInnerCircle: Boolean,
    showProgressCircle: Boolean,
    showArcOnProgressCircle: Boolean,
    modifier: Modifier = Modifier
) {


    Dialog(onDismissRequest = { /*TODO*/ }) {
        CircularProgressIndicator(
            lineHeight=lineHeight,
            isClicked=isClicked,
            initialValue = initialValue,

            primaryColor = primaryColor,
            secondaryColor = secondaryColor,
            showPercentage = showPercentage,
            showInnerCircle = showInnerCircle,
            showProgressCircle = showProgressCircle,
            showArcOnProgressCircle=showArcOnProgressCircle,
            circleRadius = circleRadius
        ) {

        }
    }
}


@Composable
fun CircularProgressIndicator(
    lineHeight: Int,
    isClicked: Boolean,
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    minValue: Int = 0,
    maxValue: Int = 100,
    circleRadius: Float,
    showPercentage: Boolean,
    showInnerCircle: Boolean,
    showProgressCircle: Boolean,
    showArcOnProgressCircle: Boolean,
    onPositionChange: (Int) -> Unit
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableStateOf(initialValue)
    }


    val scope= rememberCoroutineScope()
    val progressFLow= progressFlow()
    LaunchedEffect(isClicked) {
        scope.launch {
            progressFLow.collect{value->

                Log.d("TOM", "CircularProgressIndicator: $positionValue")
                Log.d("TOP", "CircularProgressIndicator: $value")
                positionValue=value

            }
        }
    }
//    positionValue=90


    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            circleCenter = Offset(x = width / 2f, y = height / 2f)


            //iinner cicrle
            if (showInnerCircle) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(
                            primaryColor.copy(0.45f),
                            secondaryColor.copy(0.15f)
                        )
                    ),
                    radius = circleRadius,
                    center = circleCenter
                )
            }


            //progress circle
            if(showProgressCircle){
                drawCircle(
                    style = Stroke(
                        width = circleThickness
                    ),
                    color = secondaryColor,
                    radius = circleRadius,
                    center = circleCenter
                )
            }


            if(showArcOnProgressCircle){
                drawArc(
                    color = primaryColor,
                    startAngle = 90f,
                    sweepAngle = (360f/maxValue) * positionValue.toFloat(),
                    style = Stroke(
                        width = circleThickness,
                        cap = StrokeCap.Round
                    ),
                    useCenter = false,
                    size = Size(
                        width = circleRadius * 2f,
                        height = circleRadius * 2f
                    ),
                    topLeft = Offset(
                        (width - circleRadius * 2f)/2f,
                        (height - circleRadius * 2f)/2f
                    )

                )
            }


            val outerRadius = circleRadius + circleThickness / 2f
            val gap = 65f
            for (i in 0..(maxValue - minValue)) {
                val color =
                    if (i < positionValue - minValue) primaryColor else primaryColor.copy(alpha = 0.3f)
                val angleInDegrees = i * 360f / (maxValue - minValue).toFloat()
                val angleInRad = angleInDegrees * PI / 180f + PI / 2f

                val yGapAdjustment = cos(angleInDegrees * PI / 180f) * gap
                val xGapAdjustment = -sin(angleInDegrees * PI / 180f) * gap

                val start = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleCenter.y + yGapAdjustment).toFloat()
                )

                val end = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleThickness + circleCenter.y + yGapAdjustment+ lineHeight.dp.toPx()).toFloat()
                )

                rotate(
                    angleInDegrees,
                    pivot = start
                ) {
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = 1.dp.toPx()
                    )
                }

            }

            //percentage value
            if (showPercentage) {
                drawContext.canvas.nativeCanvas.apply {
                    drawIntoCanvas {
                        drawText(
                            "$positionValue %",
                            circleCenter.x,
                            circleCenter.y + 45.dp.toPx() / 3f,
                            Paint().apply {
                                textSize = 38.sp.toPx()
                                textAlign = Paint.Align.CENTER
                                color = white.toArgb()
                                isFakeBoldText = true
                            }
                        )
                    }
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview1() {
    CustomCircularProgressIndicator(
        modifier = Modifier
            .size(250.dp)
            .background(darkGray),
        initialValue = 50,
        primaryColor = orange,
        secondaryColor = gray,
        circleRadius = 230f,
        onPositionChange = {

        }
    )
}








