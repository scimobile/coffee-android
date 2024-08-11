package com.sci.coffeeandroid.feature.auth.data.datasource
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

interface AuthRemoteDataSource {
    suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<UserModel>

    suspend fun login(
        username: String,
        password: String
    ): Result<UserModel>
}