package com.prabhat.movieapp.domain.use_case.userPreference

import com.prabhat.movieapp.domain.model.userPreference.UserPreference
import com.prabhat.movieapp.domain.repository.userPreference.UserPreferenceRepository
import com.prabhat.movieapp.mappers.usePreference.toDomain
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository) {


     operator fun invoke(): Flow<Resource<UserPreference>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(userPreferenceRepository.getPreference()?.toDomain()))


        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }

    }
}