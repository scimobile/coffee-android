package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.ui.viewmodel.CoffeeDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val coffeeDetailViewModelModule = module {
//    viewModel { params ->
//        CoffeeDetailViewModel(
//            get(),
//            params.get()
//        )
//    }
    viewModelOf(::CoffeeDetailViewModel)
}