package com.prabhat.movieapp.domain.use_case.billingDetails

import com.prabhat.movieapp.data.local.cardDetails.CardDetailsEntity
import com.prabhat.movieapp.domain.repository.cardDetails.CardDetailsRepository
import javax.inject.Inject

class GetBillingDetailsUseCase @Inject constructor(private val cardDetailsRepository: CardDetailsRepository) {
   suspend operator  fun invoke(): CardDetailsEntity? {
      return cardDetailsRepository.getCardDetails()
   }
}