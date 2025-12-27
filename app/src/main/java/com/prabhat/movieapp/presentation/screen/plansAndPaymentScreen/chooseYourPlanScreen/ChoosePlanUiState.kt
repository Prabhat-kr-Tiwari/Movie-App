package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

data class ChoosePlanUiState(
    val selectedPlan:String ="",
    val continueClicked : Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
