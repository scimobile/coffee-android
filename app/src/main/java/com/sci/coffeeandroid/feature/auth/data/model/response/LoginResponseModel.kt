package com.sci.coffeeandroid.feature.auth.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseModel(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: User
){
    @Serializable
    data class User(
        @SerialName("email")
        val email: String,
        @SerialName("phone_number")
        val phoneNumber: String,
        @SerialName("user_name")
        val username: String,
        @SerialName("access_token")
        val accessToken: String,
    )
}


