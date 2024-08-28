package com.sci.coffeeandroid.feature.auth.data.datasource

import com.sci.coffeeandroid.feature.auth.data.mapper.toModel
import com.sci.coffeeandroid.feature.auth.data.mapper.toUserModel
import com.sci.coffeeandroid.feature.auth.data.model.request.LoginRequestModel
import com.sci.coffeeandroid.feature.auth.data.model.request.RegisterRequestModel
import com.sci.coffeeandroid.feature.auth.data.model.response.Data
import com.sci.coffeeandroid.feature.auth.data.model.response.LoginResponseModel
import com.sci.coffeeandroid.feature.auth.data.model.response.OTPResponse
import com.sci.coffeeandroid.feature.auth.data.model.response.PasswordResetResponse
import com.sci.coffeeandroid.feature.auth.data.model.response.RegisterResponse
import com.sci.coffeeandroid.feature.auth.data.service.AuthNetworkService
import com.sci.coffeeandroid.feature.auth.domain.model.OTPModel
import com.sci.coffeeandroid.feature.auth.domain.model.PasswordResetModel
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel
import io.ktor.client.HttpClient

class AuthRemoteDataSourceImpl(private val authNetworkService: AuthNetworkService) : AuthRemoteDataSource {

    override suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<UserModel> {
        return authNetworkService.register(username, email, phone, password)
            .map {
                it.toUserModel()
            }
    }

   override suspend fun login(
        email: String,
        password: String
    ): Result<UserModel> {
       return authNetworkService.login( email, password)
           .map {
               it.toModel()
           }
    }

    override suspend fun resetPassword(
        email: String,
        newPassword: String
    ): Result<PasswordResetModel> {
        return  authNetworkService.resetPassword(email,newPassword)
            .map {
                it.toModel()
            }
    }

    override suspend fun getOTP(email: String): Result<OTPModel> {
        return authNetworkService.getOTP(email)
            .map {
                it.toModel()
            }
    }
}