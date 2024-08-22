package com.sci.coffeeandroid.feature.auth.ui.forgetpassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentForgotPasswordBinding
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ViewModelUiState
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ForgotPasswordViewModel
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ResetPasswordUiState
import com.sci.coffeeandroid.feature.auth.ui.forgetpassword.viewmodel.ViewModelEvent
import com.sci.coffeeandroid.feature.auth.ui.login.LoginFormEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : Fragment() {

    private val viewModel : ForgotPasswordViewModel by viewModel()
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSubmit.setOnClickListener {
            viewModel.onEvent(ForgetPasswordFormEvent.Next)
        }

        binding.etForgetEmail.doAfterTextChanged {
            val text = it.toString().trim()
            viewModel.onEvent( ForgetPasswordFormEvent.EmailChangedEvent(text) )
        }

        binding.etForgetEmail.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.etForgetEmail.clearFocus()
                if (binding.etForgetEmail.text.toString().isEmpty()) {
                    binding.texFieldForgotPasswordEmail.error = "Email is required"

                }else{
                    viewModel.onEvent(ForgetPasswordFormEvent.Next)
                }

                true
            } else {
                false
            }
        }

        observeViewModelUiState()
        observeViewModelEvent()
        observeUIState()
        binding.etForgetEmail.doAfterTextChanged {
            binding.texFieldForgotPasswordEmail.error=null
        }
    }

    private fun observeUIState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.texFieldForgotPasswordEmail.error = it.emailError.orEmpty()
        }
    }

    private fun observeViewModelEvent() {
        viewModel.viewmodelUIEvent.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelEvent.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT)
                        .show()
                }

                ViewModelEvent.Loading -> {
                }

                ViewModelEvent.ResetSuccess -> {
                    replaceFragment(ResetPasswordFragment.newInstance(email = binding.etForgetEmail.text.toString()))
                    viewModel.resetForgotPasswordUiState()
                }

                ViewModelEvent.NewUser -> {
                    Toast.makeText(context, "new user", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun observeViewModelUiState() {
        viewModel.viewmodelUIState.observe(viewLifecycleOwner) {
            when (it) {
                ViewModelUiState.Idle -> TODO()
            }
        }
    }



    companion object {
        fun newInstance(): ForgotPasswordFragment = ForgotPasswordFragment()
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment).commit()
    }


}