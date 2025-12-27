package com.prabhat.movieapp.domain.use_case.userPreference

import com.prabhat.movieapp.domain.repository.userPreference.UserPreferenceRepository
import com.prabhat.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavePreferenceUseCase @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository) {

    operator fun invoke(field: String, value: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {

            emit(
                Resource.Success(
                    userPreferenceRepository.updateField(
                        field = field,
                        value = value
                    )
                )
            )
        } catch (e: Exception) {

            emit(Resource.Error(e.message))
        }

    }
}