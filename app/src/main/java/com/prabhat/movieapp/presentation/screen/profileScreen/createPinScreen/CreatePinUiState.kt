package com.prabhat.movieapp.presentation.screen.profileScreen.createPinScreen

data class CreatePinUiState(
    val pin: String = "",
    val isPinValid: Boolean = true,
    val pinError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null,

    val isPinSaved: Boolean = false,
    val avatarId: String? = null
)
