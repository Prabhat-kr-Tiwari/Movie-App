package com.prabhat.movieapp.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prabhat.movieapp.presentation.screen.introScreen.IntroScreen
import com.prabhat.movieapp.presentation.screen.loginScreen.LoginScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen.BillingDetailsScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.choosePaymentModeScreen.ChoosePaymentModeScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen.ChooseYourPlanScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.otpScreen.OtpScreen
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.verifyPaymentScreen.VerifyPaymentScreen
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
fun PerformNavigation(modifier: Modifier = Modifier,systemUiController: SystemUiController,statusBarColor: Color) {


    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = SubGraph.onBoarding) {


        navigation<SubGraph.onBoarding>(startDestination = Destination.IntroScreen) {

            composable<Destination.IntroScreen> {


                IntroScreen(navHostController = navController)


            }
            composable<Destination.SignUpScreen> {


                SignUpScreen(navHostController = navController)
            }
            composable<Destination.LoginScreen> {

                LoginScreen(navHostController = navController)

            }


        }

        navigation<SubGraph.PlansAndPayment>(startDestination = PlansAndPaymentDestination.ChooseYourPlanScreen) {
            composable<PlansAndPaymentDestination.ChooseYourPlanScreen> {
                ChooseYourPlanScreen(navHostController = navController, systemUiController =systemUiController, statusBarColor = statusBarColor)

            }
            composable<PlansAndPaymentDestination.ChooseYourPaymentModeScreen> {
                ChoosePaymentModeScreen(navHostController = navController, systemUiController =systemUiController,statusBarColor = statusBarColor)

            }
            composable<PlansAndPaymentDestination.BillingDetailsScreen> {
                BillingDetailsScreen(navHostController = navController, systemUiController =systemUiController,statusBarColor = statusBarColor)

            }
            composable<PlansAndPaymentDestination.OtpScreen> {
                OtpScreen(navHostController = navController, systemUiController =systemUiController,statusBarColor = statusBarColor)

            }
            composable<PlansAndPaymentDestination.VerifyPaymentScreen> {
                VerifyPaymentScreen( systemUiController =systemUiController,statusBarColor = statusBarColor)

            }
        }


    }


}