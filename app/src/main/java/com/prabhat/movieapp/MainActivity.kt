package com.prabhat.movieapp

import android.animation.ObjectAnimator
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.navigation.BottomNavigationDestination
import com.prabhat.movieapp.navigation.PerformNavigation
import com.prabhat.movieapp.presentation.screen.home.MovieScreenViewModel
import com.prabhat.movieapp.presentation.screen.splashScreen.SplashScreenViewModel
import com.prabhat.movieapp.ui.theme.Blur
import com.prabhat.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

//val Context.dataStore by dataStore("app-settings.json",AppSettingSerializer)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val splashScreenViewModel by viewModels<SplashScreenViewModel>()
    private val myViewModel: MovieScreenViewModel by viewModels()
    var currentDestinationName:String? = null

    private var shouldDisplayBottomBar = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            //here you can check token is valid

            setKeepOnScreenCondition {
                !splashScreenViewModel.isReady.value
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(screen.iconView, View.SCALE_X, 0.4f, 0.0f)
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                //zooming out y value
                val zoomY = ObjectAnimator.ofFloat(screen.iconView, View.SCALE_Y, 0.4f, 0.0f)
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        setContent {
            MovieAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {

                    val bottomNavDestinations = setOf(
                        com.prabhat.movieapp.navigation.BottomNavigationDestination.MovieHomeScreen,
                        BottomNavigationDestination.MovieDownloadScreen.route,
                        BottomNavigationDestination.MovieCategoriesScreen.route,
                        BottomNavigationDestination.MoreScreen.route
                    )
                    val currentDestination = navBackStackEntry?.destination?.route
                    Log.d("FUTURE", "onCreate: currentDestination $currentDestination")
                    Log.d(
                        "QWERTY",
                        "onCreate: ${BottomNavigationDestination.MovieHomeScreen.toString()}"
                    )
//                    val currentDestinationName = currentDestination?.substringAfterLast(".")
                     currentDestinationName = currentDestination?.substringAfterLast(".")

                    shouldDisplayBottomBar = when (currentDestinationName) {
                        BottomNavigationDestination.MovieHomeScreen.toString(),
                        BottomNavigationDestination.MovieDownloadScreen.toString(),
                        BottomNavigationDestination.MovieCategoriesScreen.toString(),
                        BottomNavigationDestination.MoreScreen.toString() -> true

                        else -> false
                    }
                    Log.d("FUTURE", "onCreate:shouldDisplayBottomBar $shouldDisplayBottomBar")
                    if (shouldDisplayBottomBar) {
                        MyBottomNavigation(navController = navController, shouldDisplay = true, onBackPressedDispatcher = onBackPressedDispatcher, context = this@MainActivity)
                    }


                }
                ) { innerPadding ->
                   /* if (shouldDisplayBottomBar) {
                        Box(
                            modifier = Modifier
                                .padding(PaddingValues(bottom = innerPadding.calculateBottomPadding()))
                                .background(
                                    Color.Green
                                )
                        ) {

                            MyBottomNavigation(
                                navController = navController,
                                shouldDisplay = true,
                                onBackPressedDispatcher = onBackPressedDispatcher,
                                context = this@MainActivity
                            )
                        }
                    }*/

                    var statusBarColor by remember { mutableStateOf(Color.Transparent) }

                    val systemUi = rememberSystemUiController()

//                    statusBarColor = if (isSystemInDarkTheme()) Color.Black else Color.White
                    statusBarColor = MaterialTheme.colorScheme.surface
                    systemUi.setStatusBarColor(statusBarColor)
                    val h = innerPadding
//                   IntroScreen(modifier = Modifier.padding(innerPadding))
                    val localActivity = staticCompositionLocalOf<ComponentActivity> {
                        error("LocalActivity is not present")
                    }
                    CompositionLocalProvider(localActivity provides this@MainActivity) {
                        Log.e("ActivityScopedViewModel", "Hashcode: ${myViewModel.hashCode()} : Activity Scope")

                        PerformNavigation(
                            navHostController = navController,
                            systemUiController = systemUi,
                            statusBarColor = statusBarColor,
                            innerPadding= innerPadding
                        )
                    }

                }
                val backStack = remember { mutableStateListOf<String>() }

                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { backStackEntry ->
                        backStackEntry.destination.route?.let { route ->
                            if (backStack.isEmpty() || backStack.last() != route) {
                                backStack.add(route)
                            }
                            Log.d("CHUBBY", "Full BackStack: $backStack")
                        }
                    }
                }
            }

        }


    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        Log.d("PRABHAT", "getOnBackInvokedDispatcher: Back button pressed")
        return super.getOnBackInvokedDispatcher()
    }

}

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Painter,
    val unSelectedIcon: Painter,
    val badgeCount: Int? = null,
    val currentScreen: BottomNavigationDestination
)

