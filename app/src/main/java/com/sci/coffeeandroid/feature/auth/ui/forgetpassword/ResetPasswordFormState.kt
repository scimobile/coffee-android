package com.sci.coffeeandroid.feature.auth.ui.forgetpassword

data class ResetPasswordFormState(
    var password: String = "",
    var passwordError: String? =null,
    var repeatedPassword: String = "",
    var repeatedPasswordError: String? =null,
)
