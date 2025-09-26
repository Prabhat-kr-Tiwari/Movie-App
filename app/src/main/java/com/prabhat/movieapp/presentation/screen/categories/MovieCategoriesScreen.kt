package com.prabhat.movieapp.presentation.screen.categories

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prabhat.movieapp.R
import com.prabhat.movieapp.data.model.categories.GenreResponseDto
import com.prabhat.movieapp.data.model.categories.movieByCategories.MovieByCategoriesResponseDto
import com.prabhat.movieapp.data.model.categories.tvByCategories.TvByCategoriesResponseDto
import com.prabhat.movieapp.data.model.movie.popular.PopularSeriesDTO
import com.prabhat.movieapp.data.model.movie.popular.videos.PopularSeriesVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.trending.TrendingOfWeekResponseDto
import com.prabhat.movieapp.data.model.movie.upcoming.Dates
import com.prabhat.movieapp.data.model.movie.upcoming.Result
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.Cast
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.data.model.movie.upcoming.credits.Crew
import com.prabhat.movieapp.data.network.movie.MovieApiService
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.domain.model.tvByCategories.TvByCategories
import com.prabhat.movieapp.domain.use_case.movie.MovieUseCase
import com.prabhat.movieapp.domain.use_case.watchList.AddMovieToWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.IsMovieInWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.RemoveMovieFromWatchlistUseCase
import com.prabhat.movieapp.mappers.popularSeries.PopularSeriesDTOToPopularSeriesMapper
import com.prabhat.movieapp.mappers.trending.TrendingOfWeekResponseDtoToTrendingOfWeekMapper
import com.prabhat.movieapp.navigation.HomeDestination
import com.prabhat.movieapp.presentation.screen.home.FakePagingSource
import com.prabhat.movieapp.presentation.screen.home.MockMovieRepository
import com.prabhat.movieapp.presentation.screen.home.MovieScreenViewModel
import com.prabhat.movieapp.presentation.screen.home.movieDetail.MockWatchListRepository
import com.prabhat.movieapp.presentation.screen.introScreen.HorizontalPagerContent
import com.prabhat.movieapp.ui.theme.MovieAppTheme


@Composable
fun MovieCategoriesScreen(
    movieScreenViewModel: MovieScreenViewModel,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val genrelist = movieScreenViewModel.genreListState.value
    val movieByCategoriesPager=movieScreenViewModel.moviesByCategories.collectAsLazyPagingItems()

    val onTvSelected = remember { mutableStateOf(false) }
    val onMovieByCategoriesSelected = remember { mutableStateOf(false) }

    //tv
    val tvGenreList = movieScreenViewModel.tvGenreListState.value
    val tvByCategoriesPager = movieScreenViewModel.tvByCategories.collectAsLazyPagingItems()
    LaunchedEffect(onTvSelected.value) {
        if (onTvSelected.value){
            val selectedTvByCategories = movieScreenViewModel.selectedTvByCategories.value
            if (selectedTvByCategories!=null){
                navHostController.navigate(HomeDestination.MovieLoadingScreen)
                onTvSelected.value=false
            }
        }
    }
    LaunchedEffect(onMovieByCategoriesSelected.value) {
        if (onMovieByCategoriesSelected.value){
            val movieByCategories = movieScreenViewModel.moviesByCategories.value
            if (movieByCategories!=null){
                navHostController.navigate(HomeDestination.MovieLoadingScreen)
                onMovieByCategoriesSelected.value=false
            }else{

            }
        }
    }



    Column( modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)) {

        val tabItem = listOf(
            TabItem(title = "Movies", selectedIcon = Icons.Outlined.Home, unSelectedIcon = Icons.Outlined.Home),
            TabItem(title = "Series", selectedIcon = Icons.Outlined.Home, unSelectedIcon = Icons.Outlined.Home)
        )


        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }
        val pagerState   = rememberPagerState {
            tabItem.size
        }
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(pagerState.currentPage,pagerState.isScrollInProgress) {
            if(!pagerState.isScrollInProgress){
                selectedTabIndex = pagerState.currentPage
            }
        }
        val moviesCategories = listOf("New","oldest","Top","order")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            TabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier,
                containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.onBackground,
                { tabPositions ->
                    SecondaryIndicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color.Red // Change the indicator color here
                    )
                }
            ) {
                tabItem.forEachIndexed { index, item ->
                    Tab(

                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(text = item.title,color = MaterialTheme.colorScheme.onBackground)
                        },
                        icon = {
                            /* Icon(
                                 imageVector = if (index == selectedTabIndex) {
                                     item.selectedIcon
                                 } else item.unselectedIcon,
                                 contentDescription = item.title
                             )*/
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,

            ) { index ->


                when (index) {
                    0 -> {
                        Column(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                            SingleSelectionList (
                                movieScreenViewModel.options,
                                onOptionClicked = movieScreenViewModel::selectionOptionSelected
                            )

                            LazyColumn (
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surface),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                MovieByCategories(innerPadding = PaddingValues(0.dp), movieByCategories = movieByCategoriesPager, movieScreenViewModel = movieScreenViewModel){
                                    onMovieByCategoriesSelected.value = true
                                }
                            }
                        }
                    }

                    1 -> {


                        Column(
                            modifier = Modifier
                                .background(Color.Red)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Log.d("MovieCategoriesScreen", "Series Tab - tvOptions size: ${movieScreenViewModel.tvOptions.value.size}")
                            if (movieScreenViewModel.tvOptions.value.isNotEmpty()) {
                                Log.d("MovieCategoriesScreen", "Series Tab - First tvOption: ${movieScreenViewModel.tvOptions.value.first().genreName}")
                            }
                            SingleSelectionList (
                                movieScreenViewModel.tvOptions,
                                onOptionClicked = movieScreenViewModel::tvSelectionOptionSelected
                            )

                            LazyColumn (
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surface),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                TvByCategories(innerPadding = PaddingValues(0.dp), tvByCategories = tvByCategoriesPager, movieScreenViewModel = movieScreenViewModel){
                                    onTvSelected.value = true
                                }
                            }
                        }

                    }

                    else->{
                        Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

                            Text(text = "Something went wrong",color =  MaterialTheme.colorScheme.onBackground)
                        }
                    }
                }
            }
        }





    }

}

