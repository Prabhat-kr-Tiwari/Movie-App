package com.prabhat.movieapp.presentation.screen.home

import android.graphics.Movie
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.R
import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
import com.prabhat.movieapp.data.model.movie.upcoming.Dates
import com.prabhat.movieapp.data.model.movie.upcoming.Result
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.Cast
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.data.model.movie.upcoming.credits.Crew
import com.prabhat.movieapp.domain.model.upcomingMovie.UpComingMovieResponse
import com.prabhat.movieapp.domain.repository.movie.MovieRepository
import com.prabhat.movieapp.domain.use_case.movie.MovieUseCase
import com.prabhat.movieapp.navigation.HomeDestination
import com.prabhat.movieapp.presentation.screen.introScreen.HorizontalPagerContent
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlin.math.absoluteValue
import coil.compose.rememberImagePainter
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO


@Composable
fun MovieHomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color,
    innerPadding: PaddingValues,
    movieScreenViewModel: MovieScreenViewModel
) {

    val scrollState = rememberScrollState()
    val lazyGridState = rememberLazyGridState()


    val upComingMovieScreenState=movieScreenViewModel.upComingMovieScreenState


    val lifecycleOwner = LocalLifecycleOwner.current
    val onUpComingMovieSelected = remember { mutableStateOf(false) }
    LaunchedEffect(onUpComingMovieSelected.value) {
        if (onUpComingMovieSelected.value) {

        Log.d("PRABHU", "MovieHomeScreen:LaunchedEffect CALLED")
       /* movieScreenViewModel.selectedMovie.value { movie ->
            if (movie != null) {
                Log.d("PRABHU", "MovieHomeScreen: LaunchedEffect" + movie)

                navHostController.navigate(HomeDestination.MovieLoadingScreen)
//                movieScreenViewModel.resetSelectedMovie()
                onUpComingMovieSelected.value = false

            }

        }*/
            val movie=movieScreenViewModel.selectedMovie.value
            if (movie!=null){
                Log.d("SELECTEDMOVIE", "MovieHomeScreen: "+movie)
                navHostController.navigate(HomeDestination.MovieLoadingScreen)
                onUpComingMovieSelected.value = false
            }

    }
    }

    LazyColumn(
        modifier = Modifier
//            .padding(paddingValues = innerPadding)

            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        item {
            Spacer(modifier = Modifier.height(24.dp))


            /*Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                    val (movie, profile) = createRefs()
                    Box(
                        modifier = Modifier
                            .constrainAs(movie) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }


                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .height(80.dp)

                            .background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "MOVIES",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center
                        )
                    }
//
                    Box(
                        modifier = Modifier
                            .constrainAs(profile) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end, margin = 10.dp)
                            }
                            .clip(CircleShape)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.property_1_frame_5),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(70.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ellipse13),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.Center)
                        )

                    }
                }

            }*/


            //
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                val (text) = createRefs()


                Text(
                    text = "UpComing Movies",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 10.dp)

                    })
            }
            //            Spacer(modifier = Modifier.height(5.dp))
            fun getList(): List<HorizontalPagerContent> {
                return listOf(
                    HorizontalPagerContent(

                        R.drawable.avenger_image,
                        "All your favourite MARVEL Movies and Series at one Place"
                    ),
                    HorizontalPagerContent(

                        R.drawable.deadpool_image,
                        "Watch Online or Download offline"
                    ),
                    HorizontalPagerContent(

                        R.drawable.circle_man_image,
                        "Create Profiles for different memeber and get personalised recommendation"
                    ),
                    HorizontalPagerContent(

                        R.drawable.iron_man_image,
                        "Plans according to your needs and at affordable price"
                    ),
                    HorizontalPagerContent(

                        R.drawable.thor_image,
                        "Lets get Started"
                    ),
                    HorizontalPagerContent(

                        R.drawable.captain_america_image,
                        "Plans according to your needs and at affordable price"
                    )
                )
            }

            val list = getList()
            LaunchedEffect(Unit) {

                movieScreenViewModel.getUpComingMovie(1, "en-US")
                Log.d("ALEXA", "Fetched data: ${movieScreenViewModel.upComingMovieScreenState.value.data}")

            }


            val upcomingMoviePager = movieScreenViewModel.upComingMoviePagingFlow.collectAsLazyPagingItems()

            Log.d("ALEXA", "MovieHomeScreen: "+upcomingMoviePager.itemCount)
            Log.d("ALEXA", "MovieHomeScreen: "+upcomingMoviePager)


