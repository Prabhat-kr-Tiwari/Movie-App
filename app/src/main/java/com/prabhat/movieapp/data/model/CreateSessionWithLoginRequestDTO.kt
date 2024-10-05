package com.prabhat.movieapp.data.model

import com.prabhat.movieapp.domain.model.CreateSessionWithLogin

data class CreateSessionWithLoginRequestDTO(
    val password: String?,
    val request_token: String?,
    val username: String?
)

fun CreateSessionWithLoginRequestDTO.toDomain(): CreateSessionWithLogin {
    return CreateSessionWithLogin(
        password = this.password?:"",
        request_token = this.request_token?:"",
        username = this.password?:""
    )
}

