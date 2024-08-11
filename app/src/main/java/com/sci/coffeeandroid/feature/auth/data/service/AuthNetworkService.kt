package com.sci.coffeeandroid.feature.auth.data.service

import com.sci.coffeeandroid.feature.auth.data.model.response.LoginResponseModel
import com.sci.coffeeandroid.feature.auth.data.model.response.RegisterResponse
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel
import io.ktor.client.HttpClient

interface AuthNetworkService {
    suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<RegisterResponse>

    suspend fun login(
        username: String,
        password: String
    ): Result<LoginResponseModel>
}