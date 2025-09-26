package com.prabhat.movieapp.data.model.movie.trending

data class TrendingOfWeekResponseDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)