@Composable
fun MyBottomNavigation(
    navController: NavController,
    shouldDisplay: Boolean,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    context: Context
) {
    if (shouldDisplay) {

        val bottomItemList = listOf(
            BottomNavigationItem(
                title = "Home",
                selectedIcon = painterResource(id = R.drawable.selected_home),
                unSelectedIcon = painterResource(id = R.drawable.unselected_home),
                badgeCount = 0,
                currentScreen = BottomNavigationDestination.MovieHomeScreen
            ),

            BottomNavigationItem(
                title = "Categories",
                selectedIcon = painterResource(id = R.drawable.selected_note),
                unSelectedIcon = painterResource(id = R.drawable.unselected_note),
                badgeCount = 0,
                currentScreen = BottomNavigationDestination.MovieCategoriesScreen
            ),
            BottomNavigationItem(
                title = "Downloads",
                selectedIcon = painterResource(id = R.drawable.selected_download),
                unSelectedIcon = painterResource(id = R.drawable.unselected_download),
                badgeCount = 0,
                currentScreen = BottomNavigationDestination.MovieDownloadScreen
            ),
            BottomNavigationItem(
                title = "More",
                selectedIcon = painterResource(id = R.drawable.selected_element_plus),
                unSelectedIcon = painterResource(id = R.drawable.unselected_element_plus),
                badgeCount = 0,
                currentScreen = BottomNavigationDestination.MoreScreen
            ),
        )

        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        Log.d("PRABHAT", "MyBottomNavigation:selectedItemIndex $selectedItemIndex")
        // Back press handling to go to the previous tab
//        val context= LocalContext.current.getActivity()
        val local = context.getActivity()
        val currentDestination = navController.currentDestination?.route
        val currentDestinationName = currentDestination?.substringAfterLast(".")
        Log.d("TOMM", "MyBottomNavigation: main "+currentDestinationName)


            BackHandler(enabled = true) {

                if (selectedItemIndex > 0) {
                    Log.d("PRABHAT", "Navigating to previous tab")
                    selectedItemIndex -= 1
                    navController.navigate(bottomItemList[selectedItemIndex].currentScreen)
                } else {
                    Log.d("PRABHAT", "Back pressed, closing the activity")
//                onBackPressedDispatcher.onBackPressed()

//                context?.finish()
                    local?.finish()
                    // This will close the activity if at the first tab
                }
            }






        /*  BackHandler(enabled = selectedItemIndex > 0) {

              Log.d("PRABHAT", "MyBottomNavigation: Back pressed")
              // Change the selected index to the previous tab
              if (selectedItemIndex>0){
                  Log.d("PRABHAT", "iNSIDE IF MyBottomNavigation:selectedItemIndex $selectedItemIndex")
                  selectedItemIndex -= 1
                  // Navigate to the previous screen
                  navController.navigate(bottomItemList[selectedItemIndex].currentScreen)*//* {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }*//*
            }else{
                Log.d("PRABHAT", "MyBottomNavigation: eexit")
                (navController.context as? ComponentActivity)?.finish()

            }

        }*/



        NavigationBar(
            containerColor = Blur,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .clip(CircleShape)
        ) {

            bottomItemList.forEachIndexed { index, bottomNavigationItem ->

                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {

                        selectedItemIndex = index
                        navController.navigate(bottomNavigationItem.currentScreen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                    },
                    icon = {

                        BadgedBox(
                            badge = {
                                if (bottomNavigationItem.badgeCount != null) {
                                    /*Badge {
                                        Text(text = bottomNavigationItem.badgeCount.toString())
                                    }*/
                                }/* else if(bottomNavigationItem.hasNews) {
                                        Badge()
                                    }*/
                            }
                        ) {
                            Icon(
                                painter = if (index == selectedItemIndex) {

                                    Log.d("PRABHAT", "MyBottomNavigation: selected")
                                    bottomNavigationItem.selectedIcon
                                } else {
                                    Log.d("PRABHAT", "MyBottomNavigation: unselected")
                                    bottomNavigationItem.unSelectedIcon
                                }, contentDescription = bottomNavigationItem.title,
                                tint = Color.Unspecified // Prevent default tint

                            )
                        }
                    },
                    label = {
                        if (index == selectedItemIndex) {

                            Text(text = bottomNavigationItem.title, color = Color.White)
                        } else {
                            Text(text = bottomNavigationItem.title)
                        }


                    }

                )


            }

        }
    }

}