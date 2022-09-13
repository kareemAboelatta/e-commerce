package com.example.e_commerce.auth.data.dto

data class LoginResponse(
    val message: String,
    val status: String,
    val token: String?="",
    val user: User?= null
)