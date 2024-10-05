package com.prabhat.movieapp.domain.repository

import com.prabhat.movieapp.data.model.CreateSessionRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionResponseDTO

interface CreateSessionRepository {

    suspend fun createSession(createSessionRequestDTO: CreateSessionRequestDTO): CreateSessionResponseDTO
}