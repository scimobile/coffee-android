package com.sci.coffeeandroid.feature.auth.data.mapper

import com.sci.coffeeandroid.feature.auth.data.model.response.OTPResponse
import com.sci.coffeeandroid.feature.auth.domain.model.OTPModel

fun OTPResponse.toModel():OTPModel{
    return OTPModel(
        status= status,
        message = message
    )
}