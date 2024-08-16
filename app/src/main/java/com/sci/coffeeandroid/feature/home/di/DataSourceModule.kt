package com.sci.coffeeandroid.feature.home.di

import com.sci.coffeeandroid.feature.home.data.datasource.HomeMenuDataSource
import com.sci.coffeeandroid.feature.home.data.datasource.MockHomeMenuDataSourceImpl
import org.koin.dsl.module

val homeDatasourceModule = module {
    single {
        MockHomeMenuDataSourceImpl() as HomeMenuDataSource
    }
}