package com.prabhat.movieapp.navigation

import kotlinx.serialization.Serializable
/*
@Serializable
sealed class Screen(val route:String) {


    @Serializable
    object SignUpScreen:Screen("SignUpScreen")
    @Serializable
    object LoginScreen:Screen("LoginScreen")
    @Serializable
    object IntroScreen:Screen("IntroScreen")

}*/
sealed class SubGraph {


    @Serializable
    data object onBoarding : SubGraph()

    @Serializable
    data object PlansAndPayment : SubGraph()


    @Serializable
    data object Profile: SubGraph()

    @Serializable
    data object Home: SubGraph()

    @Serializable
    data object Categories: SubGraph()


    @Serializable
    data object Downloads: SubGraph()


    @Serializable
    data object MoreAndSetting: SubGraph()

}


sealed class Destination {
    //onboarding
    @Serializable
    data object IntroScreen : Destination()
    @Serializable
    data object SignUpScreen : Destination()
    @Serializable
    data object LoginScreen : Destination()
}
sealed class PlansAndPaymentDestination{
    @Serializable
    data object ChooseYourPlanScreen:PlansAndPaymentDestination()
    @Serializable
    data object ChooseYourPaymentModeScreen:PlansAndPaymentDestination()
    @Serializable
    data object BillingDetailsScreen:PlansAndPaymentDestination()
    @Serializable
    data object OtpScreen:PlansAndPaymentDestination()
    @Serializable
    data object VerifyPaymentScreen:PlansAndPaymentDestination()
}