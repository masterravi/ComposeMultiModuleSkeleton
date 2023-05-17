package com.training.skeleton.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.presentation.feature_dashboard.DashboardCompose
import com.training.skeleton.presentation.feature_profile.ProfileCompose
import com.training.skeleton.presentation.feature_settings.SettingsCompose


@Composable
fun NavGraph(navController: NavHostController, mainActivityViewModel: MainActivityViewModel) {

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {

        addProfileScreen(navController,mainActivityViewModel, this)

        addDashboardScreen(navController,mainActivityViewModel, this)

        addSettingsScreen(navController,mainActivityViewModel, this)

    }
}

private fun addDashboardScreen(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = Screen.Dashboard.route) {
        DashboardCompose(
            mainActivityViewModel = mainActivityViewModel,
            navigateToProfile = {
                navController.navigate(Screen.Profile.route)
            },
            navigateToSettings = {
                navController.navigate(Screen.Settings.route)
            }
        )
    }
}

private fun addProfileScreen(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = Screen.Profile.route) {
        ProfileCompose(
            mainActivityViewModel = mainActivityViewModel,
            navigateToDashboard = {
                navController.navigate(Screen.Dashboard.route)
            },
            navigateToSettings = {
                navController.navigate(Screen.Settings.route)
            }
        )
    }
}

private fun addSettingsScreen(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = Screen.Settings.route) {
        SettingsCompose(
            mainActivityViewModel = mainActivityViewModel,
            navigateToDashboard = {
                navController.navigate(Screen.Dashboard.route)
            },
            navigateToProfile = {
                navController.navigate(Screen.Profile.route)
            }
        )
    }
}