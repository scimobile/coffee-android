package com.sci.coffeeandroid.feature.auth.ui.login

sealed class LoginFormEvent {
    data class EmailChangedEvent(val email:String): LoginFormEvent()
    data class PasswordChangedEvent(val password:String): LoginFormEvent()
    data object Login: LoginFormEvent()
}