package com.prabhat.movieapp.data.network.movie.popularSeries.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prabhat.movieapp.data.network.movie.MovieApiService
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.mappers.mapAll
import com.prabhat.movieapp.mappers.popularSeries.PopularSeriesDTOToPopularSeriesMapper

class PopularSeriesPagingSource(
    private val movieApiService: MovieApiService,
    private val q: String,
    private val mapper: PopularSeriesDTOToPopularSeriesMapper
) : PagingSource<Int, PopularSeries>() {
    override fun getRefreshKey(state: PagingState<Int, PopularSeries>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularSeries> {
        val page=params.key?:1
        Log.d("PopularSeriesPagingSource", "load: "+page)
        val pageSize=params.loadSize
        return  try {
            val popularSeries = movieApiService.getPopularSeries(page = page, language = "en-US", apiKey = "b52b167cdd0627e0ecc0924ce311cf15")
            val popularSeriesList = listOf(popularSeries)

            LoadResult.Page(
                data = mapper.mapAll(popularSeriesList),
                prevKey = if(page==1) null else page.minus(1),
                nextKey = if(popularSeries.results.size<pageSize) null else page.plus(1)


            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }

    }


}