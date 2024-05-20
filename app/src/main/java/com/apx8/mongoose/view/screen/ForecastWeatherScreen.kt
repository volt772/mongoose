package com.apx8.mongoose.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.view.display.DayAfterTomorrowDisplay
import com.apx8.mongoose.view.display.TodayDisplay

@Composable
fun ForecastWeatherScreen(
    forecastState: ForecastWeatherInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /* 오늘 날씨 예보(낮경기, 저녁경기)*/
        TodayDisplay(forecastState = forecastState, modifier = modifier)

        Spacer(modifier = Modifier.height(20.dp))

        /* 익일 ~ 3일간 날씨 예보(낮경기, 저녁경기)*/
        DayAfterTomorrowDisplay(info = forecastState, modifier = modifier)
    }
}