package com.training.skeleton.presentation.feature_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.navigation.Screen
import com.training.skeleton.presentation.feature_dashboard.DashboardViewModel

@Composable
fun ProfileCompose(
    mainActivityViewModel: MainActivityViewModel =viewModel(),
    navigateToDashboard:()->Unit,
    navigateToSettings:(String?)->Unit
) {

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val productUIState= profileViewModel.productUIState.collectAsState()

    mainActivityViewModel.setScreenParams(
        screen = Screen.Profile,
        screenTitle = "Product Detail"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = { navigateToDashboard() }) {
            Text(text = productUIState.value.productDetail?.title.toString())
        }

        Button(onClick = {
            navigateToSettings(productUIState.value.productDetail?.id.toString()) }) {
            Text(text = productUIState.value.productDetail?.description.toString())
        }
    }
}