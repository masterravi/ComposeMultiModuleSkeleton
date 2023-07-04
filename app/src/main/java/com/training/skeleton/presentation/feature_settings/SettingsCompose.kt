package com.training.skeleton.presentation.feature_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.training.localization.Philology
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.navigation.Screen

@Composable
fun SettingsCompose(
    mainActivityViewModel: MainActivityViewModel,
    navigateToDashboard:()->Unit,
    navigateToProfile:(String)->Unit,
    navigateToLanguageSettings : () -> Unit = {},
    id : String = ""
) {
    mainActivityViewModel.setScreenParams(
        screen = Screen.Settings,
        screenTitle = Philology.getString(id = "Dashboard1004")
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = { navigateToDashboard()}) {
            Text(text = "Go to Dashboard")
        }

        Button(onClick = { navigateToProfile(id) }) {
            Text(text = "Go to Profile")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            navigateToLanguageSettings.invoke()
        }) {
            Text(text = "Go to Language Settings")
        }
    }
}