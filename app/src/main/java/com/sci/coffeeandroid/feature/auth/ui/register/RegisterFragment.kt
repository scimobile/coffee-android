package com.sci.coffeeandroid.feature.auth.ui.register

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
import com.sci.coffeeandroid.feature.auth.ui.login.LoginFragment
import com.sci.coffeeandroid.feature.auth.ui.register.viewmodel.RegisterViewModel
import com.sci.coffeeandroid.feature.auth.ui.register.viewmodel.RegisterViewModelEvent
import com.sci.coffeeandroid.feature.auth.ui.register.viewmodel.RegisterViewModelState
import com.sci.coffeeandroid.feature.home.HomeActivity
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

        binding.btnSignup.setOnClickListener {

            viewModel.onEvent(RegistrationFormEvent.Submit)

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
            binding.textFieldUsername.error = it.usernameError.orEmpty()
            binding.textFieldEmail.error = it.emailError.orEmpty()
            binding.textFieldPassword.error = it.passwordError.orEmpty()
            binding.textFieldConfirmPassword.error = it.repeatedPasswordError.orEmpty()
            binding.textFieldPhoneNumber.error = it.phoneError.orEmpty()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.javaClass.name).commit()
    }
}