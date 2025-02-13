package com.prabhat.movieapp.domain.repository.movie

import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto

interface MovieRepository {


//    suspend fun getUpComingMovie(page:Int,language:String):List<UpComingMovieResponseDTO>
    suspend fun getUpComingMovie(page:Int,language:String):UpComingMovieResponseDTO

    suspend fun getMovieCredits(movieId:Int,language: String):CreditsResponseDto
    suspend fun getUpComingMovieVideo(movieId: Int):UpComingMovieVideoResponseDTO
}