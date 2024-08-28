package com.sci.coffeeandroid.feature.menudetails.data.repository

import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel

interface CoffeeDetailsRepository {

    suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel>

    suspend fun addToCart(customOrderModel: CustomOrderModel): Result<CustomOrderModel>
}