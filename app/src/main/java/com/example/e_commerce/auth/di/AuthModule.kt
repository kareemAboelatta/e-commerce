package com.example.e_commerce.auth.di


import android.content.Context
import com.example.e_commerce.auth.data.local.DataStoreManager
import com.example.e_commerce.auth.data.remote.ApiAuthService
import com.example.e_commerce.auth.data.repository.RepositoryAuthImp
import com.example.e_commerce.auth.domain.repository.RepositoryAuth
import com.example.e_commerce.core.utils.Constants

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthService(): ApiAuthService{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ApiAuthService::class.java)
    }

    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

    @Singleton
    @Provides
    fun provideUserRepository(
        apiAuthService: ApiAuthService,
        dataStoreManager: DataStoreManager
    ): RepositoryAuth =
        RepositoryAuthImp(apiAuthService,dataStoreManager)


}