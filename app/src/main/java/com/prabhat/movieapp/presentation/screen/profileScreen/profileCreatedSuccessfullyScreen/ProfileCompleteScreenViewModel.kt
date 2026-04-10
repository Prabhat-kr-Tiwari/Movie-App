package com.prabhat.movieapp.presentation.screen.profileScreen.profileCreatedSuccessfullyScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.AuthenticationUseCase
import com.prabhat.movieapp.domain.use_case.userPreference.GetPreferenceUseCase
import com.prabhat.movieapp.mappers.usePreference.toPresentation
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.verifyPaymentScreen.VerifyPaymentEvent
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileCompleteScreenViewModel @Inject constructor(
    private val getPreferenceUseCase: GetPreferenceUseCase,
    private val authenticationUseCase: AuthenticationUseCase
): ViewModel() {

    private val _uiState= MutableStateFlow(ProfileCompleteUiState())
     val uiState= _uiState.asStateFlow()
    init {
        getPreference()
    }
    private fun getPreference() {

        getPreferenceUseCase().onEach { result ->
            when (result) {
                is Resource.Error<*> -> {
                    _uiState.update { it.copy(errorMessage = result.message, isLoading = false)  }
                }
                is Resource.Loading<*> ->{
                    _uiState.update { it.copy(isLoading = true)  }
                }
                is Resource.Success<*> -> {
                    val userPreference = result.data?.toPresentation()
                    _uiState.update { it.copy(avatarId = userPreference?.avatar, userName = userPreference?.userName, isLoading = false) }
                }
            }


        }.launchIn(viewModelScope)

    }

    fun onEvent(profileCreatedEvent: ProfileCreatedEvent){
        when(profileCreatedEvent){
            ProfileCreatedEvent.onProfileCreatedDone -> {
                setProfileCreatedDone()
            }
        }
    }


    private fun setProfileCreatedDone(){
        viewModelScope.launch {
            authenticationUseCase.setProfileSetupDone()
        }
    }

}