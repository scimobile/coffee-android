package com.sci.coffeeandroid.feature.auth.ui.screen.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentLoginBinding
import com.sci.coffeeandroid.feature.auth.ui.screen.HomeActivity
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.LoginUiState
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.LoginViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.LoginViewModelEvent
import com.sci.coffeeandroid.util.addTextChangeListener
import com.sci.coffeeandroid.util.validateInputs
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var callbackManager: CallbackManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callbackManager = CallbackManager.Factory.create()

//        binding.btnLoginFacebook.setPermissions("email", "public_profile")
        binding.btnLoginFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                callbackManager!!,
                listOf("email", "public_profile")
            )
        }

        LoginManager.getInstance().registerCallback(callbackManager!!, object :
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
                Log.d("FacebookLoginFragment", "Login canceled")
            }

            override fun onError(error: FacebookException) {
                Log.d("FacebookLoginFragment", "Error: $error")
            }
        })

        addTextChangeListener(
            etEmail = binding.etLoginEmail,
            etPassword = binding.etLoginPassword,
            textFieldEmail = binding.emailTextLayout,
            textFieldPassword = binding.passwordTextLayout,
        )

        binding.btnLoginGoogle.setOnClickListener {
            viewModel.getCredential(requireContext())
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            if (validateInputs(
                    email,
                    password,
                    textFieldEmail = binding.emailTextLayout,
                    textFieldPassword = binding.passwordTextLayout,
                )
            ) {
                viewModel.login(
                    userName = email,
                    password = password
                )
            }
        }

        binding.tvSignup.setOnClickListener {
            replaceFragment(RegisterFragment.newInstance())
        }

        binding.tvForgetPassword.setOnClickListener {
            replaceFragment(ForgotPasswordFragment.newInstance())
        }

        observerUiState()
        observeViewModelEvent()
    }



    override fun onDestroyView() {
        _binding = null
        callbackManager = null
        super.onDestroyView()
    }

    private fun observeViewModelEvent() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {

                LoginUiState.Idle -> {

                }
            }
        }
    }

    private fun observerUiState() {
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                LoginViewModelEvent.Loading -> {}

                is LoginViewModelEvent.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT)
                        .show()
                }

                LoginViewModelEvent.LoginSuccess -> {
                    HomeActivity.newInstance(requireActivity()).also { intent ->
                        startActivity(intent)
                    }
                }

                LoginViewModelEvent.NewUser -> {
                    Toast.makeText(context, "new user", Toast.LENGTH_SHORT)
                        .show()
                }

                LoginViewModelEvent.UserAlreadyLoggedIn -> {
                    HomeActivity.newInstance(requireActivity()).also { intent ->
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.fragment_container, fragment, fragment.javaClass.name).commit()
    }
}