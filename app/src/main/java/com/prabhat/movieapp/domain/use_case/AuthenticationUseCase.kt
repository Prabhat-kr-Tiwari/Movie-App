package com.prabhat.movieapp.domain.use_case

import android.util.Log
import com.prabhat.movieapp.data.appSettings.SessionId
import com.prabhat.movieapp.data.model.CreateSessionRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionResponseDTO
import com.prabhat.movieapp.data.model.CreateSessionWithLoginRequestDTO
import com.prabhat.movieapp.domain.repository.AuthenticationRepository
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {

    fun createAuthentication(
        username: String,
        password: String
    ): Flow<Resource<CreateSessionResponseDTO>> = flow {

        val getRequestToken = authenticationRepository.getRequestToken()
        Log.d("PRABHAT", "createAuthentication getRequestToken: $getRequestToken")
        if (getRequestToken.success == true) {
            val requestToken = getRequestToken.request_token
            Log.d("PRABHAT", "createAuthentication: $requestToken")

            val createSessionWithLogin = authenticationRepository.createSessionWithLogin(
                createSessionWithLoginRequestToken = CreateSessionWithLoginRequestDTO(
                    username = username,
                    password = password,
                    request_token = requestToken
                )
            )
            Log.d("PRABHAT", "createAuthentication createSessionWithLogin: $createSessionWithLogin")
            if (createSessionWithLogin.success == true) {
                val sessionWithLoginRequestToken = createSessionWithLogin.request_token
                Log.d("PRABHAT", "createAuthentication: $sessionWithLoginRequestToken")

                val createSession = authenticationRepository.createSession(
                    createSessionRequestDTO = CreateSessionRequestDTO(request_token = sessionWithLoginRequestToken)
                )

                Log.d("PRABHAT", "createAuthentication createSession: $createSession")
                if (createSession.success) {
                    authenticationRepository.saveSessionId(SessionId(createSession.session_id))
                    emit(Resource.Success(createSession))
                } else {
                    Log.d("PRABHAT", "createSession:${createSession.toString()} ")
                    emit(Resource.Error(createSession.toString()))
                }

            } else {
                emit(Resource.Error(createSessionWithLogin.toString()))
            }


        } else {
            emit(Resource.Error(getRequestToken.toString()))
        }


    }.onStart {
        emit(Resource.Loading())
    }.catch {
        emit(Resource.Error("eRROR"))
    }


    /*fun getSessionId(): Flow<Resource<SessionId>> = flow<Resource<SessionId>> {
        val sessionId = authenticationRepository.getSessionId()

        if (!sessionId.isEmpty()) {
            emit(Resource.Success(SessionId(sessionId = sessionId.toString())))
        } else {
            emit(Resource.Error("Error"))
        }

    }.onStart { emit(Resource.Loading()) }.catch { emit(Resource.Error("Error")) }

    fun clearSessionId():Flow<Resource<SessionId>> = flow {

        val sessionId=authenticationRepository.clearSessionId()
        if (sessionId.isEmpty()){
            emit(Resource.Success(SessionId(sessionId = sessionId.toString())))
        }else{
            emit(Resource.Error("Error"))
        }
    }.onStart { emit(Resource.Loading()) }.catch { emit(Resource.Error("Error")) }*/


    fun getSessionId(): Flow<Resource<SessionId>> = flow<Resource<SessionId>> {
        val sessionId = authenticationRepository.getSessionId()

        if (sessionId.sessionId.isNotEmpty()) {
            emit(Resource.Success(SessionId(sessionId = sessionId.toString())))
        } else {
            emit(Resource.Error("Error"))
        }

    }.onStart { emit(Resource.Loading()) }.catch { emit(Resource.Error("Error")) }

    fun clearSessionId():Flow<Resource<SessionId>> = flow {

        val sessionId=authenticationRepository.clearSessionId()
        if (sessionId.sessionId.isEmpty()){
            emit(Resource.Success(SessionId(sessionId = sessionId.toString())))
        }else{
            emit(Resource.Error("Error"))
        }
    }.onStart { emit(Resource.Loading()) }.catch { emit(Resource.Error("Error")) }
}