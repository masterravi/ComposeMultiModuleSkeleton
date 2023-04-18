package com.training.skeleton.uiCore

import android.graphics.Color
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(text = "Top App Bar")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = androidx.compose.ui.graphics.Color.White,
        elevation = 10.dp
    )
}

@Preview
@Composable
fun TopBarPreView(){
    TopBar()
}