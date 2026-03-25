package com.prabhat.movieapp.data.local.upcomingMovie

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prabhat.movieapp.data.local.cardDetails.CardDetailsDao
import com.prabhat.movieapp.data.local.cardDetails.CardDetailsEntity
import com.prabhat.movieapp.data.local.upcomingMovie.converter.Converter
import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceDao
import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceEntity
import com.prabhat.movieapp.data.local.watchList.MovieEntity
import com.prabhat.movieapp.data.local.watchList.WatchlistDao

@Database(entities = [UpComingMovieEntity::class, MovieEntity::class, UserPreferenceEntity::class,CardDetailsEntity::class], version = 6)
@TypeConverters(Converter::class)

abstract class MovieDatabase : RoomDatabase() {

    abstract val upcomingMovieDao : UpComingMovieDao
    abstract val watchlistDao: WatchlistDao
    abstract val userPreferenceDao: UserPreferenceDao

    abstract val cardDetailsDao: CardDetailsDao

}