package com.prabhat.movieapp.presentation.screen.profileScreen.createPinScreen

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.userPreference.GetPreferenceUseCase
import com.prabhat.movieapp.domain.use_case.userPreference.SavePreferenceUseCase
import com.prabhat.movieapp.mappers.usePreference.toPresentation
import com.prabhat.movieapp.presentation.screen.profileScreen.enterPasswordScreen.EnterPasswordNavigationEvent
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
class CreatePinScreenViewModel @Inject constructor(
    private val getPreferenceUseCase: GetPreferenceUseCase,
    private val savePreferenceUseCase: SavePreferenceUseCase
) : ViewModel() {

    private val _uiState= MutableStateFlow(CreatePinUiState())
    val uiState = _uiState.asStateFlow()

    private  val _navigationChannel = Channel<CreatePinNavigationEvent>(Channel.BUFFERED)
    val navigationChannel = _navigationChannel.receiveAsFlow()



    init {
        getPreference()
    }
    fun onEvent(createPinEvent: CreatePinEvent){
        when(createPinEvent){
            CreatePinEvent.iamAllSafeClicked -> {
                val pin = _uiState.value.pin

                if (pin.length < 4) {
                    _uiState.value = _uiState.value.copy(
                        isPinValid = false,
                        pinError = "Enter valid PIN"
                    )
                    return
                }

                savePreference()
            }

            is CreatePinEvent.OnOtpChange ->{
                _uiState.update { it.copy(pin = createPinEvent.otp,
                    isPinValid = createPinEvent.otp.length==4,
                    pinError = if (createPinEvent.otp.length < 4) "PIN must be 4 digits" else null

                ) }
            }
        }
    }
    private fun getPreference() {

        getPreferenceUseCase().onEach { result ->
            when (result) {
                is Resource.Error<*> -> {
                    _uiState.update { it.copy(error = result.message, isLoading = false)  }
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
        savePreferenceUseCase(field = "pin", value = _uiState.value.pin)
            .onEach { result ->
                when (result) {

                    is Resource.Loading -> {

                        _uiState.update { it.copy(isLoading = true) }

                    }

                    is Resource.Success -> {

                        _uiState.update { it.copy(isLoading = false, isPinSaved = true) }
                        viewModelScope.launch {

                            _navigationChannel.send(CreatePinNavigationEvent.navigateNext)
                        }

                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = result.message) }

                    }
                }
            }
            .launchIn(viewModelScope)

    }

}