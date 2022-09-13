package com.example.e_commerce.auth.domain.use_cases

import com.example.e_commerce.auth.data.dto.LoginResponse
import com.example.e_commerce.auth.domain.repository.RepositoryAuth
import com.example.e_commerce.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: RepositoryAuth
) {


    operator fun invoke(email: String, password: String):
            Flow<Resource<LoginResponse, String>> = flow {
        emit(Resource.Loading())

        try {
            val result= repository.login(email, password)
            emit(Resource.Success(result))
        }catch (e:Exception){
            var errorMessage = e.localizedMessage?:"UnKnow error occur"
            when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()
                    errorBody?.string()?.let {
                        try {
                            val jObjectError = JSONObject(it)
                            var message:String?=null
                            var status:String?=null
                            try {
                                message=jObjectError.getString("message")
                            }catch (e:Exception){}

                            try {
                                status=jObjectError.getString("status")
                            }catch (e:Exception){}
                            message?.let {
                                errorMessage = it
                                emit(Resource.Error(errorMessage))
                                return@flow
                            }

                        } catch (e: Exception) {
                            errorMessage = e.localizedMessage.toString()
                        }
                    }
                }
            }
            emit(Resource.Error(errorMessage))
        }



    }


//    fun isUserLoggedIn(): Boolean = repository.isUserLoggedIn()


}