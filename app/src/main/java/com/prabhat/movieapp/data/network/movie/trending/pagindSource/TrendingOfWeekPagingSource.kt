package com.prabhat.movieapp.data.network.movie.trending.pagindSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prabhat.movieapp.data.network.movie.MovieApiService
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek
import com.prabhat.movieapp.mappers.mapAll
import com.prabhat.movieapp.mappers.trending.TrendingOfWeekResponseDtoToTrendingOfWeekMapper


class TrendingOfWeekPagingSource(
    private val movieApiService: MovieApiService,
    private val mapper:TrendingOfWeekResponseDtoToTrendingOfWeekMapper
):PagingSource<Int,TrendingOfWeek>() {
    override fun getRefreshKey(state: PagingState<Int, TrendingOfWeek>): Int? {

        Log.d("TrendingOfWeekPagingSource", "getRefreshKey: called")
        return  state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?:state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingOfWeek> {

        val page=params.key?:1
        val pageSize=params.loadSize
        Log.d("TrendingOfWeekPagingSource", "load: page $page page size $pageSize")
        return  try {
            val trendingOfWeek = movieApiService.getTrendingOfWeek(page = page, language = "en-US", apiKey = "b52b167cdd0627e0ecc0924ce311cf15")

            val trendingOfWeekResult = listOf(trendingOfWeek)
//            val trendingOfWeekResult = trendingOfWeek.results
            val totalCount = trendingOfWeek.total_results // (hypothetical field)

            LoadResult.Page(
                data = mapper.mapAll(trendingOfWeekResult),
                prevKey = if(page==1)  null else page.minus(1),
//                nextKey = if(trendingOfWeek.results.size < pageSize) null else page.plus(1)

                    nextKey = if ((page * pageSize) >= totalCount) null else page.plus(1)


            )
        }catch (e:Exception){
            Log.d("TrendingOfWeekPagingSource", "load: "+e.cause)
            LoadResult.Error(e)

        }
    }
}