package com.example.e_commerce.auth.data.dto

data class RegisterResponse(
    val message: String,
    val status: String,
    val token: String,
    val user: UserX
)