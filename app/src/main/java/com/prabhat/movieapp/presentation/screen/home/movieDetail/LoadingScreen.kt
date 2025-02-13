package com.prabhat.movieapp.presentation.screen.home.movieDetail

import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.prabhat.movieapp.navigation.HomeDestination
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
    // Observe the selectedMovie state
//    val selectedMovie = movieScreenViewModel.selectedMovie.collectAsStateWithLifecycle()
    val selectedMovie = movieScreenViewModel.selectedMovie.value
    Log.d("TAG", "LoadingScreen:selectedMovie "+selectedMovie)
    LaunchedEffect(true) {
        val selectedMovie2 = movieScreenViewModel.selectedMovie.value
        Log.d("PRABHU", "LoadingScreen: selectedMovie2"+selectedMovie2)

    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {


        CircularProgressIndicator(color = Color.White)

        // Trigger navigation when selectedMovie changes
        LaunchedEffect(Unit) {
            /*selectedMovie?.let { movie ->
                Log.d("ALEXA", "LoadingScreen: LaunchedEffect"+movie)
                navHostController.navigate(HomeDestination.MovieDetailScreen(movie.id))
            }*/
            if(selectedMovie!=null){
                navHostController.navigate(HomeDestination.MovieDetailScreen)

            }else{
                Log.d("PRABHU", "LoadingScreen: "+null)
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