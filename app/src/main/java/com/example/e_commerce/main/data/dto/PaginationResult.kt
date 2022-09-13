package com.example.e_commerce.main.data.dto

data class PaginationResult(
    val currentPage: Int,
    val limit: Int,
    val nextPage: Int,
    val numberOfPage: Int
)