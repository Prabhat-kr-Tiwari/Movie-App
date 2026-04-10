package com.prabhat.movieapp.presentation.screen.loginScreen

sealed class LoginScreenEvent {
    data object onBoardingFinished: LoginScreenEvent()
}