package com.sci.coffeeandroid

import android.app.Application
import com.sci.coffeeandroid.common.di.localStorageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoffeeApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CoffeeApplication)
            modules(localStorageModule)
        }
    }
}