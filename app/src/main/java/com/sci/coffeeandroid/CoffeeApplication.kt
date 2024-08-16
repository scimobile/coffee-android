package com.sci.coffeeandroid

import android.app.Application
import com.sci.coffeeandroid.common.di.localStorageModule
import com.sci.coffeeandroid.feature.home.di.homeDatasourceModule
import com.sci.coffeeandroid.feature.home.di.homeRepositoryModule
import com.sci.coffeeandroid.feature.home.di.homeViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoffeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CoffeeApplication)
            modules(
                localStorageModule,
                homeViewModelModule,
                homeRepositoryModule,
                homeDatasourceModule
            )
        }
    }
}