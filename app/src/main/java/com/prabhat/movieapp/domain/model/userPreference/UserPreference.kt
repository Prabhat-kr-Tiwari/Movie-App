package com.prabhat.movieapp.domain.model.userPreference

data class UserPreference(
    val plans:String="",
    val paymentMode:String="",
    val avatar:String="",
    val userName:String="",
    val password:String="",
    val pin: String=""
)