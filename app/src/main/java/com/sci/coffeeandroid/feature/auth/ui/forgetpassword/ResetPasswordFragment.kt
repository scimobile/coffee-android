package com.sci.coffeeandroid.feature.auth.ui.forgetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentResetPasswordBinding
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ResetPasswordUiState
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ResetPasswordViewModel
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ViewModelEvent
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ViewModelUIEvent
import com.sci.coffeeandroid.feature.auth.ui.login.LoginFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : Fragment() {

    private val KEY = "email"
    private val viewModel: ResetPasswordViewModel by viewModel()
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private var email:String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        fun newInstance(email: String) =
            ResetPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, email)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
             email = requireArguments().getString(KEY)
        }

        binding.btnResetPassword.setOnClickListener {
            viewModel.onEvent(ResetPasswordFormEvent.Reset(email!!))
        }
        binding.edtPassword.doAfterTextChanged {
            val text = it.toString().trim()
            viewModel.onEvent( ResetPasswordFormEvent.PasswordChangedEvent(text) )
        }

        binding.edtConfirmPassword.doAfterTextChanged {
            val text = it.toString().trim()
            viewModel.onEvent( ResetPasswordFormEvent.RepeatedChangedEvent(text) )
        }

        observeViewModelUiState()
        observeViewModelEvent()
        observeUIState()

    }
    private fun observeUIState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.textPasswordLayout.error = it.passwordError.orEmpty()
            binding.textConfirmLayout.error = it.repeatedPasswordError.orEmpty()
        }
    }
    private fun observeViewModelEvent() {
        viewModel.viewmodelUIEvent.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelUIEvent.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT)
                        .show()
                }
                ViewModelUIEvent.Loading -> {}
                ViewModelUIEvent.ResetSuccess -> {
                    Toast.makeText(context, "Password reset success", Toast.LENGTH_SHORT)
                        .show()
                    replaceFragment(LoginFragment.newInstance())
                }

                ViewModelUIEvent.NewUser -> {
                    Toast.makeText(context, "new user", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

    private fun observeViewModelUiState() {
        viewModel.viewmodelUIState.observe(viewLifecycleOwner) {
            when (it) {
                ResetPasswordUiState.Idle -> TODO()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentManager. popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragmentTransaction.commit()
    }
}