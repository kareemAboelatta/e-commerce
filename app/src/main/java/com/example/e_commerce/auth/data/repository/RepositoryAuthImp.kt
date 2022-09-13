package com.example.e_commerce.auth.data.repository

import com.example.e_commerce.auth.data.dto.ForgetPasswordResponse
import com.example.e_commerce.auth.domain.models.LoginBody
import com.example.e_commerce.auth.data.dto.LoginResponse
import com.example.e_commerce.auth.data.dto.RegisterResponse
import com.example.e_commerce.auth.data.local.DataStoreManager
import com.example.e_commerce.auth.data.remote.ApiAuthService
import com.example.e_commerce.auth.domain.models.UserDataRegister
import com.example.e_commerce.auth.domain.models.UserInfoDB
import com.example.e_commerce.auth.domain.repository.RepositoryAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryAuthImp @Inject constructor(

    private var apiAuthService: ApiAuthService,
    private var dataStoreManager: DataStoreManager

) : RepositoryAuth {

    override suspend fun login(email: String, password: String): LoginResponse {
        val loginBody= LoginBody(
            email=email,
            password = password
        )
        return apiAuthService.loginUser(loginBody)
    }

    override suspend fun register(user : UserDataRegister): RegisterResponse {
        return  apiAuthService.register(user)
    }

    override suspend fun forgetPassword(email: String): ForgetPasswordResponse {
        val hashmap = HashMap<String, String>()
        hashmap["email"] = email
        return  apiAuthService.sendResetCodeForThisEmail(hashmap)
    }


    override suspend fun restPassword(password: String,confirmPassword: String,code: String) {
        val hashmap = HashMap<String, String>()
        hashmap["password"] = password
        hashmap["confirmPassword"] = confirmPassword
        return apiAuthService.restPassword(password = hashmap, code = code)

    }

    override suspend fun storeUserData(user: UserInfoDB) {
        dataStoreManager.setUserInfo(user)
    }

    override suspend fun getDataStore(): Flow<UserInfoDB> = dataStoreManager.infoUser


}