package com.training.skeleton.uiCore

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(bottomNavigationScreens: List<BottomNavigationScreens>, navController: NavHostController) {

    BottomNavigation() {
        val currentRoute= currentRoute(navController)
        bottomNavigationScreens.forEach { bottomNavigationScreens ->
        
            BottomNavigationItem(
                selected = currentRoute==bottomNavigationScreens.route,
                onClick = {
                    if (currentRoute != bottomNavigationScreens.route) {
                        navController.navigate(bottomNavigationScreens.route)
                    }
                },
                icon = {
                    Icon(bottomNavigationScreens.icon,bottomNavigationScreens.route)
                       },
                label = { Text(stringResource(id = bottomNavigationScreens.resourceId)) },
            )
        }

    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString("")
}
