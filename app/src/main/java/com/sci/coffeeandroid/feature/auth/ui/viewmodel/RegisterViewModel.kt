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
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.sci.coffeeandroid.feature.auth.data.repository.AuthRepository
import com.sci.coffeeandroid.feature.auth.domain.usecase.EmailValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.PasswordValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.PhoneValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.RepeatedPasswordValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.UsernameValidate
import com.sci.coffeeandroid.feature.auth.ui.RegistrationFormEvent
import com.sci.coffeeandroid.feature.auth.ui.RegistrationFormState
import com.sci.coffeeandroid.util.ApiException
import com.sci.coffeeandroid.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.UUID

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val usernameValidate: UsernameValidate,
    private val emailValidate: EmailValidate,
    private val phoneValidate: PhoneValidate,
    private val passwordValidate: PasswordValidate,
    private val repeatedPasswordValidate: RepeatedPasswordValidate
) : ViewModel() {

    private val nonce: String = UUID.randomUUID().toString()

    private val _registerUIState: MutableLiveData<RegistrationFormState> = MutableLiveData(RegistrationFormState())
    val registerUIState: LiveData<RegistrationFormState> = _registerUIState


    fun onEvent(event: RegistrationFormEvent){

        when (event){
            is RegistrationFormEvent.UsernameChangedEvent -> {
                val currentUsername= _registerUIState.value?.username
                val error = if (currentUsername!=event.username) null else _registerUIState.value?.usernameError
                _registerUIState.value = _registerUIState.value?.copy(username = event.username, usernameError = error)
            }
            is RegistrationFormEvent.EmailChangedEvent -> {
                val currentEmail= _registerUIState.value?.email
                val error = if (currentEmail!=event.email) null else _registerUIState.value?.emailError
                _registerUIState.value = _registerUIState.value?.copy(email = event.email, emailError = error)
            }
            is RegistrationFormEvent.PasswordChangedEvent -> {
                val currentPassword= _registerUIState.value?.password
                val error = if (currentPassword!=event.password) null else _registerUIState.value?.passwordError
                _registerUIState.value = _registerUIState.value?.copy(password = event.password, passwordError = error)
            }
            is RegistrationFormEvent.RepeatedPasswordChangedEvent -> {
                val currentRepeatedPassword= _registerUIState.value?.repeatedPassword
                val error = if (currentRepeatedPassword!=event.repeatedPassword) null else _registerUIState.value?.repeatedPasswordError
                _registerUIState.value = _registerUIState.value?.copy(repeatedPassword = event.repeatedPassword, repeatedPasswordError = error)
            }
            is RegistrationFormEvent.PhoneChangedEvent -> {
                val currentPhone= _registerUIState.value?.phone
                val error = if (currentPhone!=event.phone) null else _registerUIState.value?.phoneError
                _registerUIState.value = _registerUIState.value?.copy(phone = event.phone, phoneError = error)
            }

            is RegistrationFormEvent.Submit -> register()
        }
    }

    private val _registerViewModelState: MutableLiveData<RegisterViewModelState> = MutableLiveData()
    val registerViewModelState: LiveData<RegisterViewModelState> = _registerViewModelState

    private val _registerUiEvent: SingleLiveEvent<RegisterViewModelEvent> = SingleLiveEvent()
    val registerUiEvent: LiveData<RegisterViewModelEvent> = _registerUiEvent


    private fun register() {

        val username = _registerUIState.value?.username;
        val email = _registerUIState.value?.email;
        val password = _registerUIState.value?.password;
        val repeatedPassword = _registerUIState.value?.repeatedPassword;
        val phone = _registerUIState.value?.phone;

        val usernameResult = usernameValidate.execute(username?:"")
        val emailResult = emailValidate.execute(email?:"")
        val passwordResult = passwordValidate.execute(password?:"")
        val repeatedPasswordResult = repeatedPasswordValidate.execute(password?:"",repeatedPassword?:"")
        val phoneResult = phoneValidate.execute(phone?:"")

        val isHasError = listOf(
            usernameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            phoneResult
        ).any { !it.isSuccess }

        if(isHasError){
            _registerUIState.value = _registerUIState.value?.copy(
                usernameError = usernameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                phoneError = phoneResult.errorMessage
            )

            return
        }

        _registerUiEvent.value = RegisterViewModelEvent.Loading
        viewModelScope.launch {
            authRepository.register(username!!, email!!, phone!!, password!!)
                .fold(
                    onSuccess = {
                        _registerUiEvent.value = RegisterViewModelEvent.Success
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
                _registerUiEvent.value = RegisterViewModelEvent.NewUser
            }

            else -> {
                _registerUiEvent.value = RegisterViewModelEvent
                    .Error(apiException.message ?: "Something went wrong")
            }
        }
    }

    fun registerCallback(callbackManager: CallbackManager) {
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                val accessToken = result.accessToken
                _registerUiEvent.value = RegisterViewModelEvent.Success
            }

            override fun onCancel() {
                _registerUiEvent.value = RegisterViewModelEvent.Error("Login canceled")
            }

            override fun onError(error: FacebookException) {
                _registerUiEvent.value = RegisterViewModelEvent.Error("Login failed: ${error.message}")
            }
        })
    }

    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(true)
        .setNonce(nonce)
        .setServerClientId(serverClientId = "992963912793-u2k81a65ms3s9j22lajrm3m2vdlr01dq.apps.googleusercontent.com")
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
                Log.d("HNA", "PublishKeyCredential: $responseJson")
                updateData(RegisterViewModelEvent.Success)
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
                        updateData(RegisterViewModelEvent.SocialSuccess)

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
    private fun updateData(newValue: RegisterViewModelEvent) {
        _registerUiEvent.value = newValue
    }


}

sealed class RegisterViewModelEvent {
    data class Error(val error: String) : RegisterViewModelEvent()
    data object NewUser : RegisterViewModelEvent()
    data object Success : RegisterViewModelEvent()
    data object SocialSuccess : RegisterViewModelEvent()
    data object Loading : RegisterViewModelEvent()
    data object UserAlreadyExit : RegisterViewModelEvent()
}

sealed class RegisterViewModelState {
    data object Idel : RegisterViewModelState()
}


