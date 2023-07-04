package com.training.skeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.training.skeleton.navigation.NavGraph
import com.training.skeleton.ui.theme.JetpackSkeletonTheme
import com.training.skeleton.uiCore.BottomBar
import com.training.skeleton.uiCore.BottomNavigationScreens
import com.training.skeleton.uiCore.TopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackSkeletonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navHostController = rememberNavController()
                    val bottomNavigationItems = listOf(
                        BottomNavigationScreens.Dashboard,
                        BottomNavigationScreens.Profile,
                        BottomNavigationScreens.Settings
                    )
                    Scaffold(
                        topBar = {
                            TopBar(navHostController = navHostController,
                                mainActivityViewModel = mainActivityViewModel,
                                onButtonClicked = {
                                    navHostController.popBackStack()
                                }
                            )
                        },
                        bottomBar = {
                            BottomBar(bottomNavigationItems, navHostController)
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            MainContent(navHostController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MainContent(navHostController: NavHostController) {
        NavGraph(navController = navHostController, mainActivityViewModel)
    }

}