class SelectionOption(val genreName: String,val genreId:Int, var initialSelectedValue: Boolean) {
    var selected by mutableStateOf(initialSelectedValue)
}

@Composable
fun SingleSelectionList(options:MutableState<SnapshotStateList<SelectionOption>>, onOptionClicked: (SelectionOption) -> Unit) {
    LazyRow(Modifier.background(MaterialTheme.colorScheme.surface))  {
        items(options.value) { option -> SingleSelectionCard(option, onOptionClicked) }
    }
}
@Composable
fun SingleSelectionCard(selectionOption: SelectionOption, onOptionClicked: (SelectionOption) -> Unit) {

    Row(modifier = Modifier.fillMaxWidth()) {
        ElevatedButton(
            onClick = {
                onOptionClicked(selectionOption)


            },
            modifier = Modifier.padding(10.dp),
//            enabled = false,
            colors =if(selectionOption.selected){
                ButtonDefaults.buttonColors(contentColor =  Color.White, containerColor =Color.Red, disabledContainerColor = Color.Black, disabledContentColor = Color.White)


            }else{
                ButtonDefaults.buttonColors(contentColor =  MaterialTheme.colorScheme.onBackground, containerColor = MaterialTheme.colorScheme.surface, disabledContainerColor = Color.Black, disabledContentColor = Color.White)

            }
            , elevation =
            ButtonDefaults.elevatedButtonElevation(defaultElevation = 1.dp, pressedElevation = 4.dp, focusedElevation = 6.dp, hoveredElevation = 4.dp, disabledElevation = 1.dp)
            , border = BorderStroke(width = 2.dp,
                Brush.linearGradient(
                    colors = listOf(Color.White,Color.LightGray),

                    start = Offset.Zero, end = Offset.Zero, tileMode = TileMode.Mirror))
            , contentPadding = PaddingValues(start = 10.dp, end = 10.dp)
            /*   enabled = ,
               shape = TODO(),
               colors = TODO(),
               elevation = TODO(),
               border = TODO(),
               contentPadding = TODO(),
               interactionSource = TODO()*/
        ) {
//            Text(text = selectionOption.genreId.toString(),Modifier.padding(10.dp))
            Text(text = selectionOption.genreName.toString(),Modifier.padding(10.dp))
        }
    }
}


