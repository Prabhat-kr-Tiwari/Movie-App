package com.prabhat.movieapp.presentation.screen.profileScreen.enterUserNameScreen

sealed class EnterUserNameEvent {

    object callMeThisClicked: EnterUserNameEvent()
    object changeClicked: EnterUserNameEvent()
}