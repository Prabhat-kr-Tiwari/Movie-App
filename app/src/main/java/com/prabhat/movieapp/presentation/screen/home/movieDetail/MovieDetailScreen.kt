package com.prabhat.movieapp.presentation.screen.home.movieDetail

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.MainActivity
import com.prabhat.movieapp.R
import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
import com.prabhat.movieapp.data.model.categories.GenreResponseDto
import com.prabhat.movieapp.data.model.movie.popular.videos.PopularSeriesVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.trending.TrendingOfWeekResponseDto
import com.prabhat.movieapp.data.model.movie.upcoming.Dates
import com.prabhat.movieapp.data.model.movie.upcoming.Result
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.data.model.movie.upcoming.credits.Crew
import com.prabhat.movieapp.data.network.movie.trending.pagindSource.TrendingOfWeekPagingSource
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
import com.prabhat.movieapp.navigation.BottomNavigationDestination
import com.prabhat.movieapp.navigation.HomeDestination
import com.prabhat.movieapp.presentation.screen.home.FakePagingSource
import com.prabhat.movieapp.presentation.screen.home.MockMovieRepository
import com.prabhat.movieapp.presentation.screen.home.MovieScreenViewModel
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf



private fun getFullImageUrl(posterPath: String?): String {
    return if (!posterPath.isNullOrEmpty()) "$BASE_IMAGE_URL$posterPath" else "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg"
}

