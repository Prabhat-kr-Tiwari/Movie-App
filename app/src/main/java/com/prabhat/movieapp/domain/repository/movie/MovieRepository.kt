package com.prabhat.movieapp.domain.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingData
import com.prabhat.movieapp.data.model.categories.GenreResponseDto
import com.prabhat.movieapp.data.model.categories.movieByCategories.MovieByCategoriesResponseDto
import com.prabhat.movieapp.data.model.movie.popular.videos.PopularSeriesVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek
import com.prabhat.movieapp.domain.model.tvByCategories.TvByCategories
import kotlinx.coroutines.flow.Flow

interface MovieRepository {


    suspend fun getUpComingMovie(page:Int,language:String):UpComingMovieResponseDTO

    suspend fun getMovieCredits(movieId:Int,language: String):CreditsResponseDto
    suspend fun getUpComingMovieVideo(movieId: Int):UpComingMovieVideoResponseDTO
    suspend fun getSeriesCredits(seriesId:Int,language: String):CreditsResponseDto
    suspend fun getSeriesVideos(seriesId:Int):PopularSeriesVideoResponseDTO
    suspend fun getPopularSeries():Pager<Int, PopularSeries>
    suspend fun getTrendingOfWeek():Pager<Int,TrendingOfWeek>
    suspend fun getGenre():GenreResponseDto
//    suspend fun getMovieByCategories(withGenre:String):Pager<Int,MovieByCategories>
    suspend fun getMovieByCategories(withGenre:String):Flow<PagingData<MovieByCategories>>
    suspend fun getTvGenreList(): GenreResponseDto
    suspend fun getTvByCategories(withGenre:String):Flow<PagingData<TvByCategories>>
}