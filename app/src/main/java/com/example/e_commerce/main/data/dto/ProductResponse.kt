package com.example.e_commerce.main.data.dto

data class ProductResponse(
    val paginationResult: PaginationResult,
    val documents: List<Product>,
    val result: Int
)