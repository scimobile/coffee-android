package com.sci.coffeeandroid.feature.auth.domain.usecase

import android.util.Patterns
import com.sci.coffeeandroid.util.use_case.ValidationResult

class PasswordValidate  {
     fun execute(password: String): ValidationResult {
        if(password.length <8){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The password must be at least 8 characters"
            )
        }
         val specialCharacterRegex = Regex("[!@#$%^&*(),.?\":{}|<>]")
         val containsSpecialChar = password.any { specialCharacterRegex.containsMatchIn(it.toString()) }

         val containLettersAndDigits = password.any {it.isDigit() } && password.any { it.isLetter() }
        if (!containLettersAndDigits || !containsSpecialChar){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The password needs to contain letter, digit and special character"
            )
        }

        return ValidationResult(
            isSuccess = true
        )
    }
}