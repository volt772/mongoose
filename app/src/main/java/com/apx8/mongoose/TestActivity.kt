package com.apx8.mongoose

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.v1.presentation.ui.theme.AppColor
import com.apx8.mongoose.v1.presentation.ui.theme.MongooseTheme
import com.apx8.mongoose.view.screen.CurrentWeatherScreen
import com.apx8.mongoose.view.screen.ForecastWeatherScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TestActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scrollState = rememberScrollState()

            MongooseTheme {
                SetStatusBarColor()

                /* Later To do*/
//                StadiumListScreen()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = scrollState)
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {

                        /* Column1 : Current Screen*/
                        when (val state = vm.currentWeather.collectAsStateWithLifecycle().value) {
                            is CommonState.Loading -> {
                                LoadingProgressIndicator()
                            }
                            is CommonState.Error -> { }
                            is CommonState.Success -> {
                                CurrentWeatherScreen(state = state.data, modifier = Modifier)
                            }
                        }

                        /* Column2 : Forecast Screen*/
                        when (val state = vm.forecastWeather.collectAsStateWithLifecycle().value) {
                            is CommonState.Loading -> {
                                LoadingProgressIndicator()
                            }
                            is CommonState.Error -> { }
                            is CommonState.Success -> {
                                ForecastWeatherScreen(forecastState = state.data, modifier = Modifier)
                            }
                        }

                    }
                }
            }
        }
    }
}