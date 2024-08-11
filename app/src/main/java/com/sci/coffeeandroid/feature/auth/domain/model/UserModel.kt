package com.sci.coffeeandroid.feature.auth.domain.model

data class UserModel(
    val email: String,
    val phoneNumber : String,
    val username: String,
    val accessToken:String
)
