package com.prabhat.movieapp.data.local.upcomingMovie

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prabhat.movieapp.data.local.upcomingMovie.converter.Converter

@Database(entities = [UpComingMovieEntity::class], version = 1)
@TypeConverters(Converter::class)

abstract class MovieDatabase : RoomDatabase() {

    abstract val upcomingMovieDao : UpComingMovieDao

}