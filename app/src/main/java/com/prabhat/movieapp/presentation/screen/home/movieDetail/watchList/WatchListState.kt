package com.prabhat.movieapp.presentation.screen.home.movieDetail.watchList


data class WatchListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val message: String="",
    val isInWatchlist: Boolean = false
)

