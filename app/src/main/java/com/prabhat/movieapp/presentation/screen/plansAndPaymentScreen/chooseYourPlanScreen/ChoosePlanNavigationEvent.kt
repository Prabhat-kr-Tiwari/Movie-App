package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

sealed class ChoosePlanNavigationEvent {
    object NavigateNext : ChoosePlanNavigationEvent()
}