package com.prabhat.movieapp.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.LocalLifecycleOwner
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
import androidx.paging.insertFooterItem
import androidx.paging.map
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.R
import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
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
import com.prabhat.movieapp.data.network.movie.popularSeries.pagingSource.PopularSeriesPagingSource
import com.prabhat.movieapp.data.network.movie.trending.pagindSource.TrendingOfWeekPagingSource
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek
import com.prabhat.movieapp.domain.model.tvByCategories.TvByCategories
import com.prabhat.movieapp.domain.repository.movie.MovieRepository
import com.prabhat.movieapp.domain.use_case.movie.MovieUseCase
import com.prabhat.movieapp.domain.use_case.watchList.AddMovieToWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.IsMovieInWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.RemoveMovieFromWatchlistUseCase
import com.prabhat.movieapp.mappers.Mapper
import com.prabhat.movieapp.mappers.popularSeries.PopularSeriesDTOToPopularSeriesMapper
import com.prabhat.movieapp.mappers.trending.TrendingOfWeekResponseDtoToTrendingOfWeekMapper
import com.prabhat.movieapp.navigation.HomeDestination
import com.prabhat.movieapp.presentation.screen.home.movieDetail.MockWatchListRepository
import com.prabhat.movieapp.presentation.screen.introScreen.HorizontalPagerContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlin.math.absoluteValue


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
    val upComingMovieScreenState = movieScreenViewModel.upComingMovieScreenState
    val lifecycleOwner = LocalLifecycleOwner.current
    val popularSeriesPager=movieScreenViewModel.popularSeriesPagingData
    val trendingOfWeekPagingData = movieScreenViewModel.trendingMoviesFlow
    val trendingOfWeekItems = trendingOfWeekPagingData.collectAsState().value.collectAsLazyPagingItems()
