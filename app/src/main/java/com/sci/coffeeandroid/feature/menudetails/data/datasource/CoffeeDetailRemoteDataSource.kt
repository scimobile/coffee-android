package com.sci.coffeeandroid.feature.menudetails.data.datasource

import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel

interface CoffeeDetailRemoteDataSource {
    suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel>
}