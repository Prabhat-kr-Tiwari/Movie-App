package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen

sealed  class BillingDetailsNavigationEvent {
    object NavigateNext: BillingDetailsNavigationEvent()
    object NavigateBack: BillingDetailsNavigationEvent()
}