//    val popularSeriesPagingItems = popularSeriesPager.collectAsState().value.collectAsLazyPagingItems()
//    Log.d("TRENDINGWAX", "MovieHomeScreen: "+trendingOfWeekItems)




    val onUpComingMovieSelected = remember { mutableStateOf(false) }
    val onSeriesSelected = remember { mutableStateOf(false) }
    val onTrendingOfWeekSelected = remember { mutableStateOf(false) }


   /* LaunchedEffect(onUpComingMovieSelected.value) {
        if (onUpComingMovieSelected.value) {
            val movie = movieScreenViewModel.selectedMovie.value
            if (movie != null) {
                navHostController.navigate(HomeDestination.MovieLoadingScreen)
                onUpComingMovieSelected.value = false
            }

        }
    }*/
    LaunchedEffect(onUpComingMovieSelected.value) {
        if (onUpComingMovieSelected.value) {
            val movie = movieScreenViewModel.selectedMovie.value
            if (movie != null) {
                navHostController.navigate(HomeDestination.MovieDetailScreen)
                onUpComingMovieSelected.value = false
            }

        }
    }
    LaunchedEffect(onSeriesSelected.value) {
        if (onSeriesSelected.value){
            val series = movieScreenViewModel.selectedSeries.value
            if (series!=null){
                navHostController.navigate(HomeDestination.MovieLoadingScreen)
                onSeriesSelected.value=false
            }
        }
    }
    LaunchedEffect(onTrendingOfWeekSelected.value) {
        if (onTrendingOfWeekSelected.value){
            val trendingOfWeek = movieScreenViewModel.selectedTrendingOfWeek.value
            if (trendingOfWeek!=null){
                navHostController.navigate(HomeDestination.MovieLoadingScreen)
                onTrendingOfWeekSelected.value=false
            }
        }
    }

    LazyColumn(
        modifier = Modifier
//            .padding(paddingValues = innerPadding)

            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        item {
            Spacer(modifier = Modifier.height(24.dp))

            //
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                val (text) = createRefs()


                Text(
                    text = "UpComing Movies",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 10.dp)

                    })
            }
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

            }


            val upcomingMoviePager =
                movieScreenViewModel.upComingMoviePagingFlow.collectAsLazyPagingItems()

            if (upcomingMoviePager.itemCount > 0) {


                val upcomingMovieList = upComingMovieScreenState.value.data


                val pagerState =
                    rememberPagerState(
                        initialPage = 3,
                        pageCount = { upcomingMoviePager.itemCount })
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
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth()
                        .height(220.dp)
                ) {


                    HorizontalPager(

                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 65.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .align(Alignment.Center),
                    ) { page ->


                        val movie = upcomingMoviePager[page]
                        movie?.let {

                            Card(Modifier
                                .fillMaxWidth()
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
//            val popularSeriesPagingItems = popularSeriesPager.value.collectAsLazyPagingItems()
            val popularSeriesPagingItems = popularSeriesPager.collectAsState().value.collectAsLazyPagingItems()


            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                val (text) = createRefs()


                Text(
                    text = "Latest Series",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
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

            if (popularSeriesPagingItems.itemCount > 0) {

         /*   val seriesPagerState = rememberPagerState(pageCount = { seriesList.size })
            val seriesIsDraggedState = seriesPagerState.interactionSource.collectIsDraggedAsState()*/

                val seriesPagerState =
                    rememberPagerState(
                        initialPage = 3,
                        pageCount = { popularSeriesPagingItems.itemCount })
                val seriesIsDraggedState = seriesPagerState.interactionSource.collectIsDraggedAsState()
            LaunchedEffect(seriesIsDraggedState) {
                snapshotFlow { seriesIsDraggedState.value }
                    .collectLatest { isDragged ->
                        if (!isDragged) {


                            while (true) {
                                delay(3000) // Delay for auto-scroll
                                val nextPage = if (seriesPagerState.currentPage == popularSeriesPagingItems.itemCount - 1) {
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
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .height(270.dp)
                    .background(Color.Red)
//                    .background(MaterialTheme.colorScheme.onBackground)
            ) {


                HorizontalPager(
                    snapPosition = SnapPosition.Center,

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .align(Alignment.Center),

                    state = seriesPagerState,
//                modifier = Modifier.fillMaxSize(),
//                    key = { list[it].image },
                    beyondViewportPageCount = 3


                ) { index ->

                    val series = popularSeriesPagingItems[index]

                    series?.let {
                        Card(
                            Modifier
                                .padding(start = 40.dp, end = 40.dp)
                                .fillMaxWidth()
                                .align(Alignment.Center)

                        ) {
                            val context = LocalContext.current
                            var dominantColor by remember { mutableStateOf(Color.Black) }
                            var secondaryColor by remember { mutableStateOf(Color.DarkGray) }

                            LaunchedEffect(series.imageUrl) {
                                val imageLoader = ImageLoader(context)
                                val request = ImageRequest.Builder(context)
                                    .data(series.imageUrl)
                                    .allowHardware(false) // Needed for Palette
                                    .build()

                                val result = (imageLoader.execute(request) as? SuccessResult)?.drawable
                                result?.toBitmap()?.let { bitmap ->
                                    Palette.from(bitmap).generate { palette ->
                                        dominantColor = Color(palette?.dominantSwatch?.rgb ?: Color.Black.toArgb())
                                        secondaryColor = Color(palette?.mutedSwatch?.rgb ?: Color.DarkGray.toArgb())
                                    }
                                }
                            }
                            // Card content
                            Image(
//                            painter = painterResource(id = list[index].image),
                                painter = rememberAsyncImagePainter(model = series.imageUrl),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .fillMaxSize() .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(dominantColor, secondaryColor)
                                        )
                                    ).padding(10.dp).clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        movieScreenViewModel.onSeriesSelected(series = series)
                                        onSeriesSelected.value = true
                                    }

                            )
                        }
                    }




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
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 10.dp)

                    })
            }


        }



            TrendingMovieSection(innerPadding = innerPadding,trendingOfWeekItems=trendingOfWeekItems, movieScreenViewModel = movieScreenViewModel){
                onTrendingOfWeekSelected.value = true
            }






    }

}




fun LazyListScope.TrendingMovieSection(
    innerPadding: PaddingValues,
    trendingOfWeekItems:
    LazyPagingItems<TrendingOfWeek>,
    movieScreenViewModel: MovieScreenViewModel,
    onClick:()->Unit

) {
//    val trendingOfWeekItems = trendingOfWeekPager.value.collectAsLazyPagingItems()
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
            ),
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

//    val list = getList()
    val list = getList()
    val list2 = getList()


    items(1) { index ->
        ThreeItem(

            content = trendingOfWeekItems, innerPadding = innerPadding, movieScreenViewModel = movieScreenViewModel,onClick=onClick
        )

        /* LazyVerticalGrid(columns = GridCells.Fixed(2)) {


             items(list.size) { index ->



             }


         }*/
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThreeItem(
    modifier: Modifier = Modifier,
//    content: List<HorizontalPagerContent>,
    content: LazyPagingItems<TrendingOfWeek>,
    innerPadding: PaddingValues,movieScreenViewModel: MovieScreenViewModel,
    onClick:()->Unit
) {

    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
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
                .background(MaterialTheme.colorScheme.surface)

//                .align(Alignment.CenterHorizontally)
                .fillMaxSize(), horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Top
        ) {
           /* content.forEach { item ->
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
            }*/


            if(content.loadState.refresh is LoadState.NotLoading){
                if (content.itemCount!=0){
                    for (index in 0 until content.itemCount) {
//            repeat (content.itemCount) {index->
                        val item = content[index] // Retrieve paginated item safely

                        if (item != null) {
//                            Log.d("TRENDINGWAX", "ThreeItem: $item")

                            Image(
                                painter = rememberAsyncImagePainter(item.posterPath),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(RectangleShape)
                                    .width(110.dp)
                                    .height(200.dp)
                                    .clickable {

                                        Log.d("TRENDINGMOVIE", "ThreeItem: ${item}")
                                        movieScreenViewModel.onTrendingOfWeekSelected(item)
                                        onClick.invoke()

                                    }
                            )
                        }
                    }
                }
            }


            if (content.loadState.refresh is LoadState.Loading){
                CircularProgressIndicator(modifier.align(Alignment.CenterVertically))
            }
            if (content.loadState.refresh is LoadState.Loading){
                if (content.itemCount==0){
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = "Nothing found",                        color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
            if (content.loadState.prepend is LoadState.Error){
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Button(modifier = Modifier.fillMaxWidth(), onClick = {
                        content.retry()
                    }) {
                        Text("Retry",                        color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }

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

        override suspend fun getTvByCategories(
            apiKey: String,
            withGenres: String,
            page: Int
        ): TvByCategoriesResponseDto {
            return TvByCategoriesResponseDto(
                page = 1, results = listOf(), total_results = 2, total_pages = 2
            )
        }

        override suspend fun getTvGenreList(apiKey: String): GenreResponseDto {
            return GenreResponseDto(genres = listOf())
        }

    }
    class FakePopularSeriesMapper : Mapper<PopularSeriesDTO, PopularSeries> {
        override fun map(from: PopularSeriesDTO): PopularSeries {
            val firstResult = from.results.firstOrNull() ?: throw IllegalArgumentException("No series data available")
            return PopularSeries(
                id = firstResult.id,
                genreIds = firstResult.genre_ids,
                imageUrl = "https://image.tmdb.org/t/p/w500${firstResult.poster_path}", // Fake image URL for preview
                originalTitle = firstResult.original_name,
                overview = firstResult.overview,
                releaseDate = firstResult.first_air_date,
                adult = firstResult.adult,
                page = from.page,
                totalPages = from.total_pages
            )
        }
    }
    class FakeTrendingWeekMapper : Mapper<TrendingOfWeekResponseDto, TrendingOfWeek> {
        override fun map(from: TrendingOfWeekResponseDto): TrendingOfWeek {
            val firstResult = from.results.firstOrNull() ?: throw IllegalArgumentException("No series data available")
            return TrendingOfWeek(
                id = firstResult.id,
                overview = firstResult.overview,
                originalName = "hell", posterPath = "heh", mediaType = "movie", genereId = listOf(), firstAirDate = "12"

            )
        }
    }
    val fake=PopularSeriesDTOToPopularSeriesMapper()



    val mockMovieApiService: MovieApiService = FakeMovieApiService()
    val mapper = FakePopularSeriesMapper()
    val trendingOfWeekMapper = TrendingOfWeekResponseDtoToTrendingOfWeekMapper()

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
        pager = mockPager,addMovieToWatchlistUseCase = mockaddMovieToWatchlistUseCase,
        isMovieInWatchlistUseCase = mockisMovieInWatchlistUseCase,
        removeMovieFromWatchlistUseCase = mockremoveMovieFromWatchlistUseCase
    )

    MovieHomeScreen(
        navHostController = navController,
        systemUiController = systemUiController,
        statusBarColor = Color.Red,
        innerPadding = PaddingValues(30.dp),
        movieScreenViewModel = mockViewModel
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

class MockMovieRepository(private val movieApiService: MovieApiService,
                          private val mapper: PopularSeriesDTOToPopularSeriesMapper,
    private val trendingOfWeekMapper: TrendingOfWeekResponseDtoToTrendingOfWeekMapper
) : MovieRepository {

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
        return  Pager( config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 1,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
            pagingSourceFactory ={
                PopularSeriesPagingSource(
                    movieApiService = movieApiService,
                    q = "",
                    mapper = mapper
                )
            })
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
                    movieApiService = movieApiService,
                    mapper = trendingOfWeekMapper
                )
            }
        )
    }


    /* override suspend fun getTrendingOfWeek(): Flow<Pager<Int, TrendingOfWeek>> {
         return  Pager( config = PagingConfig(
             pageSize = 20,
             prefetchDistance = 1,
             enablePlaceholders = false,
             initialLoadSize = 20
         ),
             pagingSourceFactory ={
                 TrendingOfWeekPagingSource(
                     movieApiService = movieApiService,

                     mapper = trendingOfWeekMapper
                 )
             }).flow
     }*/

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

    override suspend fun getTvGenreList(): GenreResponseDto {
        return GenreResponseDto(genres = listOf())
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
                color = MaterialTheme.colorScheme.onBackground,

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
                color = MaterialTheme.colorScheme.onBackground,

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
                rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500//iPg0J9UzAlPj1fLEJNllpW9IhGe.jpg")
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
                color = MaterialTheme.colorScheme.onBackground,

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



