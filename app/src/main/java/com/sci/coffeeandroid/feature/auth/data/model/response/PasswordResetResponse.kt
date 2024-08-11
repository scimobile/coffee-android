package com.sci.coffeeandroid.feature.auth.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetResponse(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: String,
)