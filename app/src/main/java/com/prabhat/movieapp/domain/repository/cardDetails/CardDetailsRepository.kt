package com.prabhat.movieapp.domain.repository.cardDetails

import com.prabhat.movieapp.data.local.cardDetails.CardDetailsEntity

interface CardDetailsRepository {


    suspend fun saveCardDetails(cardDetailsEntity: CardDetailsEntity)

    suspend fun getCardDetails(): CardDetailsEntity?

    suspend fun clearCardDetails()
}