package com.prabhat.movieapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.prabhat.movieapp.data.appSettings.AppSettings
import com.prabhat.movieapp.data.appSettings.AppSettingsSerializer
import com.prabhat.movieapp.data.local.upcomingMovie.MovieDatabase
import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceDao
import com.prabhat.movieapp.data.local.watchList.WatchlistDao
import com.prabhat.movieapp.data.network.MovieApiService
import com.prabhat.movieapp.data.network.movie.UpComingMovieRemoteMediator
import com.prabhat.movieapp.mappers.popularSeries.PopularSeriesDTOToPopularSeriesMapper
import com.prabhat.movieapp.mappers.trending.TrendingOfWeekResponseDtoToTrendingOfWeekMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


val Context.dataStore: DataStore<AppSettings> by dataStore(
    fileName = "app-settings.json",
    serializer = AppSettingsSerializer
)
@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }


    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context):DataStore<AppSettings> {
        return context.dataStore
    }
    @Provides
    @Singleton
    fun provideRetrofitForMovieService(retrofit: Retrofit): com.prabhat.movieapp.data.network.movie.MovieApiService {
        return retrofit.create(com.prabhat.movieapp.data.network.movie.MovieApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db")
            .fallbackToDestructiveMigration()
            .build()

    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideUpComingMoviePager(movieDatabase: MovieDatabase, movieApiService: com.prabhat.movieapp.data.network.movie.MovieApiService)
            : Pager<Int, UpComingMovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = UpComingMovieRemoteMediator(movieDatabase=movieDatabase, movieApiService = movieApiService),
            pagingSourceFactory = { movieDatabase.upcomingMovieDao.pagingSource() }
        )
    }

    @Provides
    @Singleton
    fun provideTrendingOfWeekMapper(): TrendingOfWeekResponseDtoToTrendingOfWeekMapper {
        return TrendingOfWeekResponseDtoToTrendingOfWeekMapper()
    }

    @Provides
    @Singleton
    fun providePopularSeriesMapper(): PopularSeriesDTOToPopularSeriesMapper {
        return PopularSeriesDTOToPopularSeriesMapper()
    }

    @Provides
    @Singleton
    fun provideWatchListDao(movieDatabase: MovieDatabase): WatchlistDao{
        return movieDatabase.watchlistDao
    }
    @Provides
    @Singleton
    fun provideUserPreferenceDao(movieDatabase: MovieDatabase): UserPreferenceDao{
        return movieDatabase.userPreferenceDao
    }


}

