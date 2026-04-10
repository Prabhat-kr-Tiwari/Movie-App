package com.prabhat.movieapp.presentation.screen.splashScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.AuthenticationUseCase
import com.prabhat.movieapp.navigation.SubGraph
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val authenticationUseCase: AuthenticationUseCase): ViewModel() {

    private val _uiState= MutableStateFlow(SplashScreenUiState())
    val uiState=_uiState.asStateFlow()

    init {


            observeAppState()
           /* delay(2000L)
            _uiState.update { it.copy(isReady = true) }*/

    }



    private fun observeAppState() {
        viewModelScope.launch {

            val onboardingDone = async{ authenticationUseCase.isOnBoardingDone()}
            val plansDone = async {   authenticationUseCase.isPlansAndPaymentDone()}
            val profileDone = async {   authenticationUseCase.isProfileSetupDone()}

            val isOnboardingDone =onboardingDone.await()
            val isPlansDone = plansDone.await()
            val isProfileDone = profileDone.await()



            val startDestination = when {
                !isOnboardingDone -> SubGraph.onBoarding
                !isPlansDone -> SubGraph.PlansAndPayment
                !isProfileDone -> SubGraph.Profile
                else -> SubGraph.Home
            }
            Log.d("PRABHU", "observeAppState: "+startDestination.toString())
            Log.d("PRABHU", "observeAppState: "+isOnboardingDone)
            _uiState.update {
                it.copy(
                    startDestination = startDestination,
                )
            }
//            delay(2000L)


            _uiState.update {
                it.copy(
                    isReady = true
                )
            }
        }
    }




}