@Composable
fun CrewCastScreen(cast: Cast) {
    // Your composable logic here

}

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier.padding(bottom = 20.dp),
    systemUiController: SystemUiController,
    statusBarColor: Color,
    innerPadding: PaddingValues,
    movieScreenViewModel: MovieScreenViewModel,
    navHostController: NavHostController,
) {



    val watchList = movieScreenViewModel.watchlistState.value
    val movie = movieScreenViewModel.selectedMovie.value
    val castCrewState = movieScreenViewModel.movieCreditsState.value
    val movieVideoState=movieScreenViewModel.movieVideoState.value
    val isMovie = remember { mutableStateOf(false) }
    if (movieScreenViewModel.selectedMovie.value?.id != null) {

        isMovie.value=true
        Log.d("TOMMY", "MovieDetailScreen: " + movieScreenViewModel.selectedMovie.value?.id)
    } else {
        isMovie.value=false
        Log.d("TOMMY", "MovieDetailScreen selectedMovie: null")

    }
    //series
    val series = movieScreenViewModel.selectedSeries.value
    val seriesCastCrew=movieScreenViewModel.seriesCreditsState.value
    val seriesVideoState = movieScreenViewModel.popularSeriesVideoState.value
//    val seriesVideoState = movieScreenViewModel.popularSeriesVideoState.collectAsState()
    val isSeries = remember { mutableStateOf(false) }

    if (movieScreenViewModel.selectedSeries.value?.id != null) {

        isSeries.value=true
        Log.d("TOMMY", "MovieDetailScreen: " + movieScreenViewModel.selectedMovie.value?.id)
    } else {
        isSeries.value=false
        Log.d("TOMMY", "MovieDetailScreen selectedSeries: null")

    }
    //trending of week
    val trendingOfWeek = movieScreenViewModel.selectedTrendingOfWeek.value
//    val trendingOfWeekCastCrew=movieScreenViewModel.seriesCreditsState.value
//    val trendingOfWeekVideoState = movieScreenViewModel.popularSeriesVideoState.value
    val isTrendingOfWeek = remember { mutableStateOf(false) }
    if (movieScreenViewModel.selectedTrendingOfWeek.value?.id != null) {
        isTrendingOfWeek.value=true
    } else {
        isTrendingOfWeek.value=false
        Log.d("TOMMY", "MovieDetailScreen selectedTrendingOfWeek: null")
    }
    //movie by categories
    val movieByCategories =  movieScreenViewModel.selectedMovieByCategories.value
    val isMovieByCategories =  remember { mutableStateOf(false) }
    if (movieScreenViewModel.selectedMovieByCategories.value?.id!=null){
        isMovieByCategories.value = true
    }else{
        Log.d("TOMMY", "MovieDetailScreen selectedMovieByCategories: null")

        isMovieByCategories.value = false
    }
    val tvByCategories = movieScreenViewModel.selectedTvByCategories.value
    val isTvByCategories =  remember{mutableStateOf(false)}
    if (movieScreenViewModel.selectedTvByCategories.value?.id != null) {
        isTvByCategories.value=true
    } else {
        isTvByCategories.value=false
        Log.d("TOMMY", "MovieDetailScreen selectedTvByCategories: null")
    }




    val currentDestination = navHostController.currentDestination?.route
    Log.d("FUTURE", "onCreate: currentDestination $currentDestination")
    Log.d(
        "QWERTY",
        "onCreate: ${BottomNavigationDestination.MovieHomeScreen.toString()}"
    )
    val currentDestinationName = currentDestination?.substringAfterLast(".")
    Log.d("FUTURE", "MovieDetailScreen: "+currentDestinationName)
    val previousDestination = navHostController.previousBackStackEntry?.destination?.route
    Log.d("CHUBBY", "MovieDetailScreen: "+previousDestination)
    val screenName = previousDestination?.substringAfterLast(".")
    Log.d("PREVIOUS", "MovieDetailScreen:screenName $screenName")



    //back pressed
//    var backPressHandled by remember { mutableStateOf(false) }
    var backPressHandled by rememberSaveable { mutableStateOf(false) }


    BackHandler(enabled = !backPressHandled) {
        if (backPressHandled) return@BackHandler
        backPressHandled = true
        val previousDestination = navHostController.previousBackStackEntry?.destination?.route
        Log.d("CHUBBY", "MovieDetailScreen: "+previousDestination)
        val screenName = previousDestination?.substringAfterLast(".")
        Log.d("PREVIOUS", "MovieDetailScreen:screenName2 $screenName")


        when(screenName){
            "MovieHomeScreen"->{
                navHostController.popBackStack(BottomNavigationDestination.MovieHomeScreen, false)
            }
            "MovieCategoriesScreen"->{
                navHostController.popBackStack(BottomNavigationDestination.MovieCategoriesScreen, false)
            }
            "MovieDownloadScreen"->{
                navHostController.popBackStack(BottomNavigationDestination.MovieDownloadScreen, false)
            }
            "MoreScreen"->{
                navHostController.popBackStack(BottomNavigationDestination.MoreScreen, false)
            }
            else->{
                navHostController.navigateUp()

                Log.d("MovieDetailScreen", "MovieDetailScreen:                 navHostController.popBackStack()\n")
//                navHostController.popBackStack()
            }
        }

        // Clean up after navigation
        movieScreenViewModel.cleanupDetailScreen()

        Log.d("FUTURE", "MovieDetailScreen: BackHandler called $screenName")
    }
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
                /*Image(
                    painter = painterResource(R.drawable.avenger_image), // Replace with your movie poster
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.blur(1.dp,2.dp, BlurredEdgeTreatment.Rectangle)
                        .fillMaxSize()
                )*/
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Original Image
                    Image(
//                    painter = painterResource(R.drawable.profilew), // Replace with your image resource
                        painter = rememberAsyncImagePainter(
                            if (isMovie.value){

                                movie?.imageUrl
                            } else if (isSeries.value) {
                                series?.imageUrl
                            } else if (isTrendingOfWeek.value) {
                                trendingOfWeek?.posterPath

                            }else if (isMovieByCategories.value){
                                movieByCategories?.imageUrl

                            }else if(isTvByCategories.value){
                                tvByCategories?.imageUrl
                            }
                            else{

                            }
                        ), // Replace with your image resource
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
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { /* Handle back navigation */ }
                    )
                    if (isMovie.value){
                        movie?.originalTitle?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }
                    else if (isSeries.value){
                        series?.originalTitle?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }else if (isTrendingOfWeek.value){
                        trendingOfWeek?.originalName?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }else if (isMovieByCategories.value){
                        movieByCategories?.originalTitle?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                    }else if (isTvByCategories.value){
                        tvByCategories?.originalName?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

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
                    text = if(watchList.isInWatchlist) " Remove from Watchlist" else "+ Add to Watchlist",
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
                            if (isMovie.value) {

                                movieScreenViewModel.toggleWatchlist(
                                    Movie(
                                        movieId = movie?.id!!,
                                        title = movie.originalTitle,
                                        description = movie.overview,
                                        imageUrl = movie.imageUrl,
                                        id = 0
                                    )
                                )

                            } else if (isSeries.value) {
                                movieScreenViewModel.toggleWatchlist(
                                    Movie(
                                        movieId = series?.id!!,
                                        title = series.originalTitle,
                                        description = series.overview,
                                        imageUrl = series.imageUrl,
                                        id = 0
                                    )
                                )
                            } else if (isTrendingOfWeek.value) {
                                Log.d("AJITHAL", "MovieDetailScreen: isTrendingOfWeek")
                                Log.d(
                                    "AJITHAL",
                                    "MovieDetailScreen: isTrendingOfWeek movieId = ${trendingOfWeek?.id},\n" +
                                            "                                        title = ${trendingOfWeek?.originalName},\n" +
                                            "                                        description = ${trendingOfWeek?.overview},\n" +
                                            "                                        imageUrl = ${trendingOfWeek?.posterPath},"
                                )
                                trendingOfWeek?.let { it ->
                                    movieScreenViewModel.toggleWatchlist(

                                        Movie(
                                            movieId = it.id ?: 0,
                                            title = it.originalName ?: "",
                                            description = it.overview ?: "",
                                            imageUrl = it.posterPath ?: "",
                                            id = 0
                                        )
                                    )
                                }


                            } else if (isMovieByCategories.value) {
                                movieScreenViewModel.toggleWatchlist(
                                    Movie(
                                        movieId = movieByCategories?.id!!,
                                        title = movieByCategories.originalTitle,
                                        description = movieByCategories.overview,
                                        imageUrl = movieByCategories.imageUrl,
                                        id = 0
                                    )
                                )

                            } else if (isTvByCategories.value) {
                                movieScreenViewModel.toggleWatchlist(
                                    Movie(
                                        movieId = tvByCategories?.id!!,
                                        title = tvByCategories.originalName,
                                        description = tvByCategories.overview,
                                        imageUrl = tvByCategories.imageUrl,
                                        id = 0
                                    )
                                )
                            } else {

                            }

                        }
                )
            }
