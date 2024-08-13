package com.sci.coffeeandroid.feature.auth.ui.screen.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentRegisterBinding
import com.sci.coffeeandroid.feature.CoffeeApplication
import com.sci.coffeeandroid.feature.auth.ui.screen.HomeActivity
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterUiState
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.RegisterViewModelEvent
import com.sci.coffeeandroid.util.PhoneNumberInputFilter
import com.sci.coffeeandroid.util.addTextChangeListener
import com.sci.coffeeandroid.util.showSuccessDialog
import com.sci.coffeeandroid.util.validateInputs
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

        FacebookSdk.sdkInitialize(applicationContext = requireActivity())
        AppEventsLogger.activateApp(requireActivity().application)

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


        binding.btnFacebook.setPermissions("email", "public_profile")
        binding.btnFacebook.registerCallback(callbackManager!!, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                // Handle successful login
                val accessToken = result.accessToken
                Toast.makeText(context, "Successfully Login", Toast.LENGTH_SHORT).show()
                HomeActivity.newInstance(requireActivity()).also { intent ->
                    startActivity(intent)
                }

                // Use accessToken to access user data or make API calls
                // For example, to get user profile:
                val request = GraphRequest.newMeRequest(
                    result.accessToken
                )


                { _, graphResponse ->
                    if (graphResponse != null) {
                        val user = graphResponse.jsonObject
                        // Access user data here
                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "id, name, email, picture.type(large)")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                // Handle cancel
            }

            override fun onError(error: FacebookException) {
                // Handle error
            }
        })

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
        binding.tvLogin.setOnClickListener {
            replaceFragment(LoginFragment.newInstance())
        }
        binding.btnGoogle.setOnClickListener {
            viewModel.getCredential(requireContext())
        }

//        binding.btnFacebook.setOnClickListener {
//            viewModel.loginWithFacebook(callbackManager,requireActivity())
//        }



        observerUiState()
        observeViewModelEvent()

    }

    private fun observerUiState() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(
            requestCode,
            resultCode, data
        )
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun observeViewModelEvent() {
        viewModel.registerUiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterViewModelEvent.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.
        beginTransaction().
        replace(R.id.fragment_container,fragment, fragment.javaClass.name).
        commit()
    }
}