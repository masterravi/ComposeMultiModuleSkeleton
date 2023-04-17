package com.training.skeleton.uiCore

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.training.skeleton.R

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Dashboard : BottomNavigationScreens("dashboard", R.string.dashboard, Icons.Filled.Settings)
    object Profile : BottomNavigationScreens("profile", R.string.profile, Icons.Filled.Add)
    object Settings : BottomNavigationScreens("settings", R.string.settings, Icons.Filled.Create)
}