package com.prabhat.movieapp.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color

@Composable
fun Alignment(modifier: Modifier = Modifier) {


    Column(
        Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Button(onClick = {}) {
            Text("Buttone1")
        }

        Button(onClick = {}) {
            Text("Buttone2")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {}) {
            Text("Buttone2")
        }

    }


}

@Preview
@Composable
fun previewAlignement() {

    Alignment()
}