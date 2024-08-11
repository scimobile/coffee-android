package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.data.datasource.AuthLocalDataSource
import com.sci.coffeeandroid.feature.auth.data.datasource.AuthLocalDataSourceImpl
import com.sci.coffeeandroid.feature.auth.data.datasource.AuthRemoteDataSource
import com.sci.coffeeandroid.feature.auth.data.datasource.AuthRemoteDataSourceImpl
import com.sci.coffeeandroid.feature.auth.data.repository.AuthRepository
import org.koin.dsl.module

val datasourceModuleImpl = module {
    single<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(
            authNetworkService = get()
        )
    }

    single<AuthLocalDataSource> {
        AuthLocalDataSourceImpl()
    }
}