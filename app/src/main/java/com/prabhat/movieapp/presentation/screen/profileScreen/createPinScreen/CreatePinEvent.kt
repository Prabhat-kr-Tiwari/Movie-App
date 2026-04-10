package com.prabhat.movieapp.presentation.screen.profileScreen.createPinScreen

sealed class CreatePinEvent {
    object iamAllSafeClicked: CreatePinEvent()
    data class OnOtpChange(val otp: String) : CreatePinEvent()  // 👈 ADD THIS

}