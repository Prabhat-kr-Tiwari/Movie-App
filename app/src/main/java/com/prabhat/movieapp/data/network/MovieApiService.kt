package com.prabhat.movieapp.data.network

import com.prabhat.movieapp.data.model.CreateSessionRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionResponseDTO
import com.prabhat.movieapp.data.model.CreateSessionWithLoginRequestDTO
import com.prabhat.movieapp.data.model.CreateSessionWithLoginResponseDTO
import com.prabhat.movieapp.data.model.RequestTokenResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieApiService {


    @GET("authentication/token/new")
    suspend fun getRequestToken(@Query("api_key") apiKey: String):RequestTokenResponseDTO

    @POST("authentication/token/validate_with_login")
    suspend fun createSessionWithLogin(
        @Query("api_key") apiKey: String,
        @Body createSessionWithLogin: CreateSessionWithLoginRequestDTO
    ):CreateSessionWithLoginResponseDTO

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String,
        @Body createSessionDTO: CreateSessionRequestDTO
    ):CreateSessionResponseDTO
}