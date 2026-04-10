package com.prabhat.movieapp.presentation.screen.profileScreen.profileCreatedSuccessfullyScreen

sealed class ProfileCreatedEvent {
    object onProfileCreatedDone : ProfileCreatedEvent()
}