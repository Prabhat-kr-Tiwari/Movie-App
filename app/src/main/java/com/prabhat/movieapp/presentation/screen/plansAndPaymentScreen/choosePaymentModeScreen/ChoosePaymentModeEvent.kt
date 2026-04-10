package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.choosePaymentModeScreen

sealed class ChoosePaymentModeEvent {
    data class ModeSelected(val paymentMode:String): ChoosePaymentModeEvent()
    data object ContinueClicked: ChoosePaymentModeEvent()
    data object ChangeClicked: ChoosePaymentModeEvent()
}