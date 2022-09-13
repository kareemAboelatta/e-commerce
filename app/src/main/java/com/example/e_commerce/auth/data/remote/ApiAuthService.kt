package com.example.e_commerce.auth.data.remote

import com.example.e_commerce.auth.data.dto.ForgetPasswordResponse
import com.example.e_commerce.auth.domain.models.LoginBody
import com.example.e_commerce.auth.data.dto.LoginResponse
import com.example.e_commerce.auth.data.dto.RegisterResponse
import com.example.e_commerce.auth.domain.models.UserDataRegister
import com.example.e_commerce.core.utils.Constants
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiAuthService {


    @Headers("Content-Type:${Constants.CONTENT_TYPE}")
    @POST("users/login")
    suspend fun loginUser( @Body userinfo: LoginBody): LoginResponse



    @POST("users/register")
    suspend fun register( @Body userinfo: UserDataRegister): RegisterResponse

    @POST("users/forgetPassword")
    suspend fun sendResetCodeForThisEmail(@Body email: HashMap<String, String>): ForgetPasswordResponse


    @POST("users/restPassword/{code}")
    suspend fun restPassword(
        @Body password: HashMap<String, String>,
        @Path("code") code: String,
    )




}