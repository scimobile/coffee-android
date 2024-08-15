package com.sci.coffeeandroid.feature.menudetails.data.di

import com.sci.coffeeandroid.feature.menudetails.data.service.KtorUtils
import org.koin.dsl.module

val networkModule = module {
    single {
        KtorUtils().createKtor()
    }
}