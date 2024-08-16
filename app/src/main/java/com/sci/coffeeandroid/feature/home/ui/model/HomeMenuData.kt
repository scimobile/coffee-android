package com.sci.coffeeandroid.feature.home.ui.model

interface HomeMenuData {}

data class PromotionSectionData(
    val title: String,
    val promotions: List<Promotion>
): HomeMenuData

data class CarouselSectionData(
    val title: String,
    val menuItems: List<MenuDrink>
): HomeMenuData

data class GridSectionData(
    val categories: List<MenuCategory>,
    val menuItems: List<MenuDrink>
): HomeMenuData
