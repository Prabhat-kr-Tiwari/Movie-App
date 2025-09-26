package com.prabhat.movieapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MobileStorageBar(
    usedFraction: Float,
    marvelFraction: Float,
    freeFraction: Float
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Mobile Storage", // corrected spelling
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(usedFraction)
                    .background(Color(0xFF007AFF)) // iOS Blue
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(marvelFraction)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(freeFraction)
                    .background(if (isSystemInDarkTheme()){Color.White}else{Color.Gray})
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LegendItem(color = Color(0xFF007AFF), label = "Used")
            LegendItem(color = Color.Red, label = "Marvel")
            LegendItem(color = if (isSystemInDarkTheme()){Color.White}else{Color.Gray}, label = "Free")
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = color, shape = RectangleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}
@Preview(showBackground = true)
@Composable
fun MobileStorageBarPreview() {
    Box(modifier = Modifier.background(Color.Black).clip(RoundedCornerShape(10.dp))) {
        MobileStorageBar(
            usedFraction = 0.5f,
            marvelFraction = 0.2f,
            freeFraction = 0.3f
        )
    }
}
