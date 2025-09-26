package com.prabhat.movieapp.domain.use_case.watchList

import com.prabhat.movieapp.domain.model.watchList.Movie
import com.prabhat.movieapp.domain.repository.WatchlistRepository
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddMovieToWatchlistUseCase @Inject constructor(private val watchListRepository: WatchlistRepository) {

    fun addMovieToWatchList(movie: Movie): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {

            emit(Resource.Success(watchListRepository.addMovie(movie = movie)))
        } catch (e: Exception) {

            emit(Resource.Error(e.message))
        }
    }
}