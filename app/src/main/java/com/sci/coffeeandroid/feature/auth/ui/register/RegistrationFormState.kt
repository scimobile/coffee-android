package com.sci.coffeeandroid.feature.auth.ui.register

data class RegistrationFormState(
    var phone: String ="",
    var phoneError: String? =null,
    var username: String = "",
    var usernameError: String? =null,
    var email: String = "",
    var emailError: String? =null,
    var password: String = "",
    var passwordError: String? =null,
    var repeatedPassword : String = "",
    var repeatedPasswordError : String? =null
)
