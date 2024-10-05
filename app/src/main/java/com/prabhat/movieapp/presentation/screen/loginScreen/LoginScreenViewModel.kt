package com.prabhat.movieapp.presentation.screen.loginScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.AuthenticationUseCase
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel
@Inject constructor(private val authenticationUseCase: AuthenticationUseCase) : ViewModel() {

     val authenticateUserState= mutableStateOf(LoginScreenState())
    fun authenticateUser(username: String, password: String) {

        authenticationUseCase.createAuthentication(username = username, password = password)
            .onEach {
                when(it){
                    is Resource.Loading->{
                        authenticateUserState.value=authenticateUserState.value.copy(isLoading = true)
                    }
                    is Resource.Success->{
                        authenticateUserState.value=authenticateUserState.value.copy(isLoginSuccessful = it.data)
                    }
                    is Resource.Error->{
                        authenticateUserState.value=authenticateUserState.value.copy(errorMessage = it.message)
                    }



            }



            }.launchIn(viewModelScope)

    }


}

