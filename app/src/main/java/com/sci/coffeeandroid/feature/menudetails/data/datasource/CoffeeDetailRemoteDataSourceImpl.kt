package com.sci.coffeeandroid.feature.menudetails.data.datasource

import com.sci.coffeeandroid.feature.menudetails.data.mapper.toCoffeeModel
import com.sci.coffeeandroid.feature.menudetails.data.model.response.CoffeeResponse
import com.sci.coffeeandroid.feature.menudetails.data.utils.handle
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.Size
import com.sci.coffeeandroid.feature.menudetails.domain.model.Sugar
import com.sci.coffeeandroid.feature.menudetails.domain.model.Variation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CoffeeDetailRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CoffeeDetailRemoteDataSource {

    override suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel> {
        return handle<CoffeeResponse> {
            httpClient.get("")
        }.map {
            it.toCoffeeModel()
        }
    }

    override suspend fun addToCart(customOrderModel: CustomOrderModel): Result<CustomOrderModel> {
        return Result.success(customOrderModel)
    }
}