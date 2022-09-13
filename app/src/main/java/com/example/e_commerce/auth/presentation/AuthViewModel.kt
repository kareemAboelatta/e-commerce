package com.example.e_commerce.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.auth.data.dto.ForgetPasswordResponse
import com.example.e_commerce.auth.data.dto.LoginResponse
import com.example.e_commerce.auth.data.dto.RegisterResponse
import com.example.e_commerce.auth.domain.models.UserDataRegister
import com.example.e_commerce.auth.domain.models.UserInfoDB
import com.example.e_commerce.auth.domain.use_cases.*
import com.example.e_commerce.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val forgetPasswordUseCase: ForgetPasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val storeLocalUserDataUseCase: StoreLocalUserDataUseCase,
    private val getLocalUserDataUseCase: GetLocalUserDataUseCase

) : ViewModel() {


    private var _loginState= MutableStateFlow<Resource<LoginResponse, String>>(Resource.Empty())
    val loginState: StateFlow<Resource<LoginResponse , String>> = _loginState


    private var _registerState= MutableStateFlow<Resource<RegisterResponse, String>>(Resource.Empty())
    val registerState: StateFlow<Resource<RegisterResponse , String>> = _registerState


    private var _forgetPasswordState= MutableStateFlow<Resource<ForgetPasswordResponse, String>>(Resource.Empty())
    val forgetPasswordState: StateFlow<Resource<ForgetPasswordResponse , String>> = _forgetPasswordState

    private var _resetState= MutableStateFlow<Resource<String, String>>(Resource.Empty())
    val resetState: StateFlow<Resource<String , String>> = _resetState

    private var _storedState= MutableStateFlow<UserInfoDB>(UserInfoDB())
    val storedState: StateFlow<UserInfoDB> = _storedState



    fun login(email: String, password: String) {
        loginUseCase(email, password).onEach {
            when (it) {
                is Resource.Loading -> _loginState.value = Resource.Loading(null)
                is Resource.Success -> _loginState.value = Resource.Success(it.data!!)
                is Resource.Error -> _loginState.value = Resource.Error(it.message)
                else -> { }
            }

        }.launchIn(viewModelScope)


    }

    fun register(user: UserDataRegister) {
        registerUseCase( user).onEach {
            when (it) {
                is Resource.Loading -> _registerState.value = Resource.Loading(null)
                is Resource.Success -> _registerState.value = Resource.Success(it.data!!)
                is Resource.Error -> _registerState.value = Resource.Error(it.message)
                else -> { }
            }

        }.launchIn(viewModelScope)


    }

    fun sentResetCodeToEmail(email: String) {
        forgetPasswordUseCase(email).onEach {
            when (it) {
                is Resource.Loading -> _forgetPasswordState.value = Resource.Loading(null)
                is Resource.Success -> _forgetPasswordState.value = Resource.Success(it.data!!)
                is Resource.Error -> _forgetPasswordState.value = Resource.Error(it.message)
                else -> { }
            }
        }.launchIn(viewModelScope)

    }

    fun resetPassword(password: String,confirmPassword: String,code: String) {
        resetPasswordUseCase(password,confirmPassword,code).onEach {
            when (it) {
                is Resource.Loading -> _resetState.value = Resource.Loading(null)
                is Resource.Success -> _resetState.value = Resource.Success(it.data!!)
                is Resource.Error -> _resetState.value = Resource.Error(it.message)
                else -> { }
            }
        }.launchIn(viewModelScope)

    }

    fun storeUserDataLocally(user: UserInfoDB) {
        storeLocalUserDataUseCase(user).launchIn(viewModelScope)
    }

    fun getUserDataLocally() {
        viewModelScope.launch {
            getLocalUserDataUseCase().collect {
                _storedState.value=it
            }
        }
    }



}