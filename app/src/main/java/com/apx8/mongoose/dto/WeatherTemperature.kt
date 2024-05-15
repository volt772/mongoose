package com.apx8.mongoose.v1.presentation.dto

import com.apx8.mongoose.constants.WeatherTemperatureType

data class WeatherTemperature(
    val type: WeatherTemperatureType,
    val temp: String
)
