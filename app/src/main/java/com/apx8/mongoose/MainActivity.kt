package com.apx8.mongoose

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.v1.presentation.ui.theme.AppColor
import com.apx8.mongoose.v1.presentation.ui.theme.ErrorGray
import com.apx8.mongoose.v1.presentation.ui.theme.MongooseTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            vm.loadWeatherInfo()
            vm.loadForecastInfo()
        }

        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))

        setContent {
            val scrollState = rememberScrollState()

            MongooseTheme {
//                val aa = vm.feed.collectAsStateWithLifecycle().value
//                when(aa) {
//                    is CommonState.Success -> {}
//                    is CommonState.Loading -> {}
//                    is CommonState.Error -> {}
//                }
                when (val state = vm.currentWeather.collectAsStateWithLifecycle().value) {
                    is CommonState.Loading -> { }
                    is CommonState.Error -> { }
                    is CommonState.Success -> {
                        println("probe :: main activity Current : ${state.data}")
                    }
                }

                when (val state = vm.forecastWeather.collectAsStateWithLifecycle().value) {
                    is CommonState.Loading -> { }
                    is CommonState.Error -> { }
                    is CommonState.Success -> {
                        println("probe :: main activity Forecast : ${state.data}")
                    }
                }

//                when(val dd = vm.feed.collectAsStateWithLifecycle().value) {
//                    CommonState.Loading -> { }
//                    CurrentWeatherState.Empty -> { }
//                    is CurrentWeatherState.Success -> {
//                        println("probe :: this hamster :${dd.dummy}")
//                    }
//                    is CurrentWeatherState.Error -> { }
//                }



                vm.state.let { _state ->

                    SetStatusBarColor()

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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 5.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Info,
                                        contentDescription = "Info",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }

                            WeatherCard(
                                state = _state,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            WeatherForecast(state = _state)
                        }

                        if (_state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = AppColor
                            )
                        }
                    }

                    _state.error?.let { error ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painterResource(id = R.drawable.ic_error),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(ErrorGray)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                modifier = Modifier.padding(horizontal = 30.dp),
                                text = error,
                                color = ErrorGray,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setNavigationBarColor(
            color = if (useDarkIcons) {
                Color.White
            } else {
                Color.Black
            },
            darkIcons = useDarkIcons
        )
        systemUiController.setStatusBarColor(
            color = if (useDarkIcons) {
                Color.Gray
            } else {
                Color.Black
            },
            darkIcons = useDarkIcons
        )

        onDispose { }
    }
}