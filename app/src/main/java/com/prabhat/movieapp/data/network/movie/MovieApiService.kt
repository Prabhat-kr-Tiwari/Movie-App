package com.prabhat.movieapp.data.network.movie


import com.prabhat.movieapp.data.model.categories.GenreResponseDto
import com.prabhat.movieapp.data.model.categories.movieByCategories.MovieByCategoriesResponseDto
import com.prabhat.movieapp.data.model.categories.tvByCategories.TvByCategoriesResponseDto
import com.prabhat.movieapp.data.model.movie.popular.PopularSeriesDTO
import com.prabhat.movieapp.data.model.movie.popular.videos.PopularSeriesVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.trending.TrendingOfWeekResponseDto
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {


    @GET("movie/upcoming")
    suspend fun getUpComingMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String,

    ): UpComingMovieResponseDTO


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): CreditsResponseDto
    //GETMOVIEVIDEO
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
    ):UpComingMovieVideoResponseDTO

    //get popular series
    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String,
        ):PopularSeriesDTO
    @GET("tv/{series_id}/credits")
    suspend fun getSeriesCredits(
        @Path("series_id") seriesId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): CreditsResponseDto


    //getSeriesvide
    @GET("tv/{series_id}/videos")
    suspend fun getSeriesVideos(
        @Path("series_id") seriesId: Int,
        @Query("api_key") apiKey: String,
    ):PopularSeriesVideoResponseDTO
    //get the trending by weeek

    @GET("trending/all/week")
    suspend fun getTrendingOfWeek(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String,
    ):TrendingOfWeekResponseDto


    //get genere list
    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String
    ):GenreResponseDto

    //get movie by categories
    @GET("discover/movie")
    suspend fun getMovieByCategories(
        @Query("api_key") apiKey: String,
        @Query("with_genres") withGenres: String,
        @Query("page") page: Int,
    ):MovieByCategoriesResponseDto

    //get tv genre list
    @GET("genre/tv/list")
    suspend fun getTvGenreList(
        @Query("api_key") apiKey: String
    ): GenreResponseDto

    //get tv by categories
    @GET("discover/tv")
    suspend fun getTvByCategories(
        @Query("api_key") apiKey: String,
        @Query("with_genres") withGenres: String,
        @Query("page") page: Int,
    ): TvByCategoriesResponseDto

}