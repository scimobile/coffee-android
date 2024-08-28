package com.sci.coffeeandroid.feature.auth.data.service

import com.sci.coffeeandroid.feature.auth.data.model.response.LoginResponseModel
import com.sci.coffeeandroid.feature.auth.data.model.response.PasswordResetResponse
import kotlinx.coroutines.delay

class FakeService {

    suspend fun login(email: String, password: String): Result<LoginResponseModel> {
        // Simulate network delay
        delay(2000)
        if (email.contains("@") ) {

            return Result.success(
                LoginResponseModel(
                    data = LoginResponseModel.User(
                        email = email,
                        phoneNumber = "0995858585",
                        username = "Test User",
                        accessToken = "accessbeirjfjfjqoqjdjdoqofjfj.djdjdjkq.odododf.irrjgjdj"
                    ),
                    status = true,
                    message = "Successfully LoggedIn"
                )
            )
        } else {
            return Result.failure(
                Exception("Invalid email or password")
            )
        }
    }
}