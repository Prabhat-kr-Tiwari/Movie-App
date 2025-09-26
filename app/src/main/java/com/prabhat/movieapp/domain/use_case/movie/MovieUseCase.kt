package com.prabhat.movieapp.domain.use_case.movie

import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.liveData
import com.prabhat.movieapp.data.appSettings.SessionId
import com.prabhat.movieapp.data.model.categories.GenreResponseDto
import com.prabhat.movieapp.data.model.movie.popular.PopularSeriesDTO
import com.prabhat.movieapp.data.model.movie.popular.videos.PopularSeriesVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek
import com.prabhat.movieapp.domain.model.tvByCategories.TvByCategories
import com.prabhat.movieapp.domain.repository.movie.MovieRepository
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {



    fun getUpComingMovie(page:Int,language:String): Flow<Resource<UpComingMovieResponseDTO>> = flow<Resource<UpComingMovieResponseDTO>> {


        val response=movieRepository.getUpComingMovie(page = page, language = language)

        if (response.results.isNotEmpty()) {
            emit(Resource.Success(response))
        } else {
            emit(Resource.Error("error-->$response"))
        }

    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))
    }


    fun getMovieCredits(movieId:Int,language: String):Flow<Resource<CreditsResponseDto>> = flow<Resource<CreditsResponseDto>>{
        val response=movieRepository.getMovieCredits(movieId = movieId, language = language)

        Log.d("JOHN", "getMovieCredits:response "+response)
        if (response.id > 0) {
            emit(Resource.Success(response))
            Log.d("JOHN", "getMovieCredits: MovieUseCase "+response)
        } else {
            emit(Resource.Error("error-->$response"))
            Log.d("JOHN", "getMovieCredits:error ${response}")
        }

    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))

    }
    fun getUpComingMovieVideos(movieId:Int):Flow<Resource<UpComingMovieVideoResponseDTO>> = flow<Resource<UpComingMovieVideoResponseDTO>>{
        val response=movieRepository.getUpComingMovieVideo(movieId = movieId)


        if (response.results.isNotEmpty()) {
            emit(Resource.Success(response))

        } else {
            emit(Resource.Error("error-->$response"))

        }

    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))

    }
    //    fun getPopularSeries():Flow<Resource<PopularSeriesDTO>> = flow<Resource<PopularSeriesDTO>>{
//        val response=movieRepository.getPopularSeries()
//
//        Log.d("JOHN", "getPopularSeries:response "+response)
//        if (response) {
//            emit(Resource.Success(response))
//            Log.d("JOHN", "getPopularSeries: MovieUseCase "+response)
//        } else {
//            emit(Resource.Error("error-->$response"))
//            Log.d("JOHN", "getPopularSeries:error ${response}")
//        }
//
//    }.onStart { emit(Resource.Loading()) }.catch {
//        emit(Resource.Error(it.message))
//        Log.d("JOHN", "getPopularSeries:error2 ${it.message}")
//        Log.d("JOHN", "getPopularSeries:error2 ")
//    }
    //in use case
   /* suspend  operator fun invoke():Pair<Flow<Pager<Int, PopularSeries>>,Flow<Pager<Int,TrendingOfWeek>>> {
       return Pair(movieRepository.getPopularSeries().flow,
        movieRepository.getTrendingOfWeek().flow)
    }*/
   /* suspend operator fun invoke(): Pair<Flow<PagingData<PopularSeries>>, Flow<PagingData<TrendingOfWeek>>> {
        return Pair(
            movieRepository.getPopularSeries().flow,  // Convert Pager to Flow
            movieRepository.getTrendingOfWeek().flow  // Convert Pager to Flow
        )
    }*/

//    suspend operator fun invoke() = movieRepository.getPopularSeries()

  /*  suspend operator fun invoke(): Flow<MovieData> {
        return merge(
           *//* movieRepository.getTrendingOfWeek().map { MovieData.Trending(it) },
            movieRepository.getPopularSeries().map { MovieData.Popular(it) }*//*

             movieRepository.getTrendingOfWeek().flow.map { MovieData.Trending(it) },
            movieRepository.getPopularSeries().flow.map { MovieData.Popular(it) }
        )
    }*/
    operator fun invoke(): Flow<Pair<Flow<PagingData<TrendingOfWeek>>, Flow<PagingData<PopularSeries>>>> = flow {
        val trending = movieRepository.getTrendingOfWeek().flow
        val popular = movieRepository.getPopularSeries().flow
        emit(trending to popular) // Emits a Pair of both results
    }.flowOn(Dispatchers.IO)

    fun getSeriesCredits(seriesId:Int,language: String):Flow<Resource<CreditsResponseDto>> = flow<Resource<CreditsResponseDto>>{
        val response=movieRepository.getSeriesCredits(seriesId = seriesId, language = language)


        if (response.id > 0) {
            emit(Resource.Success(response))

        } else {
            emit(Resource.Error("error-->$response"))

        }

    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))

    }


    fun getPopularSeriesVideos(seriesId:Int):Flow<Resource<PopularSeriesVideoResponseDTO>> = flow<Resource<PopularSeriesVideoResponseDTO>>{
        val response=movieRepository.getSeriesVideos(seriesId = seriesId)

//        Log.d("JOHN", "getPopularSeriesVideos:response "+response)
//        if (response.results.isNotEmpty()) {
        if (response.id>0) {
            emit(Resource.Success(response))
//            Log.d("JOHN", "getPopularSeriesVideos: MovieUseCase "+response)
        } else {
            emit(Resource.Error("error-->$response"))
//            Log.d("JOHN", "getPopularSeriesVideos:error ${response}")
        }

    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))

    }

  /*  //
     fun getTrendingOfWeek(): Flow<PagingData<TrendingOfWeek>> = flow<PagingData<TrendingOfWeek>> {

         movieRepository.getTrendingOfWeek()
    }
*/

    fun getGenreList():Flow<Resource<GenreResponseDto>>
    = flow<Resource<GenreResponseDto>> {
        val response=movieRepository.getGenre()

        Log.d("JOHN", "getGenreList:response "+response)
//        if (response.results.isNotEmpty()) {
        if (response.genres.isNotEmpty()) {
            emit(Resource.Success(response))
            Log.d("JOHN", "getGenreList: MovieUseCase "+response)
        } else {
            emit(Resource.Error("error-->$response"))
            Log.d("JOHN", "getGenreList:error ${response}")
        }
    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))
        Log.d("JOHN", "getGenreList:error2 ${it.message}")
        Log.d("JOHN", "getGenreList:error2 ")
    }
    init {


    }
    /*fun getMovieByCategories(withGenre:String):Flow<PagingData<MovieByCategories>> = flow<PagingData<MovieByCategories>>
    {
        return movieRepository.getMovieByCategories(withGenre).flowWithLifecycle(lifecycle = )

    }.flowOn(Dispatchers.IO)*/
    suspend fun getMovieByCategories(withGenre: String): Flow<PagingData<MovieByCategories>> =
        movieRepository.getMovieByCategories(withGenre)


    fun getTvGenreList():Flow<Resource<GenreResponseDto>>
            = flow<Resource<GenreResponseDto>> {
        val response=movieRepository.getTvGenreList()
        if (response.genres.isNotEmpty()) {
            emit(Resource.Success(response))
        } else {
            emit(Resource.Error("error-->$response"))
        }
    }.onStart { emit(Resource.Loading()) }.catch {
        emit(Resource.Error(it.message))
    }

    suspend fun getTveByCategories(withGenre: String): Flow<PagingData<TvByCategories>> =
        movieRepository.getTvByCategories(withGenre)




}


