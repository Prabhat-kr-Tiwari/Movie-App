package com.prabhat.movieapp.presentation.screen.more

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prabhat.movieapp.navigation.BottomNavigationDestination
import com.prabhat.movieapp.navigation.MoreDestination

fun NavGraphBuilder.moreScreen(
    onOpenAccount: () -> Unit,
    onOpenSettings: () -> Unit,
    innerPaddingValues: PaddingValues
) {
    composable<BottomNavigationDestination.MoreScreen> {
        MoreScreen(onOpenSettings = onOpenSettings, onOpenAccount = onOpenAccount, innerPaddingValues = innerPaddingValues)
    }
}
fun NavController.navigateToMoreScreen() {
    navigate(BottomNavigationDestination.MoreScreen) {

    }
}