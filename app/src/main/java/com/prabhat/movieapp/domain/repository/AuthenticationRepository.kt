package com.prabhat.movieapp.domain.repository

import com.prabhat.movieapp.data.appSettings.SessionId
import com.prabhat.movieapp.data.model.CreateSessionRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionResponseDTO
import com.prabhat.movieapp.data.model.CreateSessionWithLoginRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionWithLoginResponseDTO
import com.prabhat.movieapp.data.model.RequestTokenResponseDTO
import kotlinx.collections.immutable.PersistentList

interface AuthenticationRepository {

    suspend fun createSession(createSessionRequestDTO: CreateSessionRequestDTO): CreateSessionResponseDTO
    suspend fun createSessionWithLogin(createSessionWithLoginRequestToken: CreateSessionWithLoginRequestDTO): CreateSessionWithLoginResponseDTO
    suspend fun getRequestToken(): RequestTokenResponseDTO
/*    suspend fun saveSessionId(sessionId: SessionId)
    suspend fun getSessionId():PersistentList<SessionId>
    suspend fun clearSessionId():PersistentList<SessionId>*/
suspend fun saveSessionId(sessionId: SessionId)
    suspend fun getSessionId():SessionId
    suspend fun clearSessionId():SessionId

}