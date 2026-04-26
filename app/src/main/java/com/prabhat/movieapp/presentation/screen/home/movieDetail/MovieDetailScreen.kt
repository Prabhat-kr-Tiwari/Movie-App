package com.prabhat.movieapp.presentation.screen.home.movieDetail


import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.R
import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
import com.prabhat.movieapp.data.model.categories.GenreResponseDto
import com.prabhat.movieapp.data.model.movie.popular.details.LastEpisodeToAir
import com.prabhat.movieapp.data.model.movie.popular.details.NextEpisodeToAir
import com.prabhat.movieapp.data.model.movie.popular.details.PopularSeriesDetailResponseDTO
import com.prabhat.movieapp.data.model.movie.popular.details.SpokenLanguage
import com.prabhat.movieapp.data.model.movie.popular.videos.PopularSeriesVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.trending.details.TvDetailResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.Dates
import com.prabhat.movieapp.data.model.movie.upcoming.Result
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.data.model.movie.upcoming.credits.Crew
import com.prabhat.movieapp.data.model.movie.upcoming.details.UpComingMovieDetailResponseDTO
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek
import com.prabhat.movieapp.domain.model.tvByCategories.TvByCategories
import com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.Cast
import com.prabhat.movieapp.domain.model.watchList.Movie
import com.prabhat.movieapp.domain.repository.WatchlistRepository
import com.prabhat.movieapp.domain.repository.movie.MovieRepository
import com.prabhat.movieapp.domain.use_case.movie.MovieUseCase
import com.prabhat.movieapp.domain.use_case.watchList.AddMovieToWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.IsMovieInWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.RemoveMovieFromWatchlistUseCase

import com.prabhat.movieapp.presentation.screen.home.FakePagingSource

import com.prabhat.movieapp.presentation.screen.home.MovieCategory
import com.prabhat.movieapp.presentation.screen.home.MovieScreenViewModel
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf




@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier.padding(bottom = 20.dp),
    systemUiController: SystemUiController,
    statusBarColor: Color,
    innerPadding: PaddingValues,
    navHostController: NavHostController,
    movieDetailScreenViewModel: MovieDetailScreenViewModel = hiltViewModel(),
    movieId: Int,
    movieCategory: MovieCategory
) {

    val state = movieDetailScreenViewModel.uiState.collectAsStateWithLifecycle()
    val watchListState = movieDetailScreenViewModel.watchlistState.collectAsStateWithLifecycle()
    val castCrewState = movieDetailScreenViewModel.movieCreditsState.collectAsStateWithLifecycle()
    val movieVideoState = movieDetailScreenViewModel.movieVideoState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        movieDetailScreenViewModel.isMovieInWatchList(movieId)
        if (movieCategory == MovieCategory.UPCOMING) {
            movieDetailScreenViewModel.getUpComingMovieDetailById(movieId)
            movieDetailScreenViewModel.getMovieCredits(movieId, "en-US")
            movieDetailScreenViewModel.getMovieVideo(movieId)
        }
        if (movieCategory == MovieCategory.SERIES) {
            movieDetailScreenViewModel.getPopularSeriesDetailById(movieId)
            movieDetailScreenViewModel.getSeriesCredits(movieId, "en-US")
            movieDetailScreenViewModel.getPopularSeriesVideo(movieId)
        }
        if (movieCategory== MovieCategory.TRENDING_WEEK){
            movieDetailScreenViewModel.getTvDetailById(movieId)
            movieDetailScreenViewModel.getSeriesCredits(movieId, "en-US")
            movieDetailScreenViewModel.getPopularSeriesVideo(movieId,)
        }
    }
    if (state.value.isLoading) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {

        val movie = state.value.data
        LazyColumn(
            modifier = modifier
                .windowInsetsPadding(WindowInsets.statusBars)

                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            item {
                // Back Arrow and Title
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                ) {


                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        // Original Image
                        Image(
//                            painter = painterResource(R.drawable.profilew), // Replace with your image resource
                            painter = rememberAsyncImagePainter(
                                movie?.imageUrl

                            ),
                            contentDescription = "Movie Poster",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Bottom Blur Effect
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp) // Adjust the height of the blurred bottom part
                                .align(Alignment.BottomCenter) // Align at the bottom
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent, // Transparent at the top
                                            Color.Black.copy(alpha = 9.6f) // Adjust the blur color and opacity
                                        )
                                    )
                                )
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navHostController.popBackStack() },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape) // 👈 ensures ripple is circular
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "Back"
                            )
                        }
                        movie?.originalTitle?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                    }
                }
            }
            item {
                // Download and Add to Watchlist Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val enabled = remember { mutableStateOf(false) } // Manage the state dynamically

                    Button(
                        onClick = { /* Handle Download */
                            enabled.value = true

                        },
                        colors = if (enabled.value) {
                            ButtonDefaults.buttonColors(containerColor = Color.Red)
                        } else {
                            ButtonDefaults.buttonColors(disabledContainerColor = Color.LightGray)
                        },
                        shape = RoundedCornerShape(8.dp), enabled = enabled.value

                    ) {
                        Text(
                            text = "Download", color =
                                if (enabled.value) {
                                    Color.White
                                } else {
                                    Color.Black
                                }

                        )
                    }
                    Text(
                        text = if (watchListState.value.isInWatchlist) " Remove from Watchlist" else "+ Add to Watchlist",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .border(
                                shape = RoundedCornerShape(8.dp), border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.onBackground
                                )
                            )
                            .padding(8.dp)

                            .clickable {
                                movieDetailScreenViewModel.toggleWatchlist(
                                    Movie(
                                        movieId = movie?.id!!,
                                        title = movie.originalTitle,
                                        description = movie.overview,
                                        imageUrl = movie.imageUrl,
                                        id = 0
                                    )
                                )

                            }
                    )
                }
