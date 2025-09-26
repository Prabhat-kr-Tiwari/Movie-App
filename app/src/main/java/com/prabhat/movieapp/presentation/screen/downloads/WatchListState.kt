package com.prabhat.movieapp.presentation.screen.downloads

import com.prabhat.movieapp.domain.model.watchList.Movie

data class WatchListState(
    val isLoading: Boolean = false,
    val data: List<Movie>? = null,
    val error: String = "",
    val message: String="",
)

