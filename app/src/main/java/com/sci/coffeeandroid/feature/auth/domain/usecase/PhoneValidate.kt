package com.sci.coffeeandroid.feature.auth.domain.usecase

import android.util.Patterns
import com.sci.coffeeandroid.util.use_case.ValidationResult

class PhoneValidate  {
     fun execute(password: String): ValidationResult {

        if(password.length <6){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Phone number must be at least 6 numbers"
            )
        }
        if (!Patterns.PHONE.matcher(password).matches()){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The isn't a valid phone number"
            )
        }

        return ValidationResult(
            isSuccess = true
        )
    }
}