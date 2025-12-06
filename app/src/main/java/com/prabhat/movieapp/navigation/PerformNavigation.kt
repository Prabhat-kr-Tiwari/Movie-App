package com.prabhat.movieapp.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.accompanist.systemuicontroller.SystemUiController
import com.prabhat.movieapp.presentation.screen.categories.MovieCategoriesScreen
import com.prabhat.movieapp.presentation.screen.downloads.DownloadScreenViewModel
import com.prabhat.movieapp.presentation.screen.downloads.MovieDownloadScreen
import com.prabhat.movieapp.presentation.screen.home.MovieHomeScreen
import com.prabhat.movieapp.presentation.screen.home.MovieScreenViewModel
import com.prabhat.movieapp.presentation.screen.home.movieDetail.LoadingScreen
import com.prabhat.movieapp.presentation.screen.home.movieDetail.MovieDetailScreen
import com.prabhat.movieapp.presentation.screen.introScreen.IntroScreen
import com.prabhat.movieapp.presentation.screen.loginScreen.LoginScreen
import com.prabhat.movieapp.presentation.screen.more.MoreScreen
import com.prabhat.movieapp.presentation.screen.more.accountScreen.accountScreenRoot
import com.prabhat.movieapp.presentation.screen.more.accountScreen.navigateToAccountScreen
import com.prabhat.movieapp.presentation.screen.more.settingsScreen.navigateToSettingScreen
import com.prabhat.movieapp.presentation.screen.more.settingsScreen.settingScreenRoot
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.PaymentFlowWrapper
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen.BillingDetailsScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.choosePaymentModeScreen.ChoosePaymentModeScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen.ChooseYourPlanScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.otpScreen.OtpScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.verifyPaymentScreen.VerifyPaymentScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.chooseAvatarScreen.ChooseAvatarScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.createPinScreen.CreatePinScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.enterPasswordScreen.EnterPasswordScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.enterUserNameScreen.EnterUserNameScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.profileCreatedSuccessfullyScreen.ProfileCompleteScreen
import com.prabhat.movieapp.presentation.screen.signUpScreen.SignUpScreen
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

class CustomNavType<T : Parcelable>(
    private val clazz: KClass<T>,
    private val serializer: KSerializer<T>
) : NavType<T>(false) {
    override fun get(bundle: Bundle, key: String): T? {

        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz.java)
        } else {

            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString(serializer, value)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: T): String {
        return Json.encodeToString(serializer, value)
    }


}

@Composable
fun PerformNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color,
    innerPadding: PaddingValues,
