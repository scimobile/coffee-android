package com.sci.coffeeandroid.feature.home.di

import com.sci.coffeeandroid.feature.home.data.repository.HomeMenuRepository
import com.sci.coffeeandroid.feature.home.data.repository.HomeMenuRepositoryImpl
import org.koin.dsl.module

val homeRepositoryModule = module {
    single {
        HomeMenuRepositoryImpl(get()) as HomeMenuRepository
    }
}