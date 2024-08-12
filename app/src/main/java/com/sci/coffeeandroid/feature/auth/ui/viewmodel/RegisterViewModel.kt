package com.sci.coffeeandroid.feature.auth.ui.viewmodel

import android.content.Context

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.sci.coffeeandroid.feature.auth.data.repository.AuthRepository
import com.sci.coffeeandroid.util.ApiException
import com.sci.coffeeandroid.util.SingleLiveEvent
import kotlinx.coroutines.launch
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

class RegisterViewModel(
    private val authRepository: AuthRepository
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
            authRepository.register(username, email, phone, password)
                .fold(
                    onSuccess = {
                        _registerUiState.value = RegisterUiState.Success
                    },
                    onFailure = { error ->
                        when (error) {
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

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setAutoSelectEnabled(true)
        .setNonce("")
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()


    fun getCredential(context: Context) {
        viewModelScope.launch {
            try {
                val credentialManager = CredentialManager.create(context)
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                handleFailure(e)
            }
        }
    }

    private fun handleFailure(e: GetCredentialException) {
        _registerUiEvent.value = RegisterViewModelEvent.Error(
            error = e.message.orEmpty()
        )
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {

            // Passkey credential
            is PublicKeyCredential -> {
                // Share responseJson such as a GetCredentialResponse on your server to
                // validate and authenticate
                val responseJson = credential.authenticationResponseJson
            }

            // Password credential
            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.
                val username = credential.id
                val password = credential.password
            }

            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        // Send ID token to your server to validate and authenticate.
                        googleIdTokenCredential.idToken
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("HNA", "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("HNA", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("HNA", "Unexpected type of credential")

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


