package com.prabhat.movieapp.data.model.categories.movieByCategories

data class MovieByCategoriesResponseDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)