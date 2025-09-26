package com.prabhat.movieapp.data.model.categories.tvByCategories

data class TvByCategoriesResponseDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)