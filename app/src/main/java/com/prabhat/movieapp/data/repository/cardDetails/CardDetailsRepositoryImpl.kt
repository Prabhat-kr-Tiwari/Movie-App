package com.prabhat.movieapp.data.repository.cardDetails

import com.prabhat.movieapp.data.local.cardDetails.CardDetailsDao
import com.prabhat.movieapp.data.local.cardDetails.CardDetailsEntity
import com.prabhat.movieapp.domain.repository.cardDetails.CardDetailsRepository
import javax.inject.Inject

class CardDetailsRepositoryImpl
@Inject constructor(private val cardDetailsDao: CardDetailsDao): CardDetailsRepository {
    override suspend fun saveCardDetails(cardDetailsEntity: CardDetailsEntity) {

        cardDetailsDao.upsertCardDetails(cardDetailsEntity)
    }

    override suspend fun getCardDetails(): CardDetailsEntity? {
      return cardDetailsDao.getCardDetails()
    }

    override suspend fun clearCardDetails() {
        cardDetailsDao.clearCardDetails()
    }


}