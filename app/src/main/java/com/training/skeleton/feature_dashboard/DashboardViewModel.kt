package com.training.skeleton.feature_dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.feature_dashboard.data.Anime
import com.training.skeleton.feature_dashboard.data.Title
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel:ViewModel() {

    data class AnimeUIState(
        var animeList:List<Anime> = mutableListOf()
    )

    private val _animeUIState= MutableStateFlow(AnimeUIState())
    val animeUIState:StateFlow<AnimeUIState> = _animeUIState.asStateFlow()
    init {
        _animeUIState.update { currentValue->
            currentValue.copy(animeList = animeList)
        }
    }


}

var animeList= listOf<Anime>(
    Anime(
        "Nakajima Atsushi was kicked out of his orphanage",
        listOf(
            "Mystery",
            "Seinen",
            "Supernatural"
        ),
        12345,
        "Apr 7, 2016, 01:05 (JST)", "Bones",
        Title("https://cdn.myanimelist.net/images/anime/1077/124345.jpg", "Bungou Stray Dogs")
    ),
    Anime(
        "Nakajima Atsushi was kicked out of his orphanage",
        listOf(
            "Mystery",
            "Seinen",
            "Supernatural"
        ),
        12345,
        "Apr 7, 2016, 01:05 (JST)", "Bones",
        Title("https://cdn.myanimelist.net/images/anime/1077/124345.jpg", "Bungou Stray Dogs")
    ),
    Anime(
        "Nakajima Atsushi was kicked out of his orphanage",
        listOf(
            "Mystery",
            "Seinen",
            "Supernatural"
        ),
        12345,
        "Apr 7, 2016, 01:05 (JST)", "Bones",
        Title("https://cdn.myanimelist.net/images/anime/1077/124345.jpg", "Bungou Stray Dogs")
    ),
    Anime(
        "Nakajima Atsushi was kicked out of his orphanage",
        listOf(
            "Mystery",
            "Seinen",
            "Supernatural"
        ),
        12345,
        "Apr 7, 2016, 01:05 (JST)", "Bones",
        Title("https://cdn.myanimelist.net/images/anime/1077/124345.jpg", "Bungou Stray Dogs")
    ),
    Anime(
        "Nakajima Atsushi was kicked out of his orphanage",
        listOf(
            "Mystery",
            "Seinen",
            "Supernatural"
        ),
        12345,
        "Apr 7, 2016, 01:05 (JST)", "Bones",
        Title("https://cdn.myanimelist.net/images/anime/1077/124345.jpg", "Bungou Stray Dogs")
    ),
    Anime(
        "Nakajima Atsushi was kicked out of his orphanage",
        listOf(
            "Mystery",
            "Seinen",
            "Supernatural"
        ),
        12345,
        "Apr 7, 2016, 01:05 (JST)", "Bones",
        Title("https://cdn.myanimelist.net/images/anime/1077/124345.jpg", "Bungou Stray Dogs")
    ),
    Anime(
        "Nakajima Atsushi was kicked out of his orphanage",
        listOf(
            "Mystery",
            "Seinen",
            "Supernatural"
        ),
        12345,
        "Apr 7, 2016, 01:05 (JST)", "Bones",
        Title("https://cdn.myanimelist.net/images/anime/1077/124345.jpg", "Bungou Stray Dogs")
    ),
    Anime(
        "Nakajima Atsushi was kicked out of his orphanage",
        listOf(
            "Mystery",
            "Seinen",
            "Supernatural"
        ),
        12345,
        "Apr 7, 2016, 01:05 (JST)", "Bones",
        Title("https://cdn.myanimelist.net/images/anime/1077/124345.jpg", "Bungou Stray Dogs")
    ),


    )