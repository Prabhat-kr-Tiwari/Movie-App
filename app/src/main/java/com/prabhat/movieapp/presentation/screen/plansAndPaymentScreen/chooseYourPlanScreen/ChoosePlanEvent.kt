package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

sealed class ChoosePlanEvent {
    data class PlanSelected(val plan:String): ChoosePlanEvent()
    object  ContinueClicked: ChoosePlanEvent()
}