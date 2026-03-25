package com.prabhat.movieapp.presentation.screen.plansAndPaymentScreen.billingDetailsScreen

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.model.billingDetails.CardDetails
import com.prabhat.movieapp.domain.use_case.billingDetails.SaveBillingDetailsUseCase
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
class BillingDetailsScreenViewModel
@Inject constructor(private val saveBillingDetailsUseCase: SaveBillingDetailsUseCase) :
    ViewModel() {

    val firstNameState = TextFieldState()
    val lastNameState = TextFieldState()
    val cardNumberState = TextFieldState()
    val expirationDateState = TextFieldState()
    val securityCodeState = TextFieldState()

    private val _uiState = MutableStateFlow(BillingDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationChannel = Channel<BillingDetailsNavigationEvent>(Channel.BUFFERED)
    val navigationChannel = _navigationChannel.receiveAsFlow()

    fun onEvent(event: BillingDetailsEvent) {
        when (event) {
            is BillingDetailsEvent.ContinueClicked -> {

                if (firstNameState.text.isEmpty()
                    && lastNameState.text.isEmpty()
                    && cardNumberState.text.isEmpty()
                    && expirationDateState.text.isEmpty()
                    && securityCodeState.text.isEmpty()
                ) {
                    return
                }
                saveCardDetails()
            }

            is BillingDetailsEvent.ChangeClicked -> {
                viewModelScope.launch {

                    _navigationChannel.send(BillingDetailsNavigationEvent.NavigateBack)
                }


            }

        }
    }


    fun saveCardDetails() {
        saveBillingDetailsUseCase
            .invoke(
                cardDetails = CardDetails(
                    firstName = firstNameState.text.toString(),
                    lastName = lastNameState.text.toString(),
                    cardNumber = cardNumberState.text.toString(),
                    securityCode = securityCodeState.text.toString(),
                    expirationDate = expirationDateState.text.toString()
                )
            )
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Something Went Wrong",
                                isLoading = false
                            )
                        }

                    }

                    is Resource.Loading -> {

                        _uiState.update { it.copy(isLoading = true) }

                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _navigationChannel.send(BillingDetailsNavigationEvent.NavigateNext)

                    }
                }
            }
            .launchIn(viewModelScope)
    }


}