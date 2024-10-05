package com.prabhat.movieapp.domain.repository

import com.prabhat.movieapp.data.model.RequestTokenResponseDTO

interface RequestTokenRepository {


    suspend fun getRequestToken():RequestTokenResponseDTO


}