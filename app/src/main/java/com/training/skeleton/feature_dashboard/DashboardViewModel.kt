package com.training.skeleton.feature_dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.skeleton.feature_dashboard.data.Anime
import com.training.skeleton.feature_dashboard.data.Title
import com.training.skeleton.network.DataState
import com.training.skeleton.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel( var animeRepository:AnimeRepository):ViewModel() {

    data class AnimeUIState(
        var animeList:List<Anime> = mutableListOf(),
        var showErrorMessage:String="",
        var isLoading:Boolean=false
    )

    private val _animeUIState= MutableStateFlow(AnimeUIState())
    val animeUIState:StateFlow<AnimeUIState> = _animeUIState.asStateFlow()
    init {
        getAnimeList(animeRepository)
    }

    fun getAnimeList(animeRepository: AnimeRepository){
        viewModelScope.launch(Dispatchers.IO) {
            animeRepository.fetchAnimeList().collect{ animeDataState->
                when (animeDataState) {
                    is DataState.Error -> {
                        _animeUIState.update {
                            it.copy(showErrorMessage =animeDataState.errorMessage, isLoading = false)
                        }
                    }

                    is DataState.Success -> {
                        if (animeDataState.data.isNotEmpty()) {
                            _animeUIState.update {
                                it.copy(
                                    animeList = animeDataState.data,
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is DataState.Loading -> {
                        _animeUIState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }

            }
        }

    }


}