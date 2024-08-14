package com.sci.coffeeandroid.feature.auth.data.service

import com.sci.coffeeandroid.feature.auth.data.model.request.LoginRequestModel
import com.sci.coffeeandroid.feature.auth.data.model.request.RegisterRequestModel
import com.sci.coffeeandroid.feature.auth.data.model.response.Data
import com.sci.coffeeandroid.feature.auth.data.model.response.LoginResponseModel
import com.sci.coffeeandroid.feature.auth.data.model.response.OTPResponse
import com.sci.coffeeandroid.feature.auth.data.model.response.PasswordResetResponse
import com.sci.coffeeandroid.feature.auth.data.model.response.RegisterResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthNetworkServiceImpl(httpClient: HttpClient) : AuthNetworkService {
    override suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ): Result<RegisterResponse> {
        val request = RegisterRequestModel(
            username = username,
            email = email,
            phoneNumber = phone,
            password = password
        )
        withContext(Dispatchers.IO) {
            Thread.sleep(2000)
        }
        return Result.success(
            RegisterResponse(
                data = Data(
                    email = request.email,
                    phoneNumber = request.phoneNumber,
                    username = request.username
                ),
                status = true,
                message = "Successfully Registered"
            )
        )
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<LoginResponseModel> {
        val request = LoginRequestModel(
            email = email,
            password = password
        )
        withContext(Dispatchers.IO) {
            Thread.sleep(2000)
        }
        if (email == "example@gmail.com" && password == "password") {

            return Result.success(
                LoginResponseModel(
                    data = LoginResponseModel.User(
                        email = request.email,
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


    override suspend fun resetPassword(
        email: String,
        newPassword: String
    ): Result<PasswordResetResponse> {
            return Result.success(
                PasswordResetResponse(
                    status = true,
                    message = "Successfully reset password"
                )
            )
        }


    override suspend fun getOTP(email: String): Result<OTPResponse> {

        if (email == "example@gmail.com") {
            return Result.success(
                OTPResponse(
                    status = true,
                    message = "OTP sent successfully"
                ))
        } else {
            return Result.failure(
                Exception("Invalid email")
            )
        }
    }
}