package com.sci.coffeeandroid.feature.menudetails.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CoffeeListResponse(
    val coffee : List<CoffeeResponse>?
)

@Serializable
data class CoffeeResponse(
    val id: Int,
    val name: String?,
    val price: Float?,
    val description: String?,
    val image: String?,
    val size: String?,
    val variation: String?,
    val sugar: String?,
    val milk: List<String>?,
    val toppings: List<String>?,
    val specialInstructions: String?
    )