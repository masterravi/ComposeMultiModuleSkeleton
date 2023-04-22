package com.training.skeleton.uiCore

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.navigation.Screen

@Composable
fun TopBar(
    navHostController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    onButtonClicked: () -> Unit = {},
) {

    val uiState by mainActivityViewModel.uiState.collectAsState()

    TopAppBar(
        title = {
            Text(text = uiState.currentScreenTitle)
        },
        navigationIcon = {
            when(uiState.currentScreen){
                Screen.Profile,Screen.Settings->{
                    IconButton(onClick = {
                        onButtonClicked()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }
                else -> {}
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = androidx.compose.ui.graphics.Color.White,
        elevation = 10.dp
//        actions = {
//            when(uiState.currentScreen){
//                Screen.Profile->{
//                    navHostController.popBackStack()
//                }
//                Screen.Settings->{
//                    navHostController.popBackStack()
//                }
//                else -> {}
//            }
//        }
    )
}

@Preview
@Composable
fun TopBarPreView(){
}