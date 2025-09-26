package com.prabhat.movieapp.domain.use_case.watchList

import android.util.Log
import com.prabhat.movieapp.domain.model.watchList.Movie
import com.prabhat.movieapp.domain.repository.WatchlistRepository
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsMovieInWatchlistUseCase @Inject constructor(private val watchListRepository: WatchlistRepository) {
    fun isMovieInWatchList(movieId:Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            Log.d("IsMovieInWatchlistUseCase", "isMovieInWatchList: ${watchListRepository.isMovieInWatchList(movieId = movieId)}")
            emit(Resource.Success(watchListRepository.isMovieInWatchList(movieId = movieId)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}