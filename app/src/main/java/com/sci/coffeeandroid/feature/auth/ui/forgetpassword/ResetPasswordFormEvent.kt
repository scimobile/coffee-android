package com.sci.coffeeandroid.feature.auth.ui.forgetpassword

sealed class ResetPasswordFormEvent {
    data class RepeatedChangedEvent(val repeatedPassword:String): ResetPasswordFormEvent()
    data class PasswordChangedEvent(val password:String): ResetPasswordFormEvent()
    data class Reset(val email:String): ResetPasswordFormEvent()
}