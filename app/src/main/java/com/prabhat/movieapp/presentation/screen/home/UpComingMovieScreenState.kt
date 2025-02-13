package com.prabhat.movieapp.presentation.screen.home

import com.prabhat.movieapp.domain.model.upcomingMovie.UpComingMovieResponse

data class UpComingMovieScreenState(
    val isLoading: Boolean = false,
    val data: List<UpComingMovieResponse?> = emptyList(),
    val error: String = ""
)

