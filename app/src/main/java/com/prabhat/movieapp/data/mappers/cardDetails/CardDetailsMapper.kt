package com.prabhat.movieapp.data.mappers.cardDetails

import com.prabhat.movieapp.data.local.cardDetails.CardDetailsEntity
import com.prabhat.movieapp.domain.model.billingDetails.CardDetails

fun CardDetails.toCardDetailsEntity(): CardDetailsEntity {
    return CardDetailsEntity(
        firstName = firstName,
        lastName = lastName,
        cardNumber = cardNumber,
        expirationDate = expirationDate,
        securityCode = securityCode
    )
}