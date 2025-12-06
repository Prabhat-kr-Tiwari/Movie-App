package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.prabhat.movieapp.presentation.components.NumberedStepIndicator

private const val CONTENT_ANIMATION_DURATION = 500

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PaymentFlowWrapper(
    currentStep: Int,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color,
    content: @Composable () -> Unit
) {
    val backstackEntry by navHostController.currentBackStackEntryAsState()
    val route = backstackEntry?.destination?.route



    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)

            .padding(horizontal = 5.dp, vertical = 10.dp)
    ) {
        NumberedStepIndicator(
            totalSteps = 5,
            currentStep = currentStep
        )


        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)
                val direction = if (targetState > initialState) {
                    AnimatedContentTransitionScope.SlideDirection.Left

                } else {
                    AnimatedContentTransitionScope.SlideDirection.Right

                }
                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec
                ) togetherWith slideOutOfContainer(
                    towards = direction,
                    animationSpec = animationSpec
                )

            },
            label = "payment_step_transition"
        ) { step ->
            Log.d("prabhat", "PaymentFlowWrapper: $step")
            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    }
}