//    movieScreenViewModel: MovieScreenViewModel = hiltViewModel()
) {

    val movieScreenViewModel: MovieScreenViewModel = hiltViewModel()
    val downloadScreenViewModel: DownloadScreenViewModel = hiltViewModel()

//    val navController = rememberNavController()


    NavHost(
        navController = navHostController,
        startDestination = SubGraph.onBoarding,
//        modifier = Modifier.padding(innerPadding)

    ) {


        navigation<SubGraph.onBoarding>(startDestination = Destination.IntroScreen) {

            composable<Destination.IntroScreen> {


                IntroScreen(navHostController = navHostController)


            }
            composable<Destination.SignUpScreen> {


                SignUpScreen(navHostController = navHostController)
            }
            composable<Destination.LoginScreen> {

                LoginScreen(navHostController = navHostController)

            }


        }

        navigation<SubGraph.PlansAndPayment>(startDestination = PlansAndPaymentDestination.ChooseYourPlanScreen) {
            composable<PlansAndPaymentDestination.ChooseYourPlanScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                }
            ) {

                /*ChooseYourPlanScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )*/
                PaymentFlowWrapper(
                    currentStep = 1,
                    navHostController,
                    systemUiController,
                    statusBarColor
                ) {
                    ChooseYourPlanScreen(
                        navHostController = navHostController,
                        systemUiController = systemUiController,
                        statusBarColor = statusBarColor
                    )
                }

            }
            composable<PlansAndPaymentDestination.ChooseYourPaymentModeScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                }
            ){
                PaymentFlowWrapper(
                    currentStep = 2,
                    navHostController,
                    systemUiController,
                    statusBarColor
                ) {
                    ChoosePaymentModeScreen(
                        navHostController = navHostController,
                        systemUiController = systemUiController,
                        statusBarColor = statusBarColor
                    )
                }


            }
            composable<PlansAndPaymentDestination.BillingDetailsScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                }
            ) {
                PaymentFlowWrapper(
                    currentStep = 3,
                    navHostController,
                    systemUiController,
                    statusBarColor
                ) {
                    BillingDetailsScreen(
                        navHostController = navHostController,
                        systemUiController = systemUiController,
                        statusBarColor = statusBarColor
                    )
                }


            }
            composable<PlansAndPaymentDestination.OtpScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                }
            ) {
                PaymentFlowWrapper(
                    currentStep = 4,
                    navHostController,
                    systemUiController,
                    statusBarColor
                ) {
                    OtpScreen(
                        navHostController = navHostController,
                        systemUiController = systemUiController,
                        statusBarColor = statusBarColor
                    )
                }


            }
            composable<PlansAndPaymentDestination.VerifyPaymentScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                }
            ) {
                PaymentFlowWrapper(
                    currentStep = 5,
                    navHostController,
                    systemUiController,
                    statusBarColor
                ) {

                    VerifyPaymentScreen(
                        navHostController = navHostController,
                        systemUiController = systemUiController,
                        statusBarColor = statusBarColor
                    )
                }


            }
        }
        navigation<SubGraph.Profile>(startDestination = ProfileDestination.ChooseAvatarScreen) {

            composable<ProfileDestination.ChooseAvatarScreen> {

                ChooseAvatarScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )
            }
            composable<ProfileDestination.EnterUserNameScreen> {
                EnterUserNameScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )
            }
            composable<ProfileDestination.EnterPasswordNameScreen> {
                EnterPasswordScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )

            }
            composable<ProfileDestination.CreatePinScreen> {
                CreatePinScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )

            }
            composable<ProfileDestination.ProfileCompleteScreen> {
                ProfileCompleteScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )

            }

        }


        navigation<SubGraph.Home>(startDestination = BottomNavigationDestination.MovieHomeScreen) {
            composable<BottomNavigationDestination.MovieHomeScreen> {
                MovieHomeScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor,
                    innerPadding = innerPadding,
                    movieScreenViewModel = movieScreenViewModel
                )


            }
            composable<BottomNavigationDestination.MovieCategoriesScreen> {

                MovieCategoriesScreen(
                    movieScreenViewModel = movieScreenViewModel,
                    navHostController = navHostController
                )

            }
            composable<BottomNavigationDestination.MovieDownloadScreen> {
                MovieDownloadScreen(downloadScreenViewModel = downloadScreenViewModel)


            }
            composable<BottomNavigationDestination.MoreScreen> {

                MoreScreen(
                    onOpenAccount = { navHostController.navigateToAccountScreen() },
                    onOpenSettings = { navHostController.navigateToSettingScreen() },
                    innerPaddingValues = innerPadding
                )
            }
//            moreScreen(onOpenAccount = {navHostController.navigateToAccountScreen() }, onOpenSettings = {navHostController.navigateToSettingScreen()})
            /* composable<BottomNavigationDestination.MovieDetailScreen>(typeMap = mapOf(
                 typeOf<UpComingMovieResponse>() to CustomNavType<UpComingMovieResponse>(
                     UpComingMovieResponse::class,
                     UpComingMovieResponse.serializer()
                 )
             )){
                 val movie = it.toRoute<BottomNavigationDestination.MovieDetailScreen>()

                 MovieDetailScreen(movie.upComingMovieResponse)
             }*/
            /* composable<HomeDestination.MovieDetailScreen>(   ) {
                 val movie = it.toRoute<Int>()

                 MovieDetailScreen(movie)


             }*/

            accountScreenRoot(
                innerPaddingValues = innerPadding,
                onNavigateUp = {
                    navHostController.navigateUp()
                }
            )
            settingScreenRoot(
                innerPaddingValues = innerPadding,
                onNavigateUp = { navHostController.navigateUp() })
        }
        /*navigation<SubGraph.MovieDetailsScreen>(startDestination = HomeDestination.MovieHomeScreen){

            composable<HomeDestination.MovieDetailScreen>(   typeMap = mapOf(
                typeOf<UpComingMovieResponse>() to CustomNavType<UpComingMovieResponse>(
                    UpComingMovieResponse::class,
                    UpComingMovieResponse.serializer()
                )
            )) {
                val movie = it.toRoute<HomeDestination.MovieDetailScreen>()

                MovieDetailScreen(movie.upComingMovieResponse)


            }
        }*/
        navigation<BottomNavigationDestination.MovieHomeScreen>(startDestination = HomeDestination.MovieLoadingScreen) {
            composable<HomeDestination.MovieLoadingScreen> {

                LoadingScreen(
                    navHostController = navHostController,
                    movieScreenViewModel = movieScreenViewModel
                )
            }

            composable<HomeDestination.MovieDetailScreen>() {
//                val movie = it.toRoute<Int>()

                MovieDetailScreen(
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor,
                    innerPadding = innerPadding,
                    movieScreenViewModel = movieScreenViewModel,
                    navHostController = navHostController
                )


            }
        }
        /* navigation<SubGraph.MoreAndSetting>(startDestination = BottomNavigationDestination.MoreScreen.route) {
             accountScreenRoot { navHostController.navigateUp() }
             settingScreenRoot { navHostController.navigateUp() }
         }*/


    }


}