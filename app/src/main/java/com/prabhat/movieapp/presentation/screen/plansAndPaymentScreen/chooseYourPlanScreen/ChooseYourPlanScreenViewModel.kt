package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.userPreference.SavePreferenceUseCase
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseYourPlanScreenViewModel
@Inject constructor(private val savePreferenceUseCase: SavePreferenceUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ChoosePlanUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationChannel = Channel<ChoosePlanNavigationEvent>(Channel.BUFFERED)
    val navigationFlow = _navigationChannel.receiveAsFlow()


    fun onEvent(event: ChoosePlanEvent) {
        when (event) {
            is ChoosePlanEvent.ContinueClicked -> {
                if (_uiState.value.selectedPlan.isEmpty()) return
                savePreference()
            }

            is ChoosePlanEvent.PlanSelected -> {
                _uiState.update { it.copy(selectedPlan = event.plan) }
            }

        }
    }

    private fun savePreference() {
        savePreferenceUseCase(field = "plans", value = _uiState.value.selectedPlan)
            .onEach { result ->
                when (result) {

                    is Resource.Loading -> {
                        Log.d("PRABHAT", "savePreference:  Resource.Loading ${result.message}")

                        _uiState.update { it.copy(isLoading = true) }

                    }

                    is Resource.Success -> {
                        Log.d("PRABHAT", "savePreference:  Resource.Success ${result.message}")

                        _uiState.update { it.copy(isLoading = false, continueClicked = true) }
                        viewModelScope.launch {

                            _navigationChannel.send(ChoosePlanNavigationEvent.NavigateNext)
                        }

                    }

                    is Resource.Error -> {
                        Log.d("PRABHAT", "savePreference:  Resource.Error ${result.message}")
                        _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }

                    }
                }
            }
            .launchIn(viewModelScope)

    }

    fun resetNavigationFlag() {
        _uiState.update { it.copy(continueClicked = false) }
    }


}