package com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.auth.data.repository.AuthRepository
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.ForgetPasswordFormEvent
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.ForgetPasswordFormState
import com.sci.coffeeandroid.util.ApiException
import com.sci.coffeeandroid.util.SingleLiveEvent
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<ForgetPasswordFormState> = MutableLiveData(ForgetPasswordFormState())
    val uiState: LiveData<ForgetPasswordFormState> = _uiState

    fun onEvent(event: ForgetPasswordFormEvent){

        when (event){
            is ForgetPasswordFormEvent.EmailChangedEvent -> {
                val isEnable = event.email.isNotBlank()
                val currentEmail= _uiState.value?.email
                val error = if (currentEmail!=event.email) null else _uiState.value?.emailError
                _uiState.value = _uiState.value?.copy(email = event.email, emailError = error, isButtonEnable = isEnable)
            }
            is ForgetPasswordFormEvent.Next -> getOTP()
        }

    }

    private val _viewmodelUIState: MutableLiveData<ViewModelUiState> = MutableLiveData()
    val viewmodelState: LiveData<ViewModelUiState> = _viewmodelUIState

    private val _viewmodelUIEvent: SingleLiveEvent<ViewModelEvent> = SingleLiveEvent()
    val viewmodelEvent: LiveData<ViewModelEvent> = _viewmodelUIEvent


    private fun getOTP() {
        val userEmail= _uiState.value?.email

        if(userEmail.isNullOrBlank()){
            _uiState.value = _uiState.value?.copy(emailError = "Enter email")
            return
        }
        _viewmodelUIEvent.value = ViewModelEvent.Loading
        viewModelScope.launch {
            authRepository.getOTP(email = userEmail)
                .fold(
                    onSuccess = {
                        _viewmodelUIEvent.value = ViewModelEvent.ResetSuccess
                    },
                    onFailure = { error ->
                        when (error) {
                            is ApiException -> handleApiException(error)

                            else -> {
                                _viewmodelUIEvent.value =
                                    ViewModelEvent.Error(
                                        error.message ?: "Something went wrong"
                                    )
                            }
                        }
                    }
                )
        }
    }

    fun resetForgotPasswordUiState() {
        _viewmodelUIEvent.value = ViewModelEvent.Loading
    }


    private fun handleApiException(apiException: ApiException) {
        when (apiException.code) {
            404 -> {
                _viewmodelUIEvent.value = ViewModelEvent.NewUser
            }

            else -> {
                _viewmodelUIEvent.value = ViewModelEvent.Error(
                    apiException.message ?: "Something went wrong"
                )
            }
        }
    }
}

sealed class ViewModelUiState {
    data object Idle : ViewModelUiState()
}

sealed class ViewModelEvent {
    data class Error(val error: String) : ViewModelEvent()
    data object Loading : ViewModelEvent()
    data object NewUser : ViewModelEvent()
    data object ResetSuccess : ViewModelEvent()
}