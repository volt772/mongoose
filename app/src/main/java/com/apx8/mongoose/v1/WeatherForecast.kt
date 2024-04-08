package com.apx8.mongoose.v1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        ) {
            Text(
                text = "Today" ,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                itemsIndexed(data) { index, weatherData ->
                    val (startPadding, endPadding) = when (index) {
                        0 -> 0.dp to 16.dp
                        data.size - 1 -> 16.dp to 0.dp
                        else -> 16.dp to 16.dp
                    }
                    HourlyWeatherDisplay(
                        weatherData = weatherData,
                        modifier = Modifier
                            .height(100.dp)
                            .padding(start = startPadding, end = endPadding)
                    )
                }
            }
        }
    }
}