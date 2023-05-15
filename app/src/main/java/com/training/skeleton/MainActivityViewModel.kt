package com.training.skeleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.training.skeleton.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
@HiltViewModel
class MainActivityViewModel : ViewModel() {

    data class SharedUiState(
        val currentScreen: Screen = Screen.Dashboard,
        val currentScreenTitle: String = "",
        val navCurrentRoute: String = ""
    )

    private val _uiState = MutableStateFlow(SharedUiState())
    val uiState: StateFlow<SharedUiState> = _uiState.asStateFlow()

    internal fun setScreenParams(
        screen: Screen,
        screenTitle: String,
    ) {
        if (_uiState.value.navCurrentRoute.isEmpty() || _uiState.value.navCurrentRoute.contains(screen.route)) {
           _uiState.update { currentState->
                   currentState.copy(currentScreen = screen, currentScreenTitle =screenTitle)
           }
        }
    }
}

