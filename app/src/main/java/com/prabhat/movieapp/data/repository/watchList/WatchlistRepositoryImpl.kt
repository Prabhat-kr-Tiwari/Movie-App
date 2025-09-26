package com.prabhat.movieapp.data.repository.watchList

import com.prabhat.movieapp.data.local.watchList.WatchlistDao
import com.prabhat.movieapp.domain.model.watchList.Movie
import com.prabhat.movieapp.domain.repository.WatchlistRepository
import com.prabhat.movieapp.mappers.watchList.toDomain
import com.prabhat.movieapp.mappers.watchList.toEntity

class WatchlistRepositoryImpl(
    private val dao: WatchlistDao
) : WatchlistRepository {

    override suspend fun addMovie(movie: Movie) {
        dao.addMovie(movie.toEntity())
    }

    override suspend fun removeMovie(movieId: Int) {
        dao.removeMovie(movieId)
    }

    override suspend fun getWatchlist(): List<Movie> {
        return dao.getWatchlist().map { it.toDomain() }
    }

    override suspend fun isMovieInWatchList(movieId: Int): Boolean {
        return dao.isMovieInWatchList(movieId = movieId)
    }
}
