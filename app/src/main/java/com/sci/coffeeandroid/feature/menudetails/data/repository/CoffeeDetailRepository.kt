package com.sci.coffeeandroid.feature.menudetails.data.repository

import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailRemoteDataSource
import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailRemoteDataSourceImpl
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import kotlinx.coroutines.delay

interface CoffeeDetailRepository {

    suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel>

    suspend fun addToCart(customOrderModel: CustomOrderModel): Result<CustomOrderModel>
}