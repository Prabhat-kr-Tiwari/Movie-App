package com.prabhat.movieapp.data.network.movie

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.prabhat.movieapp.data.local.upcomingMovie.MovieDatabase
import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
import com.prabhat.movieapp.data.mappers.toUpComingMovieEntity
import com.prabhat.movieapp.data.mappers.toUpComingMovieResponse
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UpComingMovieRemoteMediator(
    private val movieDatabase: MovieDatabase,
    private val movieApiService: MovieApiService
) : RemoteMediator<Int, UpComingMovieEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UpComingMovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.page + 1 // Increment to get the next page
                }
            }
            delay(2000L)
            Log.d("ALEXA", "load: "+loadKey)
            val upComingMovieResponseDTO = movieApiService.getUpComingMovie(
                page = loadKey,
                apiKey = "b52b167cdd0627e0ecc0924ce311cf15",
                language = "en-US"
            )

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.upcomingMovieDao.clearAll()
                }
                val upComingMovieEntity = upComingMovieResponseDTO.results.map { result ->
                    // Convert each Result object into an UpComingMovieEntity

                    UpComingMovieEntity(
                        id = result.id,
                        originalTitle = result.original_title,
                        releaseDate = result.release_date,
                        overview = result.overview,
                        imageUrl =getFullImageUrl(result.poster_path),
                        genreIds = result.genre_ids,
                        adult = result.adult,
                        page = upComingMovieResponseDTO.page,
                        totalPages = upComingMovieResponseDTO.total_pages
                    )
                }


                movieDatabase.upcomingMovieDao.upsertAll(upComingMovieEntity)
            }
//            MediatorResult.Success(endOfPaginationReached = upComingMovieResponseDTO.results.isEmpty())
            MediatorResult.Success(    endOfPaginationReached = upComingMovieResponseDTO.page >= upComingMovieResponseDTO.total_pages)


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }
    private fun getFullImageUrl(posterPath: String?): String {
        return if (!posterPath.isNullOrEmpty()) "$BASE_IMAGE_URL$posterPath" else "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg"
    }


}