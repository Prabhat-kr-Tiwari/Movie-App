package com.prabhat.movieapp.data.model

import com.prabhat.movieapp.domain.model.CreateSession

data class CreateSessionRequestDTO(
    val request_token: String?
)

fun CreateSessionRequestDTO.toDomain(): CreateSession {
    return CreateSession(requestToken = this.request_token ?: "")
}