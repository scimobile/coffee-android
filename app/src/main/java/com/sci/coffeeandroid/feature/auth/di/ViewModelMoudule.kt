package com.sci.coffeeandroid.feature.auth.di

import com.facebook.CallbackManager
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ForgotPasswordViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.LoginViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ResetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RegisterViewModel(
           authRepository =  get(),
            usernameValidate = get(),
            emailValidate = get(),
            passwordValidate = get(),
            repeatedPasswordValidate = get(),
            phoneValidate = get()
        )
    }
    viewModel {
        LoginViewModel(
           authRepository =  get()
        )
    }
    viewModel {
        ResetPasswordViewModel(
           authRepository =  get(),
            repeatedPasswordValidate = get(),
            passwordValidate = get()
        )
    }
    viewModel {
        ForgotPasswordViewModel(
           authRepository =  get()
        )
    }

}