package com.prabhat.movieapp.domain.use_case.movie

import androidx.paging.PagingData
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek

sealed class MovieData {
    data class Trending(val data: PagingData<TrendingOfWeek>) : MovieData()
    data class Popular(val data: PagingData<PopularSeries>) : MovieData()
}
