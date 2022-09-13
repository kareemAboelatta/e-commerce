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
import com.example.e_commerce.databinding.ForgetPasswordBinding
import com.example.e_commerce.databinding.RegisterFragmentBinding
import com.example.e_commerce.main.presentation.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {
    private var _binding: ForgetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ForgetPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.restPasswordBtn.setOnClickListener {
            val email = binding.inputTextEmail.text.toString()
            if (email.isNotBlank()){
                viewModel.sentResetCodeToEmail(email)
            }else
                Toast.makeText(requireActivity(), "Fill all requirements", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.forgetPasswordState.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.spinKit.visibility=View.GONE
                        Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.resetPasswordFragment)
                    }
                    is Resource.Error -> {
                        binding.spinKit.visibility=View.GONE
                        Log.e("ForgetPasswordFragment", "error: " + it.message)
                        Snackbar.make(
                            requireView(), "Error: " + it.message, Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Loading -> {
                        Log.e("ForgetPasswordFragment", "loading")
                        binding.spinKit.visibility=View.VISIBLE
                    }
                }
            }
        }



    }
}