package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.choosePaymentModeScreen

sealed  class ChoosePaymentModeNavigationEvent {
    object NavigationNext: ChoosePaymentModeNavigationEvent()
    object NavigationBack: ChoosePaymentModeNavigationEvent()
}