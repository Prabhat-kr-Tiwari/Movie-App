package com.prabhat.movieapp.presentation.screen.more

sealed interface  MoreScreenUiState{

    data object Loading: MoreScreenUiState
    data object LoadFailed: MoreScreenUiState
    data object NotShown: MoreScreenUiState
    data class UserPreference(val name: String,val avatarId: String): MoreScreenUiState
}
