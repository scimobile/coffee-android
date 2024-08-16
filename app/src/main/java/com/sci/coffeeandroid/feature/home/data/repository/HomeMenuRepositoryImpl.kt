package com.sci.coffeeandroid.feature.home.data.repository

import com.sci.coffeeandroid.feature.home.data.datasource.HomeMenuDataSource
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData

class HomeMenuRepositoryImpl(private val dataSource: HomeMenuDataSource) : HomeMenuRepository {

    override fun getHomeMenuData(): Result<List<HomeMenuData>> {
        return dataSource.getHomeMenuData()
    }

}