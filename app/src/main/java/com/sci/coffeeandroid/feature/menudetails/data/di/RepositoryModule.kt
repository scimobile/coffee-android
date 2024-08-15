package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailRepository
import org.koin.dsl.module

val coffeeRepositoryModule = module {
    single {
        CoffeeDetailRepository(
            get()
        )
    }
}