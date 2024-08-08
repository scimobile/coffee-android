package com.sci.coffeeandroid.feature.auth.data.mapper

import com.sci.coffeeandroid.feature.auth.data.model.response.RegisterResponse
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

fun RegisterResponse.toUserModel(): UserModel {
    return UserModel(
        username = data.username,
        email = data.email,
        phoneNumber = data.phoneNumber

    )

}