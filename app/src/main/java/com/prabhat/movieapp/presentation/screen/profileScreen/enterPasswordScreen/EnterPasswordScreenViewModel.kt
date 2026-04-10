package com.prabhat.movieapp.presentation.screen.profileScreen.enterPasswordScreen

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.userPreference.GetPreferenceUseCase
import com.prabhat.movieapp.domain.use_case.userPreference.SavePreferenceUseCase
import com.prabhat.movieapp.mappers.usePreference.toPresentation
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EnterPasswordScreenViewModel @Inject constructor(
    private val savePreferenceUseCase: SavePreferenceUseCase,
    private val getPreferenceUseCase: GetPreferenceUseCase
) : ViewModel() {

    val userPasswordState = TextFieldState()
    private val _uiState = MutableStateFlow(EnterPasswordUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationChannel = Channel<EnterPasswordNavigationEvent>(Channel.BUFFERED)
    val navigationChannel = _navigationChannel.receiveAsFlow()


    init {

        getPreference()
    }

    fun onEvent(event: EnterPasswordEvent) {
        when (event) {
            is EnterPasswordEvent.looksStrongClicked -> {
                if (userPasswordState.text.isEmpty()) {
                    return
                }
                savePreference()
            }

            EnterPasswordEvent.changeClicked -> {
                viewModelScope.launch {
                    _navigationChannel.send(EnterPasswordNavigationEvent.navigateBack)
                }

            }
        }
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
                    _uiState.update { it.copy(avatarId = userPreference?.avatar, isLoading = false) }
                }
            }


        }.launchIn(viewModelScope)

    }
    private fun savePreference() {
        savePreferenceUseCase(field = "password", value = userPasswordState.text.toString())
            .onEach { result ->
                when (result) {

                    is Resource.Loading -> {

                        _uiState.update { it.copy(isLoading = true) }

                    }

                    is Resource.Success -> {

                        _uiState.update { it.copy(isLoading = false) }
                        viewModelScope.launch {

                            _navigationChannel.send(EnterPasswordNavigationEvent.navigateNext)
                        }

                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }

                    }
                }
            }
            .launchIn(viewModelScope)

    }

}