package com.prabhat.movieapp.presentation.screen.profileScreen.enterUserNameScreen

data class EnterUserNameUiState(
    val avatarId : String?=null,
    val errorMessage:String?=null,
    val isLoading:Boolean=false,
    val isUserNameAvailable:Boolean=false
)
