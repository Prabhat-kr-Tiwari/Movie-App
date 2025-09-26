package com.prabhat.movieapp.data.repository.movie

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prabhat.movieapp.data.model.categories.GenreResponseDto
import com.prabhat.movieapp.data.model.categories.movieByCategories.MovieByCategoriesResponseDto
import com.prabhat.movieapp.data.model.movie.popular.videos.PopularSeriesVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.data.network.movie.MovieApiService
import com.prabhat.movieapp.data.network.movie.movieByCategories.MovieByCategoriesPagingSource
import com.prabhat.movieapp.data.network.movie.popularSeries.pagingSource.PopularSeriesPagingSource
import com.prabhat.movieapp.data.network.movie.trending.pagindSource.TrendingOfWeekPagingSource
import com.prabhat.movieapp.data.network.movie.tvByCategories.TvByCategoriesPagingSource
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek
import com.prabhat.movieapp.domain.model.tvByCategories.TvByCategories
import com.prabhat.movieapp.domain.repository.movie.MovieRepository
import com.prabhat.movieapp.mappers.popularSeries.PopularSeriesDTOToPopularSeriesMapper
import com.prabhat.movieapp.mappers.trending.TrendingOfWeekResponseDtoToTrendingOfWeekMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(
    private val movieApiService: MovieApiService,
    private val mapper: PopularSeriesDTOToPopularSeriesMapper,
    private val trendingOfWeekMapper:TrendingOfWeekResponseDtoToTrendingOfWeekMapper

) :
    MovieRepository {
    override suspend fun getUpComingMovie(page: Int, language: String): UpComingMovieResponseDTO {
        return movieApiService.getUpComingMovie(
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
            page = page,
            language = language
        )
    }

    override suspend fun getMovieCredits(
        movieId: Int,
        language: String
    ): CreditsResponseDto {
        return movieApiService.getMovieCredits(
            movieId = movieId,
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
            language = language
        )
    }

    override suspend fun getUpComingMovieVideo(movieId: Int): UpComingMovieVideoResponseDTO {
        return movieApiService.getMovieVideos(
            movieId = movieId,
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15"
        )
    }

    override suspend fun getPopularSeries(): Pager<Int, PopularSeries> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                Log.d("TRENDINGWAX", "getPopularSeries: CALLED ")
                PopularSeriesPagingSource(
                    movieApiService = movieApiService,
                    q = "",
                    mapper = mapper
                )
            }
        )
    }

    override suspend fun getSeriesCredits(seriesId: Int, language: String): CreditsResponseDto {

        return movieApiService.getSeriesCredits(
            seriesId = seriesId,
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
            language = language
        )
    }

    override suspend fun getSeriesVideos(seriesId: Int): PopularSeriesVideoResponseDTO {
        return movieApiService.getSeriesVideos(
            seriesId = seriesId,
            apiKey = "b52b167cdd0627e0ecc0924ce311cf15"
        )
    }

  /*  override suspend fun getTrendingOfWeek(): Pager<Int, TrendingOfWeek> {
        Log.d("TRENDINGWAX", "getTrendingOfWeek:MovieRepositoryImpl called")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                TrendingOfWeekPagingSource(
                    movieApiService = movieApiService,
                    mapper = trendingOfWeekMapper
                )
            }
        )
    }*/
   /* override suspend fun getTrendingOfWeek(): Pager<Int, TrendingOfWeek> {
        Log.d("TRENDINGWAX", "getTrendingOfWeek: MovieRepositoryImpl called")

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                Log.d("TRENDINGWAX", "getTrendingOfWeek:MovieRepositoryImpl pagingSourceFactory ")
                TrendingOfWeekPagingSource(
                    movieApiService = movieApiService,
                    mapper = trendingOfWeekMapper
                )
            }
        )
    }*/
    override suspend fun getTrendingOfWeek(): Pager<Int, TrendingOfWeek> {
        val mymapper =  TrendingOfWeekResponseDtoToTrendingOfWeekMapper()
      Log.d("TRENDINGWAX", "getTrendingOfWeek: mymapper"+mymapper)
      Log.d("TRENDINGWAX", "getTrendingOfWeek: mymapper"+trendingOfWeekMapper)
      Log.d("TRENDINGWAX", "getTrendingOfWeek: called ")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                Log.d("TRENDINGWAX", "getTrendingOfWeek:MovieRepositoryImpl pagingSourceFactory  ")
                TrendingOfWeekPagingSource(
                    movieApiService = movieApiService,
                    mapper = trendingOfWeekMapper
                )
            }
        )
    }

    override suspend fun getGenre(): GenreResponseDto {
        return movieApiService.getGenreList(apiKey = "b52b167cdd0627e0ecc0924ce311cf15")
    }

    override suspend fun getMovieByCategories(withGenre: String): Flow<PagingData<MovieByCategories>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                Log.d("TRENDINGWAX", "getPopularSeries: CALLED ")
                MovieByCategoriesPagingSource(
                    movieApiService = movieApiService,
                  withGenre = withGenre
                )
            }
        ).flow
    }

    override suspend fun getTvGenreList(): GenreResponseDto {
        return movieApiService.getTvGenreList(apiKey = "b52b167cdd0627e0ecc0924ce311cf15")
    }

    override suspend fun getTvByCategories(withGenre: String): Flow<PagingData<TvByCategories>> {
           return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                Log.d("TRENDINGWAX", "getPopularSeries: CALLED ")
                TvByCategoriesPagingSource(
                    movieApiService = movieApiService,
                    withGenre = withGenre
                )
            }
        ).flow
    }


}