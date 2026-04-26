package com.prabhat.movieapp.presentation.screen.home.movieDetail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.model.watchList.Movie
import com.prabhat.movieapp.domain.use_case.movie.MovieUseCase
import com.prabhat.movieapp.domain.use_case.watchList.AddMovieToWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.IsMovieInWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.RemoveMovieFromWatchlistUseCase
import com.prabhat.movieapp.mappers.popularSeries.popularSeriesDetail.toMovieDetailResponse
import com.prabhat.movieapp.mappers.popularSeries.toPopularSeriesVideo
import com.prabhat.movieapp.mappers.toMovieCredits
import com.prabhat.movieapp.mappers.toMovieVideo
import com.prabhat.movieapp.mappers.toUpcomingMovieVide
import com.prabhat.movieapp.mappers.trending.toMovieDetailResponse
import com.prabhat.movieapp.mappers.upComingMovie.upcomingMovieDetail.toMovieDetailResponse
import com.prabhat.movieapp.presentation.screen.home.movieDetail.movieCredits.MovieCreditsState
import com.prabhat.movieapp.presentation.screen.home.movieDetail.movieVideo.MovieVideoState
import com.prabhat.movieapp.presentation.screen.home.movieDetail.popularSeriesVideoState.PopularSeriesVideoState
import com.prabhat.movieapp.presentation.screen.home.movieDetail.watchList.WatchListState
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailScreenViewModel
@Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val isMovieInWatchlistUseCase: IsMovieInWatchlistUseCase,
    private val removeMovieFromWatchlistUseCase: RemoveMovieFromWatchlistUseCase,
    private val addMovieToWatchlistUseCase: AddMovieToWatchlistUseCase
) : ViewModel() {



    private val _uiState = MutableStateFlow(MovieDetailScreenState())
    val uiState = _uiState.asStateFlow()


    fun getUpComingMovieDetailById(id:Int){
        movieUseCase.getUpComingMovieDetailById(id.toString()).onEach { result->
            when(result){
                is Resource.Error<*> -> {

                    _uiState.value = _uiState.value.copy(error = result.message.toString())
                }
                is Resource.Loading<*> -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)

                }
                is Resource.Success<*> -> {
                    _uiState.value = _uiState.value.copy(data = result.data?.toMovieDetailResponse(),isLoading = false)

                }
            }

        }.launchIn(viewModelScope)
    }


    private val _watchListsState = MutableStateFlow(WatchListState())
    val watchlistState = _watchListsState.asStateFlow()

    fun isMovieInWatchList(movieId:Int){
        isMovieInWatchlistUseCase.isMovieInWatchList(movieId = movieId)
            .onEach {it->
                when(it) {
                    is Resource.Error->{

                        it.message?.let { it1 -> _watchListsState.value.copy(error = it1) }!!

                    }
                    is Resource.Loading->{

                        _watchListsState.value = _watchListsState.value.copy(isLoading = true)

                    }
                    is Resource.Success->{

                        _watchListsState.value = _watchListsState.value.copy(isInWatchlist = it.data?:false, isLoading = false)

                    }
                }

            }
            .launchIn(viewModelScope)

    }



    fun toggleWatchlist(movie: Movie) {
        viewModelScope.launch {
            var currentlyInWatchlist = false
            isMovieInWatchlistUseCase.isMovieInWatchList(movie.id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        currentlyInWatchlist = result.data == true
                    }
                    is Resource.Error -> {
                        _watchListsState.value = _watchListsState.value.copy(error = result.message ?: "Error checking watchlist")
                    }
                    else -> {}
                }
            }

            if (currentlyInWatchlist) {
                removeMovieFromWatchlistUseCase.removeMovie(movie.id).onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _watchListsState.value = _watchListsState.value.copy(
                                isLoading = false,
                                error = result.message ?: "Error removing movie"
                            )
                        }
                        is Resource.Loading -> {
                            _watchListsState.value = _watchListsState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            _watchListsState.value = _watchListsState.value.copy(
                                isLoading = false,
                                message = "Removed from watchlist",
                                isInWatchlist = false
                            )
                        }
                    }
                }.launchIn(viewModelScope)

            } else {
                addMovieToWatchlistUseCase.addMovieToWatchList(movie).onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _watchListsState.value = _watchListsState.value.copy(
                                isLoading = false,
                                error = result.message ?: "Error adding movie"
                            )
                        }
                        is Resource.Loading -> {
                            _watchListsState.value = _watchListsState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            _watchListsState.value = _watchListsState.value.copy(
                                isLoading = false,
                                message = "Added to watchlist",
                                isInWatchlist = true

                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }


    private val _movieCreditsState = MutableStateFlow(MovieCreditsState())
    val movieCreditsState = _movieCreditsState.asStateFlow()


     fun getMovieCredits(movieId:Int, language:String){
        movieUseCase.getMovieCredits(movieId=movieId, language = language)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    _movieCreditsState.value=_movieCreditsState.value.copy(isLoading = true)
                }
                is Resource.Success->{
                    val movieCredits=it.data?.toMovieCredits()

                    _movieCreditsState.value=_movieCreditsState.value.copy(isLoading = false,data = movieCredits!!)


                }
                is Resource.Error->{
                    _movieCreditsState.value= it.message?.let { it1 -> _movieCreditsState.value.copy(error = it1) }!!
                }

            }

        }.launchIn(viewModelScope)


    }


    private val _movieVideoState= MutableStateFlow(MovieVideoState())
    val movieVideoState = _movieVideoState.asStateFlow()

     fun getMovieVideo(movieId:Int){
        movieUseCase.getUpComingMovieVideos(movieId=movieId)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    _movieVideoState.value=_movieVideoState.value.copy(isLoading = true)
                }
                is Resource.Success->{
                    val movieVideos=it.data?.toUpcomingMovieVide()

                    _movieVideoState.value=_movieVideoState.value.copy(isLoading = false,data = movieVideos!!)


                }
                is Resource.Error->{
                    _movieVideoState.value= it.message?.let { it1 -> _movieVideoState.value.copy(error = it1) }!!
                }

            }

        }.launchIn(viewModelScope)


    }


    //SERIES
    fun getPopularSeriesDetailById(id:Int){
        movieUseCase.getPopularSeriesDetailById(id.toString()).onEach { result->
            when(result){
                is Resource.Error<*> -> {

                    _uiState.value = _uiState.value.copy(error = result.message.toString(), isLoading = false)
                }
                is Resource.Loading<*> -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)

                }
                is Resource.Success<*> -> {
                    _uiState.value = _uiState.value.copy(data = result.data?.toMovieDetailResponse(),isLoading = false)

                }
            }

        }.launchIn(viewModelScope)
    }


    fun getSeriesCredits(seriesId:Int, language:String){
        movieUseCase.getSeriesCredits(seriesId =seriesId, language = language)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    _movieCreditsState.value=_movieCreditsState.value.copy(isLoading = true)
                }
                is Resource.Success->{
                    val seriesCredits=it.data?.toMovieCredits()

                    _movieCreditsState.value=_movieCreditsState.value.copy(isLoading = false,data = seriesCredits!!)


                }
                is Resource.Error->{
                    _movieCreditsState.value= it.message?.let { it1 -> _movieCreditsState.value.copy(error = it1) }!!
                }

            }

        }.launchIn(viewModelScope)


    }





     fun getPopularSeriesVideo(seriesId:Int){
        movieUseCase.getPopularSeriesVideos(seriesId = seriesId)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    _movieVideoState.value=_movieVideoState.value.copy(isLoading = true)
                }
                is Resource.Success->{
                    val popularSeriesVideos=it.data?.toMovieVideo()

                    _movieVideoState.value=_movieVideoState.value.copy(isLoading = false,data = popularSeriesVideos!!)


                }
                is Resource.Error->{
                    _movieVideoState.value= it.message?.let { it1 -> _movieVideoState.value.copy(error = it1, isLoading = false) }!!
                }

            }

        }.launchIn(viewModelScope)


    }


    //trending
    fun getTvDetailById(id:Int){
        movieUseCase.getTvDetailById(id = id, language = "en-Us").onEach{result->
            when(result){
                is Resource.Error<*> -> {
                    _uiState.value = _uiState.value.copy(error = result.message.toString(), isLoading = false)
                }
                is Resource.Loading<*> ->{
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is Resource.Success<*> -> {
                    _uiState.value = _uiState.value.copy(data = result.data?.toMovieDetailResponse(),isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }







}