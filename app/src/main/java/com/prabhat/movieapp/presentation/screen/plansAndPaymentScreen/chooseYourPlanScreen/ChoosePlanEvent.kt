package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

sealed class ChoosePlanEvent {
    data class PlanSelected(val planType: PlanType): ChoosePlanEvent()
    object  ContinueClicked: ChoosePlanEvent()
}