package com.sci.coffeeandroid.feature.menudetails.data.datasource

import com.sci.coffeeandroid.feature.menudetails.data.mapper.toCoffeeModel
import com.sci.coffeeandroid.feature.menudetails.data.model.response.CoffeeDetailResponse
import com.sci.coffeeandroid.feature.menudetails.data.utils.handle
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post

class CoffeeDetailsRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CoffeeDetailsRemoteDataSource {

    override suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel> {
        return handle<CoffeeDetailResponse> {
            httpClient.get("")
        }.map {
            it.toCoffeeModel()
        }
    }

    override suspend fun addToCart(customOrderModel: CustomOrderModel): Result<CustomOrderModel> {
        return handle<CustomOrderModel> {
            httpClient.post("")
        }
    }
}