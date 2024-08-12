package com.sci.coffeeandroid.util

import android.text.InputFilter
import android.text.Spanned
import android.util.Patterns
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout

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


fun validateField(
    editText: EditText,
    isPassword : Boolean,
    textField: TextInputLayout,
    errorMessage: String
): Boolean {
    return if (editText.text.isNullOrBlank()) {
        textField.error = errorMessage
        false
    } else {
        if (isPassword) {
            if (!isValidPassword(editText.text.toString())) {
                textField.error =
                    "Password must be contain a number and a special character"
                return false
            }
        }
        textField.error = null
        true
    }
}

 fun isValidPassword(password: String): Boolean {
    // Check if password is at least 8 characters long
    if (password.length < 8) return false

    // Check if password contains at least one number
    val containsNumber = password.any { it.isDigit() }

    // Check if password contains at least one special character
    val specialCharacterRegex = Regex("[!@#$%^&*(),.?\":{}|<>]")
    val containsSpecialChar =
        password.any { specialCharacterRegex.containsMatchIn(it.toString()) }

    return containsNumber && containsSpecialChar
}

fun addTextChangeListener(
    etUsername: EditText,
    etEmail: EditText,
    etPhone: EditText,
    etPassword: EditText,
    etConfirmPassword: EditText,
    textFieldUsername : TextInputLayout,
    textFieldEmail : TextInputLayout,
    textFieldPhoneNumber : TextInputLayout,
    textFieldPassword : TextInputLayout,
    textFieldConfirmPassword : TextInputLayout
) {
    etUsername.doAfterTextChanged {
        textFieldUsername.error = null
    }

    etEmail.doAfterTextChanged {
        textFieldEmail.error = null
    }

    etPhone.doAfterTextChanged {
        textFieldPhoneNumber.error = null
    }

    etConfirmPassword.doAfterTextChanged {
        textFieldConfirmPassword.error = null
    }

    etPassword.doAfterTextChanged {
        textFieldPassword.error = null
    }
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
) {
    etPassword.doAfterTextChanged {
        textFieldPassword.error = null
    }

    etEmail.doAfterTextChanged {
        textFieldEmail.error = null
    }
    }

 fun validateInputs(
    username: String,
    email: String,
    phone: String,
    password: String,
    confirmPassword: String,
    textFieldUserName : TextInputLayout,
    textFieldEmail : TextInputLayout,
    textFieldPhoneNumber : TextInputLayout,
    textFieldPassword : TextInputLayout,
    textFieldConfirmPassword : TextInputLayout
): Boolean {

    var isAllValidate = true


    if (username.isEmpty()) {
        textFieldUserName.error = "Username is required"
        isAllValidate = false
    }

    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        textFieldEmail.error = "Valid email is required"
        isAllValidate = false
    }

    if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length < 6) {
        textFieldPhoneNumber.error = "Valid phone number is required"
        isAllValidate = false
    }

    if (password.isEmpty() || !isValidPassword(password)) {
        textFieldPassword.error =
            "Password must be at least 8 characters and contain a number and a special character"
        isAllValidate = false
    }

    if (confirmPassword.isEmpty() || confirmPassword != password) {
        textFieldConfirmPassword.error = "Passwords do not match"
        isAllValidate = false
    }

    return isAllValidate
}

fun validateInputs(
    email: String,
    password: String,
    textFieldEmail : TextInputLayout,
    textFieldPassword : TextInputLayout,
): Boolean {

    var isAllValidate = true

    if (email.isEmpty()) {
        textFieldEmail.error = "Enter email"
        isAllValidate = false
    }

    if (password.isEmpty()) {
        textFieldPassword.error =
            "Enter password"
        isAllValidate = false
    }

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
    }
    if (!isValidPassword(password)){
        texFieldPassword.error =
            "Password must be at least 8 characters and contain a number and a special character"
        isAllValidate = false
    }
    if(newPassword.isEmpty()){
        texFieldNewPassword.error = "Enter new password"
        isAllValidate = false
    }
    if(password != newPassword){
        texFieldNewPassword.error = "Passwords do not match"
        isAllValidate = false
    }


    return isAllValidate
}



