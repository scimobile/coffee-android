package com.sci.coffeeandroid.feature.menudetails.domain.model

data class CustomOrderModel(
    val size : String,
    val variation : String,
    val sugar: String,
    val milk: String,
    val topping : String,
    val specialInstructions : String
)
