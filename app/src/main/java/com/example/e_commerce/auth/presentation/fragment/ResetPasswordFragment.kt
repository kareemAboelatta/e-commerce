package com.example.e_commerce.auth.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.auth.presentation.AuthViewModel
import com.example.e_commerce.core.utils.Resource
import com.example.e_commerce.databinding.ResetPasswordFragmentBinding
import com.example.e_commerce.main.presentation.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment(R.layout.reset_password_fragment) {
    private var _binding: ResetPasswordFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.restPasswordBtn.setOnClickListener {
            val code = binding.inputTextCode.text.toString()
            val password = binding.inputTextPassword.text.toString()
            val confirmPassword = binding.inputTextConfirmPassword.text.toString()
            if (code.isNotBlank()&&password.isNotBlank()&&confirmPassword.isNotBlank()){
                viewModel.resetPassword(
                    password = password,
                    confirmPassword=confirmPassword,
                    code = code
                )
            }else
                Toast.makeText(requireActivity(), "Fill all requirements", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetState.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.spinKit.visibility= View.GONE
                        Toast.makeText(requireActivity(), it.data.toString(), Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        binding.spinKit.visibility= View.GONE
                        Log.e("ResetPasswordFragment", "error: " + it.message)
                        Snackbar.make(
                            requireView(), "Error: " + it.message, Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Loading -> {
                        Log.e("ResetPasswordFragment", "loading")
                        binding.spinKit.visibility= View.VISIBLE
                    }
                    else -> {
                        Log.e("ResetPasswordFragment", "Empty: " )
                    }
                }
            }
        }

    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResetPasswordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}