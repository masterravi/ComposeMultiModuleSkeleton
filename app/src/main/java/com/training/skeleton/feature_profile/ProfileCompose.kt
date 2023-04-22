package com.training.skeleton.feature_profile

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
import com.training.skeleton.feature_dashboard.DashboardViewModel

@Composable
fun ProfileCompose(
    mainActivityViewModel: MainActivityViewModel =viewModel(),
    navigateToDashboard:()->Unit,
    navigateToSettings:()->Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = { navigateToDashboard() }) {
            Text(text = "Go to Dashboard")
        }

        Button(onClick = { navigateToSettings() }) {
            Text(text = "Go to settings")
        }
    }
}