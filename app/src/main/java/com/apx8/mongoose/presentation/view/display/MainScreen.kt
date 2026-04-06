package com.apx8.mongoose.presentation.view.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.presentation.MongooseApp.Companion.adMobKey
import com.apx8.mongoose.presentation.topbar.MyTopBar
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import com.apx8.mongoose.presentation.view.dialog.AppInfoDialog
import com.apx8.mongoose.presentation.view.screen.CurrentWeatherScreen
import com.apx8.mongoose.presentation.view.screen.ForecastWeatherScreen
import com.apx8.mongoose.presentation.view.vms.MainViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel,
    onConfirmAppInfo: () -> Unit,
    onInfoClick: () -> Unit,
    onSelectStadium: (String) -> Unit
) {

    val isFirstLaunch = vm.isFirstRun.collectAsStateWithLifecycle().value
    val currentStadium = vm.currentStadium.collectAsStateWithLifecycle().value
    val isRefreshing = vm.isRefreshing.collectAsStateWithLifecycle().value
    val currentWeatherState = vm.currentWeather.collectAsStateWithLifecycle().value
    val forecastWeatherState = vm.forecastWeather.collectAsStateWithLifecycle().value

    var openAlertDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isFirstLaunch) {
        openAlertDialog = isFirstLaunch
    }

    if (openAlertDialog) {
        AppInfoDialog(
            onDismissRequest = { },
            onConfirmation = {
                openAlertDialog = false
                onConfirmAppInfo()
            },
            dialogTitle = stringResource(id = R.string.welcome),
            dialogText = stringResource(id = R.string.inaccurate_info1),
            icon = Icons.Default.Face,
            buttonConfirmLabel = stringResource(id = R.string.confirmed)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MgDarkBlue)
            .navigationBarsPadding()
    ) {
        MyTopBar(
            onInfoClick = onInfoClick,
        )

        PullToRefreshBox(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            isRefreshing = isRefreshing,
            onRefresh = {
                vm.refresh(currentStadium)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                when (currentWeatherState) {
                    is CommonState.Loading -> {
                        LoadingProgressIndicator()
                    }

                    is CommonState.Error -> {
                        CurrentErrorDisplay(
                            refresh = {
                                vm.requestWeather(currentStadium)
                            }
                        )
                    }

                    is CommonState.Success -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            CurrentWeatherScreen(
                                info = currentWeatherState.data,
                                currentStadium = currentStadium,
                                doSelectStadium = onSelectStadium,
                                modifier = Modifier
                            )

                            if (forecastWeatherState is CommonState.Success) {
                                ForecastWeatherScreen(
                                    info = forecastWeatherState.data,
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }
            }
        }


        BannersAds()
    }
}

@Preview
@Composable
fun PreviewAppInfo() {
}

@Composable
private fun LoadingProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(300.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(30.dp, 30.dp),
            strokeCap = StrokeCap.Round,
            color = MgWhite
        )
    }
}

@Composable
fun BannersAds(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adMobKey
                loadAd(AdRequest.Builder().build())
            }
        },
    )
}