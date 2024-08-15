package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailRemoteDataSource
import com.sci.coffeeandroid.feature.menudetails.data.datasource.FakeRemoteDataSource
import org.koin.dsl.module

val coffeeRemoteDataSourceModule = module {
    single {
        FakeRemoteDataSource() as CoffeeDetailRemoteDataSource
    }
}