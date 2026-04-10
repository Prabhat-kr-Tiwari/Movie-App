package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.verifyPaymentScreen

import com.prabhat.movieapp.data.appSettings.SessionId

data class VerifyPaymentScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPlansAndPaymentDone: Boolean = false
)
