package com.prabhat.movieapp.presentation.screen.profileScreen.enterUserNameScreen

sealed class EnterUserNameNavigationEvent {

    object navigateNext: EnterUserNameNavigationEvent()
    object navigateBack: EnterUserNameNavigationEvent()
}