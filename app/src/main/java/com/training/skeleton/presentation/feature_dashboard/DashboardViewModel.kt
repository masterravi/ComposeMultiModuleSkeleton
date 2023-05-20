package com.training.skeleton.presentation.feature_dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.datastore.entity.ProductEntity
import com.training.network.DataState
import com.training.network.ResponseCodeEnums
import com.training.skeleton.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(productRepository :ProductRepository):ViewModel() {

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
             productRepository.observeProductList().onEach {
                     value -> _productUIState.update {
                                it.copy(productList=value, isLoading = false)
                    }
             }.collect()
         }

    }

    fun showHideLoader(toggle:Boolean){
        _productUIState.update {
            it.copy(isLoading = toggle)
        }
    }

}