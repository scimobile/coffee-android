package com.sci.coffeeandroid.feature.menudetails.data.datasource

import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import kotlinx.coroutines.delay

class FakeCoffeeDetailsRemoteDataSource() : CoffeeDetailsRemoteDataSource {
    override suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel> {
        delay(3000)
        return Result.success(
            CoffeeModel(
                id = 1,
                name = "Vanilla Latte",
                price = 13.90f,
                isFavourite = true,
                image = "https://static.vecteezy.com/system/resources/previews/036/159/545/original/ai-generated-vanilla-latte-latte-with-vanilla-syrup-on-transparent-background-free-png.png",
                description = "A brief yet enticing description of the coffee, highlighting its flavor profile, ingredients, and any unique features",
                milk = listOf(
                    "None",
                    "Almond Milk",
                    "Soy Milk",
                    "Oat Milk",
                    "Whole Milk",
                    "Skim Milk",
                    "Heavy Cream",
                    "Half-and-Half",
                    "2% Milk"
                ),
                toppings = listOf(
                    "None",
                    "Whipped Cream",
                    "Cinnamon",
                    "Cocoa Powder",
                    "Chocolate Shavings",
                    "Nutmeg",
                    "Vanilla Extract",
                    "Caramel Drizzle",
                    "Chocolate Syrup",
                    "Honey",
                    "Maple Syrup",
                    "Peppermint",
                    "Nut Butter"
                )
            )
        )
    }

    override suspend fun addToCart(customOrderModel: CustomOrderModel): Result<CustomOrderModel> {
        return Result.success(
            customOrderModel
        )
    }
}