//                upComingMovieScreenState.value.data.let {
                    if(upcomingMoviePager.itemCount>0){



               val upcomingMovieList = upComingMovieScreenState.value.data
                Log.d("ALEXA ", "MovieHomeScreen: INSIDE" + upcomingMovieList)

            Log.d("ALEXA", "MovieHomeScreen: $upcomingMovieList")

            val pagerState =
//                rememberPagerState(initialPage = 3, pageCount = { upcomingMovieList.size })
//                rememberPagerState(initialPage = 3, pageCount = { upcomingMoviePager.itemCount })
                rememberPagerState(initialPage = 3, pageCount = { upcomingMoviePager.itemCount })
            val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()
            LaunchedEffect(isDraggedState) {
                snapshotFlow { isDraggedState.value }
                    .collectLatest { isDragged ->
                        if (!isDragged) {


                            while (true) {
                                delay(3000) // Delay for auto-scroll
                                val nextPage =
                                    if (pagerState.currentPage == upcomingMoviePager.itemCount - 1) {
                                        0 // Go to first page after last page

                                    } else {
                                        pagerState.currentPage + 1 // Next page
                                    }

                                kotlin.runCatching {
                                    pagerState.animateScrollToPage(nextPage)
                                }
                            }
                        }

                    }
            }


            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {


                HorizontalPager(

                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 65.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .align(Alignment.Center),
                ) { page ->


                    val movie = upcomingMoviePager[page]
//                    val imageUrl = upcomingMovieList.get(index = page)?.imageUrl // Assuming you have a posterPath in the movie object


                    movie?.let {


                        Card(Modifier
                            .fillMaxWidth()

//                        .padding(20.dp)
                            .align(Alignment.Center)
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue
                                lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )

                                    .also { scale ->
                                        scaleX = scale
                                        scaleY = scale

                                    }
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )

                            }) {


                            Image(
                                painter = rememberAsyncImagePainter(model = movie.imageUrl),

                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .clickable {
                                        Log.d("ALEXA", "MovieHomeScreen: CLICK" + movie)
                                        movieScreenViewModel.onMovieSelected(movie)
                                        onUpComingMovieSelected.value = true

                                    }
                                    .fillMaxSize()

                            )


                        }
                    }

                    if (upcomingMoviePager.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator(modifier.align(Alignment.Center))

                    }
                }


            }
        }



            //series
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                val (text) = createRefs()


                Text(
                    text = "Latest Series",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 10.dp)

                    })
            }
//            Spacer(modifier = Modifier.height(10.dp))
            fun getListSeries(): List<HorizontalPagerContent> {
                return listOf(
                    HorizontalPagerContent(

                        R.drawable.avenger_image,
                        "All your favourite MARVEL Movies and Series at one Place"
                    ),
                    HorizontalPagerContent(

                        R.drawable.deadpool_image,
                        "Watch Online or Download offline"
                    ),
                    HorizontalPagerContent(

                        R.drawable.circle_man_image,
                        "Create Profiles for different memeber and get personalised recommendation"
                    ),
                    HorizontalPagerContent(

                        R.drawable.iron_man_image,
                        "Plans according to your needs and at affordable price"
                    ),
                    HorizontalPagerContent(

                        R.drawable.thor_image,
                        "Lets get Started"
                    ),
                    HorizontalPagerContent(

                        R.drawable.captain_america_image,
                        "Plans according to your needs and at affordable price"
                    )
                )
            }

            val seriesList = getListSeries()

            val seriesPagerState = rememberPagerState(pageCount = { seriesList.size })
            val seriesIsDraggedState = seriesPagerState.interactionSource.collectIsDraggedAsState()
            LaunchedEffect(seriesIsDraggedState) {
                snapshotFlow { seriesIsDraggedState.value }
                    .collectLatest { isDragged ->
                        if (!isDragged) {


                            while (true) {
                                delay(3000) // Delay for auto-scroll
                                val nextPage = if (seriesPagerState.currentPage == list.size - 1) {
                                    0 // Go to first page after last page
                                } else {
                                    seriesPagerState.currentPage + 1 // Next page
                                }

                                kotlin.runCatching {
                                    seriesPagerState.animateScrollToPage(nextPage)
                                }
                            }
                        }

                    }
            }


            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color.Red)
