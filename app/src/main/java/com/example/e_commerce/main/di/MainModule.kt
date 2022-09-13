package com.example.e_commerce.main.di

import com.example.e_commerce.auth.data.remote.ApiAuthService
import com.example.e_commerce.auth.data.repository.RepositoryAuthImp
import com.example.e_commerce.auth.domain.repository.RepositoryAuth
import com.example.e_commerce.core.utils.Constants
import com.example.e_commerce.main.data.remote.ApiProductService
import com.example.e_commerce.main.data.repository.RepositoryMainImp
import com.example.e_commerce.main.domain.repository.RepositoryMain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideMovieService(): ApiProductService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ApiProductService::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(
        apiProductService: ApiProductService
    ): RepositoryMain =
        RepositoryMainImp(apiProductService)

}