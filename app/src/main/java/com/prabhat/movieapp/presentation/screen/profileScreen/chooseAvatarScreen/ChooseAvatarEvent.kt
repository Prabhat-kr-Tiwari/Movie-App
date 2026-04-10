package com.prabhat.movieapp.presentation.screen.profileScreen.chooseAvatarScreen

import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen.ChoosePlanEvent

sealed class ChooseAvatarEvent {
    data class AvatarSelected(val avatar: String): ChooseAvatarEvent()
    object  ContinueClicked: ChooseAvatarEvent()

}