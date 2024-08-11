package com.sci.coffeeandroid.feature.auth.data.repository

import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

interface AuthRepository {

    suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<UserModel>

    suspend fun login(username: String, password: String): Result<UserModel>

    fun isUserLoggedIn(): Boolean

    fun removeAccessToken()

}