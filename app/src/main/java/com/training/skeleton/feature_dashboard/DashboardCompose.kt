package com.training.skeleton.feature_dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.feature_dashboard.data.Anime
import com.training.skeleton.feature_dashboard.data.DasboardViewModelFactory
import com.training.skeleton.feature_dashboard.data.Product
import com.training.skeleton.feature_dashboard.data.ProductDetail
import com.training.skeleton.navigation.Screen
import com.training.skeleton.repository.ProductRepository

@Composable
fun DashboardCompose(
    mainActivityViewModel: MainActivityViewModel =viewModel(),
    navigateToProfile:()->Unit,
    navigateToSettings:()->Unit,
) {
    val dashboardViewModel:DashboardViewModel = viewModel(factory = DasboardViewModelFactory(
        ProductRepository(LocalContext.current.applicationContext)
    ))
    val uiState= dashboardViewModel.productUIState.collectAsState()

    val productList=uiState.value.productList

    mainActivityViewModel.setScreenParams(
        screen = Screen.Dashboard,
        screenTitle = "Dashboard"
    )

    DashboardMainContent(navigateToProfile,navigateToSettings,productList)


}
@Composable
fun DashboardMainContent(
    navigateToProfile: () -> Unit,
    navigateToSettings: () -> Unit,
    productList:List<ProductDetail>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){


        Button(onClick = { navigateToProfile()}) {
            Text(text = "Go to profile")
        }

        Button(onClick = { navigateToSettings()}) {
            Text(text = "Go to settings")
        }
        ShowAnimeList(productList)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowAnimeList(productList:List<ProductDetail>){

    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(productList.size) { index ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(productList[index].thumbnail)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
                Text(
                    text = productList[index].title,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
@Composable
@Preview
fun DashboardPreview(){
    val  navigateToProfile : () -> Unit = {}
    val  navigateToSettings : () -> Unit = {}
    DashboardMainContent(navigateToProfile, navigateToSettings, listOf<ProductDetail>())
}

@Composable
@Preview
fun ShowAnimeList(){
    ShowAnimeList( )
}



