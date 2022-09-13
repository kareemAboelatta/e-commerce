package com.example.e_commerce.main.data.dto

import com.example.e_commerce.main.domain.models.SummaryProduct

data class Product(
    val _id: String,
    val category: String,
    val colors: List<Any>,
    val createdAt: String,
    val description: String,
    val imageCover: String,
    val images: List<Any>,
    val price: Double,
    val priceAfterDiscount: Int,
    val quantity: String,
    val sold: Int,
    val subCategory: List<Any>,
    val title: String,
    val updatedAt: String
)

fun Product.toSummaryProduct(): SummaryProduct {
    return SummaryProduct(
        _id = _id,
        category = category,
        colors = colors,
        description = description,
        imageCover = imageCover,
        images = images,
        price = price,
        quantity = quantity,
        sold = sold,
        subCategory = subCategory,
        title = title,
        priceAfterDiscount=priceAfterDiscount
    )

}