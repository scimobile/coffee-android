package com.sci.coffeeandroid.feature.auth.data.repository

import com.sci.coffeeandroid.feature.auth.data.datasource.UserRemoteDataSource
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

class RegisterRepository(
    private val userRemoteDataSource: UserRemoteDataSource
) {
    suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): UserModel {
        return userRemoteDataSource.register(username, email, phone, password)
    }
}