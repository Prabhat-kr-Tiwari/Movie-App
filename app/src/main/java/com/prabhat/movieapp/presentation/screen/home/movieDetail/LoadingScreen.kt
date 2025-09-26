package com.prabhat.movieapp.presentation.screen.home.movieDetail

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.prabhat.movieapp.navigation.BottomNavigationDestination
import com.prabhat.movieapp.navigation.HomeDestination
import com.prabhat.movieapp.navigation.SubGraph
import com.prabhat.movieapp.presentation.screen.home.MovieScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    movieScreenViewModel: MovieScreenViewModel
//    movieScreenViewModel: MovieScreenViewModel = hiltViewModel()
) {


    BackHandler(enabled = true) {
        val previousDestination = navHostController.previousBackStackEntry?.destination?.route

        Log.d("CHUBBY", "LoadingScreen: previousDestination "+previousDestination)
        when(previousDestination){
            "MovieHomeScreen" -> {
                navHostController.popBackStack(BottomNavigationDestination.MovieHomeScreen, false)
            }

        }
    }
    // Observe the selectedMovie state
//    val selectedMovie = movieScreenViewModel.selectedMovie.collectAsStateWithLifecycle()
    val selectedMovie = movieScreenViewModel.selectedMovie.value
    Log.d("TAG", "LoadingScreen:selectedMovie "+selectedMovie)

    val  selectedSeries = movieScreenViewModel.selectedSeries.value
    val context = LocalContext.current

    val selectedTrendingOfWeek =  movieScreenViewModel.selectedTrendingOfWeek.value
    val selectedMovieByCategories= movieScreenViewModel.selectedMovieByCategories
    val selectedTvByCategories = movieScreenViewModel.selectedTvByCategories

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {


        CircularProgressIndicator(color = Color.White)

        // Trigger navigation when selectedMovie changes
//        LaunchedEffect(Unit) {
        LaunchedEffect(   selectedMovie,
            selectedSeries,
            selectedTrendingOfWeek,
            selectedMovieByCategories.value,
            selectedTvByCategories.value) {
            /*selectedMovie?.let { movie ->
                Log.d("ALEXA", "LoadingScreen: LaunchedEffect"+movie)
                navHostController.navigate(HomeDestination.MovieDetailScreen(movie.id))
            }*/
            if(selectedMovie!=null){
                navHostController.navigate(HomeDestination.MovieDetailScreen){
                    popUpTo(HomeDestination.MovieLoadingScreen){inclusive = true}

                }

            }else{
                Log.d("PRABHU", "LoadingScreen: "+null)
            }
            //selected series
            if (selectedSeries!=null){
                navHostController.navigate(HomeDestination.MovieDetailScreen){
                    popUpTo(HomeDestination.MovieLoadingScreen){inclusive = true}
                }
            }else{
                Toast.makeText(context, "No series selected", Toast.LENGTH_SHORT).show()
            }
            if (selectedTrendingOfWeek!=null){
                navHostController.navigate(HomeDestination.MovieDetailScreen){
                    popUpTo(HomeDestination.MovieLoadingScreen){inclusive = true}
                }
            }else{
                Toast.makeText(context, "No trending of week selected", Toast.LENGTH_SHORT).show()

            }
            if (selectedMovieByCategories!=null){
                navHostController.navigate(HomeDestination.MovieDetailScreen){

                    popUpTo(HomeDestination.MovieLoadingScreen){inclusive = true}
                }
            }
            else{
                Toast.makeText(context, "No selected movie by categories", Toast.LENGTH_SHORT).show()
            }
            if (selectedTvByCategories!=null){
                Log.d("RAAM", "LoadingScreen:selectedTvByCategories ")
                navHostController.navigate(HomeDestination.MovieDetailScreen){
                    popUpTo(HomeDestination.MovieLoadingScreen){inclusive = true}
                }
            }else{
                Toast.makeText(context, "No selected tv by categories", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoadingScreen(modifier: Modifier = Modifier) {
    val navController= rememberNavController()

//    LoadingScreen(navHostController = navController)
}