package com.apx8.mongoose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.presentation.ui.theme.AppColor
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.view.screen.CurrentWeatherScreen
import com.apx8.mongoose.presentation.view.screen.ForecastWeatherScreen
import com.apx8.mongoose.v1.presentation.ui.theme.MongooseTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val vm: MainViewModel by viewModels()
    private var currentStadium: Stadium = Stadium.NAN

    private fun setCurrentStadium(code: String) {
        vm.setCurrentStadium(code)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }

//        lifecycleScope.launch {
//            vm.fetch(Stadium.SOJ)
//        }

        lifecycleScope.run {
            launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
//                    println("probe :: stadium : first : started")
                    currentStadium = Stadium.SOJ
                    vm.fetch(Stadium.SOJ)
                }
            }

            launch {
                vm.currentStadium.collectLatest { stadium ->
//                    println("probe :: stadium : after : changed")
                    currentStadium = stadium
                    vm.fetch(stadium)
//                    println("probe :: latest stadium : $stadium")
                }
            }

//            vm.currentWeather.collectAsStateWithLifecycle().value
        }

        setContent {
            val scrollState = rememberScrollState()

            MongooseTheme {
                SetStatusBarColor()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MgBlue),
                ) {

                    /* AD*/
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        BannersAds()
                    }

                    /* Content*/
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = scrollState)
                            .background(MgBlue),
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
//                                    println("probe :: render1 : ${state.data}")
                                    CurrentWeatherScreen(info = state.data, stadium = currentStadium, modifier = Modifier, ::setCurrentStadium)
                                }
                            }

                            /* Column2 : Forecast Screen*/
                            when (val state = vm.forecastWeather.collectAsStateWithLifecycle().value) {
                                is CommonState.Loading -> {
                                    LoadingProgressIndicator()
                                }
                                is CommonState.Error -> { }
                                is CommonState.Success -> {
//                                    println("probe :: render2 ! ")
                                    ForecastWeatherScreen(info = state.data, modifier = Modifier)
                                }
                            }

                        }
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
            color = MgBlue,
            darkIcons = useDarkIcons
        )

        onDispose { }
    }
}