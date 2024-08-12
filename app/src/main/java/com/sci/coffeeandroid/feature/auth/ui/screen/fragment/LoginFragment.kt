package com.sci.coffeeandroid.feature.auth.ui.screen.fragment

import android.content.Intent
import android.credentials.CredentialManager
import android.credentials.GetCredentialRequest
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
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.sci.coffeeandroid.MainActivity
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

    private lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FacebookSdk.sdkInitialize(applicationContext = requireActivity())

        AppEventsLogger.activateApp(requireActivity().application)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callbackManager = CallbackManager.Factory.create()

        binding.btnLoginFacebook.setPermissions("email", "public_profile")
        binding.btnLoginFacebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult)
            {
                // Handle successful login
                val accessToken = result.accessToken
                // Use accessToken to access user data or make API calls
                // For example, to get user profile:
                val request = GraphRequest.newMeRequest(
                    result.accessToken
                ) { _, graphResponse ->
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

            override fun onCancel()
            {
                // Handle cancel
            }

            override fun onError(error: FacebookException) {
                // Handle error
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
//            HomeActivity.newInstance(requireActivity()).also { intent ->
//                startActivity(intent)
//            }
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode,
        resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

    }
    private fun observeViewModelEvent() {
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is LoginViewModelEvent.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun observerUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                LoginUiState.Loading -> {}

                LoginUiState.Idle -> {

                }
                LoginUiState.LoginSuccess -> {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }

                LoginUiState.NewUser -> {
                    Toast.makeText(context, "new user", Toast.LENGTH_SHORT)
                        .show()
                }

                LoginUiState.UserAlreadyLoggedIn -> {
                    HomeActivity.newInstance(requireActivity()).also { intent ->
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.
        beginTransaction().
        addToBackStack(null).
        replace(R.id.fragment_container,fragment, fragment.javaClass.name).
        commit()
    }
}