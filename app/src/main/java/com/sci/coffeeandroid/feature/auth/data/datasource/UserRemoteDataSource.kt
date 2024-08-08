package com.sci.coffeeandroid.feature.auth.data.datasource

import com.sci.coffeeandroid.feature.auth.data.mapper.toUserModel
import com.sci.coffeeandroid.feature.auth.data.model.request.RegisterRequestModel
import com.sci.coffeeandroid.feature.auth.data.model.response.Data
import com.sci.coffeeandroid.feature.auth.data.model.response.RegisterResponse
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel
import io.ktor.client.HttpClient

class UserRemoteDataSource(private val httpClient: HttpClient) {
    suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<UserModel> {
        val request = RegisterRequestModel(
            username = username,
            email = email,
            phoneNumber = phone,
            password = password
        )
        return Result.success(
             RegisterResponse(
                id = "1",
                data = Data(
                    email = request.email,
                    phoneNumber = request.phoneNumber,
                    username = request.username
                ),
                status = true,
                message = "Register Successfully"
            )
                .toUserModel()
        )
    }
}