//                    .background(MaterialTheme.colorScheme.onBackground)
            ) {

                HorizontalPager(
                    snapPosition = SnapPosition.Center,

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .align(Alignment.Center),

                    state = seriesPagerState,
//                modifier = Modifier.fillMaxSize(),
                    key = { list[it].image },
                    beyondViewportPageCount = 3


                ) { index ->


                    Card(
                        Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center)
                        /* .graphicsLayer {
                             // Calculate the absolute offset for the current page from the
                             // scroll position. We use the absolute value which allows us to mirror
                             // any effects for both directions
                             val pageOffset = (
                                     (seriesPagerState.currentPage - index) + pagerState
                                         .currentPageOffsetFraction
                                     ).absoluteValue

                             // We animate the alpha, between 50% and 100%
                             alpha = lerp(
                                 start = 0.5f,
                                 stop = 1f,
                                 fraction = 1f - pageOffset.coerceIn(0f, 1f)
                             )
                         }*/
                    ) {
                        // Card content
                        Image(
                            painter = painterResource(id = list[index].image),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()

                        )
                    }


                }

            }


            //trending today
//            Spacer(modifier = Modifier.height(10.dp))
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                val (text) = createRefs()


                Text(
                    text = "Trending Today",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 10.dp)

                    })
            }


        }

        TrendingMovieSection(innerPadding = innerPadding)


    }

}

