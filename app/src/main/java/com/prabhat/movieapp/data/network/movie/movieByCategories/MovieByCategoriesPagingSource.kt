package com.prabhat.movieapp.data.network.movie.movieByCategories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prabhat.movieapp.data.network.movie.MovieApiService
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.mappers.categories.movieByCategories.toDomainMovieByCategories
import retrofit2.HttpException
import java.io.IOException

class MovieByCategoriesPagingSource(
    private val movieApiService: MovieApiService,
    private val withGenre:String
): PagingSource<Int, MovieByCategories>() {
    override fun getRefreshKey(state: PagingState<Int, MovieByCategories>): Int? {
        return state.anchorPosition

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieByCategories> {

        return try {
            val currentPage = params.key ?: 1
            val movies = movieApiService.getMovieByCategories(
                apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
              page = currentPage, withGenres = withGenre
            )
            LoadResult.Page(
                data = movies.toDomainMovieByCategories(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.results.isEmpty()) null else movies.page!! + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }


}