package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.data.repository.RegisterRepository
import org.koin.dsl.module

val registerRepositoryModule = module {
    single {
        RegisterRepository(
            get()
        )
    }
}