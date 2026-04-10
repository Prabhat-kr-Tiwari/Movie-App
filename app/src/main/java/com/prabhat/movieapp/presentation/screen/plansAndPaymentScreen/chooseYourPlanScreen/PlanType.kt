package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
enum class PlanType(
    val displayName: String,
    val price: String
) : Parcelable {
    Series("Series", "$15/month"),
    Movies("Movies", "$10/month"),
    MovieSeries("Movies & Series", "$20/month")
}