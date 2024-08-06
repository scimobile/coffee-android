package com.sci.coffeeandroid.feature.auth.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentRegisterBinding
import com.sci.coffeeandroid.util.PhoneNumberInputFilter


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPhoneNumber.filters = arrayOf(PhoneNumberInputFilter())

        binding.btnSignup.setOnClickListener {


            if (binding.etUsername.text.isNullOrEmpty() || binding.etUsername.text!!.trim()
                    .isEmpty()
            ) {
                binding.textFieldUsername.error = "Username is required"
            }

            if (binding.etEmail.text.isNullOrEmpty() || binding.etEmail.text!!.trim().isEmpty()) {
                binding.textFieldEmail.error = "Email is required"
            }

            if (binding.etPhoneNumber.text.isNullOrEmpty() || binding.etPhoneNumber.text!!.trim()
                    .isEmpty()
            ) {
                binding.textFieldPhoneNumber.error = "Phone number is required"
            }

            if (binding.etPassword.text.isNullOrEmpty() || binding.etPassword.text!!.trim()
                    .isEmpty()
            ) {
                binding.textFieldPassword.error = "Password is required"
            }

            if (binding.etConfirmPassword.text.isNullOrEmpty() || binding.etConfirmPassword.text!!.trim()
                    .isEmpty()
            ) {
                binding.textFieldConfirmPassword.error = "Confirm password is required"
            }

            if (binding.etPhoneNumber.text!!.length < 6) {
                binding.textFieldPhoneNumber.error = "Enter correct phone number"
                return@setOnClickListener
            }

            if (binding.etPassword.text!!.length < 8) {
                binding.textFieldPassword.error = "Password must contain at least 8 characters. "
                return@setOnClickListener
            }
            else {
                if (!isValidPassword(binding.etPassword.text!!.toString())) {
                    binding.textFieldPassword.error =
                        "Password must contain at least one number, and one special character."
                    return@setOnClickListener
                }
            }
        }


    }

    private fun isValidPassword(password: String): Boolean {
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
}