//        }
//        item {
                // Movie Description
                movie?.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }


            }
            // Tab Row for Trailer, Cast, More

            val tabItems = listOf(
                TabItem(
                    title = "Trailer",
                    unselectedIcon = Icons.Outlined.Home,
                    selectedIcon = Icons.Filled.Home
                ),
                TabItem(
                    title = "Cast",
                    unselectedIcon = Icons.Outlined.ShoppingCart,
                    selectedIcon = Icons.Filled.ShoppingCart
                ),
                TabItem(
                    title = "Crew",
                    unselectedIcon = Icons.Outlined.AccountCircle,
                    selectedIcon = Icons.Filled.AccountCircle
                ),
            )
            item() {
                var selectedTabIndex by remember {
                    mutableIntStateOf(0)
                }
                val pagerState = rememberPagerState {
                    tabItems.size
                }
                LaunchedEffect(selectedTabIndex) {
                    pagerState.animateScrollToPage(selectedTabIndex)
                }
                LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                    if (!pagerState.isScrollInProgress) {
                        selectedTabIndex = pagerState.currentPage
                    }
                }
                Column(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.surface)
                        .padding(bottom = 30.dp)
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        { tabPositions ->
                            SecondaryIndicator(
                                Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = Color.Red // Change the indicator color here
                            )
                        }
                    ) {
                        tabItems.forEachIndexed { index, item ->
                            Tab(

                                selected = index == selectedTabIndex,
                                onClick = {
                                    selectedTabIndex = index
                                },
                                text = {
                                    Text(text = item.title)
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
                            .fillMaxWidth()
                            .weight(1f)
                    ) { index ->


                        when (index) {
                            0 -> {


                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = tabItems[index].title)
                                    Column {
                                        if (movieVideoState.value.isLoading) {
                                            CircularProgressIndicator()
                                        } else {
                                            movieVideoState.value.data?.let {
                                                ReelsView(
                                                    it,
                                                    popularSeriesVideo = null
                                                )
                                            }
                                        }

                                    }

                                }


                            }

                            1 -> {

                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {

                                    if (castCrewState.value.isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        castCrewState.value.data.let {
                                            LazyColumn(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(16.dp)
                                            ) {
                                                val cast = castCrewState.value.data?.cast
                                                    ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                // Crew Section
                                                item {
                                                    Text(
                                                        text = "Cast",
                                                        style = MaterialTheme.typography.headlineMedium,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(bottom = 8.dp),
                                                        color = MaterialTheme.colorScheme.onBackground
                                                    )
                                                }
                                                items(cast) { castMember ->
                                                    CastItem(
                                                        castMember
                                                    )
                                                }


                                            }
                                        }
                                    }
                                }


                            }

                            2 -> {
//
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (castCrewState.value.isLoading) {
                                        CircularProgressIndicator()
                                    } else {

                                        castCrewState.value.data.let {
                                            LazyColumn(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(16.dp)
                                            ) {
                                                val crew = castCrewState.value.data?.crew
                                                    ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                Log.d(
                                                    "MOVIEDETAILSCREEN",
                                                    "MovieDetailScreen: " + crew
                                                )
                                                // Crew Section
                                                item {
                                                    Text(
                                                        text = "Crew",
                                                        style = MaterialTheme.typography.headlineMedium,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(bottom = 8.dp),
                                                        color = MaterialTheme.colorScheme.onBackground

                                                    )
                                                }
                                                items(crew) { crewMember ->
                                                    CrewItem(
                                                        crewMember
                                                    )
                                                }


                                            }
                                        }
                                    }


                                }


                            }

                            else -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(text = "Something went wrong")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

class MockWatchListRepository : WatchlistRepository {
    override suspend fun addMovie(movie: Movie) {
    }

    override suspend fun removeMovie(movieId: Int) {
    }

    override suspend fun getWatchlist(): List<Movie> {
        return emptyList()
    }

    override suspend fun isMovieInWatchList(movieId: Int): Boolean {
        return true

    }

}

@ThemeAnnotation
@Composable
fun PrevieMovieDetailScreen() {
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()

    val mockMovieUseCase = MovieUseCase(MockMovieRepository())
    val mockaddMovieToWatchlistUseCase = AddMovieToWatchlistUseCase(MockWatchListRepository())
    val mockisMovieInWatchlistUseCase = IsMovieInWatchlistUseCase(MockWatchListRepository())
    val mockremoveMovieFromWatchlistUseCase =
        RemoveMovieFromWatchlistUseCase(MockWatchListRepository())

    // Mock Pager
    val mockPager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { FakePagingSource() } // Implement a fake PagingSource
    )

    // Mock ViewModel
    val mockViewModel = MovieScreenViewModel(
        movieUseCase = mockMovieUseCase,
        pager = mockPager, addMovieToWatchlistUseCase = mockaddMovieToWatchlistUseCase,
        isMovieInWatchlistUseCase = mockisMovieInWatchlistUseCase,
        removeMovieFromWatchlistUseCase = mockremoveMovieFromWatchlistUseCase

    )

    MovieAppTheme {
        MovieDetailScreen(
            systemUiController = systemUiController,
            modifier = Modifier,
            innerPadding = PaddingValues(9.dp),
            statusBarColor = Color.Red,
            navHostController = navController,
            movieId = 12,
            movieCategory = MovieCategory.UPCOMING
        )
    }


}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES


)
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO

)
annotation class ThemeAnnotation

