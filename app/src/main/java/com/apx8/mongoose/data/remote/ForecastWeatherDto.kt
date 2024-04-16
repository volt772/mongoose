package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class ForecastWeatherDto(
    @field:Json(name = "list")
    val list: List<ForecastListDto>,

    @field:Json(name = "city")
    val city: CityDto,

    @field:Json(name = "cnt")
    val cnt: Int,

    @field:Json(name = "cod")
    val cod: Int
)
