package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.domain.usecase.EmailValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.PasswordValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.PhoneValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.RepeatedPasswordValidate
import com.sci.coffeeandroid.feature.auth.domain.usecase.UsernameValidate
import org.koin.dsl.module

val validateModelue = module {
    single<EmailValidate> {
        EmailValidate()
    }

    single<PasswordValidate> {
        PasswordValidate()
    }

    single<RepeatedPasswordValidate> {
        RepeatedPasswordValidate()
    }

    single<PhoneValidate> {
        PhoneValidate()
    }

    single<UsernameValidate> {
        UsernameValidate()
    }
}