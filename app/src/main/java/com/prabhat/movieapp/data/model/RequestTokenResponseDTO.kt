package com.prabhat.movieapp.data.model

import com.prabhat.movieapp.domain.model.RequestToken

data class RequestTokenResponseDTO(
    val expires_at: String?,
    val request_token: String?,
    val success: Boolean?
)
fun RequestTokenResponseDTO.toDomainRequestToken():RequestToken {
    return RequestToken(requestToken = this.request_token?:"", success = this.success?:false)
}