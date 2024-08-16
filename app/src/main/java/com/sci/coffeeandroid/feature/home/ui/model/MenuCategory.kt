package com.sci.coffeeandroid.feature.home.ui.model

data class MenuCategory(
    val id: Long,
    val name: String,
    val imageUrl: String,
    var isSelected: Boolean
)
