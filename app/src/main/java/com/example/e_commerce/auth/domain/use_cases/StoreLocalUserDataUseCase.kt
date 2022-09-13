package com.example.e_commerce.auth.domain.use_cases

import com.example.e_commerce.auth.data.dto.LoginResponse
import com.example.e_commerce.auth.domain.models.UserInfoDB
import com.example.e_commerce.auth.domain.repository.RepositoryAuth
import com.example.e_commerce.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class StoreLocalUserDataUseCase  @Inject constructor(
    private val repository: RepositoryAuth
) {

    operator fun invoke(
        user: UserInfoDB
    ):Flow<UserInfoDB> = flow {
        try {
            repository.storeUserData(user)
        }catch (e: Exception){

        }
    }


}