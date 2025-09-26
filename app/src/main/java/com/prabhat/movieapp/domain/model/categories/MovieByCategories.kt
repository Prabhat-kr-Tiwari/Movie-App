package com.prabhat.movieapp.domain.model.categories

data class MovieByCategories(
    val id: Int,
    val genreIds: List<Int>,
    val imageUrl: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val page:Int,
    val totalPages:Int,
    val totalResult:Int
)
