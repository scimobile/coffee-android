package com.sci.coffeeandroid.feature.auth.domain.usecase

import android.util.Patterns
import com.sci.coffeeandroid.util.use_case.ValidationResult

class UsernameValidate  {
     fun execute(username: String): ValidationResult {

         if(username.isBlank()){
             return ValidationResult(
                 isSuccess = false,
                 errorMessage = "The username can't be blank"
             )
         }

        return ValidationResult(
            isSuccess = true
        )
    }
}