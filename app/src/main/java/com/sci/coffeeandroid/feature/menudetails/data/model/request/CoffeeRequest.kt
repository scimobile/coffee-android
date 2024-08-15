package com.sci.coffeeandroid.feature.menudetails.data.model.request

data class CoffeeRequest(
    val id: Int,
    val name: String,
    val price: Float,
    val description: String,
    val image : String,
    val size : String,
    val variation : String,
    val milk: String,
    val topping : String,
    val specialInstructions : String?
)
