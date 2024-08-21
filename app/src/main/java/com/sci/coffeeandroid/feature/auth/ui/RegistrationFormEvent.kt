package com.sci.coffeeandroid.feature.auth.ui

sealed class RegistrationFormEvent {
    data class UsernameChangedEvent(val username:String): RegistrationFormEvent()
    data class EmailChangedEvent(val email:String): RegistrationFormEvent()
    data class PhoneChangedEvent(val phone:String): RegistrationFormEvent()
    data class PasswordChangedEvent(val password:String): RegistrationFormEvent()
    data class RepeatedPasswordChangedEvent(val repeatedPassword:String): RegistrationFormEvent()

    data object Submit: RegistrationFormEvent()
}

sealed class RestartPasswordFormEvent
{
    data class PasswordChangedEvent(val password:String): RestartPasswordFormEvent()
    data class RepeatedPasswordChangedEvent(val repeatedPassword:String): RestartPasswordFormEvent()

    data object Submit: RestartPasswordFormEvent()
}