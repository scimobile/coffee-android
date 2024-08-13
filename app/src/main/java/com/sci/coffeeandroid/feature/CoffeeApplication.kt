package com.sci.coffeeandroid.feature

import android.app.Application
import com.facebook.CallbackManager
import com.sci.coffeeandroid.feature.auth.di.networkModule
import com.sci.coffeeandroid.feature.auth.di.repositoryModule
import com.sci.coffeeandroid.feature.auth.di.datasourceModuleImpl
import com.sci.coffeeandroid.feature.auth.di.viewModelModule
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoffeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        startKoin {
            modules(
                networkModule,
                datasourceModuleImpl,
                repositoryModule,
                viewModelModule,
            )
            androidContext(this@CoffeeApplication)
        }
    }
}

