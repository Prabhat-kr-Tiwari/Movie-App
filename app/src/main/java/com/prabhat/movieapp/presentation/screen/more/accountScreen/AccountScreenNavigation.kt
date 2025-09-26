package com.prabhat.movieapp.presentation.screen.more.accountScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prabhat.movieapp.navigation.MoreDestination

fun NavGraphBuilder.accountScreenRoot(
    onNavigateUp: () -> Unit,
    innerPaddingValues: PaddingValues
) {
    composable<MoreDestination.AccountScreen> {
        AccountScreen(
            onNavigateUp = onNavigateUp, innerPaddingValues = innerPaddingValues
        )
    }
}

fun NavController.navigateToAccountScreen() {
    navigate(MoreDestination.AccountScreen) {
        popUpTo<MoreDestination.AccountScreen> {
            inclusive = true
        }
    }
}