@Composable
@ThemeAnnotation
fun PreviewMovieCategoriesScreen(modifier: Modifier = Modifier) {

    val mockMovieApiService: MovieApiService = FakeMovieApiService()
    val mockMovieUseCase = MovieUseCase(MockMovieRepository(movieApiService = mockMovieApiService, mapper = fake, trendingOfWeekMapper = trendingOfWeekMapper))

    val mockaddMovieToWatchlistUseCase = AddMovieToWatchlistUseCase(MockWatchListRepository())
    val mockisMovieInWatchlistUseCase = IsMovieInWatchlistUseCase(MockWatchListRepository())
    val mockremoveMovieFromWatchlistUseCase = RemoveMovieFromWatchlistUseCase(MockWatchListRepository())
    // Mock Pager
    val mockPager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { FakePagingSource() } // Implement a fake PagingSource
    )

    // Mock ViewModel
    val mockViewModel = MovieScreenViewModel(
        movieUseCase = mockMovieUseCase,
        pager = mockPager,
        addMovieToWatchlistUseCase = mockaddMovieToWatchlistUseCase,
        isMovieInWatchlistUseCase = mockisMovieInWatchlistUseCase,
        removeMovieFromWatchlistUseCase = mockremoveMovieFromWatchlistUseCase
    )
    val navController = rememberNavController()
    MovieAppTheme{

        MovieCategoriesScreen(mockViewModel, navHostController = navController)
    }
}
class FakeMovieApiService : MovieApiService{
    override suspend fun getUpComingMovie(
        apiKey: String,
        page: Int,
        language: String
    ): UpComingMovieResponseDTO {
        return (
                UpComingMovieResponseDTO(
                    dates = Dates(
                        maximum = "2025-02-28",
                        minimum = "2025-01-01"
                    ),
                    page = 1,
                    results = listOf(
                        Result(
                            adult = false,
                            backdrop_path = "/mock_backdrop1.jpg",
                            genre_ids = listOf(28, 12, 16),
                            id = 101,
                            original_language = "en",
                            original_title = "Mock Movie 1",
                            overview = "This is the overview of Mock Movie 1. It's an exciting action-adventure movie.",
                            popularity = 1234.5,
                            poster_path = "/5qGIxdEO841C0tdY8vOdLoRVrr0.jpg",
                            release_date = "2025-01-15",
                            title = "Mock Movie 1",
                            video = false,
                            vote_average = 7.8,
                            vote_count = 256
                        )

                    ),
                    total_pages = 1,
                    total_results = 2
                ))
    }

    override suspend fun getMovieCredits(
        movieId: Int,
        apiKey: String,
        language: String
    ): CreditsResponseDto {
        return CreditsResponseDto(
            id = 1, crew =

            listOf(
                Crew(
                    adult = false,
                    gender = 0,
                    id = 3068872,
                    known_for_department = "Directing",
                    name = "Derek Presley",
                    original_name = "Derek Presley",
                    popularity = 0.503,
                    profile_path = "null",
                    credit_id = "6087656533a533002a02051c",
                    department = "Directing",
                    job = "Director"
                )
            ),


            cast =
            listOf(
                Cast(
                    adult = false,
                    gender = 2,
                    id = 2203,
                    known_for_department = "Acting",
                    name = "Neal McDonough",
                    original_name = "Neal McDonough",
                    popularity = 33.364,
                    profile_path = "/3mI3i1CpjATSCta1Tb2qsyl1KCh.jpg",
                    cast_id = 12,
                    character = "Boon",
                    credit_id = "608766f0bc2cb30040de4b0f",
                    order = 0
                )
            )
        )
    }

    override suspend fun getMovieVideos(
        movieId: Int,
        apiKey: String
    ): UpComingMovieVideoResponseDTO {
        return UpComingMovieVideoResponseDTO(
            id = 604685, results = listOf(
                com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.Result(
                    iso_639_1 = "en",
                    iso_3166_1 = "US",
                    name = "Porsche Chase",
                    key = "iPIHckDjN7A",
                    site = "YouTube",
                    size = 2160,
                    type = "Clip",
                    official = true,
                    published_at = "2025-01-23T13:00:56.000Z",
                    id = "67a208a46b9f666a9903075a"
                )
            )
        )
    }

    override suspend fun getPopularSeries(
        apiKey: String,
        page: Int,
        language: String
    ): PopularSeriesDTO {
        return PopularSeriesDTO(
            page = 1,
            results = listOf()
            , total_results = 2,
            total_pages = 4
        )
    }

