package com.prabhat.movieapp.domain.use_case.userPreference

import com.prabhat.movieapp.domain.repository.userPreference.UserPreferenceRepository
import javax.inject.Inject

class getPreferenceUseCase @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository) {


    suspend operator fun invoke() = userPreferenceRepository.getPreference()
}