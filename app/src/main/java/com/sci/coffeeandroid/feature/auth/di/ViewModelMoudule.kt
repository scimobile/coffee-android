package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerViewModelModuel = module {
    viewModel {
        RegisterViewModel(
            get()
        )
    }
}