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
    data object Profile : SubGraph()

    @Serializable
    data object Home : SubGraph()

    @Serializable
    data object Categories : SubGraph()


    @Serializable
    data object Downloads : SubGraph()


    @Serializable
    data object MoreAndSetting : SubGraph()

    /* @Serializable
     data object MovieDetailsScreen:SubGraph()*/
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

sealed class PlansAndPaymentDestination {
    @Serializable
    data object ChooseYourPlanScreen : PlansAndPaymentDestination()

    @Serializable
    data object ChooseYourPaymentModeScreen : PlansAndPaymentDestination()

    @Serializable
    data object BillingDetailsScreen : PlansAndPaymentDestination()

    @Serializable
    data object OtpScreen : PlansAndPaymentDestination()

    @Serializable
    data object VerifyPaymentScreen : PlansAndPaymentDestination()
}

sealed class ProfileDestination {

    @Serializable
    data object ChooseAvatarScreen : ProfileDestination()

    @Serializable
    data object EnterUserNameScreen : ProfileDestination()

    @Serializable
    data object EnterPasswordNameScreen : ProfileDestination()

    @Serializable
    data object CreatePinScreen : ProfileDestination()

    @Serializable
    data object ProfileCompleteScreen : ProfileDestination()

}

/*sealed class HomeDestination{

    @Serializable
    data object MovieHomeScreen:HomeDestination()

    @Serializable
    data class MovieDetailScreen(val upComingMovieResponse: UpComingMovieResponse):HomeDestination()
}*/
@Serializable
sealed class HomeDestination {

    @Serializable
    data object MovieLoadingScreen : HomeDestination()

    /*   @Serializable
       data class MovieDetailScreen(val id: Int) : HomeDestination()*/
    @Serializable
    data object MovieDetailScreen : HomeDestination()
}

sealed class CategoriesDestination {
    @Serializable
    data object MovieCategoriesScreen : CategoriesDestination()

    @Serializable
    data object MovieCategoriesDetailScreen : CategoriesDestination()
}

sealed class DownloadDestination {
    @Serializable
    data object MovieDownloadScreen : DownloadDestination()
}

sealed class MoreDestination {
    @Serializable
    data object MoreScreen : MoreDestination()
}

@Serializable
sealed class BottomNavigationDestination(val route: String) {
    @Serializable
    data object MovieHomeScreen : BottomNavigationDestination("home")

    @Serializable
    data object MovieCategoriesScreen : BottomNavigationDestination("categories")

    @Serializable
    data object MovieDownloadScreen : BottomNavigationDestination("downloads")

    @Serializable
    data object MoreScreen : BottomNavigationDestination("more")


    /*   @Serializable
       data class MovieDetailScreen(val upComingMovieResponse: UpComingMovieResponse):BottomNavigationDestination(route = "movieDetail")*/


}

