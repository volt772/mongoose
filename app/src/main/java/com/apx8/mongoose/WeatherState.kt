package com.apx8.mongoose

import com.apx8.mongoose.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo?= null,
    val isLoading: Boolean = false,
    val error: String?= null
)