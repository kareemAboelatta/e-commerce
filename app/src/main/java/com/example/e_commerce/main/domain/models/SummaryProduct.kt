package com.example.e_commerce.main.domain.models

import java.io.Serializable


data class SummaryProduct(
    val _id: String,
    val category: String,
    val colors: List<Any>,
    val description: String,
    val imageCover: String,
    val images: List<Any>,
    val price: Double,
    val priceAfterDiscount: Int,
    val quantity: String,
    val sold: Int,
    val subCategory: List<Any>,
    val title: String,
): Serializable