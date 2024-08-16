package com.sci.coffeeandroid.util.use_case

interface ValidateUsecase <Param> {
    fun  execute(param : Param ) : ValidationResult
}