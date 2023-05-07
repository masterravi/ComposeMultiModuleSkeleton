package com.training.skeleton.feature_dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.skeleton.feature_dashboard.data.ProductDetail
import com.training.skeleton.network.DataState
import com.training.skeleton.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(productRepository :ProductRepository):ViewModel() {

    data class ProductUIState(
        var productList:List<ProductDetail> = mutableListOf(),
        var showErrorMessage:String="",
        var isLoading:Boolean=false
    )

    private val _productUIState= MutableStateFlow(ProductUIState())
    val productUIState:StateFlow<ProductUIState> = _productUIState.asStateFlow()
    init {
        getProductList(productRepository)
    }

    fun getProductList(productRepository: ProductRepository){
        _productUIState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.fetchProductList().collect{ productDataState->
                when (productDataState) {
                    is DataState.Error -> {
                        _productUIState.update {
                            it.copy(showErrorMessage =productDataState.errorMessage, isLoading = false)
                        }
                    }

                    is DataState.Success -> {
                        if (productDataState.data.isNotEmpty()) {
                            _productUIState.update {
                                it.copy(
                                    productList = productDataState.data,
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is DataState.Loading -> {
                        _productUIState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }

            }
        }

    }

    fun showHideLoader(toggle:Boolean){
        _productUIState.update {
            it.copy(isLoading = toggle)
        }
    }

}