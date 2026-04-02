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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apx.apx108.presentation.topbar.TopBarWithSearch
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.presentation.MongooseApp.Companion.adMobKey
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue
import com.apx8.mongoose.presentation.ui.theme.MgSubDarkBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import com.apx8.mongoose.presentation.ui.theme.MgYellow
import com.apx8.mongoose.presentation.view.dialog.AppInfoDialog
import com.apx8.mongoose.presentation.view.screen.CurrentWeatherScreen
import com.apx8.mongoose.presentation.view.screen.ForecastWeatherScreen
import com.apx8.mongoose.presentation.view.vms.MainViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Composable
fun MainScreen(
    vm: MainViewModel,
    isFirstLaunch: Boolean,
    onConfirmAppInfo: () -> Unit,
    onInfoClick: () -> Unit,
    onSelectStadium: (String) -> Unit
) {
    val currentStadium = vm.currentStadium.collectAsState().value

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
        TopBarWithSearch(
            onInfoClick = onInfoClick
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (vm.isFailed) {
                CurrentErrorDisplay(
                    refresh = {
                        vm.fetch(currentStadium)
                    }
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    RenderCurrentWeatherScreen(
                        vm = vm,
                        currentStadium = currentStadium,
                        onSelectStadium = onSelectStadium
                    )
                    RenderForecastWeatherScreen(vm = vm)
                }
            }
        }

        BannersAds()
    }
}

@Composable
fun RenderCurrentWeatherScreen(
    vm: MainViewModel,
    currentStadium: Stadium,
    onSelectStadium: (String) -> Unit
) {
    when (val state = vm.currentWeather.collectAsStateWithLifecycle().value) {
        is CommonState.Loading -> {
            LoadingProgressIndicator()
        }

        is CommonState.Error -> {
            vm.isFailed = true
        }

        is CommonState.Success -> {
            vm.onLoading = false

            if (!vm.isFailed) {
                CurrentWeatherScreen(
                    info = state.data,
                    currentStadium = currentStadium,
                    doSelectStadium = onSelectStadium,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun RenderForecastWeatherScreen(
    vm: MainViewModel
) {
    when (val state = vm.forecastWeather.collectAsStateWithLifecycle().value) {
        is CommonState.Loading -> Unit
        is CommonState.Error -> Unit
        is CommonState.Success -> {
            ForecastWeatherScreen(
                info = state.data,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun RenderAppInfo(
    onInfoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MgDarkBlue)
            .padding(end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = stringResource(id = R.string.inaccurate_info2),
            fontSize = 16.sp,
            color = MgYellow,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            colors = ButtonColors(
                containerColor = MgSubDarkBlue,
                contentColor = MgWhite,
                disabledContainerColor = MgSubDarkBlue,
                disabledContentColor = MgWhite,
            ),
            onClick = onInfoClick
        ) {
            Text(text = stringResource(id = R.string.app_info))
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun PreviewAppInfo() {
    RenderAppInfo(onInfoClick = { })
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
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}