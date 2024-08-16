package com.sci.coffeeandroid.feature.menudetails.data.mapper

import com.sci.coffeeandroid.feature.menudetails.data.model.response.CoffeeResponse
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel

fun CoffeeResponse.toCoffeeDetailModel(): CoffeeModel =
    CoffeeModel(
        id = this.id,
        name = this.name.orEmpty(),
        price = this.price ?: 0.00f,
        description = this.description.orEmpty(),
        image = this.image.orEmpty(),
//        size = this.size.orEmpty(),
//        variation = this.variation.orEmpty(),
//        sugar = this.sugar.orEmpty(),
        milk = this.milk.orEmpty(),
        toppings = this.toppings.orEmpty(),
        specialInstructions = this.specialInstructions.orEmpty()
    )
