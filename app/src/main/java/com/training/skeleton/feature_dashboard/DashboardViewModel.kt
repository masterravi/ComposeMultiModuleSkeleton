package com.training.skeleton.feature_dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.datastore.entity.ProductEntity
import com.training.network.DataState
import com.training.network.ResponseCodeEnums
import com.training.skeleton.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(productRepository :ProductRepository):ViewModel() {

    data class ProductUIState(
        var productList:List<ProductEntity> = mutableListOf(),
        var showErrorMessage:String="",
        var isLoading:Boolean=false
    )

    private val _productUIState= MutableStateFlow(ProductUIState())
    val productUIState:StateFlow<ProductUIState> = _productUIState.asStateFlow()
    init {
        fetchProductList(productRepository)
        getProductList(productRepository)
    }

    fun fetchProductList(productRepository: ProductRepository){
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
                        if (productDataState.data==ResponseCodeEnums.STATUS_OK) {
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

     fun getProductList(productRepository: ProductRepository) {
         viewModelScope.launch(Dispatchers.IO) {
             productRepository.getProductList().collect{data->
                 _productUIState.update {
                     it.copy(productList=data, isLoading = false)
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