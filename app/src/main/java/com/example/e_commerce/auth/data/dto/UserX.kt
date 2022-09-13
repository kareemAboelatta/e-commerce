package com.example.e_commerce.auth.data.dto

data class UserX(
    val _id: String,
    val city: String,
    val companyName: String,
    val country: String,
    val email: String,
    val firstAddress: String,
    val isAdmin: Boolean,
    val name: String,
    val password: String,
    val phone: Int,
    val secondAddress: String,
    val zipCode: Int
)