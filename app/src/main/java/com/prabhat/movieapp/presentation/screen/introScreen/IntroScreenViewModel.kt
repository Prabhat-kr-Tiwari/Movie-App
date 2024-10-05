package com.prabhat.movieapp.presentation.screen.introScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.data.appSettings.SessionId
import com.prabhat.movieapp.domain.use_case.AuthenticationUseCase
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class IntroScreenViewModel
@Inject constructor(private val authenticationUseCase: AuthenticationUseCase):ViewModel() {


    val sessionIdState= mutableStateOf(IntroScreenState())
    init {
        getSessionId()
    }

    private fun getSessionId(){

        authenticationUseCase.getSessionId().onEach {
            when(it){
                is Resource.Error -> {

                    sessionIdState.value=sessionIdState.value.copy(error = it.message)
                }
                is Resource.Loading -> {
                    sessionIdState.value=sessionIdState.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    sessionIdState.value=sessionIdState.value.copy(sessionId = it.data?.sessionId?.let { it1 ->
                        SessionId(
                            it1
                        )
                    })
                }
            }


        }.launchIn(viewModelScope)


    }




}