package com.prabhat.movieapp.presentation.screen.home.movieDetail.movieVideo


import com.prabhat.movieapp.domain.model.upcomingMovie.upcomingMovieVideo.UpcomingMovieVideo

data class MovieVideoState(
    val isLoading: Boolean = false,
    val data: UpcomingMovieVideo? =null,
    val error: String? = null
)

