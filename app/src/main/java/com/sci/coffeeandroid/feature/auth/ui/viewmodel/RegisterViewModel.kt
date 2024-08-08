package com.sci.coffeeandroid.feature.auth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.auth.data.repository.RegisterRepository
import com.sci.coffeeandroid.util.ApiException
import com.sci.coffeeandroid.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerUiState: MutableLiveData<RegisterUiState> = MutableLiveData()
    val registerUiState: MutableLiveData<RegisterUiState> = _registerUiState

    private val _registerUiEvent: SingleLiveEvent<RegisterViewModelEvent> = SingleLiveEvent()
    val registerUiEvent: LiveData<RegisterViewModelEvent> = _registerUiEvent



     fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ) {

        _registerUiState.value = RegisterUiState.Loading
        viewModelScope.launch {
            registerRepository.register(username, email, phone, password)
                .fold(
                    onSuccess = {
                        _registerUiState.value = RegisterUiState.Success
                    },
                    onFailure = { error ->
                        when(error) {
                            is ApiException -> handleApiException(error)
                            else -> _registerUiEvent.value = RegisterViewModelEvent.Error(
                                error.message ?: "Something went wrong"
                            )
                        }

                    }
                )

        }


    }

    private fun handleApiException(apiException: ApiException) {
        when (apiException.code) {
            404 -> {
                _registerUiState.value = RegisterUiState.NewUser
            }

            else -> {
                _registerUiEvent.value = RegisterViewModelEvent
                    .Error(apiException.message ?: "Something went wrong")
            }
        }
    }

}

sealed class RegisterViewModelEvent {
    data class Error(val error: String) : RegisterViewModelEvent()

}

sealed class RegisterUiState {
    data object Loading : RegisterUiState()
    data object Success : RegisterUiState()
    data object NewUser : RegisterUiState()
    data object UserAlreadyExit : RegisterUiState()
}


