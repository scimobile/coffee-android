package com.sci.coffeeandroid.feature.home.data.repository

import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData

interface HomeMenuRepository {

    fun getHomeMenuData(): Result<List<HomeMenuData>>

}