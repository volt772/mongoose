package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class ForecastListDto(
    @field:Json(name = "dt")
    val dt: Long,

    @field:Json(name = "main")
    val main: WeatherMainDto,

    @field:Json(name = "weather")
    val weather: List<WeatherDto>,

    @field:Json(name = "dt_txt")
    val dtTxt: String,
)