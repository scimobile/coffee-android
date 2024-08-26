package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailsRepository
import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailsRepositoryImpl
import org.koin.dsl.module

val coffeeDetailsRepositoryModule = module {
    single {
        CoffeeDetailsRepositoryImpl(
            get()
        ) as CoffeeDetailsRepository
    }
}