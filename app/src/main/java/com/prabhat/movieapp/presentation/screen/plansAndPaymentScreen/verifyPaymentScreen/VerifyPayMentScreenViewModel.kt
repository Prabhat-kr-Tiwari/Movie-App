package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.verifyPaymentScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class VerifyPayMentScreenViewModel @Inject constructor(private val authenticationUseCase: AuthenticationUseCase): ViewModel() {
    private val _uiState= MutableStateFlow(VerifyPaymentScreenUiState())
    val uiState=_uiState.asStateFlow()

    fun onEvent(verifyPaymentEvent: VerifyPaymentEvent){
        when(verifyPaymentEvent){
            VerifyPaymentEvent.OnPlansAndPaymentDone -> {
                setPlansAndPaymentDone()
            }
        }
    }


    private fun setPlansAndPaymentDone(){
        viewModelScope.launch {
            authenticationUseCase.setPlansAndPaymentDone()
        }
    }



}