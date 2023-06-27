package com.training.skeleton.presentation.feature_language_settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.training.skeleton.R
import kotlinx.coroutines.launch


val DEFAULT_APP_LANGUAGE = "en"


@Composable
fun LanguageSettingScreen(
    navHostController: NavHostController
){

    val languageSettingViewModel:LanguageSettingViewModel = hiltViewModel()
    val uiState by languageSettingViewModel.uiState.collectAsState()


    val languageList = uiState.languageList
    var selectedLanguage by remember {
        mutableStateOf(languageList[0])
    }

    var isInitialDataSet by remember {
        mutableStateOf(true)
    }

    // check default language at initial
    if (languageList.size > 1 && isInitialDataSet) {
        val list = languageList.filter { it.languageCode == uiState.selectedLanguageCode }
        if (list.isNotEmpty()) {
            selectedLanguage = list[0]
        }
        isInitialDataSet = false
    }

    LanguageUICompose(
        navHostController = navHostController,
        selectedLanguage = selectedLanguage,
        languageList = uiState.languageList,
        onChangeLanguage = languageSettingViewModel::changeLanguage,
    )

    if(uiState.showLoader){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blurred_bg)), contentAlignment = Alignment.Center){
            CircularProgressIndicator(modifier = Modifier.size(40.dp))
        }
    }
}

@Composable
fun LanguageUICompose(
    navHostController:NavHostController,
    selectedLanguage: LanguageModel,
    languageList: List<LanguageModel>,
    onChangeLanguage:(LanguageModel, (Boolean)-> Unit) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val selectedRadioItem = remember{
        mutableStateOf(languageList[0])
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {

            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = 4.dp
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.language_settings),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp),
                        tint = Color.Black,
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 8.dp
                            )
                    ) {
                        Column {
                            Text(
                                text = "Language Settings",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                            )
                            Text(
                                text = selectedLanguage.title,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = 4.dp
            ) {
                LazyColumn(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(
                            top = 16.dp,
                            bottom = 8.dp
                        )
                ) {

                    items(languageList) { language ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.White
                                )
                                .clickable {
                                    selectedRadioItem.value = language
                                }

                        ) {
                            Text(
                                text = language.languageName,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(16.dp)
                            )

                            RadioButton(
                                selected = language.languageCode == selectedRadioItem.value.languageCode,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(
                                        end = 16.dp
                                    ),
                                 colors = RadioButtonDefaults.colors(
                                     selectedColor = MaterialTheme.colors.primary,
                                     unselectedColor = Color.LightGray,
                                 ),
                                onClick = {
                                    selectedRadioItem.value = language
                                })
                        }
                    }
                }
            }

        }


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .height(48.dp),
            onClick = {
                onChangeLanguage.invoke(
                    selectedRadioItem.value
                ) {
                    if (it) {
                        coroutineScope.launch {
                            navHostController.popBackStack()
                        }
                    }
                }
            },
            content = { Text(text = "Update") },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewLanguageCompose() {
    LanguageUICompose(
        navHostController = NavHostController(LocalContext.current),
        selectedLanguage = LanguageModel(languageName = "English", "en", "sxddf", "English"),
        listOf(
            LanguageModel(languageName = "English", "en", "sxddf", "English"),
            LanguageModel(languageName = "Hindi", "hi", "sxddf", "Hindi")
        ),
        onChangeLanguage = { _,_ ->

        }
    )
}