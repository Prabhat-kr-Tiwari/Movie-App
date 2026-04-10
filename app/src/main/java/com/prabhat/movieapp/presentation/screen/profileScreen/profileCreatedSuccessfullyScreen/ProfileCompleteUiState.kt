package com.prabhat.movieapp.presentation.screen.profileScreen.profileCreatedSuccessfullyScreen

data class ProfileCompleteUiState(
    val userName:String?=null,
    val avatarId:String?=null,
    val isLoading: Boolean=false,
    val errorMessage:String?=null,
)