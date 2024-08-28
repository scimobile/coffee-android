package com.sci.coffeeandroid.feature.menudetails.data.repository

import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailsRemoteDataSource
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import kotlinx.coroutines.delay

class CoffeeDetailsRepositoryImpl(
    private val coffeeDetailsRemoteDataSource: CoffeeDetailsRemoteDataSource
) : CoffeeDetailsRepository {

    override suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel> {
        return coffeeDetailsRemoteDataSource.getCoffeeDetail(id = id)
    }

    override suspend fun addToCart(customOrderModel: CustomOrderModel): Result<CustomOrderModel> {
        return Result.success(customOrderModel)
    }
}