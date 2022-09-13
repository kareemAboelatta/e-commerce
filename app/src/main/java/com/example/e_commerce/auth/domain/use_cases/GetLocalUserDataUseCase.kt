package com.example.e_commerce.auth.domain.use_cases

import com.example.e_commerce.auth.domain.models.UserInfoDB
import com.example.e_commerce.auth.domain.repository.RepositoryAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocalUserDataUseCase @Inject constructor(
    private val repository: RepositoryAuth
) {
    suspend operator fun invoke(): Flow<UserInfoDB> = repository.getDataStore()
}