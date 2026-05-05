package com.prabhat.movieapp.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CustomContainedLoadingIndicator() {

    val indicatorColor = if (isSystemInDarkTheme()) Color.Red.copy(alpha = 0.8F) else Color.Red
    val containerColor =
        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.errorContainer
    ContainedLoadingIndicator(
        modifier =  Modifier

            .padding(16.dp),
        containerColor = containerColor,
        indicatorColor = indicatorColor
    )
}