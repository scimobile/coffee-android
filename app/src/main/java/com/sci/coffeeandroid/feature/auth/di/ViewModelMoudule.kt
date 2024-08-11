package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.ui.viewmodel.LoginViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RegisterViewModel(
           authRepository =  get()
        )
    }
    viewModel {
        LoginViewModel(
           authRepository =  get()
        )
    }
}