package com.prabhat.movieapp.domain.repository

import com.prabhat.movieapp.domain.model.watchList.Movie

interface WatchlistRepository {
    suspend fun addMovie(movie: Movie)
    suspend fun removeMovie(movieId: Int)
    suspend fun getWatchlist(): List<Movie>
    suspend fun isMovieInWatchList(movieId: Int): Boolean
}