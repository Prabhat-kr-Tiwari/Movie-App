package com.prabhat.movieapp.domain.repository

import com.prabhat.movieapp.data.model.CreateSessionWithLoginRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionWithLoginResponseDTO

interface CreateSessionWithLoginRepository {

    suspend fun createSessionWithLogin(createSessionWithLoginRequestToken: CreateSessionWithLoginRequestDTO): CreateSessionWithLoginResponseDTO
}