package com.sci.coffeeandroid.feature.auth.data.repository

import com.sci.coffeeandroid.feature.auth.data.datasource.AuthLocalDataSource
import com.sci.coffeeandroid.feature.auth.data.datasource.AuthRemoteDataSource
import com.sci.coffeeandroid.feature.auth.data.datasource.AuthRemoteDataSourceImpl
import com.sci.coffeeandroid.feature.auth.data.model.response.OTPResponse
import com.sci.coffeeandroid.feature.auth.data.model.response.PasswordResetResponse
import com.sci.coffeeandroid.feature.auth.domain.model.OTPModel
import com.sci.coffeeandroid.feature.auth.domain.model.PasswordResetModel
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {
  override suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<UserModel> {
        return authRemoteDataSource.register(username, email, phone, password)
    }

   override suspend fun login(
        email: String,
        password: String
    ): Result<UserModel> {
       return authRemoteDataSource.login(email,password)
           .onSuccess {
               authLocalDataSource.saveAccessToken(
                   token = it.accessToken
               )
           }
    }

    override suspend fun resetPassword(
        email: String,
        newPassword: String
    ): Result<PasswordResetModel> {
        return authRemoteDataSource.resetPassword(email,newPassword)

    }

    override fun isUserLoggedIn() = authLocalDataSource.isUserLoggedIn()


    override fun removeAccessToken() {
        authLocalDataSource.removeAccessToken()
    }

    override suspend fun getOTP(email: String): Result<OTPModel> {
        return authRemoteDataSource.getOTP(email)
    }
}