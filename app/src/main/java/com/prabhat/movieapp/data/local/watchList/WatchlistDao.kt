package com.prabhat.movieapp.data.local.watchList

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Query("DELETE FROM watchlist WHERE movieId = :movieId")
    suspend fun removeMovie(movieId: Int)

    @Query("SELECT * FROM watchlist")
    suspend fun getWatchlist(): List<MovieEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE movieId = :movieId)")
    suspend fun isMovieInWatchList(movieId: Int): Boolean
}