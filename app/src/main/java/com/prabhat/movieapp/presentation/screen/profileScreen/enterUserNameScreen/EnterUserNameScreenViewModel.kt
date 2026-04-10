package com.prabhat.movieapp.presentation.screen.profileScreen.enterUserNameScreen

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
class EnterUserNameScreenViewModel @Inject constructor(
    private val savePreferenceUseCase: SavePreferenceUseCase,
    private val getPreferenceUseCase: GetPreferenceUseCase
) : ViewModel() {

    val userNameState = TextFieldState()
    private val _uiState = MutableStateFlow(EnterUserNameUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationChannel = Channel<EnterUserNameNavigationEvent>(Channel.BUFFERED)
    val navigationChannel = _navigationChannel.receiveAsFlow()


    init {

        getPreference()
    }

    fun onEvent(event: EnterUserNameEvent) {
        when (event) {
            is EnterUserNameEvent.callMeThisClicked -> {
                if (userNameState.text.isEmpty()) {
                    return
                }
                savePreference()
            }

            EnterUserNameEvent.changeClicked -> {
                viewModelScope.launch {
                    _navigationChannel.send(EnterUserNameNavigationEvent.navigateBack)
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
        savePreferenceUseCase(field = "userName", value = userNameState.text.toString())
            .onEach { result ->
                when (result) {

                    is Resource.Loading -> {

                        _uiState.update { it.copy(isLoading = true) }

                    }

                    is Resource.Success -> {

                        _uiState.update { it.copy(isLoading = false) }
                        viewModelScope.launch {

                            _navigationChannel.send(EnterUserNameNavigationEvent.navigateNext)
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