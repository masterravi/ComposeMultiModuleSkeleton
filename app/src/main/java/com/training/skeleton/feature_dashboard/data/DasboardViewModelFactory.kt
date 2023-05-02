package com.training.skeleton.feature_dashboard.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.training.skeleton.feature_dashboard.DashboardViewModel
import com.training.skeleton.repository.ProductRepository

class DasboardViewModelFactory(private val productRepository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DashboardViewModel(productRepository) as T
}