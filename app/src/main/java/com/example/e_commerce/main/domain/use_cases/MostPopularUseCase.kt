package com.example.e_commerce.main.domain.use_cases


import com.example.e_commerce.core.utils.Resource
import com.example.e_commerce.main.data.dto.ProductResponse
import com.example.e_commerce.main.data.dto.toSummaryProduct
import com.example.e_commerce.main.domain.models.SummaryProduct
import com.example.e_commerce.main.domain.repository.RepositoryMain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MostPopularUseCase @Inject constructor(
    private val repository: RepositoryMain
) {

    operator fun invoke():
            Flow<Resource<ProductResponse, String>> = flow {
        emit(Resource.Loading())

        try {
            val result= repository.getMostPopular()
            emit(Resource.Success(result))
        }catch (e:Exception){
            var errorMessage = e.localizedMessage  ?:" UnKnow error occur"
            when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()
                    errorBody?.string()?.let {
                        try {
                            val jObjectError = JSONObject(it)
                            var message:String?=null
                            try {
                                message=jObjectError.getString("message")+55
                            }catch (e:Exception){

                            }

                            message?.let {
                                errorMessage = it+555
                                emit(Resource.Error(errorMessage))
                                return@flow
                            }

                        } catch (e: Exception) {
                            errorMessage = e.localizedMessage.toString()+ 66
                        }
                    }
                }
                is IOException->{
                    errorMessage="Couldn't reach server. Check your internet connection."
                }
            }
            emit(Resource.Error(errorMessage))
        }

    }

}