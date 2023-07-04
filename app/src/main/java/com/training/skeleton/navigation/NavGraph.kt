package com.training.skeleton.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.presentation.feature_dashboard.DashboardCompose
import com.training.skeleton.presentation.feature_language_settings.LanguageSettingScreen
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
                navController.navigate(
                    route = "${Screen.Profile.route}/$it",
                )
            },
            navigateToSettings = {
                navController.navigate(
                    route = "${Screen.Settings.route}/$it"
                )
            }
        )
    }
}

private fun addProfileScreen(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = "${Screen.Profile.route}/{productId}",
        arguments = listOf(navArgument("productId") { type = NavType.StringType })) {
        ProfileCompose(
            mainActivityViewModel = mainActivityViewModel,
            navigateToDashboard = {
                navController.navigate(Screen.Dashboard.route)
            },
            navigateToSettings = {
                navController.navigate(
                    route = "${Screen.Settings.route}/${it ?: "0"}"
                )
            }
        )
    }
}

private fun addSettingsScreen(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = "${Screen.Settings.route}/{productId}",
        arguments = listOf(navArgument("productId") { type = NavType.StringType })
    ) {
        SettingsCompose(
            mainActivityViewModel = mainActivityViewModel,
            navigateToDashboard = {
                navController.navigate(Screen.Dashboard.route)
            },
            navigateToProfile = {
                navController.navigate(
                    route = "${Screen.Profile.route}/$it"
                )
            },
            id = it.arguments?.getString("productId")?: "0",
            navigateToLanguageSettings = {
                navController.navigate(Screen.LanguageSettings.route)
            }
        )
    }

    navGraphBuilder.composable(route = Screen.LanguageSettings.route) {
        LanguageSettingScreen(
            navHostController = navController
        )
    }
}