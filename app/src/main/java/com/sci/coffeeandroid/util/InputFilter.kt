package com.sci.coffeeandroid.util

import android.text.InputFilter
import android.text.Spanned
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.sci.coffeeandroid.R

class PhoneNumberInputFilter : InputFilter {
    // Regex pattern to allow digits, spaces, dashes, plus sign, and parentheses
    private val regex = Regex("[0-9+\\-() ]*")

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source == null) return null

        val input = dest?.subSequence(0, dstart).toString() +
                source.subSequence(start, end).toString() +
                dest?.subSequence(dend, dest.length).toString()

        return if (regex.matches(input)) {
            null // Accept the input
        } else {
            ""   // Reject the input
        }
    }
}

fun isValidPassword(password: String): Boolean {

    val containsNumber = password.any { it.isDigit() }
    val containsLetter = password.any { it.isLetter() }

    val specialCharacterRegex = Regex("[!@#$%^&*(),.?\":{}|<>]")
    val containsSpecialChar = password.any { specialCharacterRegex.containsMatchIn(it.toString()) }

    return containsNumber && containsSpecialChar && containsLetter
}

fun addTextChangesListener(
    etPassword: EditText,
    etConfirmPassword: EditText,
    textFieldPassword : TextInputLayout,
    textFieldConfirmPassword : TextInputLayout
) {

    etConfirmPassword.doAfterTextChanged {
        textFieldConfirmPassword.error = null
    }

    etPassword.doAfterTextChanged {
        textFieldPassword.error = null
    }
}

fun addTextChangeListener(
    etEmail: EditText,
    etPassword: EditText,
    textFieldEmail : TextInputLayout,
    textFieldPassword : TextInputLayout,
    btnLogin: Button,
    enableTextColor:Int,
    disableTextColor:Int
) {
    etPassword.doAfterTextChanged {
        val isEnable = etEmail.text.isNotBlank() && etPassword.text.isNotBlank()
        if(isEnable){
            setBtnEnable(enableTextColor,btnLogin)
        }else{
           setBtnDisable(disableTextColor,btnLogin)
        }
        if (etPassword.text.isBlank()) {
            textFieldPassword.error = "Enter password"
        }else{
            textFieldPassword.error = null
        }
    }

    etEmail.doAfterTextChanged {
        val isEnable = etEmail.text.isNotBlank() && etPassword.text.isNotBlank()
        if(isEnable){
            setBtnEnable(enableTextColor,btnLogin)
        }else{
            setBtnDisable(disableTextColor,btnLogin)
        }
        if (etEmail.text.isBlank()) {
            textFieldEmail.error =
                "Enter email"
        }else{
            textFieldEmail.error = null
        }

    }
}

fun setBtnDisable(disableTextColor: Int, btnLogin: Button) {
    btnLogin.isEnabled = false
    btnLogin.setBackgroundResource(R.drawable.button_disable_shape)
    btnLogin.setTextColor(disableTextColor)
}

fun setBtnEnable(enableTextColor: Int, btnLogin: Button) {
    btnLogin.isEnabled = true
    btnLogin.setBackgroundResource(R.drawable.button_enable_shape)
    btnLogin.setTextColor(enableTextColor)
}


fun validateInputs(
    email: String,
    password: String,
    textFieldEmail : TextInputLayout,
    textFieldPassword : TextInputLayout,
): Boolean {

    var isAllValidate = true

    if (email.isBlank()) {
        textFieldEmail.error = "Enter email"
        isAllValidate = false
    }
    if (password.isBlank()) {
        textFieldPassword.error =
            "Enter password"
        isAllValidate = false
    }
//    else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//        textFieldEmail.error = "That's not a valid email"
//        isAllValidate = false
//    }

    return isAllValidate
}

fun isMatchPassword(
    password: String,
    newPassword: String,
    texFieldPassword : TextInputLayout,
    texFieldNewPassword : TextInputLayout,
): Boolean {

    var isAllValidate = true

    if (password.isEmpty() ) {
        texFieldPassword.error = "Enter email"
        isAllValidate = false
    }else if (password.length < 8) {
        texFieldPassword.error ="Password must be at least 8 characters and"
        isAllValidate = false
    }else if (!isValidPassword(password)){
        texFieldPassword.error = "Password contain a number, letter and a special character"
        isAllValidate = false
    }
    if(newPassword.isEmpty()){
        texFieldNewPassword.error = "Enter new password"
        isAllValidate = false
    }else if(password != newPassword){
        texFieldNewPassword.error = "Passwords do not match"
        isAllValidate = false
    }

    return isAllValidate
}



