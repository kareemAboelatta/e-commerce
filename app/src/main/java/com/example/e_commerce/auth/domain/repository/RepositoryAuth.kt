package com.example.e_commerce.auth.domain.repository

import com.example.e_commerce.auth.data.dto.ForgetPasswordResponse
import com.example.e_commerce.auth.data.dto.LoginResponse
import com.example.e_commerce.auth.data.dto.RegisterResponse
import com.example.e_commerce.auth.domain.models.UserDataRegister
import com.example.e_commerce.auth.domain.models.UserInfoDB
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryAuth {

    suspend fun login(email: String, password: String) : LoginResponse

    suspend fun register(user : UserDataRegister) : RegisterResponse

    suspend fun forgetPassword(email: String) : ForgetPasswordResponse

    suspend fun restPassword(password: String,confirmPassword: String,code: String)

    suspend fun storeUserData(user: UserInfoDB)

    suspend fun getDataStore(): Flow<UserInfoDB>


}