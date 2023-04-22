package com.training.skeleton.feature_dashboard

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
fun DashboardCompose(
    mainActivityViewModel: MainActivityViewModel =viewModel(),
    navigateToProfile:()->Unit,
    navigateToSettings:()->Unit,
) {

    mainActivityViewModel.setScreenParams(
        screen = Screen.Dashboard,
        screenTitle = "Dashboard"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Button(onClick = { navigateToProfile()}) {
            Text(text = "Go to profile")
        }

        Button(onClick = { navigateToSettings()}) {
            Text(text = "Go to settings")
        }
    }
}