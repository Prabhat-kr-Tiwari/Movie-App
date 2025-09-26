package com.prabhat.movieapp.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
import com.prabhat.movieapp.data.mappers.toUpComingMovieResponse
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek
import com.prabhat.movieapp.domain.model.tvByCategories.TvByCategories
import com.prabhat.movieapp.domain.model.upcomingMovie.UpComingMovieResponse
import com.prabhat.movieapp.domain.model.watchList.Movie
import com.prabhat.movieapp.domain.use_case.movie.MovieUseCase
import com.prabhat.movieapp.domain.use_case.watchList.AddMovieToWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.IsMovieInWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.RemoveMovieFromWatchlistUseCase
import com.prabhat.movieapp.mappers.categories.genre.toGenre
import com.prabhat.movieapp.mappers.popularSeries.toPopularSeriesVideo
import com.prabhat.movieapp.mappers.toMovieCredits
import com.prabhat.movieapp.mappers.toUpComingMovieResponse
import com.prabhat.movieapp.mappers.toUpcomingMovieVide
import com.prabhat.movieapp.presentation.screen.categories.GenreListState
import com.prabhat.movieapp.presentation.screen.categories.SelectionOption
import com.prabhat.movieapp.presentation.screen.home.movieDetail.watchList.WatchListState
import com.prabhat.movieapp.presentation.screen.home.movieDetail.movieCredits.MovieCreditsState
import com.prabhat.movieapp.presentation.screen.home.movieDetail.movieVideo.MovieVideoState
import com.prabhat.movieapp.presentation.screen.home.movieDetail.popularSeriesVideoState.PopularSeriesVideoState
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieScreenViewModel @Inject constructor(private val movieUseCase: MovieUseCase,private val pager: Pager<Int, UpComingMovieEntity>,private val addMovieToWatchlistUseCase: AddMovieToWatchlistUseCase,private val isMovieInWatchlistUseCase: IsMovieInWatchlistUseCase,private val removeMovieFromWatchlistUseCase: RemoveMovieFromWatchlistUseCase) :
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
        isMovieInWatchList(movieId = movie.id)


    }
    fun resetSelectedMovie() {
        _selectedMovie.value = null
    }

    private val _movieCreditsState = mutableStateOf(MovieCreditsState())
    val movieCreditsState:State<MovieCreditsState> = _movieCreditsState
 /* private val _movieCreditsState = MutableStateFlow(MovieCreditsState())
    val movieCreditsState:StateFlow<MovieCreditsState> = _movieCreditsState*/

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

    fun resetMovieCredits(){

        _movieCreditsState.value = MovieCreditsState()
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
    fun resetMovieVideoState(){
        _movieVideoState.value = MovieVideoState()
    }
    //popular series
    val popularSeriesPagingData:StateFlow< Flow<PagingData<PopularSeries>>>
        get() = _popularSeriesPagingData
    private val _popularSeriesPagingData = MutableStateFlow<Flow<PagingData<PopularSeries>>>(
        emptyFlow()
    )

    private val _trendingMoviesFlow = MutableStateFlow<Flow<PagingData<TrendingOfWeek>>>(emptyFlow())
    val trendingMoviesFlow: StateFlow<Flow<PagingData<TrendingOfWeek>>>
    get() = _trendingMoviesFlow

    init {
        getPopularSeries()
//        getTrendingOfWeek()
        getGenreList()
        getTvGenreList()
    }

   /* private fun getPopularSeries() {
        viewModelScope.launch {
            movieUseCase().collect { data ->
                val resultFlow = movieUseCase.invoke()
                resultFlow.collectLatest { (trending, popular) ->
                    // Handle both results
                    Log.d("JUHI", "getPopularSeries: VIEEWMODEL"+trending.toString())
                    _trendingMoviesFlow.value = trending
                    _popularSeriesPagingData.value=popular
                }
            }
        }
    }*/
//    @OptIn(FlowPreview::class)
    private fun getPopularSeries() {
       val movieFlow = movieUseCase().debounce(2000)
           .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000), replay = 1)
        viewModelScope.launch {
            viewModelScope.launch {
                movieFlow.collectLatest { (trending, popular) ->
                    Log.d("JUHI", "getPopularSeries: VIEWMODEL $trending")
                    _trendingMoviesFlow.value = trending
                    _popularSeriesPagingData.value = popular
                }
            }
        }
    }

    // Trending movies state
