package com.sci.coffeeandroid.feature.auth.ui.screen

import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentRegisterBinding
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterUiState
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModel
import com.sci.coffeeandroid.util.PhoneNumberInputFilter
import com.sci.coffeeandroid.util.addTextChangeListener
import com.sci.coffeeandroid.util.showSuccessDialog
import com.sci.coffeeandroid.util.validateInputs
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModel()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPhoneNumber.filters = arrayOf(PhoneNumberInputFilter())

        addTextChangeListener(
            etUsername = binding.etUsername,
            etEmail = binding.etEmail,
            etPhone = binding.etPhoneNumber,
            etPassword = binding.etPassword,
            etConfirmPassword = binding.etConfirmPassword,
            textFieldUsername = binding.textFieldUsername,
            textFieldEmail = binding.textFieldEmail,
            textFieldPhoneNumber = binding.textFieldPhoneNumber,
            textFieldPassword = binding.textFieldPassword,
            textFieldConfirmPassword = binding.textFieldConfirmPassword
        )
        binding.btnSignup.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val phone = binding.etPhoneNumber.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (validateInputs(
                    username,
                    email,
                    phone,
                    password,
                    confirmPassword,
                    textFieldUserName = binding.textFieldUsername,
                    textFieldEmail = binding.textFieldEmail,
                    textFieldPhoneNumber = binding.textFieldPhoneNumber,
                    textFieldPassword = binding.textFieldPassword,
                    textFieldConfirmPassword = binding.textFieldConfirmPassword
                )
            ) {
                viewModel.register(
                    username,
                    email,
                    phone,
                    password
                )
            }
        }
        viewModel.registerUiState.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterUiState.Loading -> binding.pbRegister.visibility = View.VISIBLE
                RegisterUiState.NewUser -> Toast.makeText(context, "New User", Toast.LENGTH_LONG)
                    .show()

                RegisterUiState.Success -> {
                    binding.pbRegister.visibility = View.GONE
                    showSuccessDialog(
                        context = requireContext(),
                        message = "Successfully Registered!",
                        title = "Success",
                        onClick = {
                            requireActivity()
                                .supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_container, LoginFragment())
                                .commit()
                        }
                    )

                }


                RegisterUiState.UserAlreadyExit -> {
                    binding.pbRegister.visibility = View.GONE
                }
            }
        }
    }
}