package com.prabhat.movieapp.data.model.movie.upcoming

data class UpComingMovieResponseDTO(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)