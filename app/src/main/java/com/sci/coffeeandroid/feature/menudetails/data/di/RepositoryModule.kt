package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailRepository
import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailRepositoryImpl
import org.koin.dsl.module

val coffeeRepositoryModule = module {
    single {
        CoffeeDetailRepositoryImpl(
            get()
        ) as CoffeeDetailRepository
    }
}