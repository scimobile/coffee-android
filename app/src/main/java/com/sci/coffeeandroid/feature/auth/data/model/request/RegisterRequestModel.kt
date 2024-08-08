package com.sci.coffeeandroid.feature.auth.data.model.request

data class RegisterRequestModel(
    val email: String,
    val password: String,
    val username: String,
    val phoneNumber : String

)
