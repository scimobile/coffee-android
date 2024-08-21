package com.sci.coffeeandroid.feature.auth.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentResetPasswordBinding
import com.sci.coffeeandroid.feature.auth.ui.RegistrationFormEvent
import com.sci.coffeeandroid.feature.auth.ui.ResetPasswordFormState
import com.sci.coffeeandroid.feature.auth.ui.RestartPasswordFormEvent
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ResetPasswordUiState
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ResetPasswordViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ResetPasswordViewModelEvent
import com.sci.coffeeandroid.util.addTextChangesListener
import com.sci.coffeeandroid.util.isMatchPassword
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
            // Use the value as needed
        }

        setupTextWatchers()

        addTextChangesListener(
            etConfirmPassword = binding.edtConfirmPassword,
            etPassword = binding.edtPassword,
            textFieldPassword = binding.textPasswordLayout,
            textFieldConfirmPassword = binding.textConfirmLayout,
        )
        binding.btnResetPassword.setOnClickListener {
            viewModel.onEvent(RestartPasswordFormEvent.Submit)

        }


        observeUIState()
        observerViewModelState()
        observeViewModelEvent()

    }

    private fun setupTextWatchers() {
        val fields = listOf(
            binding.tvPassword to RestartPasswordFormEvent::PasswordChangedEvent,
            binding.tvConfirmPassword to RestartPasswordFormEvent::RepeatedPasswordChangedEvent
        )

        fields.forEach { (editText, event) ->
            editText.doAfterTextChanged {
                val text = it.toString().trim()
                viewModel.onEvent(event(text))
            }
        }
    }

    private fun observeViewModelEvent() {
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is ResetPasswordViewModelEvent.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun observerViewModelState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                ResetPasswordUiState.Loading -> {}

                ResetPasswordUiState.ResetSuccess -> {
                    Toast.makeText(context, "Password reset success", Toast.LENGTH_SHORT)
                        .show()
                    replaceFragment(LoginFragment.newInstance())
                }

                ResetPasswordUiState.NewUser -> {
                    Toast.makeText(context, "new user", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }



    private fun observeUIState() {
        viewModel.resetPasswordUiState.observe(viewLifecycleOwner) {
            binding.textPasswordLayout.error = it.passwordError
            binding.textConfirmLayout.error = it.repeatedPasswordError
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