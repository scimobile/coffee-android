package com.sci.coffeeandroid.feature.home.data.datasource

import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData

interface HomeMenuDataSource {

    fun getHomeMenuData(): Result<List<HomeMenuData>>

}