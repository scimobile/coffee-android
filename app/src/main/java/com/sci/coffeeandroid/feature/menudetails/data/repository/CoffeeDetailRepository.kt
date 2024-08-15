package com.sci.coffeeandroid.feature.menudetails.data.repository

import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailRemoteDataSource
import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailRemoteDataSourceImpl
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import kotlinx.coroutines.delay

class CoffeeDetailRepository(private val coffeeDetailRemoteDataSource: CoffeeDetailRemoteDataSource) {

    suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel>{
        delay(600)
        return coffeeDetailRemoteDataSource.getCoffeeDetail(id = id)
    }
}