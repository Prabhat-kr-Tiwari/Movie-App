package com.prabhat.movieapp.domain.di

import androidx.datastore.core.DataStore
import com.prabhat.movieapp.data.appSettings.AppSettings
import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceDao
import com.prabhat.movieapp.data.local.watchList.WatchlistDao
import com.prabhat.movieapp.data.network.MovieApiService
import com.prabhat.movieapp.data.repository.AuthenticationRepositoryImpl
import com.prabhat.movieapp.data.repository.movie.MovieRepositoryImpl
import com.prabhat.movieapp.data.repository.userPreference.UserPreferenceRepositoryImpl
import com.prabhat.movieapp.data.repository.watchList.WatchlistRepositoryImpl
import com.prabhat.movieapp.domain.repository.AuthenticationRepository
import com.prabhat.movieapp.domain.repository.WatchlistRepository
import com.prabhat.movieapp.domain.repository.movie.MovieRepository
import com.prabhat.movieapp.domain.repository.userPreference.UserPreferenceRepository
import com.prabhat.movieapp.mappers.popularSeries.PopularSeriesDTOToPopularSeriesMapper
import com.prabhat.movieapp.mappers.trending.TrendingOfWeekResponseDtoToTrendingOfWeekMapper
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
    fun provideAuthenticationRepo(
        movieApiService: MovieApiService,
        dataStore: DataStore<AppSettings>
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            movieApiService = movieApiService,
            dataStore = dataStore
        )
    }


    @Provides
    @Singleton
    fun provideMovieRepo(
        movieApiService: com.prabhat.movieapp.data.network.movie.MovieApiService,
        popularSeriesMapper: PopularSeriesDTOToPopularSeriesMapper,
        trendingOfWeekMapper: TrendingOfWeekResponseDtoToTrendingOfWeekMapper
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieApiService = movieApiService,
            mapper = popularSeriesMapper,
            trendingOfWeekMapper = trendingOfWeekMapper
        )
    }

    @Provides
    @Singleton
    fun provideWatchListRepo(watchlistDao: WatchlistDao): WatchlistRepository {
        return WatchlistRepositoryImpl(dao = watchlistDao)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceRepo(userPreferenceDao: UserPreferenceDao): UserPreferenceRepository {
        return UserPreferenceRepositoryImpl(userPreferenceDao = userPreferenceDao)
    }
}