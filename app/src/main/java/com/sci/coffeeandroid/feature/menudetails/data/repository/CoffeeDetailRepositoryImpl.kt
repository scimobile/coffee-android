package com.sci.coffeeandroid.feature.menudetails.data.repository

import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailRemoteDataSource
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import kotlinx.coroutines.delay

class CoffeeDetailRepositoryImpl(
    private val coffeeDetailRemoteDataSource: CoffeeDetailRemoteDataSource
) : CoffeeDetailRepository {

    override suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel> {
        delay(600)
        return coffeeDetailRemoteDataSource.getCoffeeDetail(id = id)
    }

    override suspend fun addToCart(customOrderModel: CustomOrderModel): Result<CustomOrderModel> {
        return Result.success(customOrderModel)
    }
}