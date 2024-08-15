package com.sci.coffeeandroid.feature.menudetails.data.datasource

import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CoffeeDetailRemoteDataSourceImpl() : CoffeeDetailRemoteDataSource {

    override suspend fun getCoffeeDetail(id: Int): Result<CoffeeModel> {
        return Result.success(
            CoffeeModel(
                id = 1,
                name = "Latte",
                price = 19.09f,
                image = "https://static.vecteezy.com/system/resources/previews/036/159/545/original/ai-generated-vanilla-latte-latte-with-vanilla-syrup-on-transparent-background-free-png.png",
                description = "blah blah blah",
//                size = "small",
//                variation = "hot",
//                sugar = "30%",
                milk = listOf(
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
                    "Nut Butter",
                    "Sugar Crystals",
                    "Edible Glitter",
                    "Sea Salt",
                    "Lavender",
                    "Toasted Coconut Flakes",
                    "Chopped Nuts",
                    "Marshmallows",
                    "Gingerbread Crumbs"
                ),
                specialInstructions = ""
            )
        )
    }
}