package com.apx8.mongoose.presentation.view.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.presentation.MongooseApp.Companion.adMobKey
import com.apx8.mongoose.presentation.ext.getDateAfter2DaysWithToday
import com.apx8.mongoose.presentation.ext.getWeatherConditionCodes
import com.apx8.mongoose.presentation.model.WeatherUiColors
import com.apx8.mongoose.presentation.topbar.MyTopBar
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import com.apx8.mongoose.presentation.view.dialog.AppInfoDialog
import com.apx8.mongoose.presentation.view.screen.CurrentWeatherScreen
import com.apx8.mongoose.presentation.view.screen.ForecastWeatherScreen
import com.apx8.mongoose.presentation.view.vms.MainUiState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: MainUiState,
    showAppInfoDialog: Boolean,
    onConfirmAppInfo: () -> Unit,
    onInfoClick: () -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onSelectStadium: (String) -> Unit
) {

    val colors: WeatherUiColors = when (val state = uiState.currentWeatherState) {
        is CommonState.Success -> {
            state.data.weatherId
                .getWeatherConditionCodes()
                .colors
        }
        else -> WeatherUiColors(
            background = MgDarkBlue,
            content = MgWhite,
            secondary = MgWhite
        )
    }


    if (showAppInfoDialog) {
        AppInfoDialog(
            onDismissRequest = { },
            onConfirmation = onConfirmAppInfo,
            dialogTitle = stringResource(id = R.string.welcome),
            dialogText = stringResource(id = R.string.inaccurate_info1),
            icon = Icons.Default.Face,
            buttonConfirmLabel = stringResource(id = R.string.confirmed)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .navigationBarsPadding()
    ) {
        MyTopBar(
            onInfoClick = onInfoClick,
            backgroundColor = colors.background,
            contentColor = colors.content
        )

        PullToRefreshBox(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            isRefreshing = uiState.isRefreshing,
            onRefresh = onRefresh
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.background)
            ) {
                when (uiState.currentWeatherState) {
                    is CommonState.Loading -> {
                        LoadingProgressIndicator(colors.content)
                    }

                    is CommonState.Error -> {
                        CurrentErrorDisplay(
                            refresh = onRetry,
                            contentColor = colors.content,
                            secondaryColor = colors.secondary
                        )
                    }

                    is CommonState.Success -> {
                        val hasTodayForecast = (uiState.forecastWeatherState as? CommonState.Success)
                            ?.data
                            ?.forecastList
                            ?.filter { it.dtTxtDate == getDateAfter2DaysWithToday().first() }
                            ?.isNotEmpty() == true

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(
                                    bottom = if (hasTodayForecast) 0.dp else 180.dp
                                ),
                            verticalArrangement = if (hasTodayForecast) Arrangement.Top else Arrangement.Center
                        ) {
                            CurrentWeatherScreen(
                                info = uiState.currentWeatherState.data,
                                currentStadium = uiState.currentStadium,
                                doSelectStadium = onSelectStadium,
                                backgroundColor = colors.background,
                                contentColor = colors.content,
                                secondaryColor = colors.secondary,
                                modifier = Modifier
                            )

                            if (hasTodayForecast) {
                                ForecastWeatherScreen(
                                    info = (uiState.forecastWeatherState as CommonState.Success).data,
                                    backgroundColor = colors.background,
                                    contentColor = colors.content,
                                    secondaryColor = colors.secondary,
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
private fun LoadingProgressIndicator(contentColor: Color) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(30.dp, 30.dp),
            strokeCap = StrokeCap.Round,
            color = contentColor
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