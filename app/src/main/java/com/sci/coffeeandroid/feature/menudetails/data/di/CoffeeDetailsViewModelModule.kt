package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.ui.viewmodel.MenuDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val menuDetailsViewModelModule = module {
//    viewModel { params ->
//        CoffeeDetailViewModel(
//            get(),
//            params.get()
//        )
//    }
    viewModelOf(::MenuDetailsViewModel)
}