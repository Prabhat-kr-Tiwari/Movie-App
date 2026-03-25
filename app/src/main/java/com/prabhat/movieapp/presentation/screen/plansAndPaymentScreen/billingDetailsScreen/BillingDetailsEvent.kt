package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen

sealed  class BillingDetailsEvent {


    object ContinueClicked: BillingDetailsEvent()
    object  ChangeClicked: BillingDetailsEvent()

}