class FakePagingSource : PagingSource<Int, UpComingMovieEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpComingMovieEntity> {
        return LoadResult.Page(
            data = listOf(
                UpComingMovieEntity(
                    id = 1,
                    genreIds = listOf(28, 12),
                    imageUrl = "https://image.tmdb.org/t/p/w500/5qGIxdEO841C0tdY8vOdLoRVrr0.jpg",
                    originalTitle = "Mock Movie 1",
                    overview = "This is a mock overview for Movie 1",
                    releaseDate = "2024-01-01",
                    adult = false, page = 1, totalPages = 43
                ),
                UpComingMovieEntity(
                    id = 2,
                    genreIds = listOf(35, 18),
                    imageUrl = "https://image.tmdb.org/t/p/w500/5qGIxdEO841C0tdY8vOdLoRVrr0.jpg",
                    originalTitle = "Mock Movie 2",
                    overview = "This is a mock overview for Movie 2",
                    releaseDate = "2024-02-01",
                    adult = false,
                    page = 1, totalPages = 43
                )
            ),
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, UpComingMovieEntity>): Int? = null
}

class MockMovieRepository : MovieRepository {

    override suspend fun getUpComingMovie(
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

    override suspend fun getUpComingMovieDetailById(id: String): UpComingMovieDetailResponseDTO {
        return UpComingMovieDetailResponseDTO(
            adult = false,
            backdrop_path = "/mock_backdrop1.jpg",
            belongs_to_collection = "null",
            budget = 1000000,
            genres = listOf(),
            homepage = "https://www.example.com",
            id = 101,
            imdb_id = "tt1234567",
            original_language = "en",
            original_title = "Mock Movie 1",
            overview = "This is the overview of Mock Movie 1. It's an exciting action-adventure movie.",
            popularity = 1234.5,
            poster_path = "/5qGIxdEO841C0tdY8vOdLoRVrr0.jpg",
            production_companies = listOf(),
            production_countries = listOf(),
            release_date = "2025-01-15",
            revenue = 2000000,
            runtime = 120,
            spoken_languages = listOf(),
            status = "Released",
            tagline = "This is a tagline for Mock Movie 1.",
            title = "Mock Movie 1",
            video = false,
            vote_average = 7.8,
            vote_count = 256,
            origin_country = emptyList(),
        )
    }

    override suspend fun getMovieCredits(movieId: Int, language: String): CreditsResponseDto {
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

    override suspend fun getUpComingMovieVideo(movieId: Int): UpComingMovieVideoResponseDTO {
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

    override suspend fun getPopularSeries(): Pager<Int, PopularSeries> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { DummyPagingSource() }
        )
    }

    override suspend fun getPopularSeriesDetailById(id: Int): PopularSeriesDetailResponseDTO {
        return PopularSeriesDetailResponseDTO(
            adult = false,
            backdrop_path = "/path_to_backdrop.jpg",
            created_by = emptyList(),
            episode_run_time = listOf(45, 50),
            first_air_date = "2020-01-01",
            genres = emptyList(),
            homepage = "https://example.com",
            id = 12345,
            in_production = true,
            languages = listOf("en"),
            last_air_date = "2023-12-01",
            last_episode_to_air = LastEpisodeToAir(
                id = 101,
                name = "Final Episode",
                overview = "The story concludes.",
                vote_average = 9.8,
                vote_count = 1200,
                air_date = "2023-12-01",
                episode_number = 10,
                production_code = "S01E10",
                runtime = 50,
                season_number = 1,
                show_id = 12345,
                still_path = "/episode_still.jpg",
                episode_type =" TODO()"
            ),
            name = "Sample Series",
            networks =emptyList(),
            next_episode_to_air = NextEpisodeToAir(
                air_date =" TODO()",
                episode_number = 1,
                episode_type = "",
                id = 1,
                name = "",
                overview = "",
                production_code = "TODO()",
                runtime = "TODO()",
                season_number = 1,
                show_id = 1,
                still_path ="",
                vote_average = 12,
                vote_count = 12
            ),
            number_of_episodes = 10,
            number_of_seasons = 1,
            origin_country = listOf("US"),
            original_language = "en",
            original_name = "Sample Series Original",
            overview = "This is a sample overview of the series used for testing purposes.",
            popularity = 1234.56,
            poster_path = "/poster.jpg",
            production_companies = emptyList(),
            production_countries = emptyList(),
            seasons = emptyList(),
            spoken_languages = listOf(
                SpokenLanguage(
                    english_name = "English",
                    iso_639_1 = "en",
                    name = "English"
                )
            ),
            status = "Ended",
            tagline = "Every story has an end.",
            type = "Scripted",
            vote_average = 8.7,
            vote_count = 2500
        )
    }

    override suspend fun getTrendingOfWeek(): Pager<Int, TrendingOfWeek> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                TrendingOfWeekPagingSource(
                )
            })
    }

    override suspend fun getSeriesCredits(seriesId: Int, language: String): CreditsResponseDto {
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

    override suspend fun getSeriesVideos(seriesId: Int): PopularSeriesVideoResponseDTO {

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

    override suspend fun getGenre(): GenreResponseDto {
        return GenreResponseDto(genres = listOf())
    }

    override suspend fun getMovieByCategories(withGenre: String): Flow<PagingData<MovieByCategories>> {
        val movie = MovieByCategories(
            id = 1,
            genreIds = listOf(),
            imageUrl = "",
            originalTitle = "",
            overview = "",
            releaseDate = "",
            adult = false,
            page = 1, totalPages = 2, totalResult = 2
        )

        // Create a PagingData with a single item (the movie)
        val pagingData = PagingData.from(listOf(movie))

        // Return a Flow that emits the PagingData
        return flowOf(pagingData)
    }

    override suspend fun getTvByCategories(withGenre: String): Flow<PagingData<TvByCategories>> {
        val movie = TvByCategories(
            id = 1,
            genreIds = listOf(),
            imageUrl = "",
            originalName = "",
            overview = "",
            firstAirDate = "",
            adult = false,
            page = 1, totalPages = 2, totalResult = 2
        )

        // Create a PagingData with a single item (the movie)
        val pagingData = PagingData.from(listOf(movie))

        // Return a Flow that emits the PagingData
        return flowOf(pagingData)
    }

    override suspend fun getTvDetailById(
        id: Int,
        language: String
    ): TvDetailResponseDTO {
        return TvDetailResponseDTO(
            adult = false,
            backdrop_path = "/path_to_backdrop.jpg",
            created_by = emptyList(),
            episode_run_time = listOf(45, 50),
            first_air_date = "2020-01-01",
            genres = emptyList(),
            homepage = "https://example.com",
            id = 12345,
            in_production = true,
            languages = listOf("en"),
            last_air_date = "2023-12-01",
            last_episode_to_air = com.prabhat.movieapp.data.model.movie.trending.details.LastEpisodeToAir(
                id = 101,
                name = "Final Episode",
                overview = "The story concludes.",
                vote_average = 8.8,
                vote_count = 1200,
                air_date = "2023-12-01",
                episode_number = 10,
                production_code = "S01E10",
                runtime = 50,
                season_number = 1,
                show_id = 12345
                , still_path = "/episode_still.jpg"
                , episode_type =" TODO()"
            ),
            name = "Sample Series",
            networks =emptyList(),
            next_episode_to_air = com.prabhat.movieapp.data.model.movie.trending.details.NextEpisodeToAir(
                air_date =" TODO()",
                episode_number = 1,
                episode_type = "",
                id = 1,
                name = "",
                overview = "",
                production_code = "TODO()",
                runtime = "TODO()",
                season_number = 1,
                show_id = 1,
                still_path ="",
                vote_average = 12,
                vote_count = 12
            ),
            number_of_episodes = 10,
            number_of_seasons = 1,
            origin_country = listOf("US"),
            original_language = "en",
            original_name = "Sample Series Original",
            overview = "This is a sample overview of the series used for testing purposes.",
            popularity = 1234.56,
            poster_path = "/poster.jpg",
            production_companies = emptyList(),
            production_countries = emptyList(),
            seasons = emptyList(),
            spoken_languages = emptyList(),
            status = "Ended",
            tagline = "Every story has an end.",
            type = "Scripted",
            vote_average = 8.7,
            vote_count = 2500
        )
    }

    override suspend fun getTvGenreList(): GenreResponseDto {
        return GenreResponseDto(genres = listOf())
    }
}

class DummyPagingSource : PagingSource<Int, PopularSeries>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularSeries> {
        val page = params.key ?: 1
        val dummyData = List(20) { index ->
            PopularSeries(
                id = 1,
                genreIds = listOf(),
                imageUrl = "",
                originalTitle = "",
                overview = "",
                releaseDate = "",
                adult = true,
                page = 1,
                totalPages = 2
            )
        }
        return LoadResult.Page(
            data = dummyData,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (dummyData.isNotEmpty()) page + 1 else null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, PopularSeries>): Int? {
        return state.anchorPosition
    }
}

class TrendingOfWeekPagingSource : PagingSource<Int, TrendingOfWeek>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingOfWeek> {
        val page = params.key ?: 1
        val dummyData = List(20) { index ->
            TrendingOfWeek(
                id = 1,
                overview = "",
                originalName = "",
                posterPath = "",
                mediaType = "",
                genereId = listOf(),
                firstAirDate = ""

            )
        }
        return LoadResult.Page(
            data = dummyData,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (dummyData.isNotEmpty()) page + 1 else null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, TrendingOfWeek>): Int? {
        return state.anchorPosition
    }
}

@Composable
fun CastItem(cast: Cast) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = if (cast.profilePath.isNotEmpty()) {

                rememberAsyncImagePainter(
                    model = cast.profilePath,
                    onError = { error ->
                        // Log or handle the error
                        println("Image loading failed: ${error.result.throwable.message}")
                    }
                )
//
            } else {
                painterResource(id = R.drawable.placeholder) // Placeholder for null profile paths
            },
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        // Name and Role
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = cast.originalName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White

            )
            Text(
                text = cast.knownForDepartment,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
            )
        }
    }
}

@Composable
fun CrewItem(crew: com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.Crew) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = if (crew.profilePath.isNotEmpty()) {

                rememberAsyncImagePainter(
                    model = crew.profilePath,
                    onError = { error ->
                        // Log or handle the error
                        println("Image loading failed: ${error.result.throwable.message}")
                    }
                )
//
            } else {
                painterResource(id = R.drawable.placeholder) // Placeholder for null profile paths
            },
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        // Name and Role
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = crew.originalName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White

            )
            Text(
                text = crew.job,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCast(modifier: Modifier = Modifier) {

    CastItem(
        Cast(

            id = 2203,
            knownForDepartment = "Acting",
            originalName = "Neal McDonough",
            profilePath = "https://image.tmdb.org/t/p/w500//iPg0J9UzAlPj1fLEJNllpW9IhGe.jpg",
            castId = 12,
            character = "Boon",
            creditId = "608766f0bc2cb30040de4b0f",
        )
    )
}
