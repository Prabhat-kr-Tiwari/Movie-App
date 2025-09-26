package com.prabhat.movieapp.domain.model.tvByCategories

data class TvByCategories(
    val id: Int,
    val genreIds: List<Int>,
    val imageUrl: String,
    val originalName: String,
    val overview: String,
    val firstAirDate: String,
    val adult: Boolean,
    val page:Int,
    val totalPages:Int,
    val totalResult:Int
)
