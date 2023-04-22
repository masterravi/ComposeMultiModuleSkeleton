package com.training.skeleton.navigation

sealed class Screen(val route: String) {

    object Dashboard : Screen("dashboard")

    object Profile : Screen("profile")

    object Settings : Screen("settings")
}