package com.prabhat.movieapp.domain.model.billingDetails

data class CardDetails(
    val firstName:String,
    val lastName:String,
    val cardNumber:String,
    val expirationDate:String,
    val securityCode: String
)
