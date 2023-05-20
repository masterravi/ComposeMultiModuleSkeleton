package com.training.skeleton.presentation.feature_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.datastore.entity.ProductEntity
import com.training.skeleton.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
) :ViewModel() {


    data class ProductUIState(
        var productId:String="",
        var productDetail:ProductEntity?=null,
        var showErrorMessage:String="",
        var isLoading:Boolean=false
    )

    private val _productUIState= MutableStateFlow(ProductUIState())
    val productUIState: StateFlow<ProductUIState> = _productUIState.asStateFlow()
    init {
        val productId = savedStateHandle.get<String>("productId").orEmpty()
        getProductDetail(productId.toInt())
    }

    fun getProductDetail(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.observeProductDetail(productId).onEach {
                    value -> _productUIState.update {
                it.copy(productDetail = value)
            }
            }.collect()
        }

    }
}