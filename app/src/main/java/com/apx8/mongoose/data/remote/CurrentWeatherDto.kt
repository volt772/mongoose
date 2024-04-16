package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class CurrentWeatherDto(
    @field:Json(name = "weather")
    val weatherData: List<WeatherDataDto>,

    @field:Json(name = "main")
    val main: WeatherMainDto,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "cod")
    val cod: Int
)
