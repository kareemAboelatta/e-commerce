package com.example.e_commerce.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.core.utils.Resource
import com.example.e_commerce.main.data.dto.ProductResponse
import com.example.e_commerce.main.domain.models.SummaryProduct
import com.example.e_commerce.main.domain.use_cases.MostPopularUseCase
import com.example.e_commerce.main.domain.use_cases.RecommendedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val mostPopularUseCase: MostPopularUseCase,
    private val recommendedProductsUseCase: RecommendedProductsUseCase

) : ViewModel() {

    private var _mostPopularState= MutableStateFlow<Resource<ProductResponse, String>>(Resource.Empty())
    val mostPopularState: StateFlow<Resource<ProductResponse, String>> = _mostPopularState

    private var _recommendedState= MutableStateFlow<Resource< List<SummaryProduct>, String>>(Resource.Empty())
    val recommendedState: StateFlow<Resource< List<SummaryProduct>, String>> = _recommendedState


    fun getMostPopular() {
        mostPopularUseCase().onEach {
            when (it) {
                is Resource.Loading -> _mostPopularState.value = Resource.Loading(null)
                is Resource.Success -> _mostPopularState.value = Resource.Success(it.data!!)
                is Resource.Error -> _mostPopularState.value = Resource.Error(it.message)
                else -> { }
            }
        }.launchIn(viewModelScope)
    }

    init {
       // getMostPopular()
        getRecommended()
    }

    fun getRecommended() {
        recommendedProductsUseCase().onEach {
            when (it) {
                is Resource.Loading -> _recommendedState.value = Resource.Loading(null)
                is Resource.Success -> _recommendedState.value = Resource.Success(it.data!!)
                is Resource.Error -> _recommendedState.value = Resource.Error(it.message)
                else -> { }
            }
        }.launchIn(viewModelScope)
    }



}