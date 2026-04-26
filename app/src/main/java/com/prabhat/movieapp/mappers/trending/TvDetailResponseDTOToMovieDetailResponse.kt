package com.prabhat.movieapp.mappers.trending

import com.prabhat.movieapp.data.model.movie.trending.details.TvDetailResponseDTO
import com.prabhat.movieapp.domain.model.movieDetail.MovieDetailResponse
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL

fun TvDetailResponseDTO.toMovieDetailResponse(): MovieDetailResponse{
    return MovieDetailResponse(
        id = id,
        genreIds = genres.map { it.id },
        imageUrl = "${BASE_IMAGE_URL}${poster_path ?: ""}",
        originalTitle = original_name,
        overview = overview,
        releaseDate = first_air_date,
        adult = adult
    )
}