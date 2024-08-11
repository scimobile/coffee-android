package com.sci.coffeeandroid.feature.auth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.auth.data.repository.AuthRepository
import com.sci.coffeeandroid.util.ApiException
import com.sci.coffeeandroid.util.SingleLiveEvent
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState: MutableLiveData<ResetPasswordUiState> = MutableLiveData()
    val uiState: LiveData<ResetPasswordUiState> = _uiState

    private val _uiEvent: SingleLiveEvent<ResetPasswordViewModelEvent> = SingleLiveEvent()
    val uiEvent: LiveData<ResetPasswordViewModelEvent> = _uiEvent

    fun resetPassword(email: String, newPassword: String) {

        _uiState.value = ResetPasswordUiState.Loading
        viewModelScope.launch {
            authRepository
                .resetPassword(
                    email = email,
                    newPassword = newPassword
                )
                .fold(
                    onSuccess = {
                        _uiState.value = ResetPasswordUiState.ResetSuccess
                    },

                    onFailure = { error ->
                        when (error) {
                            is ApiException -> handleApiException(error)

                            else -> {
                                _uiEvent.value =
                                    ResetPasswordViewModelEvent.Error(
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
                _uiState.value = ResetPasswordUiState.NewUser
            }

            else -> {
                _uiEvent.value = ResetPasswordViewModelEvent
                    .Error(apiException.message ?: "Something went wrong")
            }
        }
    }


}

sealed class ResetPasswordUiState {
    data object Loading : ResetPasswordUiState()
    data object NewUser : ResetPasswordUiState()
    data object ResetSuccess : ResetPasswordUiState()
}

sealed class ResetPasswordViewModelEvent {
    data class Error(val error: String) : ResetPasswordViewModelEvent()
}