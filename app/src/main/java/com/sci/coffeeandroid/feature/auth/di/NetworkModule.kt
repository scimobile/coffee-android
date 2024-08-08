package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.data.service.KtorUtils
import org.koin.dsl.module

val networkModule = module {
    single {
        KtorUtils().createKtor(
            context = get()
        )
    }
}