    override suspend fun getSeriesCredits(
        seriesId: Int,
        apiKey: String,
        language: String
    ): CreditsResponseDto {
        return CreditsResponseDto(
            id = 1, crew =

            listOf(
                Crew(
                    adult = false,
                    gender = 0,
                    id = 3068872,
                    known_for_department = "Directing",
                    name = "Derek Presley",
                    original_name = "Derek Presley",
                    popularity = 0.503,
                    profile_path = "null",
                    credit_id = "6087656533a533002a02051c",
                    department = "Directing",
                    job = "Director"
                )
            ),


            cast =
            listOf(
                com.prabhat.movieapp.data.model.movie.upcoming.credits.Cast(
                    adult = false,
                    gender = 2,
                    id = 2203,
                    known_for_department = "Acting",
                    name = "Neal McDonough",
                    original_name = "Neal McDonough",
                    popularity = 33.364,
                    profile_path = "/3mI3i1CpjATSCta1Tb2qsyl1KCh.jpg",
                    cast_id = 12,
                    character = "Boon",
                    credit_id = "608766f0bc2cb30040de4b0f",
                    order = 0
                )
            )
        )
    }

    override suspend fun getSeriesVideos(
        seriesId: Int,
        apiKey: String
    ): PopularSeriesVideoResponseDTO {
        return PopularSeriesVideoResponseDTO(
            id = 604685, results = listOf(
                com.prabhat.movieapp.data.model.movie.popular.videos.Result(
                    iso_639_1 = "en",
                    iso_3166_1 = "US",
                    name = "Porsche Chase",
                    key = "iPIHckDjN7A",
                    site = "YouTube",
                    size = 2160,
                    type = "Clip",
                    official = true,
                    published_at = "2025-01-23T13:00:56.000Z",
                    id = "67a208a46b9f666a9903075a"
                )
            )
        )
    }

    override suspend fun getTrendingOfWeek(
        apiKey: String,
        page: Int,
        language: String
    ): TrendingOfWeekResponseDto {
        return TrendingOfWeekResponseDto(page = 2, results =
        listOf(
            com.prabhat.movieapp.data.model.movie.trending.Result(

                backdrop_path= "/h7r6LZ32dgLwtwSW3CxoWIYD9pr.jpg",
                id= 426063,
                title= "Nosferatu",
                original_title= "Nosferatu",
                overview="A gothic tale of obsession between a haunted young woman and the terrifying vampire infatuated with her, causing untold horror in its wake.",
                poster_path= "/5qGIxdEO841C0tdY8vOdLoRVrr0.jpg",
                media_type= "movie",
                adult= false,
                original_language= "en",
                genre_ids= listOf(
                    14,
                    27
                ) ,
                popularity= 180.338,
                release_date= "2024-12-25",
                video= false,
                vote_average= 6.7,
                vote_count= 2218, first_air_date = "12", name = "hell", origin_country = listOf("us")
                , original_name = "dfs"

            )
        )

            , total_pages = 3, total_results = 4)
    }

    override suspend fun getGenreList(apiKey: String): GenreResponseDto {
        return GenreResponseDto(genres = listOf())
    }

    override suspend fun getMovieByCategories(
        apiKey: String,
        withGenres: String,
        page: Int
    ): MovieByCategoriesResponseDto {
        return MovieByCategoriesResponseDto(page = 1, results = listOf(), total_results = 2, total_pages = 2)
    }

    override suspend fun getTvGenreList(apiKey: String): GenreResponseDto {
        return  GenreResponseDto(genres = listOf())
    }

    override suspend fun getTvByCategories(
        apiKey: String,
        withGenres: String,
        page: Int
    ): TvByCategoriesResponseDto {
        return TvByCategoriesResponseDto(page = 1, results = listOf(), total_results = 2, total_pages = 2)
    }

}
val fake= PopularSeriesDTOToPopularSeriesMapper()
val trendingOfWeekMapper = TrendingOfWeekResponseDtoToTrendingOfWeekMapper()
data class TabItem(
    val title:String,
    val selectedIcon:ImageVector,
    val unSelectedIcon:ImageVector,
)

