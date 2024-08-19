package com.sci.coffeeandroid.feature.menudetails.domain.model

data class CoffeeModel(
    val id: Int,
    val name: String,
    val price: Float,
    val isFavourite: Boolean,
    val description: String,
    val image: String,
    val milk: List<String>,
    val toppings: List<String>,
)