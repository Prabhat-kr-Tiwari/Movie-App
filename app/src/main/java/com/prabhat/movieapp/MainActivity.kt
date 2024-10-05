package com.prabhat.movieapp

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.navigation.PerformNavigation
import com.prabhat.movieapp.presentation.screen.splashScreen.SplashScreenViewModel
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

//val Context.dataStore by dataStore("app-settings.json",AppSettingSerializer)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val splashScreenViewModel by viewModels<SplashScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            //here you can check token is valid

            setKeepOnScreenCondition{
                !splashScreenViewModel.isReady.value
            }
            setOnExitAnimationListener{screen->
                val zoomX= ObjectAnimator.ofFloat(screen.iconView, View.SCALE_X,0.4f,0.0f)
                zoomX.interpolator= OvershootInterpolator()
                zoomX.duration=500L
                zoomX.doOnEnd { screen.remove() }

                //zooming out y value
                val zoomY= ObjectAnimator.ofFloat(screen.iconView, View.SCALE_Y,0.4f,0.0f)
                zoomY.interpolator= OvershootInterpolator()
                zoomY.duration=500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        setContent {
            MovieAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var statusBarColor by remember { mutableStateOf(Color.Transparent) }

                    val systemUi = rememberSystemUiController()

                    statusBarColor = if (isSystemInDarkTheme()) Color.Black else Color.Black
                    systemUi.setStatusBarColor(statusBarColor)
                    val h=innerPadding
//                   IntroScreen(modifier = Modifier.padding(innerPadding))
                    PerformNavigation(systemUiController=systemUi, statusBarColor = statusBarColor)
                }
            }
        }
    }
}