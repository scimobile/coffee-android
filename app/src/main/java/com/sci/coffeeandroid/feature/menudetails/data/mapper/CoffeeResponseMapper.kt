package com.sci.coffeeandroid.feature.menudetails.data.mapper

import com.sci.coffeeandroid.feature.menudetails.data.model.response.CoffeeDetailResponse
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel

fun CoffeeDetailResponse.toCoffeeModel(): CoffeeModel =
    CoffeeModel(
        id = this.id,
        name = this.name.orEmpty(),
        price = this.price ?: 0.00f,
        isFavourite = this.isFavourite ?: false,
        description = this.description.orEmpty(),
        image = this.image.orEmpty(),
        milk = this.milk.orEmpty(),
        toppings = this.toppings.orEmpty(),
        availableQuantity = this.availableQuantity ?: 0
    )

