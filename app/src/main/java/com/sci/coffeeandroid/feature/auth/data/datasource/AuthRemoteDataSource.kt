package com.sci.coffeeandroid.feature.auth.data.datasource
import com.sci.coffeeandroid.feature.auth.data.model.response.OTPResponse
import com.sci.coffeeandroid.feature.auth.data.model.response.PasswordResetResponse
import com.sci.coffeeandroid.feature.auth.domain.model.OTPModel
import com.sci.coffeeandroid.feature.auth.domain.model.PasswordResetModel
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

interface AuthRemoteDataSource {
    suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<UserModel>

    suspend fun login(
        email: String,
        password: String
    ): Result<UserModel>

    suspend fun resetPassword(
        email: String,
        newPassword: String
    ): Result<PasswordResetModel>

    suspend fun getOTP(
        email: String,
    ) : Result<OTPModel>
}