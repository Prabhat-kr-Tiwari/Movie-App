package com.prabhat.movieapp.domain.di

import androidx.datastore.core.DataStore
import com.prabhat.movieapp.data.appSettings.AppSettings
import com.prabhat.movieapp.data.appSettings.SessionId
import com.prabhat.movieapp.data.network.MovieApiService
import com.prabhat.movieapp.data.repository.AuthenticationRepositoryImpl
import com.prabhat.movieapp.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepo(movieApiService: MovieApiService,dataStore: DataStore<AppSettings>):AuthenticationRepository{
        return AuthenticationRepositoryImpl(movieApiService=movieApiService, dataStore = dataStore)
    }
}