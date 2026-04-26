package com.prabhat.movieapp.mappers.upComingMovie.upcomingMovieDetail

import com.prabhat.movieapp.data.model.movie.upcoming.details.UpComingMovieDetailResponseDTO
import com.prabhat.movieapp.domain.model.movieDetail.MovieDetailResponse
import com.prabhat.movieapp.domain.model.upcomingMovie.upcomingMovieDetail.UpcomingMovieDetailResponse
import com.prabhat.movieapp.navigation.HomeDestination
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL

fun UpComingMovieDetailResponseDTO.toMovieDetailResponse(): MovieDetailResponse {

    return  MovieDetailResponse(
        id = id,
        genreIds = genres.map { it.id },
        imageUrl = "${BASE_IMAGE_URL}${poster_path ?: ""}",
        originalTitle = original_title,
        overview = overview,
        releaseDate = release_date,
        adult = adult
    )
}