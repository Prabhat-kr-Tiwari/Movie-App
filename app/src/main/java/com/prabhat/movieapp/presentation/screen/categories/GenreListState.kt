package com.prabhat.movieapp.presentation.screen.categories

import com.prabhat.movieapp.domain.model.genre.Genre

data class GenreListState(
    val isLoading: Boolean = false,
    val data: List<Genre?> = emptyList(),
    val error: String = ""
)
