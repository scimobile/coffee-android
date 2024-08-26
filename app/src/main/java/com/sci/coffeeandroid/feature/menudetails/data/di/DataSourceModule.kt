package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailsRemoteDataSource
import com.sci.coffeeandroid.feature.menudetails.data.datasource.FakeCoffeeDetailsRemoteDataSource
import org.koin.dsl.module

val coffeeDetailsRemoteDataSourceModule = module {
    single {
        FakeCoffeeDetailsRemoteDataSource() as CoffeeDetailsRemoteDataSource
    }
}