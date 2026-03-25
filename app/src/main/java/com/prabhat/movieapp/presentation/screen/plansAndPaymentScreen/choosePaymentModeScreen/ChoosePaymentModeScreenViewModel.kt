package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.choosePaymentModeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.userPreference.SavePreferenceUseCase
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
class ChoosePaymentModeScreenViewModel @Inject constructor(private val savePreferenceUseCase: SavePreferenceUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ChoosePaymentModeUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationChannel = Channel<ChoosePaymentModeNavigationEvent>(Channel.BUFFERED)
    val navigationChannel = _navigationChannel.receiveAsFlow()


    fun onEvent(event: ChoosePaymentModeEvent) {
        when (event) {
            is ChoosePaymentModeEvent.ModeSelected -> {
                _uiState.update { it.copy(selectedMode = event.paymentMode) }
            }

            is ChoosePaymentModeEvent.ContinueClicked -> {
                if (_uiState.value.selectedMode.isEmpty()) return
                //savePrefrence
                savePreference()
            }
        }

    }

    private fun savePreference() {
        savePreferenceUseCase.invoke(field = "paymentMode", _uiState.value.selectedMode)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false, continueClicked = true) }
                        _navigationChannel.send(ChoosePaymentModeNavigationEvent.NavigationNext)
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(errorMessage = it.errorMessage) }

                    }
                }
            }.launchIn(viewModelScope)
    }

    fun resetNavigationFlow() {
        _uiState.update { it.copy(continueClicked = false) }
    }

}