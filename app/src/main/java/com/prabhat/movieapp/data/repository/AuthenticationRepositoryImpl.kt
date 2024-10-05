package com.prabhat.movieapp.data.repository

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.prabhat.movieapp.data.appSettings.AppSettings
import com.prabhat.movieapp.data.appSettings.SessionId
import com.prabhat.movieapp.data.model.CreateSessionRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionResponseDTO
import com.prabhat.movieapp.data.model.CreateSessionWithLoginRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionWithLoginResponseDTO
import com.prabhat.movieapp.data.model.RequestTokenResponseDTO
import com.prabhat.movieapp.data.network.MovieApiService
import com.prabhat.movieapp.domain.repository.AuthenticationRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

class AuthenticationRepositoryImpl @Inject
constructor(private val movieApiService: MovieApiService,
//            private val dataStore: DataStore<AppSettings>) :
            private val dataStore: DataStore<AppSettings>) :
    AuthenticationRepository {
    override suspend fun createSession(createSessionRequestDTO: CreateSessionRequestDTO): CreateSessionResponseDTO {
        return movieApiService.createSession(
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
            createSessionDTO = createSessionRequestDTO
        )

    }

    override suspend fun createSessionWithLogin(createSessionWithLoginRequestToken: CreateSessionWithLoginRequestDTO): CreateSessionWithLoginResponseDTO {

        return movieApiService.createSessionWithLogin(
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
            createSessionWithLogin = createSessionWithLoginRequestToken
        )
    }

    override suspend fun getRequestToken(): RequestTokenResponseDTO {
        return movieApiService.getRequestToken(apiKey = "b52b167cdd0627e0ecc0924ce311cf15")
    }

 /*   override suspend fun saveSessionId(sessionId: SessionId) {

        dataStore.updateData { it->
            it.copy(sessionId = persistentListOf(sessionId))
        }

    }

    override suspend fun getSessionId(): PersistentList<SessionId> {
        val appSettings=dataStore.data.first()
        return appSettings.sessionId
    }

    override suspend fun clearSessionId(): PersistentList<SessionId> {

        //first task
        dataStore.updateData { it->
            it.copy(sessionId = persistentListOf(SessionId("")))

        }
        //sescond task for coroutines
        val appSettings=dataStore.data.first()
        //first complete second complete then return
        return appSettings.sessionId
    }
*/
 override suspend fun saveSessionId(sessionId: SessionId) {

     dataStore.updateData { it->
         it.copy(persistentListOf( SessionId(sessionId = sessionId.sessionId)))
     }

 }

    override suspend fun getSessionId():SessionId {
        val appSettings=dataStore.data.first()
        return appSettings.sessionId.firstOrNull()?: SessionId("")
    }

    override suspend fun clearSessionId(): SessionId {

        //first task
        dataStore.updateData { it->
            it.copy(sessionId = persistentListOf( SessionId("")))

        }
        //sescond task for coroutines
        val appSettings=dataStore.data.first()
        //first complete second complete then return
        return appSettings.sessionId.firstOrNull()?:SessionId("")
    }


}