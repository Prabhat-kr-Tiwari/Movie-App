package com.prabhat.movieapp.domain.model

data class CreateSessionWithLogin(
    val password: String,
    val request_token: String,
    val username: String
)