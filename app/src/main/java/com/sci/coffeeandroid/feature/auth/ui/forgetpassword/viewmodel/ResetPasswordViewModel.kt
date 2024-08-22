package com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.auth.data.repository.AuthRepository
import com.sci.coffeeandroid.feature.auth.domain.usecase.PasswordValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.RepeatedPasswordValidate
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.ResetPasswordFormEvent
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.ResetPasswordFormState
import com.sci.coffeeandroid.util.ApiException
import com.sci.coffeeandroid.util.SingleLiveEvent
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val authRepository: AuthRepository,
    private val passwordValidate: PasswordValidate,
    private val repeatedPasswordValidate: RepeatedPasswordValidate
) : ViewModel() {

    private val _uiState: MutableLiveData<ResetPasswordFormState> = MutableLiveData(ResetPasswordFormState())
    val uiState: LiveData<ResetPasswordFormState> = _uiState

    fun onEvent(event: ResetPasswordFormEvent){

        when (event){


            is ResetPasswordFormEvent.PasswordChangedEvent -> {
                val currentPassword= _uiState.value?.password
                val error = if (currentPassword!=event.password) null else _uiState.value?.passwordError
                _uiState.value = _uiState.value?.copy(password = event.password, passwordError = error)
            }
            is ResetPasswordFormEvent.RepeatedChangedEvent -> {
                val currentEmail= _uiState.value?.repeatedPassword
                val error = if (currentEmail!=event.repeatedPassword) null else _uiState.value?.repeatedPasswordError
                _uiState.value = _uiState.value?.copy(repeatedPassword = event.repeatedPassword, repeatedPasswordError = error)
            }

            is ResetPasswordFormEvent.Reset -> resetPassword(event.email)
        }

    }

    private val _viewmodelUIState: MutableLiveData<ResetPasswordUiState> = MutableLiveData()
    val viewmodelUIState: LiveData<ResetPasswordUiState> = _viewmodelUIState

    private val _viewmodelUIEvent: SingleLiveEvent<ViewModelUIEvent> = SingleLiveEvent()
    val viewmodelUIEvent: LiveData<ViewModelUIEvent> = _viewmodelUIEvent

    private fun resetPassword(email : String) {
        val repeatedPassword= _uiState.value?.repeatedPassword
        val password= _uiState.value?.password

        val passwordResult = passwordValidate.execute(password?:"")
        val repeatedPasswordResult = repeatedPasswordValidate.execute(password?:"",repeatedPassword?:"")

        val isHasError = listOf(
            passwordResult,
            repeatedPasswordResult
        ).any { !it.isSuccess }

        if(isHasError){
            _uiState.value = _uiState.value?.copy(
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage
            )

            return
        }

        _viewmodelUIEvent.value = ViewModelUIEvent.Loading
        viewModelScope.launch {
            authRepository
                .resetPassword(
                    email = email,
                    newPassword = password!!
                )
                .fold(
                    onSuccess = {
                        _viewmodelUIEvent.value = ViewModelUIEvent.ResetSuccess
                    },

                    onFailure = { error ->
                        when (error) {
                            is ApiException -> handleApiException(error)

                            else -> {
                                _viewmodelUIEvent.value =
                                    ViewModelUIEvent.Error(
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
                _viewmodelUIEvent.value = ViewModelUIEvent.NewUser
            }

            else -> {
                _viewmodelUIEvent.value = ViewModelUIEvent.Error(
                    apiException.message ?: "Something went wrong"
                )
            }
        }
    }
}

sealed class ResetPasswordUiState {
    data object Idle : ResetPasswordUiState()
}

sealed class ViewModelUIEvent {
     data class Error(val error: String) : ViewModelUIEvent()
    data object Loading : ViewModelUIEvent()
    data object NewUser : ViewModelUIEvent()
    data object ResetSuccess : ViewModelUIEvent()
}