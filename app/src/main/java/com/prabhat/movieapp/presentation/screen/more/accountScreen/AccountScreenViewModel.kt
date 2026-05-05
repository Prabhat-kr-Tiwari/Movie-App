package com.prabhat.movieapp.presentation.screen.more.accountScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhat.movieapp.domain.use_case.userPreference.GetPreferenceUseCase
import com.prabhat.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel
@Inject constructor(
   getPreferenceUseCase: GetPreferenceUseCase
) : ViewModel(){
    val accountScreenUiState: StateFlow<AccountScreenUiState> =
    getPreferenceUseCase().map{ resource->
        when(resource){
            is Resource.Loading->{
                AccountScreenUiState.Loading
            }
            is Resource.Success->{
                val data = resource.data
                if (data==null){
                    AccountScreenUiState.NotShown
                }else{
                    AccountScreenUiState.UserPreference(name = data.userName, avatarId = data.avatar)
                }
            }
            is Resource.Error->{
                AccountScreenUiState.LoadFailed
            }
        }
    }.stateIn(
        scope=viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AccountScreenUiState.Loading

    )
}