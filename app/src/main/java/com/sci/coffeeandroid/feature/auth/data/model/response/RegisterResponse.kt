package com.sci.coffeeandroid.feature.auth.data.model.response

data class RegisterResponse(
    val id : String,
    val data: Data,
    val message: String,
    val status: Boolean
)

data class Data(
    val email: String,
    val phoneNumber: String,
    val username: String
)