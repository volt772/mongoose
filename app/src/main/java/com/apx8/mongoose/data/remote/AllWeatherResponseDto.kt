package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class AllWeatherResponseDto(
    @field:Json(name = "current")
    val current: CurrentResponseDto,

    @field:Json(name = "forecast")
    val forecast: ForecastResponseDto
)
