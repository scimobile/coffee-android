package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.data.datasource.UserRemoteDataSource
import org.koin.dsl.module

val userRemoteDatasourceModule = module {
    single {
        UserRemoteDataSource(
            httpClient = get()
        )
    }
}