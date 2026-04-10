package com.prabhat.movieapp.presentation.screen.profileScreen.chooseAvatarScreen

data class ChooseAvatarUiState(
    val selectedAvatar: String? =null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
