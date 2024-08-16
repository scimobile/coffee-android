package com.sci.coffeeandroid.feature.auth.domain.usecase

import android.util.Patterns
import com.sci.coffeeandroid.util.use_case.ValidateUsecase
import com.sci.coffeeandroid.util.use_case.ValidationResult

class RepeatedPasswordValidate : ValidateUsecase<String> {
    override fun execute(password: String, ): ValidationResult {
        if(password.length <8){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The password must be at least 8 characters"
            )
        }

        return ValidationResult(
            isSuccess = true
        )
    }
}