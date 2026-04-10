package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

data class ChoosePlanUiState(
    val selectedPlan: PlanType? =null,
    val continueClicked : Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
