package com.sci.coffeeandroid.feature.home.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuDrink(
    val id: Long,
    val name: String,
    val price: String,
    val imageUrl: String
)
