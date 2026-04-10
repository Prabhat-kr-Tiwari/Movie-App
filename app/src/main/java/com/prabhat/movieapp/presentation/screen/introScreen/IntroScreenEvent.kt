package com.prabhat.movieapp.presentation.screen.introScreen

sealed class IntroScreenEvent {
    data object onBoardingFinished: IntroScreenEvent()
}