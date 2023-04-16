package com.training.skeleton.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.training.skeleton.feature_dashboard.DashboardCompose
import com.training.skeleton.feature_profile.ProfileCompose
import com.training.skeleton.feature_settings.SettingsCompose


@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Dashboard.path
    ) {

        addProfileScreen(navController, this)

        addDashboardScreen(navController, this)

        addSettingsScreen(navController, this)

    }
}

private fun addDashboardScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Dashboard.path) {
        DashboardCompose(
            navigateToProfile = {
                navController.navigate(NavRoute.Profile.path)
            },
            navigateToSettings = {
                navController.navigate(NavRoute.Settings.path)
            }
        )
    }
}

private fun addProfileScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Profile.path) {
        ProfileCompose(
            navigateToDashboard = {
                navController.navigate(NavRoute.Dashboard.path)
            },
            navigateToSettings = {
                navController.navigate(NavRoute.Settings.path)
            }
        )
    }
}

private fun addSettingsScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Settings.path) {
        SettingsCompose(
            navigateToDashboard = {
                navController.navigate(NavRoute.Dashboard.path)
            },
            navigateToProfile = {
                navController.navigate(NavRoute.Profile.path)
            }
        )
    }
}