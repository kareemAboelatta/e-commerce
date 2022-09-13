package com.example.e_commerce.auth.domain.models

public data class UserDataRegister(
    val city: String,
    val companyName: String,
    val confirmPassword: String,
    val country: String,
    val email: String,
    val firstAddress: String,
    val name: String,
    val password: String,
    val phone: String,
    val secondAddress: String,
    val zipCode: String
)