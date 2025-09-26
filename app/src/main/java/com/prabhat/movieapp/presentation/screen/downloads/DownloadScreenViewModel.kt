package com.prabhat.movieapp.presentation.screen.downloads

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.watchList.RemoveMovieFromWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.WatchListUseCase
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DownloadScreenViewModel @Inject constructor(private val watchListUseCase: WatchListUseCase,private val removeMovieFromWatchlistUseCase: RemoveMovieFromWatchlistUseCase) :
    ViewModel() {
    private val _watchListsState = mutableStateOf(WatchListState())
    val watchlistState: State<WatchListState> = _watchListsState

    init {
        loadWatchlist()
    }






    fun loadWatchlist() {
        watchListUseCase.getWatchList().onEach { it ->
            when (it) {
                is Resource.Error -> {
                    val errorMsg = it.message ?: "Unknown error"

                    _watchListsState.value = _watchListsState.value.copy(error = errorMsg, isLoading = false)

                }
                is Resource.Loading -> {
                    _watchListsState.value = _watchListsState.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _watchListsState.value = _watchListsState.value.copy(data = it.data, isLoading = false, error = "")
                }
            }

        }.launchIn(viewModelScope)
    }

    fun removeMovie(movieId:Int){
        removeMovieFromWatchlistUseCase.removeMovie(movieId = movieId).onEach{it->
            when(it) {
                is Resource.Error->{
                    val errorMsg = it.message ?: "Unknown error"
                    _watchListsState.value = _watchListsState.value.copy(error = errorMsg, isLoading = false)
                }
                is Resource.Loading->{
                    _watchListsState.value = _watchListsState.value.copy(isLoading = true)

                }
                is Resource.Success->{
                    _watchListsState.value = _watchListsState.value.copy(message = "Deleted Successfully", isLoading = false)

                }
            }
        }.launchIn(viewModelScope)
    }


}