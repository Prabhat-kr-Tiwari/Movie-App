package com.prabhat.movieapp.presentation.screen.downloads

import android.R.attr.disabledAlpha
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.prabhat.movieapp.R
import com.prabhat.movieapp.domain.model.watchList.Movie
import com.prabhat.movieapp.domain.use_case.watchList.RemoveMovieFromWatchlistUseCase
import com.prabhat.movieapp.domain.use_case.watchList.WatchListUseCase
import com.prabhat.movieapp.presentation.components.CustomLinearProgressBar
import com.prabhat.movieapp.presentation.screen.categories.TabItem
import com.prabhat.movieapp.presentation.screen.home.movieDetail.MockWatchListRepository
import com.prabhat.movieapp.presentation.screen.introScreen.HorizontalPagerContent
import com.prabhat.movieapp.ui.theme.GreyLight
import com.prabhat.movieapp.ui.theme.MovieAppTheme

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieDownloadScreen(modifier: Modifier = Modifier,downloadScreenViewModel: DownloadScreenViewModel) {
    val onTvSelected = rememberSaveable { mutableStateOf(false) }
    val onMovieByCategoriesSelected = rememberSaveable { mutableStateOf(false) }
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
    val watchList = downloadScreenViewModel.watchlistState.value
    
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val tabItem = listOf(
            TabItem(
                title = "Download",
                selectedIcon = Icons.Outlined.Home,
                unSelectedIcon = Icons.Outlined.Home
            ),
            TabItem(
                title = "Watchlist",
                selectedIcon = Icons.Outlined.Home,
                unSelectedIcon = Icons.Outlined.Home
            )
        )

        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }
        val pagerState = rememberPagerState {
            tabItem.size
        }
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
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
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,

            ) { index ->


            when (index) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)

                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val list = getDownloadMovieList()
                        LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
                            itemsIndexed(list) { index, movie ->
                                Spacer(modifier = Modifier.height(10.dp))
                                DownloadItem(movie)
                            }
                        }


                    }
                }

                1 -> {


                    Column(
                        modifier = Modifier.padding(10.dp)
                            .fillMaxSize()
                           /* .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally*/
                    ) {
                        downloadScreenViewModel.loadWatchlist()
                        val list = watchList.data!!
                        LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
                            itemsIndexed(list) { index, movie ->
                                Spacer(modifier = Modifier.height(10.dp))
                                WatchListItem(movie){
                                    downloadScreenViewModel.removeMovie(movieId = movie.movieId)
                                    downloadScreenViewModel.loadWatchlist()
                                }
                            }
                        }


                    }

                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        Text(text = "Something went wrong")
                    }
                }
            }
        }

    }
}

//@ThemeAnnotation
@Composable
fun DownloadItem(downloadedMovie: DownloadedMovie) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    width = 0.1.dp, if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
            )
    ) {
        Row(
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                .background(com.prabhat.movieapp.ui.theme.MaterialTheme.colorScheme.surface)
        ) {
            val disabledAlpha = 0.38f


//            AsyncImage(painter)
            Image(
                painter = painterResource(id = R.drawable.captain_america_image),
                contentDescription = stringResource(id = R.string.app_name)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(8.dp),
                    .padding(com.prabhat.movieapp.ui.theme.MaterialTheme.spacing.small),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
//                    text = "Spider Man: HomeComing",
                    text = downloadedMovie.movieName,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp, textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                var progressCount by remember { mutableIntStateOf(0) }
                progressCount = 10
                Row(
                    Modifier
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    // State to track if the animation is running
                    var isAnimating by remember { mutableStateOf(false) }
                    CustomLinearProgressBar(
                        modifier = Modifier.weight(1f),
                        progressCount = downloadedMovie.watchProgress,
                        backgroundColor = GreyLight,
                        foreGroundColor = if (downloadedMovie.watchProgress == 10) Color.Green else Red,
                        showProgressPercentage = false,
                        // Update the isAnimating state based on the callback
                        onAnimationRunning = { running ->
                            isAnimating = running
                        }
                    )



                    Text(
                        text = "1.2GB",
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Default, // or your custom Inter fontFamily
                        modifier = Modifier.padding(top = 4.dp, start = 40.dp),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                    )

                }
                Text(
                    text = "${(downloadedMovie.watchProgress * 10).toInt()}% Watched ",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp, textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                IconButton(
                    onClick = {},

                    modifier = Modifier
                        .align(Alignment.End)
                        .clip(
                            RoundedCornerShape(14.dp)
                        )
                        .background(color = MaterialTheme.colorScheme.onError),

                    colors = IconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = disabledAlpha),
                        disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = disabledAlpha)
                    )


                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "My Icon",


                        )
                }
                Spacer(
                    Modifier
                        .height(8.dp)
                        .background(color = Color.White)
                )
            }
        }
    }

}
@Composable
fun WatchListItem(movie: Movie, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    width = 0.1.dp, if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
            )
    ) {
        Row(
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                .background(com.prabhat.movieapp.ui.theme.MaterialTheme.colorScheme.surface)
        ) {
            val disabledAlpha = 0.38f


//            AsyncImage(painter)
           /* Image(
                painter = painterResource(id = R.drawable.captain_america_image),
                contentDescription = stringResource(id = R.string.app_name)
            )*/
            AsyncImage(model =movie.imageUrl, contentDescription = "Image" )
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(8.dp),
                    .padding(com.prabhat.movieapp.ui.theme.MaterialTheme.spacing.small),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = movie.title,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp, textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = movie.description,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp, textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )





                Spacer(
                    Modifier
                        .height(8.dp)
                        .background(color = Color.White)
                )
            }
        }
        IconButton(
            onClick = {
                onDelete()
            },

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(color = MaterialTheme.colorScheme.onError),

            colors = IconButtonColors(
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.errorContainer,
                disabledContainerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = disabledAlpha.toFloat()),
                disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = disabledAlpha.toFloat())
            )


        ) {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "My Icon",


                )
        }
    }

}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,


    )
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO

)
annotation class ThemeAnnotation


