package com.sci.coffeeandroid.feature.auth.ui.forgetpassword

sealed class ForgetPasswordFormEvent {
    data class EmailChangedEvent(val email:String): ForgetPasswordFormEvent()
    data object Next: ForgetPasswordFormEvent()
}