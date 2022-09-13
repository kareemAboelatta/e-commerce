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
import com.example.e_commerce.auth.data.local.DataStoreManager
import com.example.e_commerce.auth.domain.models.UserInfoDB
import com.example.e_commerce.auth.domain.use_cases.GetLocalUserDataUseCase
import com.example.e_commerce.auth.presentation.AuthViewModel
import com.example.e_commerce.core.utils.Resource
import com.example.e_commerce.databinding.LoginFragmentBinding
import com.example.e_commerce.main.presentation.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @Inject
    lateinit var getLocalUserDataUseCase: GetLocalUserDataUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //get stored data from dataSource
        viewModel.getUserDataLocally()
        lifecycleScope.launch {
            viewModel.storedState.collect{
                binding.inputTextEmail.setText(it.email)
                binding.inputTextPassword.setText(it.password)
            }
        }


        binding.textSignUp.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

        binding.singinBtn.setOnClickListener {

            if (binding.switchRememberMe.isChecked) {
                val email =binding.inputTextEmail.text.toString()
                val password = binding.inputTextPassword.text.toString()
                val id ="it.data?.user?._id"
                val token ="it.data?.token"
                viewModel.storeUserDataLocally(
                    UserInfoDB(
                        email = email!!,
                        password=password,
                        id=id!!,
                        token = token!!
                    )
                )
            }

            val email = binding.inputTextEmail.text.toString()
            val password = binding.inputTextPassword.text.toString()
            if (email.isNotBlank() && password.isNotBlank()){
                viewModel.login(email, password)
            }else
                Toast.makeText(requireActivity(), "Fill all requirements", Toast.LENGTH_SHORT).show()


        }
        binding.forgetPassword.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordFragment)
        }


        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect {
                when (it) {
                    is Resource.Success -> {
                        if (binding.switchRememberMe.isChecked) {
                            val email =it.data?.user?.email
                            val password = binding.inputTextPassword.text.toString()
                            val id =it.data?.user?._id
                            val token =it.data?.token
                            viewModel.storeUserDataLocally(
                                UserInfoDB(
                                    email = email!!,
                                    password=password,
                                    id=id!!,
                                    token = token!!
                                )
                            )
                        }
                        binding.spinKit.visibility=View.GONE
                        requireActivity().startActivity(Intent(requireActivity(),MainActivity::class.java))
                        requireActivity().finish()
                        Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        binding.spinKit.visibility=View.GONE
                        Log.e("LoginFragment", "error: " + it.message)
                        Snackbar.make(
                            requireView(), "Error: " + it.message, Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Loading -> {
                        Log.e("LoginFragment", "loading")
                        binding.spinKit.visibility=View.VISIBLE
                    }
                    else -> {
                        Log.e("LoginFragment", "Empty: " )
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
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}