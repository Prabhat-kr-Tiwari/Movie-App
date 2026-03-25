package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.choosePaymentModeScreen

data class ChoosePaymentModeUiState(
    val selectedMode: String="",
    val continueClicked: Boolean = false,
    val isLoading:Boolean = false,
    val errorMessage:String? =""
)
