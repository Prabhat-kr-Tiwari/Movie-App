package com.prabhat.movieapp.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.google.accompanist.systemuicontroller.SystemUiController
import com.prabhat.movieapp.presentation.screen.categories.MovieCategoriesScreen
import com.prabhat.movieapp.presentation.screen.downloads.MovieDownloadScreen
import com.prabhat.movieapp.presentation.screen.home.MovieCategory
import com.prabhat.movieapp.presentation.screen.home.MovieHomeScreen
import com.prabhat.movieapp.presentation.screen.home.MovieTag
import com.prabhat.movieapp.presentation.screen.home.movieDetail.LoadingScreen
import com.prabhat.movieapp.presentation.screen.home.movieDetail.MovieDetailScreen
import com.prabhat.movieapp.presentation.screen.introScreen.IntroScreen
import com.prabhat.movieapp.presentation.screen.loginScreen.LoginScreen
import com.prabhat.movieapp.presentation.screen.more.MoreScreen
import com.prabhat.movieapp.presentation.screen.more.accountScreen.accountScreenRoot
import com.prabhat.movieapp.presentation.screen.more.accountScreen.navigateToAccountScreen
import com.prabhat.movieapp.presentation.screen.more.settingsScreen.navigateToSettingScreen
import com.prabhat.movieapp.presentation.screen.more.settingsScreen.settingScreenRoot
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen.BillingDetailsScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.choosePaymentModeScreen.ChoosePaymentModeScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen.ChooseYourPlanScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen.PlanType
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.otpScreen.OtpScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.verifyPaymentScreen.VerifyPaymentScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.chooseAvatarScreen.ChooseAvatarScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.createPinScreen.CreatePinScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.enterPasswordScreen.EnterPasswordScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.enterUserNameScreen.EnterUserNameScreen
import com.prabhat.movieapp.presentation.screen.profileScreen.profileCreatedSuccessfullyScreen.ProfileCompleteScreen
import com.prabhat.movieapp.presentation.screen.signUpScreen.SignUpScreen
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf




object CustomNavType {

    val MovieTagType = object : NavType<MovieTag>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): MovieTag? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): MovieTag {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: MovieTag): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: MovieTag) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}
/*class CustomNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer:KSerializer<T>
) : NavType<T?>(isNullableAllowed = true){

    companion object{
        const val NULL = "null"
    }

    override fun get(bundle: Bundle, key: String): T? {
        return if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            bundle.getParcelable(key,clazz)
        }else{
            bundle.getParcelable(key)
        }

    }

    override fun parseValue(value: String): T? {
        return if(value==NULL) null else Json.decodeFromString(serializer,value)
    }

    override fun put(bundle: Bundle, key: String, value: T?) {
        bundle.putParcelable(key,value)
    }

    override fun serializeAsValue(value: T?): String {
        return value?.let { Json.encodeToString(serializer,it) }?:NULL
    }


}*/

@Composable
fun PerformNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    statusBarColor: Color,
    innerPadding: PaddingValues,
    startDestination: SubGraph
) {




    NavHost(
        navController = navHostController,
        startDestination = startDestination,
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
            composable<PlansAndPaymentDestination.ChooseYourPlanScreen> {
                ChooseYourPlanScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor,
                )

            }

            composable<PlansAndPaymentDestination.ChooseYourPaymentModeScreen>(
                typeMap = mapOf(
                    typeOf<PlanType>() to NavType.EnumType(PlanType::class.java),
                    ))

            {

                val selectedPlanType = it.toRoute<PlansAndPaymentDestination.ChooseYourPaymentModeScreen>().selectedPlan
                ChoosePaymentModeScreen(
                    selectedPlanType=selectedPlanType,
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )

            }
            composable<PlansAndPaymentDestination.BillingDetailsScreen>(
                typeMap = mapOf(
                    typeOf<PlanType>() to NavType.EnumType(PlanType::class.java)
                )
            ) {
                val selectedPlanType=it.toRoute<PlansAndPaymentDestination.BillingDetailsScreen>().selectedPlanType
                BillingDetailsScreen(
                    innerPaddingValues = innerPadding,
                    selectedPlanType = selectedPlanType,
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )

            }
            composable<PlansAndPaymentDestination.OtpScreen> {
                OtpScreen(
                    innerPaddingValues = innerPadding,
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )

            }
            composable<PlansAndPaymentDestination.VerifyPaymentScreen> {
                VerifyPaymentScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )

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
                    innerPaddingValues = innerPadding,
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor
                )
            }
            composable<ProfileDestination.EnterPasswordNameScreen> {
                EnterPasswordScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor,
                    innerPaddingValues = innerPadding
                )

            }
            composable<ProfileDestination.CreatePinScreen> {
                CreatePinScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor,
                    innerPaddingValues = innerPadding
                )

            }
            composable<ProfileDestination.ProfileCompleteScreen> {
                ProfileCompleteScreen(
                    navHostController = navHostController,
                    systemUiController = systemUiController,
                    statusBarColor = statusBarColor,
                    innerPaddingValues = innerPadding
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
                )


            }
            composable<BottomNavigationDestination.MovieCategoriesScreen> {

                MovieCategoriesScreen(navHostController = navHostController)

            }
            composable<BottomNavigationDestination.MovieDownloadScreen> {
                MovieDownloadScreen()


            }
            composable<BottomNavigationDestination.MoreScreen> {

                MoreScreen(onOpenAccount = {navHostController.navigateToAccountScreen() }, onOpenSettings = {navHostController.navigateToSettingScreen()}, innerPaddingValues = innerPadding)
            }
//

            accountScreenRoot(
                innerPaddingValues = innerPadding,
                onNavigateUp = {
                    navHostController.navigateUp()
                }
            )
            settingScreenRoot(innerPaddingValues = innerPadding, onNavigateUp =  { navHostController.navigateUp() })
        }

        /*navigation<HomeDestination.MovieDetailScreen>(startDestination = HomeDestination.MovieLoadingScreen) {
            composable<HomeDestination.MovieLoadingScreen> {

                LoadingScreen(
                    navHostController = navHostController,
                )
            }


        }*/
        composable<HomeDestination.MovieDetailScreen>(
            typeMap = mapOf(
                typeOf<MovieTag>() to CustomNavType.MovieTagType
                ,
                typeOf<MovieCategory>() to NavType.EnumType(MovieCategory::class.java)
            )
        ) {
            val movieTag = it.toRoute<HomeDestination.MovieDetailScreen>().movieTag
            val movieCategory = it.toRoute<HomeDestination.MovieDetailScreen>().movieCategory
            Log.d("PRABHU", "MovieDetailScreen: ${movieTag.id}  ${movieCategory.toString()}")

            MovieDetailScreen(
                movieId = movieTag.id,
                movieCategory =movieCategory,
                systemUiController = systemUiController,
                statusBarColor = statusBarColor,
                innerPadding = innerPadding,
                navHostController = navHostController
            )


        }






    }


}