fun LazyListScope.TrendingMovieSection(innerPadding: PaddingValues
) {
    fun getList(): List<HorizontalPagerContent> {
        return listOf(
            HorizontalPagerContent(

                R.drawable.avenger_image,
                "All your favourite MARVEL Movies and Series at one Place"
            ),
            HorizontalPagerContent(

                R.drawable.deadpool_image,
                "Watch Online or Download offline"
            ),
            HorizontalPagerContent(

                R.drawable.circle_man_image,
                "Create Profiles for different memeber and get personalised recommendation"
            ),
            HorizontalPagerContent(

                R.drawable.iron_man_image,
                "Plans according to your needs and at affordable price"
            ),
            HorizontalPagerContent(

                R.drawable.thor_image,
                "Lets get Started"
            ),
            HorizontalPagerContent(

                R.drawable.captain_america_image,
                "Plans according to your needs and at affordable price"
            )
        )
    }

    val list = getList()

    items(1) { index ->
        ThreeItem(

            content = list
            , innerPadding = innerPadding
        )

        /* LazyVerticalGrid(columns = GridCells.Fixed(2)) {


             items(list.size) { index ->



             }


         }*/
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThreeItem(modifier: Modifier = Modifier, content: List<HorizontalPagerContent>,innerPadding: PaddingValues) {

    Card(
        modifier = Modifier
            .background(Color.Black)
//            .padding(5.dp)
            .padding(paddingValues = innerPadding)


            .fillMaxSize()
//            .clip(RoundedCornerShape(40.dp))
//            .border(1.dp, Color.Red, shape = RoundedCornerShape(40.dp))
    ) {
        // Card content
        FlowRow(
            maxItemsInEachRow = 3,  // Ensures 3 items per row
            modifier = Modifier
                .background(Color.Black)

//                .align(Alignment.CenterHorizontally)
                .fillMaxSize(), horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Top
        ) {
            content.forEach { item ->
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(8.dp)
//                        .align(Alignment.CenterVertically)
                        .clip(RectangleShape)
                        .width(110.dp)
                        .height(200.dp)
//                        .size(110.dp)
                        .clickable {

                            Log.d("Prabhat", "ThreeItem: ${item.desc}")
                        }

                )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrendingItem(modifier: Modifier = Modifier, content: HorizontalPagerContent) {
    Card(
        Modifier
            .size(120.dp)
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))

    ) {
        // Card content
        FlowRow(
            maxItemsInEachRow = 3
        ) {
            Image(
                painter = painterResource(id = content.image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()

            )

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMovieHomeScreen() {

    // Mock NavHostController for the preview
    val navController = rememberNavController()

    // Mock SystemUiController (You might need to provide a proper implementation or mock)
    val systemUiController = rememberSystemUiController()
    // Mock MovieUseCase


    val mockMovieUseCase = MovieUseCase(MockMovieRepository())

    // Mock Pager
    val mockPager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { FakePagingSource() } // Implement a fake PagingSource
    )

    // Mock ViewModel
    val mockViewModel = MovieScreenViewModel(
        movieUseCase = mockMovieUseCase,
        pager = mockPager
    )

    MovieHomeScreen(
        navHostController = navController,
        systemUiController = systemUiController,
        statusBarColor = Color.Red,
        innerPadding = PaddingValues(30.dp),
        movieScreenViewModel =mockViewModel
    )
}
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

    override suspend fun getMovieCredits(movieId: Int, language: String): CreditsResponseDto {
        return CreditsResponseDto(id = 1, crew =

        listOf(Crew(adult= false,
            gender= 0,
            id= 3068872,
        known_for_department="Directing",
        name= "Derek Presley",
        original_name= "Derek Presley",
        popularity=0.503,
        profile_path= "null",
        credit_id= "6087656533a533002a02051c",
        department="Directing",
        job= "Director")),


            cast =
        listOf(Cast(adult= false,
            gender= 2,
            id= 2203,
        known_for_department= "Acting",
        name= "Neal McDonough",
        original_name= "Neal McDonough",
        popularity= 33.364,
        profile_path= "/3mI3i1CpjATSCta1Tb2qsyl1KCh.jpg",
        cast_id= 12,
        character= "Boon",
        credit_id= "608766f0bc2cb30040de4b0f",
        order= 0)))
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
}



@Composable
fun CastCrewScreen(
    crew: List<Crew>,
    cast: List<Cast>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Crew Section
        item {
            Text(
                text = "Crew",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(crew) { crewMember ->
            CrewCastItem(
                name = crewMember.name,
                role = crewMember.job,
                profilePath = crewMember.profile_path
            )
        }

        // Cast Section
        item {
            Text(
                text = "Cast",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }
        items(cast) { castMember ->
            CrewCastItem(
                name = castMember.name,
                role = castMember.character,
                profilePath = castMember.profile_path
            )
        }
    }
}

@Composable
fun CrewCastItem(
    name: String,
    role: String,
    profilePath: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = if (!profilePath.isNullOrEmpty()) {
               /* rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = "https://image.tmdb.org/t/p/w500//iPg0J9UzAlPj1fLEJNllpW9IhGe.jpg")
                        .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                )*/
                rememberAsyncImagePainter(model="https://image.tmdb.org/t/p/w500//iPg0J9UzAlPj1fLEJNllpW9IhGe.jpg")
            } else {
                painterResource(id = R.drawable.placeholder) // Placeholder for null profile paths
            },
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(color = Color.Red),
            contentScale = ContentScale.Crop
        )

        // Name and Role
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = role,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CastCrewScreenPreview() {
    val crew = listOf(
        Crew(
            adult = false,
            gender = 0,
            id = 3068872,
            known_for_department = "Directing",
            name = "Derek Presley",
            original_name = "Derek Presley",
            popularity = 0.503,
            profile_path = "/iPg0J9UzAlPj1fLEJNllpW9IhGe.jpg",
            credit_id = "6087656533a533002a02051c",
            department = "Directing",
            job = "Director"
        )
    )

    val cast = listOf(
        Cast(
            adult = false,
            gender = 2,
            id = 2203,
            known_for_department = "Acting",
            name = "Neal McDonough",
            original_name = "Neal McDonough",
            popularity = 33.364,
            profile_path = "/q2KZZ0ltTEl7Sf8volNFV1JDEP4.jpg",
            cast_id = 12,
            character = "Boon",
            credit_id = "608766f0bc2cb30040de4b0f",
            order = 0
        )
    )

    CastCrewScreen(crew = crew, cast = cast)
}



