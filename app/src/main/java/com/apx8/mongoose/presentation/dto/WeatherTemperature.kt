package com.apx8.mongoose.presentation.dto

import com.apx8.mongoose.presentation.constants.WeatherTemperatureType

data class WeatherTemperature(
    val type: WeatherTemperatureType,
    val temp: String
)
