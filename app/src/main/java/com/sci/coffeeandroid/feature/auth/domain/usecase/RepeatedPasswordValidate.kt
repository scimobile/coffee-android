package com.sci.coffeeandroid.feature.auth.domain.usecase

import android.util.Patterns
import com.sci.coffeeandroid.util.use_case.ValidationResult

class RepeatedPasswordValidate {
     fun execute(password: String, repeatedPassword: String): ValidationResult {
         if(password.isBlank()){
             return ValidationResult(
                 isSuccess = false,
                 errorMessage = "Password can't be blank"
             )
         }
        if(password != repeatedPassword){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The password don't match"
            )
        }

        return ValidationResult(
            isSuccess = true
        )
    }
}