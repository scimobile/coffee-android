package com.sci.coffeeandroid.feature.menudetails.domain.model

data class CoffeeModel(
    val id: Int,
    val name: String,
    val price: Float,
    val description: String,
    val image: String,
//    val size: String,
//    val variation: String,
//    val sugar: String,
    val milk: List<String>,
    val toppings: List<String>,
    val specialInstructions: String
)