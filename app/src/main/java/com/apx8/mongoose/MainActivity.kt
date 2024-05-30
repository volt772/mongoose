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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.v1.presentation.ui.theme.AppColor
import com.apx8.mongoose.v1.presentation.ui.theme.MongooseTheme
import com.apx8.mongoose.view.screen.CurrentWeatherScreen
import com.apx8.mongoose.view.screen.ForecastWeatherScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val vm: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        MobileAds.initialize(this)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            lifecycleScope.launch {
                vm.fetch()
            }
        }

        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))

        setContent {
            val scrollState = rememberScrollState()

            MongooseTheme {
                SetStatusBarColor()

                /* Later To do*/
//                StadiumListScreen()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = scrollState)
                        .height(IntrinsicSize.Max)
                        .background(Color.White),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                    ) {

                        /* Column1 : Current Screen*/
                        when (val state = vm.currentWeather.collectAsStateWithLifecycle().value) {
                            is CommonState.Loading -> {
                                LoadingProgressIndicator()
                            }

                            is CommonState.Error -> {}
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

                    Column(
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        BannersAds()
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp, 60.dp),
            color = AppColor
        )
    }
}

@Composable
fun BannersAds() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/9214589741"
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}

//private fun SetLoadingStatue() {
//    CircularProgressIndicator(
//        modifier = Modifier.align(Alignment.Center),
//        color = AppColor
//    )
//}

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