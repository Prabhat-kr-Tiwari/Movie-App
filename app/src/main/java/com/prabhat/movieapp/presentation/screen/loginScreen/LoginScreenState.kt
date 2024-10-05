package com.prabhat.movieapp.presentation.screen.loginScreen

import com.prabhat.movieapp.data.model.CreateSessionResponseDTO

data class

LoginScreenState(
    val isLoading:Boolean=false,
    val username:String="",
    val password:String="",
    val errorMessage:String?=null,
    val isLoginSuccessful:CreateSessionResponseDTO?=null,
)
