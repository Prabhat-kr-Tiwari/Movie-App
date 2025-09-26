package com.prabhat.movieapp.presentation.screen.more.settingsScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prabhat.movieapp.navigation.MoreDestination

fun NavGraphBuilder.settingScreenRoot(
    onNavigateUp: () -> Unit,innerPaddingValues: PaddingValues
) {
    composable<MoreDestination.SettingScreen> {
        SettingsScreen(
            onNavigateUp = onNavigateUp, innerPaddingValues = innerPaddingValues
        )
    }
}

fun NavController.navigateToSettingScreen() {
    navigate(MoreDestination.SettingScreen) {
        popUpTo<MoreDestination.SettingScreen> {
            inclusive = true
        }
    }
}