@ThemeAnnotation // Your custom annotation grouping Previews
@Composable
private fun DownloadItemPreview() { // Create a private wrapper composable for preview
    MovieAppTheme { // <<< WRAP with your actual theme!
        DownloadItem(
            downloadedMovie = DownloadedMovie(   image = R.drawable.avenger_image,
                desc = "All your favourite MARVEL Movies and Series at one Place",
                watchProgress = 4,
                movieName = "Avenger")
        ) // Call the composable you want to preview *inside* the theme
    }
}
@ThemeAnnotation // Your custom annotation grouping Previews
@Composable
private fun WatchListItemPreview() { // Create a private wrapper composable for preview
    MovieAppTheme { // <<< WRAP with your actual theme!
        WatchListItem(
          movie = Movie(id = 1, movieId = 2, title = "Avenger", description = "A gothic tale of obsession between a haunted young woman and the terrifying vampire infatuated with her, causing untold horror in its wake what is your name and how to call.", imageUrl = "")
        ) {}// Call the composable you want to preview *inside* the theme
    }
}

@ThemeAnnotation // Your custom annotation grouping Previews
@Composable
private fun MovieDownloadScreenPreview() { // Create a private wrapper composable for preview
    val watchListUseCase = WatchListUseCase(MockWatchListRepository())
    val mockremoveMovieFromWatchlistUseCase = RemoveMovieFromWatchlistUseCase(MockWatchListRepository())

    val downloadScreenviewmode = DownloadScreenViewModel(watchListUseCase = watchListUseCase, removeMovieFromWatchlistUseCase = mockremoveMovieFromWatchlistUseCase)
    MovieAppTheme { // <<< WRAP with your actual theme!
        MovieDownloadScreen(downloadScreenViewModel = downloadScreenviewmode) // Call the composable you want to preview *inside* the theme
    }
}

data class DownloadedMovie(
    @DrawableRes val image: Int,
    val movieName: String,
    val desc: String,
    val watchProgress: Int
)

fun getDownloadMovieList(): List<DownloadedMovie> {
    return listOf(
        DownloadedMovie(

            image = R.drawable.avenger_image,
            desc = "All your favourite MARVEL Movies and Series at one Place",
            watchProgress = 4,
            movieName = "Avenger"
        ),
        DownloadedMovie(

           image =  R.drawable.deadpool_image,
           desc =  "Watch Online or Download offline",
            watchProgress = 5,
            movieName = "DeadPool"
        ),
        DownloadedMovie(

           image =  R.drawable.circle_man_image,
            desc = "Create Profiles for different memeber and get personalised recommendation",
            watchProgress = 10,
            movieName = "Illusion"
        ),
        DownloadedMovie(

            image = R.drawable.iron_man_image,
           desc =  "Plans according to your needs and at affordable price",
                   watchProgress = 6,
            movieName = "Illusion"
        ),
        DownloadedMovie(

            image = R.drawable.thor_image,
            desc = "Lets get Started",
            watchProgress = 6,
            movieName = "Illusion"
        ),
        DownloadedMovie(

           image =  R.drawable.captain_america_image,
            desc = "Plans according to your needs and at affordable price",
            watchProgress = 6,
            movieName = "Illusion"
        )
    )
}