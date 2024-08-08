package com.sci.coffeeandroid.feature

import android.app.Application
import com.sci.coffeeandroid.feature.auth.di.networkModule
import com.sci.coffeeandroid.feature.auth.di.registerRepositoryModule
import com.sci.coffeeandroid.feature.auth.di.registerViewModelModuel
import com.sci.coffeeandroid.feature.auth.di.userRemoteDatasourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoffeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                networkModule,
                registerRepositoryModule,
                registerViewModelModuel,
                userRemoteDatasourceModule
            )
            androidContext(this@CoffeeApplication)
        }
    }
}