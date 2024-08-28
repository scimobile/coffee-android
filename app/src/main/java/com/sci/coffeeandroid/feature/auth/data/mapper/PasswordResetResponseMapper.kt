package com.sci.coffeeandroid.feature.auth.data.mapper

import com.sci.coffeeandroid.feature.auth.data.model.response.PasswordResetResponse
import com.sci.coffeeandroid.feature.auth.domain.model.PasswordResetModel

fun PasswordResetResponse.toModel(): PasswordResetModel {
    return PasswordResetModel(
        status= status,
        message = message
    )
}