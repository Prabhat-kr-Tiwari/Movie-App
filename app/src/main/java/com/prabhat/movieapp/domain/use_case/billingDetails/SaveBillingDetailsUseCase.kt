package com.prabhat.movieapp.domain.use_case.billingDetails

import com.prabhat.movieapp.data.mappers.cardDetails.toCardDetailsEntity
import com.prabhat.movieapp.domain.model.billingDetails.CardDetails
import com.prabhat.movieapp.domain.repository.cardDetails.CardDetailsRepository
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveBillingDetailsUseCase @Inject constructor(private val cardDetailsRepository: CardDetailsRepository) {
    operator fun invoke(cardDetails: CardDetails): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())
        try {
            emit(
                Resource
                    .Success(cardDetailsRepository
                        .saveCardDetails(cardDetailsEntity = cardDetails.toCardDetailsEntity())))

        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }


    }
}