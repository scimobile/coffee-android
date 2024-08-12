package com.sci.coffeeandroid.feature.auth.data.repository

import com.sci.coffeeandroid.feature.auth.data.model.response.OTPResponse
import com.sci.coffeeandroid.feature.auth.data.model.response.PasswordResetResponse
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

interface AuthRepository {

    suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<UserModel>

    suspend fun login(username: String, password: String): Result<UserModel>

    suspend fun resetPassword(email: String, newPassword: String): Result<PasswordResetResponse>

    fun isUserLoggedIn(): Boolean

    fun removeAccessToken()

    suspend fun getOTP(email: String): Result<OTPResponse>

}