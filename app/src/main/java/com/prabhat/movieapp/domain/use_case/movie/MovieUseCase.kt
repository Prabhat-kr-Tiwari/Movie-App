package com.prabhat.movieapp.domain.use_case.movie

import android.util.Log
import com.prabhat.movieapp.data.appSettings.SessionId
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.domain.repository.movie.MovieRepository
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {



    fun getUpComingMovie(page:Int,language:String): Flow<Resource<UpComingMovieResponseDTO>> = flow<Resource<UpComingMovieResponseDTO>> {


        val response=movieRepository.getUpComingMovie(page = page, language = language)

        if (response.results.isNotEmpty()) {
            emit(Resource.Success(response))
            Log.d("ALEXA", "getUpComingMovie: MovieUseCase "+response)
        } else {
            emit(Resource.Error("error-->$response"))
            Log.d("ALEXA", "getUpComingMovie:error ${response}")
        }

    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))
        Log.d("ALEXA", "getUpComingMovie:error2 ${it.message}")
    }


    fun getMovieCredits(movieId:Int,language: String):Flow<Resource<CreditsResponseDto>> = flow<Resource<CreditsResponseDto>>{
        val response=movieRepository.getMovieCredits(movieId = movieId, language = language)

        Log.d("JOHN", "getMovieCredits:response "+response)
        if (response.id > 0) {
            emit(Resource.Success(response))
            Log.d("JOHN", "getMovieCredits: MovieUseCase "+response)
        } else {
            emit(Resource.Error("error-->$response"))
            Log.d("JOHN", "getMovieCredits:error ${response}")
        }

    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))
        Log.d("JOHN", "getMovieCredits:error2 ${it.message}")
        Log.d("JOHN", "getMovieCredits:error2 ")
    }
    fun getUpComingMovieVideos(movieId:Int):Flow<Resource<UpComingMovieVideoResponseDTO>> = flow<Resource<UpComingMovieVideoResponseDTO>>{
        val response=movieRepository.getUpComingMovieVideo(movieId = movieId)

        Log.d("JOHN", "getUpComingMovieVideos:response "+response)
        if (response.results.isNotEmpty()) {
            emit(Resource.Success(response))
            Log.d("JOHN", "getUpComingMovieVideos: MovieUseCase "+response)
        } else {
            emit(Resource.Error("error-->$response"))
            Log.d("JOHN", "getUpComingMovieVideos:error ${response}")
        }

    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))
        Log.d("JOHN", "getUpComingMovieVideos:error2 ${it.message}")
        Log.d("JOHN", "getUpComingMovieVideos:error2 ")
    }

}