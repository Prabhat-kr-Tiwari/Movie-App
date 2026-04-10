package com.prabhat.movieapp.presentation.screen.splashScreen

import com.prabhat.movieapp.navigation.SubGraph

data class SplashScreenUiState(
    val isReady:Boolean=false,
    val isOnboardingDone:Boolean=false,
    val isPlansAndPaymentDone:Boolean=false,
    val isProfileSetupDone:Boolean=false,
    val isLogin:Boolean=false,
    val error: String?=null,
    val isLoading:Boolean=false,
    val startDestination: SubGraph=SubGraph.onBoarding
)