/*    private val _trendingOfWeekPagingData = MutableStateFlow<Flow<PagingData<TrendingOfWeek>>>(
        emptyFlow()
    )
    val trendingOfWeekPagingData: StateFlow<Flow<PagingData<TrendingOfWeek>>> = _trendingOfWeekPagingData*/



   /* private fun getTrendingOfWeek() {
        viewModelScope.launch {
            movieUseCase.getTrendingOfWeek()
                .cachedIn(viewModelScope) // Caches paging data
                .collect { pagingData ->
                    Log.d("TRENDINGWAX", "getTrendingOfWeek: "+pagingData)
                    _trendingOfWeekPagingData.value = pagingData
                }
        }
    }*/


   /* private fun getTrendingOfWeek() {
        viewModelScope.launch {
            movieUseCase.getTrendingOfWeek()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _trendingMoviesFlow.value = pagingData // ✅ Correct assignment
                }
        }
    }*/



    private val _seriesCreditsState = mutableStateOf(MovieCreditsState())
    val seriesCreditsState:State<MovieCreditsState> = _seriesCreditsState

    private fun getSeriesCredits(seriesId:Int, language:String){
        movieUseCase.getSeriesCredits(seriesId =seriesId, language = language)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    Log.d("JOHN", "getSeriesCredits: loading")
                    _seriesCreditsState.value=_seriesCreditsState.value.copy(isLoading = true)
                }
                is Resource.Success->{
                    val seriesCredits=it.data?.toMovieCredits()
                    Log.d("JOHN", "getSeriesCredits:  upcomingMovies"+seriesCredits)

                    _seriesCreditsState.value=_seriesCreditsState.value.copy(isLoading = false,data = seriesCredits!!)


                }
                is Resource.Error->{
                    _seriesCreditsState.value= it.message?.let { it1 -> _seriesCreditsState.value.copy(error = it1) }!!
                    Log.d("JOHN", "getSeriesCredits: "+it.message.toString())
                }

            }

        }.launchIn(viewModelScope)


    }
    fun resetSeriesCredits(){
        _seriesCreditsState.value = MovieCreditsState()
    }

    private val _selectedSeries:MutableState<PopularSeries?> = mutableStateOf<PopularSeries?>(null)
    val selectedSeries: MutableState<PopularSeries?>  = _selectedSeries

    fun onSeriesSelected(series: PopularSeries) {

        _selectedSeries.value = series
        Log.d("JOHN", "onMovieSelected: "+series)
      /*  getMovieCredits(movieId = movie.id, language = "en-US")
        getMovieVideo(movieId = movie.id)*/
        //same as series so using the movie
        getSeriesCredits(seriesId = series.id, language = "en-US")
        getPopularSeriesVideo(seriesId = series.id)
        isMovieInWatchList(movieId = series.id)

    }
    fun resetSelectedSeries() {
        _selectedSeries.value = null
    }

    //trending
    private val _selectedTrendingOfWeek:MutableState<TrendingOfWeek?> = mutableStateOf<TrendingOfWeek?>(null)
    val selectedTrendingOfWeek: MutableState<TrendingOfWeek?>  = _selectedTrendingOfWeek

    fun onTrendingOfWeekSelected(trendingOfWeek: TrendingOfWeek) {

        _selectedTrendingOfWeek.value = trendingOfWeek
        //get the mediatype
        val mediaType=trendingOfWeek.mediaType
        if (mediaType.equals("movie")){
            trendingOfWeek.id?.let { getMovieVideo(it) }
            trendingOfWeek.let { it.id?.let { it1 -> getMovieCredits(it1, language = "en-US") } }
        }
        if (mediaType.equals("tv")){
            trendingOfWeek.id?.let { getSeriesCredits(seriesId = it, language = "en-US") }
            trendingOfWeek.id?.let { getPopularSeriesVideo(seriesId = it) }
        }
        isMovieInWatchList(movieId = trendingOfWeek.id!!)

    }
    fun resetSelectedTrendingOfWeek() {
        _selectedTrendingOfWeek.value = null
    }


    //popular series videos
    private val _popularSeriesVideoState= mutableStateOf(PopularSeriesVideoState())
    val popularSeriesVideoState:State<PopularSeriesVideoState> = _popularSeriesVideoState
    /*private val _popularSeriesVideoState= MutableStateFlow(PopularSeriesVideoState())
    val popularSeriesVideoState:StateFlow<PopularSeriesVideoState> = _popularSeriesVideoState*/

    private fun getPopularSeriesVideo(seriesId:Int){
        movieUseCase.getPopularSeriesVideos(seriesId = seriesId)     .onEach { it ->
            when(it){
                is Resource.Loading->{
                    Log.d("JOHN", "getMovieCredits: loading")
                    _popularSeriesVideoState.value=_popularSeriesVideoState.value.copy(isLoading = true)
                }
                is Resource.Success->{
                    val popularSeriesVideos=it.data?.toPopularSeriesVideo()
                    Log.d("JOHN", "getMovieCredits:  upcomingMovies"+popularSeriesVideos)

                    _popularSeriesVideoState.value=_popularSeriesVideoState.value.copy(isLoading = false,data = popularSeriesVideos!!)


                }
                is Resource.Error->{
                    _popularSeriesVideoState.value= it.message?.let { it1 -> _popularSeriesVideoState.value.copy(error = it1) }!!
                    Log.d("JOHN", "getMovieCredits: "+it.message.toString())
                }

            }

        }.launchIn(viewModelScope)


    }
    fun resetPopularSeriesVideoState(){
        _popularSeriesVideoState.value = PopularSeriesVideoState()
    }

    fun cleanupDetailScreen() {
        resetSelectedMovie()
        resetSelectedSeries()
        resetSelectedTrendingOfWeek()
        resetSeriesCredits()
        resetMovieCredits()
        resetMovieVideoState()
        resetPopularSeriesVideoState()
        resetSelectedMovieByCategories()
        resetSelectedTvByCategories()
    }

    //movie categories
 /*   private val _options = listOf(
        SelectionOption("Newest", false),
        SelectionOption("Oldest", false),
        SelectionOption("Top", false),
        SelectionOption("Order", false),
    ).toMutableStateList()*/
