package com.sci.coffeeandroid.feature.auth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.auth.data.repository.AuthRepository
import com.sci.coffeeandroid.util.ApiException
import com.sci.coffeeandroid.util.SingleLiveEvent
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {


    private val _uiState: MutableLiveData<ForgotPasswordUiState> = MutableLiveData()
    val uiState: LiveData<ForgotPasswordUiState> = _uiState

    private val _uiEvent: SingleLiveEvent<ForgotPasswordViewModelEvent> = SingleLiveEvent()
    val uiEvent: LiveData<ForgotPasswordViewModelEvent> = _uiEvent


    fun getOTP(email: String) {
        _uiState.value = ForgotPasswordUiState.Loading
        viewModelScope.launch {
            authRepository.getOTP(email)
                .fold(
                    onSuccess = {
                        _uiState.value = ForgotPasswordUiState.ResetSuccess
                    },
                    onFailure = { error ->
                        when (error) {
                            is ApiException -> handleApiException(error)

                            else -> {
                                _uiEvent.value =
                                    ForgotPasswordViewModelEvent.Error(
                                        error.message ?: "Something went wrong"
                                    )
                            }
                        }
                    }
                )
        }
    }


    private fun handleApiException(apiException: ApiException) {
        when (apiException.code) {
            404 -> {
                _uiState.value = ForgotPasswordUiState.NewUser
            }

            else -> {
                _uiEvent.value = ForgotPasswordViewModelEvent
                    .Error(apiException.message ?: "Something went wrong")
            }
        }
    }
}

sealed class ForgotPasswordUiState {
    data object Loading : ForgotPasswordUiState()
    data object NewUser : ForgotPasswordUiState()
    data object ResetSuccess : ForgotPasswordUiState()
}

sealed class ForgotPasswordViewModelEvent {
    data class Error(val error: String) : ForgotPasswordViewModelEvent()
}