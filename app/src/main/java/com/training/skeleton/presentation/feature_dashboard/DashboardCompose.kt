package com.training.skeleton.presentation.feature_dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.datastore.entity.ProductEntity
import com.training.skeleton.MainActivityViewModel
import com.training.skeleton.presentation.feature_dashboard.data.DasboardViewModelFactory
import com.training.skeleton.navigation.Screen
import com.training.skeleton.repository.ProductRepository

@Composable
fun DashboardCompose(
    mainActivityViewModel: MainActivityViewModel =viewModel(),
    navigateToProfile:()->Unit,
    navigateToSettings:()->Unit,
) {
    val dashboardViewModel: DashboardViewModel = viewModel()
    val uiState= dashboardViewModel.productUIState.collectAsState()

    val productList=uiState.value.productList
    val isLoading=uiState.value.isLoading

    mainActivityViewModel.setScreenParams(
        screen = Screen.Dashboard,
        screenTitle = "Dashboard"
    )

    DashboardMainContent(dismissLoader =
        {
        dashboardViewModel.showHideLoader(it)
        },
        productList=productList
        ,isLoading=isLoading
    )


}
@Composable
fun DashboardMainContent(
    dismissLoader: (Boolean) -> Unit,
    productList: List<ProductEntity>,
    isLoading:Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){

        ShowProductList(productList)

        if (isLoading) {
            Dialog(
                onDismissRequest = {  dismissLoader.invoke(false) },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment= Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(White, shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowProductList(productList: List<ProductEntity>){

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
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(128.dp)
                    .padding(16.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(1.dp, Color.Black, CircleShape)
                )
                Text(
                    text = productList[index].title,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
@Preview
fun DashboardPreview(){
    DashboardMainContent(dismissLoader={}, listOf<ProductEntity>(),false)
}

@Composable
@Preview
fun ShowProductList(){
    ShowProductList( )
}



