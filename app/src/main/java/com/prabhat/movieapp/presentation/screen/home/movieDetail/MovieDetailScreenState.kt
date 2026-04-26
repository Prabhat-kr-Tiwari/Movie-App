package com.prabhat.movieapp.presentation.screen.home.movieDetail

import com.prabhat.movieapp.domain.model.movieDetail.MovieDetailResponse

data class MovieDetailScreenState(
    val isLoading: Boolean = false,
    val data: MovieDetailResponse? = null,
    val error: String = ""
)
