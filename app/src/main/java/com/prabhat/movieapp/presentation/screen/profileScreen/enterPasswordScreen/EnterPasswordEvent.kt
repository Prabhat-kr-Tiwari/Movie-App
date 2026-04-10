package com.prabhat.movieapp.presentation.screen.profileScreen.enterPasswordScreen

sealed class EnterPasswordEvent {

    object looksStrongClicked: EnterPasswordEvent()
    object changeClicked: EnterPasswordEvent()
}