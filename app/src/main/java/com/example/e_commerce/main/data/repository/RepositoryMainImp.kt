package com.example.e_commerce.main.data.repository

import com.example.e_commerce.main.data.dto.ProductResponse
import com.example.e_commerce.main.data.remote.ApiProductService
import com.example.e_commerce.main.domain.repository.RepositoryMain
import javax.inject.Inject

class RepositoryMainImp @Inject constructor(

    private var apiProductService: ApiProductService

) : RepositoryMain {


    override suspend fun getMostPopular(): ProductResponse {
        return apiProductService.getPopularProducts()
    }

    override suspend fun getRecommended(): ProductResponse {
        return apiProductService.getRecommendedProducts(limit = 20)
    }


}