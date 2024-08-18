package com.sci.coffeeandroid.feature.auth.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentRegisterBinding
import com.sci.coffeeandroid.feature.auth.ui.RegistrationFormEvent
import com.sci.coffeeandroid.feature.home.HomeActivity
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModelState
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModelEvent
import com.sci.coffeeandroid.util.PhoneNumberInputFilter
import com.sci.coffeeandroid.util.showSuccessDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModel()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var callbackManager: CallbackManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root


    }

    companion object {
        fun newInstance(): RegisterFragment = RegisterFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPhoneNumber.filters = arrayOf(PhoneNumberInputFilter())
        callbackManager = CallbackManager.Factory.create()


        binding.btnFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                callbackManager!!,
                listOf("email", "public_profile")
            )
        }
        viewModel.registerCallback(callbackManager!!)

        setupTextWatchers()

//        binding.etUsername.doAfterTextChanged {
//            val username = binding.etUsername.text.toString().trim()
//            viewModel.onEvent(RegistrationFormEvent.UsernameChangedEvent(username))
//        }
//        binding.etEmail.doAfterTextChanged {
//            val email = binding.etEmail.text.toString().trim()
//            viewModel.onEvent(RegistrationFormEvent.EmailChangedEvent(email))
//        }
//        binding.etPassword.doAfterTextChanged {
//            val password = binding.etPassword.text.toString().trim()
//            viewModel.onEvent(RegistrationFormEvent.PasswordChangedEvent(password))
//        }
//        binding.etConfirmPassword.doAfterTextChanged {
//            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
//            viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChangedEvent(confirmPassword))
//        }
//        binding.etPhoneNumber.doAfterTextChanged {
//            val phone = binding.etPhoneNumber.text.toString().trim()
//            viewModel.onEvent(RegistrationFormEvent.PhoneChangedEvent(phone))
//        }

//        addTextChangeListener(
//            etUsername = binding.etUsername,
//            etEmail = binding.etEmail,
//            etPhone = binding.etPhoneNumber,
//            etPassword = binding.etPassword,
//            etConfirmPassword = binding.etConfirmPassword,
//            textFieldUsername = binding.textFieldUsername,
//            textFieldEmail = binding.textFieldEmail,
//            textFieldPhoneNumber = binding.textFieldPhoneNumber,
//            textFieldPassword = binding.textFieldPassword,
//            textFieldConfirmPassword = binding.textFieldConfirmPassword
//        )
        binding.btnSignup.setOnClickListener {

//            val username = binding.etUsername.text.toString().trim()
//            val email = binding.etEmail.text.toString().trim()
//            val phone = binding.etPhoneNumber.text.toString().trim()
//            val password = binding.etPassword.text.toString().trim()
//            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
//
//            if (validateInputs(
//                    username,
//                    email,
//                    phone,
//                    password,
//                    confirmPassword,
//                    textFieldUserName = binding.textFieldUsername,
//                    textFieldEmail = binding.textFieldEmail,
//                    textFieldPhoneNumber = binding.textFieldPhoneNumber,
//                    textFieldPassword = binding.textFieldPassword,
//                    textFieldConfirmPassword = binding.textFieldConfirmPassword
//                )
//            ) {
            viewModel.onEvent(RegistrationFormEvent.Submit)
//            }
        }
        binding.tvLogin.setOnClickListener {
            replaceFragment(LoginFragment.newInstance())
        }
        binding.btnGoogle.setOnClickListener {
            viewModel.getCredential(requireContext())
        }

        observeViewModelState()
        observeViewModelEvent()
        observeUIState()
    }

    private fun setupTextWatchers() {
        val fields = listOf(
            binding.etUsername to RegistrationFormEvent::UsernameChangedEvent,
            binding.etEmail to RegistrationFormEvent::EmailChangedEvent,
            binding.etPassword to RegistrationFormEvent::PasswordChangedEvent,
            binding.etConfirmPassword to RegistrationFormEvent::RepeatedPasswordChangedEvent,
            binding.etPhoneNumber to RegistrationFormEvent::PhoneChangedEvent
        )

        fields.forEach { (editText, event) ->
            editText.doAfterTextChanged {
                val text = it.toString().trim()
                viewModel.onEvent(event(text))
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        callbackManager = null
        super.onDestroyView()
    }

    private fun observeViewModelEvent() {
        viewModel.registerUiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterViewModelEvent.Loading -> binding.pbRegister.visibility = View.VISIBLE
                RegisterViewModelEvent.NewUser -> Toast.makeText(
                    context,
                    "New User",
                    Toast.LENGTH_LONG
                )
                    .show()

                RegisterViewModelEvent.Success -> {
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


                RegisterViewModelEvent.UserAlreadyExit -> {
                    binding.pbRegister.visibility = View.GONE
                }

                RegisterViewModelEvent.SocialSuccess -> {
                    binding.pbRegister.visibility = View.GONE
                    Toast.makeText(context, "Successfully Login", Toast.LENGTH_SHORT).show()
                    HomeActivity.newInstance(requireActivity()).also { intent ->
                        startActivity(intent)
                    }
                }

                is RegisterViewModelEvent.Error -> {
                    binding.pbRegister.visibility = View.GONE
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    private fun observeViewModelState() {
        viewModel.registerViewModelState.observe(viewLifecycleOwner) {
            when (it) {
                RegisterViewModelState.Idel -> binding.pbRegister.visibility = View.GONE
            }
        }
    }

    private fun observeUIState() {
        viewModel.registerUIState.observe(viewLifecycleOwner) {
            binding.textFieldUsername.error = it.usernameError
            binding.textFieldEmail.error = it.emailError
            binding.textFieldPassword.error = it.passwordError
            binding.textFieldConfirmPassword.error = it.repeatedPasswordError
            binding.textFieldPhoneNumber.error = it.phoneError
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.javaClass.name).commit()
    }
}