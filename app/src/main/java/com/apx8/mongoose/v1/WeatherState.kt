package com.apx8.mongoose.v1

import com.apx8.mongoose.v1.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo?= null,
    val isLoading: Boolean = false,
    val error: String?= null
)