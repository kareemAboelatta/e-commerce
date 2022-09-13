package com.example.e_commerce.main.data.remote

import com.example.e_commerce.main.data.dto.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiProductService {

    @GET("products")
    suspend fun getPopularProducts(
        @Query("limit") limit: Int=10,
        @Query("sort") sort: String ="-sold", //default is the popular
    ): ProductResponse



    @GET("products")
    suspend fun getRecommendedProducts(
        @Query("limit") limit: Int=10,
    ): ProductResponse




}