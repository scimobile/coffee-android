package com.sci.coffeeandroid.common.di

import android.content.Context
import com.sci.coffeeandroid.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localStorageModule = module {
    single {
        androidContext().getSharedPreferences(
            androidContext().getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
    }
}