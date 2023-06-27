package com.training.skeleton.uiCore

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.training.skeleton.R

sealed class BottomNavigationScreens(val route: String, val resourceId: String, val icon: ImageVector) {
    object Dashboard : BottomNavigationScreens("dashboard", "Dashboard1002", Icons.Filled.Settings)
    object Profile : BottomNavigationScreens("profile", "Dashboard1003", Icons.Filled.Add)
    object Settings : BottomNavigationScreens("settings", "Dashboard1004", Icons.Filled.Create)
}