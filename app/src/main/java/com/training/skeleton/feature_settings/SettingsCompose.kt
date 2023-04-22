package com.training.skeleton.feature_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.navigation.Screen

@Composable
fun SettingsCompose(
    mainActivityViewModel: MainActivityViewModel,
    navigateToDashboard:()->Unit,
    navigateToProfile:()->Unit
) {
    mainActivityViewModel.setScreenParams(
        screen = Screen.Settings,
        screenTitle = "Settings"
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = { navigateToDashboard()}) {
            Text(text = "Go to Dashboard")
        }

        Button(onClick = { navigateToProfile() }) {
            Text(text = "Go to Profile")
        }
    }
}