//        }
//        item {
            // Movie Description
            if (isMovie.value){
                movie?.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }else if(isSeries.value){
                series?.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }else if (isTrendingOfWeek.value){
                trendingOfWeek?.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }else if (isMovieByCategories.value){
                movieByCategories?.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }else if (isTvByCategories.value){
                tvByCategories?.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }
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
                TabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier,
                    containerColor =MaterialTheme.colorScheme.surface, contentColor =  MaterialTheme.colorScheme.onBackground,
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
                    /*Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = tabItems[index].title)

                    }*/

                    when (index) {
                        0 -> {


                          /*  if (isMovie.value){
                                if(castCrewState.isLoading){
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()

                                    }
                                }
                            }
                            if (isSeries.value){
                                if (seriesCastCrew.isLoading){
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()

                                    }
                                }
                            }

                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = tabItems[index].title)
                                    Column {

//                                    CustomYoutubePlayer(youtubeVideoId = "E_8LHkn4g-Q")
                                        if (isMovie.value){
                                            movieVideoState.data?.let { ReelsView(it, popularSeriesVideo = null) }
                                        }else if (isSeries.value){
                                            if(seriesVideoState.data?.key?.isEmpty() == true){
                                                Text(text = "No Trailer Available", textAlign = TextAlign.Center, color = Color.White)
                                            }else{

                                                seriesVideoState.data?.let { ReelsView(upcomingMovieVideo = null,it) }
                                            }
                                        }


                                    }

                                }*/
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                                // Show loading indicator if needed
                                when {
                                    isMovie.value && movieVideoState.isLoading -> {
                                        CircularProgressIndicator()
                                        Log.d("DARLING", "MovieDetailScreen: MOVIE${isMovie.value} && ${castCrewState.isLoading}  ")
                                    }
                                    isSeries.value && seriesVideoState.isLoading -> {
                                        CircularProgressIndicator()
                                        Log.d("DARLING", "MovieDetailScreen: SERIES${isSeries.value} && ${seriesCastCrew.isLoading}  ")
                                    }
                                    isMovieByCategories.value&&movieVideoState.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    isTvByCategories.value && seriesVideoState.isLoading->{
                                        CircularProgressIndicator()
                                    }

                                    else -> {
                                        // Show actual content when not loading
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = tabItems[index].title)

                                            if (isMovie.value) {
                                                movieVideoState.data?.let { ReelsView(it, popularSeriesVideo = null) }
                                            } else if (isSeries.value) {
                                                Log.d("DARLING", "MovieDetailScreen: ${seriesVideoState.data}")
                                                if (seriesVideoState.data?.key?.isEmpty() == true) {
                                                    Text(text = "No Trailer Available", textAlign = TextAlign.Center, color =  MaterialTheme.colorScheme.onBackground,
                                                    )
                                                } else {
                                                    seriesVideoState.data?.let { ReelsView(upcomingMovieVideo = null, it) }
                                                }
                                            }else if (isTrendingOfWeek.value){
                                                    movieVideoState.data?.let { ReelsView(it, popularSeriesVideo = null) }
                                                    Log.d("DARLING", "MovieDetailScreen: ${seriesVideoState.data}")
                                                    if (seriesVideoState.data?.key?.isEmpty() == true) {
                                                        Text(text = "No Trailer Available", textAlign = TextAlign.Center, color =  MaterialTheme.colorScheme.onBackground)
                                                    } else {
                                                        seriesVideoState.data?.let { ReelsView(upcomingMovieVideo = null, it) }
                                                    }


                                            }else if (isMovieByCategories.value){
                                                if (movieVideoState.data?.key?.isEmpty() == true){
                                                    Text(text = "No Trailer Available", textAlign = TextAlign.Center, color =  MaterialTheme.colorScheme.onBackground)
                                                }else{
                                                    movieVideoState.data?.let { ReelsView(it, popularSeriesVideo = null) }
                                                }

                                            }
                                            else if (isTvByCategories.value){
                                                if (seriesVideoState.data?.key?.isEmpty() == true){
                                                    Text(text = "No Trailer Available", textAlign = TextAlign.Center, color =  MaterialTheme.colorScheme.onBackground)
                                                }else{
                                                    seriesVideoState.data?.let { ReelsView(upcomingMovieVideo = null, it) }
                                                }

                                            }
                                        }
                                    }
                                }
                            }



                        }

                        1 -> {

                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                                // Show loading indicator if needed
                                when {
                                    isMovie.value && castCrewState.isLoading -> {
                                        CircularProgressIndicator()
                                        Log.d("DARLING", "MovieDetailScreen: MOVIE${isMovie.value} && ${castCrewState.isLoading}  ")
                                    }
                                    isSeries.value && seriesCastCrew.isLoading -> {
                                        CircularProgressIndicator()
                                        Log.d("DARLING", "MovieDetailScreen: SERIES${isSeries.value} && ${seriesCastCrew.isLoading}  ")
                                    }
                                    isTrendingOfWeek.value && castCrewState.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    isTrendingOfWeek.value&&seriesCastCrew.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    isMovieByCategories.value&&movieVideoState.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    isTvByCategories.value && seriesVideoState.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    else -> {
                                        // Show actual content when not loading
                                        if (isMovie.value){
                                            castCrewState.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val cast = castCrewState.data?.cast
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Cast",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground
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
                                        if (isSeries.value){
                                            seriesCastCrew.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val cast = seriesCastCrew.data?.cast
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Cast",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground
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
                                        if (isTrendingOfWeek.value){
                                            castCrewState.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val cast = castCrewState.data?.cast
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
                                            seriesCastCrew.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val cast = seriesCastCrew.data?.cast
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
                                        if(isTvByCategories.value){

                                            seriesCastCrew.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val cast = seriesCastCrew.data?.cast
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
                                        if(isMovieByCategories.value){
                                            castCrewState.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val cast = castCrewState.data?.cast
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Cast",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground
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
                            }
                         /*   if (isMovie.value){
                                if(castCrewState.isLoading){
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()

                                    }
                                }
                            }
                            if (isSeries.value){
                                if (seriesCastCrew.isLoading){
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()

                                    }
                                }
                            }


                                if (isMovie.value){
                                    castCrewState.data.let {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp)
                                        ) {
                                            val cast = castCrewState.data?.cast
                                                ?: emptyList() // Use an empty list as fallback if `cast` is null
                                            // Crew Section
                                            item {
                                                Text(
                                                    text = "Cast",
                                                    style = MaterialTheme.typography.headlineMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(bottom = 8.dp),
                                                    color = Color.White
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
                                if (isSeries.value){
                                    seriesCastCrew.data.let {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp)
                                        ) {
                                            val cast = seriesCastCrew.data?.cast
                                                ?: emptyList() // Use an empty list as fallback if `cast` is null
                                            // Crew Section
                                            item {
                                                Text(
                                                    text = "Cast",
                                                    style = MaterialTheme.typography.headlineMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(bottom = 8.dp),
                                                    color = Color.White
                                                )
                                            }
                                            items(cast) { castMember ->
                                                CastItem(
                                                    castMember
                                                )
                                            }


                                        }
                                    }
                                }*/




                        }

                        2 -> {
//
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                                // Show loading indicator if needed
                                when {
                                    isMovie.value && castCrewState.isLoading -> {
                                        CircularProgressIndicator()
                                        Log.d("DARLING", "MovieDetailScreen: MOVIE${isMovie.value} && ${castCrewState.isLoading}  ")
                                    }
                                    isSeries.value && seriesCastCrew.isLoading -> {
                                        CircularProgressIndicator()
                                        Log.d("DARLING", "MovieDetailScreen: SERIES${isSeries.value} && ${seriesCastCrew.isLoading}  ")
                                    }
                                    isTrendingOfWeek.value && castCrewState.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    isTrendingOfWeek.value&&seriesCastCrew.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    isMovieByCategories.value&&movieVideoState.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    isTvByCategories.value && seriesVideoState.isLoading->{
                                        CircularProgressIndicator()
                                    }
                                    else -> {
                                        // Show actual content when not loading
                                        if (isMovie.value){
                                            if(castCrewState.isLoading){
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    CircularProgressIndicator()

                                                }
                                            }
                                            castCrewState.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val crew = castCrewState.data?.crew
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    Log.d("MOVIEDETAILSCREEN", "MovieDetailScreen: "+crew)
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Crew",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground

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
                                        if (isSeries.value){
                                            if (seriesCastCrew.isLoading){
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    CircularProgressIndicator()

                                                }
                                            }
                                            seriesCastCrew.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val crew = seriesCastCrew.data?.crew
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Crew",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground

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
                                        if (isTrendingOfWeek.value){
                                            Log.d("MOVIEDETAILSCREEN", "MovieDetailScreen: Trendingof week")
                                            Log.d("MOVIEDETAILSCREEN", "MovieDetailScreen: Trendingof week"+isTrendingOfWeek.value)
                                            castCrewState.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val crew = castCrewState.data?.crew
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    Log.d("MOVIEDETAILSCREEN", "MovieDetailScreen: "+crew)
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Crew",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground
                                                        )
                                                    }
                                                    items(crew) { crewMember ->
                                                        CrewItem(
                                                            crewMember
                                                        )
                                                    }


                                                }
                                            }
                                            seriesCastCrew.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val crew = seriesCastCrew.data?.crew
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Crew",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground

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
                                        if (isMovieByCategories.value){
                                            castCrewState.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val crew = castCrewState.data?.crew
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    Log.d("MOVIEDETAILSCREEN", "MovieDetailScreen: "+crew)
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Crew",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground

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
                                        if(isTvByCategories.value){
                                            seriesCastCrew.data.let {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(16.dp)
                                                ) {
                                                    val crew = seriesCastCrew.data?.crew
                                                        ?: emptyList() // Use an empty list as fallback if `cast` is null
                                                    // Crew Section
                                                    item {
                                                        Text(
                                                            text = "Crew",
                                                            style = MaterialTheme.typography.headlineMedium,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(bottom = 8.dp),
                                                            color =  MaterialTheme.colorScheme.onBackground

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
                            }
                            //


                        }
                        else->{
                            Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

                                Text(text = "Something went wrong")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = if (isSelected) Color.Red else Color.White,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp)
            .fillMaxHeight()
    )
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)
class MockWatchListRepository: WatchlistRepository{
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
    val mockremoveMovieFromWatchlistUseCase = RemoveMovieFromWatchlistUseCase(MockWatchListRepository())

    // Mock Pager
    val mockPager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { FakePagingSource() } // Implement a fake PagingSource
    )

    // Mock ViewModel
    val mockViewModel = MovieScreenViewModel(
        movieUseCase = mockMovieUseCase,
        pager = mockPager
        , addMovieToWatchlistUseCase = mockaddMovieToWatchlistUseCase,
        isMovieInWatchlistUseCase = mockisMovieInWatchlistUseCase,
        removeMovieFromWatchlistUseCase = mockremoveMovieFromWatchlistUseCase

    )

    MovieAppTheme {
        MovieDetailScreen(
            systemUiController = systemUiController,
            modifier = Modifier,
            movieScreenViewModel = mockViewModel,
            innerPadding = PaddingValues(9.dp),
            statusBarColor = Color.Red,
            navHostController = navController
        )
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

    override suspend fun getTrendingOfWeek(): Pager<Int, TrendingOfWeek> {
        return  Pager( config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 1,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
            pagingSourceFactory ={
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

    override suspend fun getTvGenreList(): GenreResponseDto {
        return GenreResponseDto(genres = listOf())
    }
}

class DummyPagingSource : PagingSource<Int, PopularSeries>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularSeries> {
        val page = params.key ?: 1
        val dummyData = List(20) { index ->
            PopularSeries(
                 id= 1,
             genreIds= listOf(),
             imageUrl= "",
             originalTitle="",
            overview= "",
             releaseDate= "",
             adult= true,
             page=1,
             totalPages=2
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
                id= 1,
                overview= "",
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
fun CastItem(cast: com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.Cast) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = if (cast.profilePath.isNotEmpty()) {

                rememberAsyncImagePainter(model = cast.profilePath,
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

                rememberAsyncImagePainter(model = crew.profilePath,
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
        com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.Cast(

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