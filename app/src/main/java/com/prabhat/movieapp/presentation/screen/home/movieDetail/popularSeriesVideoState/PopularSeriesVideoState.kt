package com.prabhat.movieapp.presentation.screen.home.movieDetail.popularSeriesVideoState

import com.prabhat.movieapp.domain.model.popular.popularSeriesVideo.popularSeriesVideo

data class PopularSeriesVideoState(
    val isLoading: Boolean = false,
    val data: popularSeriesVideo? =null,
    val error: String = ""
)
