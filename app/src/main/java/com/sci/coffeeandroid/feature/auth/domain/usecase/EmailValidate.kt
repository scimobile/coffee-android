package com.sci.coffeeandroid.feature.auth.domain.usecase

import android.util.Patterns
import com.sci.coffeeandroid.util.use_case.ValidationResult

class EmailValidate  {
     fun execute(email: String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "That's not a valid email"
            )
        }

        return ValidationResult(
            isSuccess = true
        )
    }
}