fun LazyListScope.MovieByCategories(
    innerPadding: PaddingValues,
    movieByCategories:
    LazyPagingItems<MovieByCategories>,
    movieScreenViewModel: MovieScreenViewModel,
    onClick:()->Unit

) {


    items(1) { index ->
        ThreeItem(

            content = movieByCategories, innerPadding = innerPadding, movieScreenViewModel = movieScreenViewModel,onClick=onClick
        )
    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThreeItem(
    modifier: Modifier = Modifier,
    content: LazyPagingItems<MovieByCategories>,
    innerPadding: PaddingValues, movieScreenViewModel: MovieScreenViewModel,
    onClick:()->Unit
) {

    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingValues = innerPadding)
            .fillMaxWidth()
    ) {
        // Card content


            if(content.loadState.refresh is LoadState.NotLoading){
                if (content.itemCount!=0){



                            LazyVerticalGrid (
                                columns = GridCells.Fixed(3), // 3 items per row
                                modifier = Modifier.height(200.dp * 4), // Adjust height as needed
//
                            ) {
                                items(count = content.itemCount) { index ->
                                    val item = content[index]
                                    if (item != null) {
                                        key(item.id) {
                                            // ... your AsyncImage composable
                                            AsyncImage(
                                                model = ImageRequest.Builder(context = LocalContext.current)
                                                    .data(item.imageUrl)
                                                    .crossfade(true)
                                                    .build(),
                                                contentDescription = stringResource(R.string.app_name),
                                                contentScale = ContentScale.FillBounds,
                                                modifier = Modifier
                                                    .background(MaterialTheme.colorScheme.surface)
                                                    .padding(8.dp)
                                                    .clip(RectangleShape)
                                                    .width(110.dp)
                                                    .height(200.dp)
                                                    .clickable {

                                                        movieScreenViewModel.onMovieByCategoriesSelected(
                                                            item
                                                        )
                                                        onClick.invoke()

                                                    },
                                            )
                                        }
                                    }
                                }
                            }


                }
            }
            else if (content.loadState.refresh is LoadState.Loading){
                Box(Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .align(Alignment.CenterHorizontally)){

                    CircularProgressIndicator(modifier
                        .align(Alignment.Center)
                        .padding(16.dp))
                }
            }
           else if (content.loadState.refresh is LoadState.Loading){
                if (content.itemCount==0){
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = "Nothing found", color =MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center)
                    }
                }
            }
           else if (content.loadState.prepend is LoadState.Error){
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Button(modifier = Modifier.fillMaxWidth(), onClick = {
                        content.retry()
                    }) {
                        Text("Retry",color =MaterialTheme.colorScheme.onBackground)
                    }
                }

            }



//        }
    }
}


//
fun LazyListScope.TvByCategories(
    innerPadding: PaddingValues,
    tvByCategories: LazyPagingItems<TvByCategories>,
    movieScreenViewModel: MovieScreenViewModel,
    onClick:()->Unit

) {


    items(1) { index ->
        ThreeItemForTv(

            content = tvByCategories, innerPadding = innerPadding, movieScreenViewModel = movieScreenViewModel,onClick=onClick
        )
    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThreeItemForTv(
    modifier: Modifier = Modifier,
    content: LazyPagingItems<TvByCategories>,
    innerPadding: PaddingValues, movieScreenViewModel: MovieScreenViewModel,
    onClick:()->Unit
) {

    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingValues = innerPadding)
            .fillMaxWidth()
    ) {
        // Card content


        if(content.loadState.refresh is LoadState.NotLoading){
            if (content.itemCount!=0){



                LazyVerticalGrid (
                    columns = GridCells.Fixed(3), // 3 items per row
                    modifier = Modifier.height(200.dp * 4), // Adjust height as needed
//
                ) {
                    items(count = content.itemCount) { index ->
                        val item = content[index]
                        if (item != null) {
                            key(item.id) {
                                // ... your AsyncImage composable
                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .data(item.imageUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = stringResource(R.string.app_name),
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(8.dp)
                                        .clip(RectangleShape)
                                        .width(110.dp)
                                        .height(200.dp)
                                        .clickable {

                                            movieScreenViewModel.onTvByCategoriesSelected(item)
                                            onClick.invoke()

                                        },
                                )
                            }
                        }
                    }
                }


            }
        }
        else if (content.loadState.refresh is LoadState.Loading){
            Box(Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .align(Alignment.CenterHorizontally)){

                CircularProgressIndicator(modifier
                    .align(Alignment.Center)
                    .padding(16.dp))
            }
        }
        else if (content.loadState.refresh is LoadState.Loading){
            if (content.itemCount==0){
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Nothing found", color =MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center)
                }
            }
        }
        else if (content.loadState.prepend is LoadState.Error){
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    content.retry()
                }) {
                    Text("Retry",color =MaterialTheme.colorScheme.onBackground)
                }
            }

        }



//        }
    }
}
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES


)
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO

)
annotation class ThemeAnnotation