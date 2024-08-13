package com.sci.coffeeandroid.feature.auth.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.sci.coffeeandroid.feature.auth.data.repository.AuthRepository
import com.sci.coffeeandroid.util.ApiException
import com.sci.coffeeandroid.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.UUID

class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    val nonce: String = UUID.randomUUID().toString()

    private val _uiState: MutableLiveData<LoginUiState> = MutableLiveData()
    val uiState: LiveData<LoginUiState> = _uiState

    private val _uiEvent: SingleLiveEvent<LoginViewModelEvent> = SingleLiveEvent()
    val uiEvent: LiveData<LoginViewModelEvent> = _uiEvent

    init {
        if (authRepository.isUserLoggedIn()) {
//            _uiEvent.value = LoginViewModelEvent.UserAlreadyLoggedIn
        }
    }
    private fun handleApiException(apiException: ApiException) {
        when (apiException.code) {
            404 -> {
                _uiState.value = LoginUiState.NewUser
            }

            else -> {
                _uiEvent.value = LoginViewModelEvent
                    .Error(apiException.message ?: "Something went wrong")
            }
        }
    }

    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(true)
        .setNonce(nonce)
        .setServerClientId(serverClientId = "992963912793-komghl2ue9rku5lkie3uovmrmhclas30.apps.googleusercontent.com")
        .build()

    private val request: GetCredentialRequest = GetCredentialRequest.Builder()
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
        _uiEvent.value = LoginViewModelEvent.Error(
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
                Log.d("HNA", "PublishKeyCredential: $responseJson")
            }

            // Password credential
            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.
                val username = credential.id
                val password = credential.password
                Log.d("HNA", "PasswordCredential: $username, $password")
            }

            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        // Send ID token to your server to validate and authenticate.
                        googleIdTokenCredential.idToken
                        Log.d("HNA", "GoogleIdTokenCredential: ${googleIdTokenCredential.idToken}")
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

   fun login(userName: String, password: String) {

        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            authRepository
                .login(
                    username = userName,
                    password = password
                )
                .fold(
                    onSuccess = {
                        _uiState.value = LoginUiState.LoginSuccess
                    },

                    onFailure = { error ->
                        when (error) {
                            is ApiException -> handleApiException(error)

                            else -> {
                                _uiEvent.value =
                                    LoginViewModelEvent.Error(
                                        error.message ?: "Something went wrong"
                                    )
                            }
                        }
                    }
                )
        }

    }




}

sealed class LoginUiState {
    data object Idle : LoginUiState()

    data object Loading : LoginUiState()

    data object LoginSuccess : LoginUiState()

    data object NewUser : LoginUiState()

    data object UserAlreadyLoggedIn : LoginUiState()

}

sealed class LoginViewModelEvent {

    data class Error(val error: String) : LoginViewModelEvent()
}