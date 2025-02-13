package com.prabhat.movieapp.data.repository.movie

import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.data.network.movie.MovieApiService
import com.prabhat.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    MovieRepository {
    override suspend fun getUpComingMovie(page: Int, language: String): UpComingMovieResponseDTO {
        return movieApiService.getUpComingMovie(
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
            page = page,
            language = language
        )
    }

    override suspend fun getMovieCredits(
        movieId: Int,
        language: String
    ): CreditsResponseDto {
        return movieApiService.getMovieCredits(
            movieId = movieId,
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
            language = language
        )
    }

    override suspend fun getUpComingMovieVideo(movieId: Int): UpComingMovieVideoResponseDTO {
        return movieApiService.getMovieVideos(
            movieId = movieId,
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15"
        )
    }

}