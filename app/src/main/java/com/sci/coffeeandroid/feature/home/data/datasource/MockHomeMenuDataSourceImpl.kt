package com.sci.coffeeandroid.feature.home.data.datasource

import com.sci.coffeeandroid.feature.home.ui.model.CarouselSectionData
import com.sci.coffeeandroid.feature.home.ui.model.GridSectionData
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData
import com.sci.coffeeandroid.feature.home.ui.model.MenuCategory
import com.sci.coffeeandroid.feature.home.ui.model.MenuDrink
import com.sci.coffeeandroid.feature.home.ui.model.Promotion
import com.sci.coffeeandroid.feature.home.ui.model.PromotionSectionData

class MockHomeMenuDataSourceImpl : HomeMenuDataSource {

    override fun getHomeMenuData(): Result<List<HomeMenuData>> {
        return Result.success(
            listOf(
                PromotionSectionData(
                    "Promotion",
                    listOf(
                        Promotion(
                            0L,
                            "Promotion One",
                            "Description one for the promotion one to use in this coffee android app.",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        Promotion(
                            1L,
                            "Promotion Two",
                            "Description two for the promotion two to use in this coffee android app.",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        Promotion(
                            2L,
                            "Promotion Three",
                            "Description three for the promotion three to use in this coffee android app.",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        )
                    )
                ),
                CarouselSectionData(
                    "Recommended",
                    listOf(
                        MenuDrink(
                            0L,
                            "Drink One",
                            "5500 MMK",
                            "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11"
                        ),
                        MenuDrink(
                            1L,
                            "Drink Two",
                            "5500 MMK",
                            "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11"
                        ),
                        MenuDrink(
                            2L,
                            "Drink Three",
                            "5500 MMK",
                            "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11"
                        ),
                        MenuDrink(
                            3L,
                            "Drink Four",
                            "5500 MMK",
                            "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11"
                        )
                    )
                ),
                CarouselSectionData(
                    "New Arrivals",
                    listOf(
                        MenuDrink(
                            0L,
                            "Drink One",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        MenuDrink(
                            1L,
                            "Drink Two",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        MenuDrink(
                            2L,
                            "Drink Three",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        MenuDrink(
                            3L,
                            "Drink Four",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        )
                    )
                ),
                GridSectionData(
                    listOf(
                        MenuCategory(
                            id = 0L,
                            name = "ALL",
                            imageUrl = "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11",
                            isSelected = true
                        ),
                        MenuCategory(
                            id = 1L,
                            name = "Capucino",
                            imageUrl = "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11",
                            isSelected = false
                        ),
                        MenuCategory(
                            id = 2L,
                            name = "Expresso",
                            imageUrl = "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11",
                            isSelected = false
                        ),
                        MenuCategory(
                            id = 3L,
                            name = "Latte",
                            imageUrl = "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11",
                            isSelected = false
                        ),
                        MenuCategory(
                            id = 4L,
                            name = "Other",
                            imageUrl = "https://firebasestorage.googleapis.com/v0/b/social-media-app-d4ea0.appspot.com/o/uploads%2Fcoffee.png?alt=media&token=727f355a-8e4f-4ae0-8ac1-552a22f32e11",
                            isSelected = false
                        ),
                    ),
                    listOf(
                        MenuDrink(
                            0L,
                            "Drink One",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        MenuDrink(
                            1L,
                            "Drink Two",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        MenuDrink(
                            2L,
                            "Drink Three",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        MenuDrink(
                            3L,
                            "Drink Four",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        ),
                        MenuDrink(
                            4L,
                            "Drink Four",
                            "5500 MMK",
                            "https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1949&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                        )
                    )
                ),
            )
        )
    }

}