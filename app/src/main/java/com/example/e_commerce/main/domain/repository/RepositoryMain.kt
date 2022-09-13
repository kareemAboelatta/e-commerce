package com.example.e_commerce.main.domain.repository

import com.example.e_commerce.main.data.dto.ProductResponse

interface RepositoryMain {


    suspend fun getMostPopular(): ProductResponse
    suspend fun getRecommended(): ProductResponse


}