package com.sci.coffeeandroid.feature.auth.ui.forgetpassword

data class ForgetPasswordFormState(
    var email: String = "",
    var emailError: String? =null,
    var isButtonEnable : Boolean = false
)
