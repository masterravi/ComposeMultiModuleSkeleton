package com.training.skeleton.navigation

sealed class NavRoute(val path: String) {

    object Dashboard : NavRoute("dashboard")

    object Profile : NavRoute("profile")

    object Settings : NavRoute("settings")
}