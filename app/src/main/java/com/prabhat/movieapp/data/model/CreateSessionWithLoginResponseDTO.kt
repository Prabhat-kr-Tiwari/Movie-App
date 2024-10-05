package com.prabhat.movieapp.data.model

data class CreateSessionWithLoginResponseDTO(
    val expires_at: String?,
    val request_token: String?,
    val success: Boolean?
)