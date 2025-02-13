package com.prabhat.movieapp.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
import com.prabhat.movieapp.data.mappers.toUpComingMovieResponse
import com.prabhat.movieapp.domain.model.upcomingMovie.UpComingMovieResponse
import com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.MovieCredits
import com.prabhat.movieapp.domain.use_case.movie.MovieUseCase
import com.prabhat.movieapp.mappers.toMovieCredits
import com.prabhat.movieapp.mappers.toUpComingMovieResponse
import com.prabhat.movieapp.mappers.toUpcomingMovieVide
import com.prabhat.movieapp.presentation.screen.home.movieDetail.movieCredits.MovieCreditsState
import com.prabhat.movieapp.presentation.screen.home.movieDetail.movieVideo.MovieVideoState
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieScreenViewModel @Inject constructor(private val movieUseCase: MovieUseCase,private val pager: Pager<Int, UpComingMovieEntity>) :
    ViewModel() {


    private val _upcomingMovieScreenState = mutableStateOf(UpComingMovieScreenState())

    val upComingMovieScreenState: State<UpComingMovieScreenState> = _upcomingMovieScreenState

    fun getUpComingMovie(page:Int,language:String){
        movieUseCase.getUpComingMovie(page=page, language = language)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    Log.d("ALEXA", "getUpComingMovie: loading")
                    _upcomingMovieScreenState.value=_upcomingMovieScreenState.value.copy(isLoading = true)
                }
                is Resource.Success->{
//                    _upcomingMovieScreenState.value=_upcomingMovieScreenState.value.copy(data = listOf( it.data?.toUpComingMovieResponse()))
                    val upcomingMovies = it.data?.toUpComingMovieResponse()
                    Log.d("ALEXA", "getUpComingMovie:  upcomingMovies"+upcomingMovies)
//                    val imageUrlList = upcomingMovies?.map { it.imageUrl }

                    _upcomingMovieScreenState.value=_upcomingMovieScreenState.value.copy(data = upcomingMovies!!)
                    Log.d("ALEXA", "getUpComingMovie: MovieScreenViewModel $upcomingMovies")


                }
                is Resource.Error->{
                    _upcomingMovieScreenState.value= it.message?.let { it1 -> _upcomingMovieScreenState.value.copy(error = it1) }!!
                    Log.d("ALEXA", "getUpComingMovie: "+it.message.toString())
                }

            }

        }.launchIn(viewModelScope)


    }
    val upComingMoviePagingFlow = pager.flow.map { pagingData->
        pagingData.map { it.toUpComingMovieResponse() }

    }.cachedIn(viewModelScope)

/*    private val _selectedMovie = MutableStateFlow<UpComingMovieResponse?>(null)
    val selectedMovie: StateFlow<UpComingMovieResponse?>  = _selectedMovie.asStateFlow()*/
    private val _selectedMovie:MutableState<UpComingMovieResponse?> = mutableStateOf<UpComingMovieResponse?>(null)
    val selectedMovie: MutableState<UpComingMovieResponse?>  = _selectedMovie

    fun onMovieSelected(movie: UpComingMovieResponse) {
        /*viewModelScope.launch {
            _selectedMovie.emit(movie)
        }*/
        _selectedMovie.value = movie
        Log.d("JOHN", "onMovieSelected: "+movie)
        getMovieCredits(movieId = movie.id, language = "en-US")
        getMovieVideo(movieId = movie.id)


    }
    fun resetSelectedMovie() {
        _selectedMovie.value = null
    }

    private val _movieCreditsState = mutableStateOf(MovieCreditsState())
    val movieCreditsState:State<MovieCreditsState> = _movieCreditsState

    private fun getMovieCredits(movieId:Int, language:String){
        movieUseCase.getMovieCredits(movieId=movieId, language = language)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    Log.d("JOHN", "getMovieCredits: loading")
                    _movieCreditsState.value=_movieCreditsState.value.copy(isLoading = true)
                }
                is Resource.Success->{
                    val movieCredits=it.data?.toMovieCredits()
                    Log.d("JOHN", "getMovieCredits:  upcomingMovies"+movieCredits)

                    _movieCreditsState.value=_movieCreditsState.value.copy(isLoading = false,data = movieCredits!!)


                }
                is Resource.Error->{
                    _movieCreditsState.value= it.message?.let { it1 -> _movieCreditsState.value.copy(error = it1) }!!
                    Log.d("JOHN", "getMovieCredits: "+it.message.toString())
                }

            }

        }.launchIn(viewModelScope)


    }

    private val _movieVideoState= mutableStateOf(MovieVideoState())
    val movieVideoState:State<MovieVideoState> = _movieVideoState

    private fun getMovieVideo(movieId:Int){
        movieUseCase.getUpComingMovieVideos(movieId=movieId)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    Log.d("JOHN", "getMovieCredits: loading")
                    _movieVideoState.value=_movieVideoState.value.copy(isLoading = true)
                }
                is Resource.Success->{
                    val movieVideos=it.data?.toUpcomingMovieVide()
                    Log.d("JOHN", "getMovieCredits:  upcomingMovies"+movieVideos)

                    _movieVideoState.value=_movieVideoState.value.copy(isLoading = false,data = movieVideos!!)


                }
                is Resource.Error->{
                    _movieVideoState.value= it.message?.let { it1 -> _movieVideoState.value.copy(error = it1) }!!
                    Log.d("JOHN", "getMovieCredits: "+it.message.toString())
                }

            }

        }.launchIn(viewModelScope)


    }

    override fun onCleared() {
        super.onCleared()
        Log.d("PRABHU", "VIEWMODEL onCleared: ")
    }
    

}