package com.apx8.mongoose.domain.dto

data class AllWeatherInfo(
    val currentWeatherInfo: CurrentWeatherInfo,
    val forecastWeatherInfo: ForecastWeatherInfo
)