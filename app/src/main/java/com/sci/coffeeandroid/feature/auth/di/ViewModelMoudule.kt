package com.sci.coffeeandroid.feature.auth.di

import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ForgotPasswordViewModel
import com.sci.coffeeandroid.feature.auth.ui.login.viewmodel.LoginViewModel
import com.sci.coffeeandroid.feature.auth.ui.register.viewmodel.RegisterViewModel
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ResetPasswordViewModel
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
            passwordValidate = get(),
            repeatedPasswordValidate = get()
        )
    }
    viewModel {
        ForgotPasswordViewModel(
           authRepository =  get()
        )
    }

}