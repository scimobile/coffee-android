package com.sci.coffeeandroid.feature.auth.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentLoginBinding
import com.sci.coffeeandroid.feature.home.HomeActivity
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
    private var isButtonEnabled = true

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
            if (isButtonEnabled) {
                isButtonEnabled = false
                LoginManager.getInstance().logInWithReadPermissions(
                    this,
                    callbackManager!!,
                    listOf("email", "public_profile")
                )
            }
        }
        viewModel.registerCallback(callbackManager!!)



        addTextChangeListener(
            etEmail = binding.etLoginEmail,
            etPassword = binding.etLoginPassword,
            textFieldEmail = binding.emailTextLayout,
            textFieldPassword = binding.passwordTextLayout,
        )

        binding.btnLoginGoogle.setOnClickListener {
            if (isButtonEnabled) {
                isButtonEnabled = false
                viewModel.getCredential(requireContext())
            }
        }

        binding.btnLogin.setOnClickListener {
            if (isButtonEnabled) {
                isButtonEnabled = false
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
            isButtonEnabled = true
            when (it) {
                LoginUiState.Idle -> {
                    binding.pbLogin.visibility = View.GONE
                }
            }
        }
    }

    private fun observerUiState() {
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            isButtonEnabled = true
            when (it) {
                LoginViewModelEvent.Loading -> {
                    binding.pbLogin.visibility = View.VISIBLE
                }

                is LoginViewModelEvent.Error -> {
                    binding.pbLogin.visibility = View.GONE
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT)
                        .show()
                }

                LoginViewModelEvent.LoginSuccess -> {
                    binding.pbLogin.visibility = View.GONE

                    HomeActivity.newInstance(requireActivity()).also { intent ->
                        startActivity(intent)
                    }
                    Toast.makeText(context, "login success", Toast.LENGTH_SHORT).show()
                }

                LoginViewModelEvent.NewUser -> {
                    binding.pbLogin.visibility = View.GONE
                    Toast.makeText(context, "new user", Toast.LENGTH_SHORT)
                        .show()
                }

                LoginViewModelEvent.UserAlreadyLoggedIn -> {
                    binding.pbLogin.visibility = View.GONE
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