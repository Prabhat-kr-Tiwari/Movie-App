package com.prabhat.movieapp.data.local.upcomingMovie

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prabhat.movieapp.data.local.upcomingMovie.converter.Converter
import com.prabhat.movieapp.data.local.watchList.MovieEntity
import com.prabhat.movieapp.data.local.watchList.WatchlistDao

@Database(entities = [UpComingMovieEntity::class, MovieEntity::class], version = 3)
@TypeConverters(Converter::class)

abstract class MovieDatabase : RoomDatabase() {

    abstract val upcomingMovieDao : UpComingMovieDao
    abstract val watchlistDao: WatchlistDao

}