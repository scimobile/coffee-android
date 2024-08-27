package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.data.datasource.CoffeeDetailsRemoteDataSource
import com.sci.coffeeandroid.feature.menudetails.data.datasource.FakeCoffeeDetailsRemoteDataSource
import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailsRepository
import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailsRepositoryImpl
import com.sci.coffeeandroid.feature.menudetails.ui.viewmodel.MenuDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val menuDetailsModule = module {
    single {
        FakeCoffeeDetailsRemoteDataSource() as CoffeeDetailsRemoteDataSource
    }
    single {
        CoffeeDetailsRepositoryImpl(
            get()
        ) as CoffeeDetailsRepository
    }

    viewModelOf(::MenuDetailsViewModel)

}