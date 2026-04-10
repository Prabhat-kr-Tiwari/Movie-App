package com.prabhat.movieapp.presentation.screen.profileScreen.chooseAvatarScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.userPreference.SavePreferenceUseCase
import com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.chooseYourPlanScreen.ChoosePlanNavigationEvent
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
class ChooseAvatarScreenViewModel @Inject constructor(private val savePreferenceUseCase: SavePreferenceUseCase) :
    ViewModel() {


    private val _uiState = MutableStateFlow(ChooseAvatarUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationChannel = Channel<ChooseAvatarNavigationEvent>(Channel.BUFFERED)
    val navigationFlow = _navigationChannel.receiveAsFlow()


    fun onEvent(chooseAvatarEvent: ChooseAvatarEvent) {
        when (chooseAvatarEvent) {
            is ChooseAvatarEvent.AvatarSelected -> {
                _uiState.update { it.copy(selectedAvatar = chooseAvatarEvent.avatar) }

            }

            ChooseAvatarEvent.ContinueClicked -> {
                if (_uiState.value.selectedAvatar==null) return
                savePreference()

            }
        }
    }
    private fun savePreference() {
        savePreferenceUseCase(field = "avatar", value = _uiState.value.selectedAvatar?:"")
            .onEach { result ->
                when (result) {

                    is Resource.Loading -> {

                        _uiState.update { it.copy(isLoading = true) }

                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _navigationChannel.send(ChooseAvatarNavigationEvent.NavigateNext)

                        Log.d("PRABHAT", "savePreference:  Resource.Success ${result.message}")


                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }

                    }
                }
            }
            .launchIn(viewModelScope)

    }
}

