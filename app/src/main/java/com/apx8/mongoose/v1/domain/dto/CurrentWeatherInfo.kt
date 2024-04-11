package com.apx8.mongoose.v1.domain.dto

import com.apx8.mongoose.v1.data.remote.CurrentWeatherDataDto
import com.squareup.moshi.Json

data class CurrentWeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
    val name: String,
    val cod: Int
)
