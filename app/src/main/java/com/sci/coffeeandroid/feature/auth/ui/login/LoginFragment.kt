package com.sci.coffeeandroid.feature.auth.ui.login
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
import com.sci.coffeeandroid.databinding.FragmentLoginBinding
import com.sci.coffeeandroid.feature.home.HomeActivity
import com.sci.coffeeandroid.feature.auth.ui.login.viewmodel.LoginViewModel
import com.sci.coffeeandroid.feature.auth.ui.login.viewmodel.LoginViewModelEvent
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.ForgotPasswordFragment
import com.sci.coffeeandroid.feature.auth.ui.login.viewmodel.ViewModelUIState
import com.sci.coffeeandroid.feature.auth.ui.register.RegisterFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var callbackManager: CallbackManager? = null

    private var enableTextColor : Int? = null
    private var disableTextColor : Int? = null

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

        enableTextColor = resources.getColor(R.color.white,null)
        disableTextColor = resources.getColor(R.color.black,null)

        binding.btnLoginFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                callbackManager!!,
                listOf("email", "public_profile")
            )
        }

        viewModel.registerCallback(callbackManager!!)

        setBtnDisable(disableTextColor!!)

        binding.etLoginEmail.doAfterTextChanged {
            val text = it.toString().trim()
            viewModel.onEvent( LoginFormEvent.EmailChangedEvent(text) )
        }
        binding.etLoginPassword.doAfterTextChanged {

            val text = it.toString().trim()
            viewModel.onEvent( LoginFormEvent.PasswordChangedEvent(text) )
        }

        binding.btnLoginGoogle.setOnClickListener {
            viewModel.getCredential(requireContext())
        }

        binding.btnLogin.setOnClickListener {
            if(!binding.btnLogin.isEnabled)return@setOnClickListener
            binding.btnLogin.isEnabled=false
            viewModel.onEvent(LoginFormEvent.Login)
        }

        binding.tvSignup.setOnClickListener {
            replaceFragment(RegisterFragment.newInstance())
        }

        binding.tvForgetPassword.setOnClickListener {
            replaceFragment(ForgotPasswordFragment.newInstance())
        }

        observeViewModelState()
        observeViewModelEvent()
        observeUIState()
    }

    private fun observeUIState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.emailTextLayout.error = it.emailError.orEmpty()
            binding.passwordTextLayout.error = it.passwordError.orEmpty()
            if (it.isButtonEnable){
                setBtnEnable(enableTextColor!!)
            }else{
                setBtnDisable(disableTextColor!!)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        callbackManager = null
        super.onDestroyView()
    }

    private fun observeViewModelEvent() {
        viewModel.viewmodelState.observe(viewLifecycleOwner) {
            when (it) {
                ViewModelUIState.Idle -> {
                    binding.pbLogin.visibility = View.GONE
                }
            }
        }
    }

    private fun observeViewModelState() {
        viewModel.viewmodelEvent.observe(viewLifecycleOwner) {
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

    private fun setBtnDisable(disableTextColor: Int) {
        binding.btnLogin.isEnabled = false
        binding.btnLogin.setBackgroundResource(R.drawable.button_disable_shape)
        binding.btnLogin.setTextColor(disableTextColor)
    }

    private fun setBtnEnable(enableTextColor: Int) {
        binding.btnLogin.isEnabled = true
        binding.btnLogin.setBackgroundResource(R.drawable.button_enable_shape)
        binding.btnLogin.setTextColor(enableTextColor)
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.fragment_container, fragment, fragment.javaClass.name).commit()
    }
}