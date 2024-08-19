package com.sci.coffeeandroid.feature.menudetails.data.utils

import com.sci.coffeeandroid.feature.menudetails.data.exceptions.ApiException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> handle(
    apiCall: () -> HttpResponse
): Result<T> {
    return try {
        val httpResponse = apiCall()
        when (httpResponse.status) {
            HttpStatusCode.OK -> {
                //Success
                val response: T = httpResponse.body()
                if (response != null) {
                    return Result.success(response)
                }
                return Result.failure(
                    ApiException(
                        code = httpResponse.status.value,
                        message = "Something went wrong"
                    )
                )
            }

            HttpStatusCode.NotFound -> Result.failure(
                ApiException(
                    code = httpResponse.status.value,
                    message = "NEW USER"
                )
            )

            else -> Result.failure(
                ApiException(
                    code = httpResponse.status.value,
                    message = "Something went wrong"
                )
            )
        }
    } catch (e: Exception) {
        //Fail
        return Result.failure(e)
    }
}