package com.prabhat.movieapp.presentation.screen.profileScreen.enterPasswordScreen

sealed class EnterPasswordNavigationEvent {

    object navigateNext: EnterPasswordNavigationEvent()
    object navigateBack: EnterPasswordNavigationEvent()
}