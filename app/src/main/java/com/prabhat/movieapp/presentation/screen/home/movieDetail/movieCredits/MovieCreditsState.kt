package com.prabhat.movieapp.presentation.screen.home.movieDetail.movieCredits


import com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.MovieCredits

data class MovieCreditsState(
    val isLoading: Boolean = false,
    val data: MovieCredits? =null,
//    val data: List<MovieCredits?> = emptyList(),
    val error: String = ""
)

