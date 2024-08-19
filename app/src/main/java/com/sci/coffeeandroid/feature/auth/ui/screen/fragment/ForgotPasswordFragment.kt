package com.sci.coffeeandroid.feature.auth.ui.screen.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentForgotPasswordBinding
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ForgotPasswordUiState
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ForgotPasswordViewModel
import com.sci.coffeeandroid.feature.auth.ui.viewmodel.ForgotPasswordViewModelEvent
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


            if (binding.etForgetEmail.text.toString().isEmpty()) {
                binding.texFieldForgotPasswordEmail.error = "Email is required"
                return@setOnClickListener
            }
            viewModel.getOTP(
                email = binding.etForgetEmail.text.toString()
            )
        }

        binding.etForgetEmail.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.etForgetEmail.clearFocus()
                if (binding.etForgetEmail.text.toString().isEmpty()) {
                    binding.texFieldForgotPasswordEmail.error = "Email is required"

                }else{
                    viewModel.getOTP(
                        email = binding.etForgetEmail.text.toString()
                    )
                }

                true // Return true to indicate that you've handled the action
            } else {
                false
            }
        }

        observerUiState()
        observeViewModelEvent()
        binding.etForgetEmail.doAfterTextChanged {
            binding.texFieldForgotPasswordEmail.error=null
        }
    }

    private fun observeViewModelEvent() {
        viewModel.uiEvent.observe(viewLifecycleOwner) {
            when (it) {
                is ForgotPasswordViewModelEvent.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun observerUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                ForgotPasswordUiState.Loading -> {
                }

                ForgotPasswordUiState.ResetSuccess -> {
                    replaceFragment(ResetPasswordFragment.newInstance(email = binding.etForgetEmail.text.toString()))
                    viewModel.resetForgotPasswordUiState()
                }

                ForgotPasswordUiState.NewUser -> {
                    Toast.makeText(context, "new user", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }



    companion object {
        fun newInstance(): ForgotPasswordFragment = ForgotPasswordFragment()
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.fragment_container, fragment, fragment.javaClass.name).commit()
    }


}