package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.data.datasource.TokenService
import org.koin.dsl.module

var tokenservice = module {
    single<TokenService> {
        TokenService()
    }
}