//    private val _options: MutableState<SelectionOption?> = mutableStateOf(null)
    private val _options: MutableState<SnapshotStateList<SelectionOption>> = mutableStateOf(SnapshotStateList<SelectionOption>())
    
    val options: MutableState<SnapshotStateList<SelectionOption>>
        get() = _options

   /* fun selectionOptionSelected(selectedOption: SelectionOption) {
        *//*_options.forEach { it.selected = false }
        _options.find { it == selectedOption.option }?.selected = true*//*
        val list = _options.value
        list.forEach { it.selected=false }
        _options.value.forEach { it.selected=false }
        _options.value.find {
            it==selectedOption
        }?.selected = true

    }*/
    fun selectionOptionSelected(selectedOption: SelectionOption) {
        _options.value.forEach { it.selected = false }

        _options.value.find { it == selectedOption }?.let {
            it.selected = true
            onGenreSelected(it.genreId.toString()) // Pass the correct genreId
        }
    }



    //get the genrelist
    private val _genreListState= mutableStateOf(GenreListState())
    val genreListState:State<GenreListState> = _genreListState
    private fun getGenreList() {
        Log.d("RAJA", "getGenreList: called VIEWMODEL")

        val genreFlow = movieUseCase.getGenreList()
            .debounce(1000)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed(1000), replay = 1)  // This makes the flow hot

        viewModelScope.launch {
            genreFlow.collectLatest { result ->  // Actively collect here!
                when (result) {
                    is Resource.Loading -> {
                        Log.d("ALEXA", "getGenreList: loading")
                        _genreListState.value = _genreListState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val genreList = result.data?.toGenre()
                        Log.d("ALEXA", "getGenreList:  upcomingMovies" + genreList)
                        val mappedList = genreList?.map { genre ->
                            SelectionOption(genreId = genre.genreId, genreName = genre.genreName, initialSelectedValue = false)
                        }?.toMutableStateList()
                        _options.value.clear()
                        if (mappedList != null) {
                            _options.value.addAll(mappedList)
                        }

                        _genreListState.value = _genreListState.value.copy(data = genreList!!)
                        Log.d("ALEXA", "getGenreList: MovieScreenViewModel $genreList")
                    }
                    is Resource.Error -> {
                        _genreListState.value = result.message?.let { it1 ->
                            _genreListState.value.copy(error = it1)
                        }!!
                        Log.d("ALEXA", "getGenreList: " + result.message.toString())
                    }
                }
            }
        }
    }
    private val _moviesByCategories: MutableStateFlow<PagingData<MovieByCategories>> = MutableStateFlow(value = PagingData.empty())
    val moviesByCategories: MutableStateFlow<PagingData<MovieByCategories>> get() = _moviesByCategories
    private suspend fun getMovieByCategories(withGenre:String) {

        val movieByCategoriesFlow =
        movieUseCase.getMovieByCategories(withGenre =withGenre).cachedIn(viewModelScope)
            .distinctUntilChanged().debounce(1000)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000), replay = 1)
        viewModelScope.launch {
            movieByCategoriesFlow.collectLatest {
                _moviesByCategories.value = it
            }
        }
    }
    private fun onGenreSelected(genreId: String) {
        viewModelScope.launch {
            getMovieByCategories(genreId)
        }
    }



    //slect movie by categories
    private val _selectedMovieByCategories:MutableState<MovieByCategories?> = mutableStateOf<MovieByCategories?>(null)
    val selectedMovieByCategories: MutableState<MovieByCategories?>  = _selectedMovieByCategories

    fun onMovieByCategoriesSelected(movieByCategories: MovieByCategories) {

        _selectedMovieByCategories.value = movieByCategories
        Log.d("JOHN", "onMovieSelected: "+movieByCategories)
        /*  getMovieCredits(movieId = movie.id, language = "en-US")
          getMovieVideo(movieId = movie.id)*/
        //same as series so using the movie
        getMovieCredits(movieId = movieByCategories.id, language = "en-US")
        getMovieVideo(movieId = movieByCategories.id)
        isMovieInWatchList(movieId = movieByCategories.id)

    }
    fun resetSelectedMovieByCategories() {
        _selectedMovieByCategories.value = null
    }

    //tv
    private val _tvGenreListState= mutableStateOf(GenreListState())
    val tvGenreListState:State<GenreListState> = _tvGenreListState
    private fun getTvGenreList() {
        val genreFlow = movieUseCase.getTvGenreList()
            .debounce(1000)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed(1000), replay = 1)  // This makes the flow hot

        viewModelScope.launch {
            genreFlow.collectLatest { result ->  // Actively collect here!
                when (result) {
                    is Resource.Loading -> {
                        _tvGenreListState.value = _tvGenreListState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val genreList = result.data?.toGenre()
                        val mappedList = genreList?.map { genre ->
                            SelectionOption(genreId = genre.genreId, genreName = genre.genreName, initialSelectedValue = false)
                        }?.toMutableStateList()
                        _tvOptions.value.clear()
                        if (mappedList != null) {
                            _tvOptions.value.addAll(mappedList)
                        }

                        _tvGenreListState.value = _tvGenreListState.value.copy(data = genreList!!)
                    }
                    is Resource.Error -> {
                        _tvGenreListState.value = result.message?.let { it1 ->
                            _tvGenreListState.value.copy(error = it1)
                        }!!
                    }
                }
            }
        }
    }
    private val _tvOptions: MutableState<SnapshotStateList<SelectionOption>> = mutableStateOf(SnapshotStateList<SelectionOption>())

    val tvOptions: MutableState<SnapshotStateList<SelectionOption>>
        get() = _tvOptions

    fun tvSelectionOptionSelected(selectedOption: SelectionOption) {
        _tvOptions.value.forEach { it.selected = false }

        _tvOptions.value.find { it == selectedOption }?.let {
            it.selected = true
            onTvGenreSelected(it.genreId.toString()) // Pass the correct genreId
        }
    }
    private val _tvByCategories: MutableStateFlow<PagingData<TvByCategories>> = MutableStateFlow(value = PagingData.empty())
    val tvByCategories: MutableStateFlow<PagingData<TvByCategories>> get() = _tvByCategories
    private suspend fun getTvByCategories(withGenre:String) {

        val tvByCategoriesFlow =
            movieUseCase.getTveByCategories(withGenre =withGenre).cachedIn(viewModelScope)
                .distinctUntilChanged().debounce(1000)
                .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000), replay = 1)
        viewModelScope.launch {
            tvByCategoriesFlow.collectLatest {
                _tvByCategories.value = it
            }
        }
    }
    private fun onTvGenreSelected(genreId: String) {
        viewModelScope.launch {
            getTvByCategories(genreId)
        }
    }

    private val _selectedTvByCategories:MutableState<TvByCategories?> = mutableStateOf<TvByCategories?>(null)
    val selectedTvByCategories: MutableState<TvByCategories?>  = _selectedTvByCategories

    fun onTvByCategoriesSelected(tvByCategories: TvByCategories) {

        _selectedTvByCategories.value = tvByCategories
        Log.d("JOHN", "onMovieSelected: "+tvByCategories)
        /*  getMovieCredits(movieId = movie.id, language = "en-US")
          getMovieVideo(movieId = movie.id)*/
        //same as series so using the movie
        getSeriesCredits(seriesId = tvByCategories.id, language = "en-US")
        getPopularSeriesVideo(seriesId = tvByCategories.id)
        isMovieInWatchList(movieId = tvByCategories.id)

    }
    fun resetSelectedTvByCategories() {
        _selectedTvByCategories.value = null
    }
    private val _watchListsState = mutableStateOf(WatchListState())
    val watchlistState: State<WatchListState> = _watchListsState

    fun isMovieInWatchList(movieId:Int){
        Log.d("AJITHAL", "isMovieInWatchList: $movieId")
        isMovieInWatchlistUseCase.isMovieInWatchList(movieId = movieId)
            .onEach {it->
                when(it) {
                    is Resource.Error->{
                        Log.d("AJITHAL", "isMovieInWatchList: Error")

                        it.message?.let { it1 -> _watchListsState.value.copy(error = it1) }!!

                    }
                    is Resource.Loading->{
                        Log.d("AJITHAL", "isMovieInWatchList: Loading")

                        _watchListsState.value = _watchListsState.value.copy(isLoading = true)

                    }
                    is Resource.Success->{
                        Log.d("AJITHAL", "isMovieInWatchList: Success")
                        Log.d("AJITHAL", "isMovieInWatchList: ${it.data}")
                        _watchListsState.value = _watchListsState.value.copy(isInWatchlist = it.data?:false)
                        Log.d("AJITHAL", "isMovieInWatchList: ${watchlistState.value.isInWatchlist}")

                    }
                }

            }
            .launchIn(viewModelScope)

    }

    fun addMovieToWatchlist(movie: Movie) {
        addMovieToWatchlistUseCase.addMovieToWatchList(movie = movie).onEach { it ->
            when (it) {
                is Resource.Error -> {
                    _watchListsState.value =
                        it.message?.let { it1 -> _watchListsState.value.copy(error = it1) }!!

                }

                is Resource.Loading -> {
                    _watchListsState.value = _watchListsState.value.copy(isLoading = true)

                }

                is Resource.Success -> {
                    Log.d("PRABHAT", "addMovieToWatchlist: Success")
                    _watchListsState.value = _watchListsState.value.copy(message = "Added Successfully")

                }
            }

        }.launchIn(viewModelScope)
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

    override fun onCleared() {
        super.onCleared()
        Log.d("PRABHU", "VIEWMODEL onCleared: ")
    }
    

}