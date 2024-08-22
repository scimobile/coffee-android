package com.sci.coffeeandroid.feature.auth.ui.login

data class LoginFormState(
    var email: String = "",
    var emailError: String? =null,
    var password: String = "",
    var passwordError: String? =null,
    var isButtonEnable: Boolean = false
)
