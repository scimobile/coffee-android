package com.sci.coffeeandroid.feature.auth.ui.screen.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sci.coffeeandroid.MainActivity
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentLoginBinding
import com.sci.coffeeandroid.databinding.FragmentResetPasswordBinding
import com.sci.coffeeandroid.feature.auth.ui.screen.HomeActivity
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.LoginUiState
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.LoginViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.LoginViewModelEvent
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ResetPasswordUiState
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ResetPasswordViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ResetPasswordViewModelEvent
import com.sci.coffeeandroid.util.addTextChangeListener
import com.sci.coffeeandroid.util.addTextChangesListener
import com.sci.coffeeandroid.util.validateInputs
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : Fragment() {

    private val viewModel: ResetPasswordViewModel by viewModel()
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private var email:String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            ResetPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString("email", param1)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTextChangesListener(
            etConfirmPassword = binding.edtConfirmPassword,
            etPassword = binding.edtPassword,
            textFieldPassword = binding.textPasswordLayout,
            textFieldConfirmPassword = binding.textConfirmLayout,
        )
        binding.btnResetPassword.setOnClickListener {
            val password = binding.edtPassword.text.toString().trim()
            val newPassword = binding.edtConfirmPassword.text.toString().trim()

            if (validateInputs(
                    email,
                    password,
                    textFieldEmail = binding.textPasswordLayout,
                    textFieldPassword = binding.textConfirmLayout,
                )
            ) {
                viewModel.resetPassword(
                    email = email,
                    newPassword = password
                )
            }
        }

        observerUiState()
        observeViewModelEvent()

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

    private fun observerUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                ResetPasswordUiState.Loading -> {}

                ResetPasswordUiState.ResetSuccess -> {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }

                ResetPasswordUiState.NewUser -> {
                    Toast.makeText(context, "new user", Toast.LENGTH_SHORT)
                        .show()
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