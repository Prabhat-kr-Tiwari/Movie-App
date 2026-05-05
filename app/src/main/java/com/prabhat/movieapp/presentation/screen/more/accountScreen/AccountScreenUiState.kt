package com.prabhat.movieapp.presentation.screen.more.accountScreen

sealed interface AccountScreenUiState {
    data object Loading: AccountScreenUiState
    data object LoadFailed: AccountScreenUiState
    data object NotShown: AccountScreenUiState
    data class UserPreference(val name:String,val avatarId: String): AccountScreenUiState
}