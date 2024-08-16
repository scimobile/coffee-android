package com.sci.coffeeandroid.util.use_case

data class ValidationResult (
    val isSuccess: Boolean,
    val errorMessage: String? = null
)