package com.sci.coffeeandroid.feature.menudetails.domain.model

data class CustomOrderModel(
    val size : Size,
    val variation : Variation,
    val sugar: Sugar,
    val milk: String,
    val topping : String,
    val specialInstructions : String,
    val quantity : Int
)

enum class Sugar {
    NONE, THIRTY_PERCENT, FIFTY_PERCENT
}

enum class Variation {
    HOT, COLD
}

enum class Size{
    SMALL, MEDIUM, LARGE
}