package com.training.skeleton.feature_dashboard.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.training.skeleton.feature_dashboard.DashboardViewModel
import com.training.skeleton.repository.AnimeRepository

class DasboardViewModelFactory(private val animeRepository: AnimeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DashboardViewModel(animeRepository) as T
}