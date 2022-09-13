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
import com.example.e_commerce.auth.domain.models.UserDataRegister
import com.example.e_commerce.auth.presentation.AuthViewModel
import com.example.e_commerce.core.utils.Resource
import com.example.e_commerce.databinding.LoginFragmentBinding
import com.example.e_commerce.databinding.RegisterFragmentBinding
import com.example.e_commerce.main.presentation.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :Fragment(R.layout.register_fragment) {

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.alreadyHaveAccount.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.createAccountBtn.setOnClickListener {
           val user = UserDataRegister(
                name = binding.inputTextUserName.text.toString(),
                email = binding.inputTextEmail.text.toString(),
                password = binding.inputTextPassword.text.toString(),
                confirmPassword = binding.inputTextConfirmPassword.text.toString(),
                phone = binding.inputTextPhone.text.toString(),
                firstAddress = binding.inputTextFirstAddress.text.toString(),
                secondAddress = binding.inputTextSecondAddress.text.toString(),
                companyName = binding.inputTextCompanyName.text.toString(),
                city = binding.inputTextCity.text.toString(),
                country = binding.inputTextCountry.text.toString(),
                zipCode = binding.inputTextZipCode.text.toString()

            )
            viewModel.register(user)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.registerState.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.spinKit.visibility=View.GONE
                        findNavController().popBackStack()
                        Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        binding.spinKit.visibility=View.GONE
                        Log.e("RegisterFragment", "error: " + it.message)
                        Snackbar.make(
                            requireView(), "Error: " + it.message, Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Loading -> {
                        Log.e("RegisterFragment", "loading")
                        binding.spinKit.visibility=View.VISIBLE
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
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}