package com.sci.coffeeandroid

import android.app.Application
import com.sci.coffeeandroid.common.di.localStorageModule
import com.sci.coffeeandroid.feature.menudetails.data.di.coffeeDetailViewModelModule
import com.sci.coffeeandroid.feature.menudetails.data.di.coffeeRemoteDataSourceModule
import com.sci.coffeeandroid.feature.menudetails.data.di.coffeeRepositoryModule
import com.sci.coffeeandroid.feature.menudetails.data.di.networkModule
import com.sci.coffeeandroid.feature.menudetails.ui.viewmodel.CoffeeDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoffeeApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CoffeeApplication)
            modules(
                localStorageModule,
                coffeeDetailViewModelModule,
                coffeeRemoteDataSourceModule,
                coffeeRepositoryModule,
                networkModule
                )
        }
    }
}