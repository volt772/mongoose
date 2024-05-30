package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class ForecastResponseDto(
    @field:Json(name = "list")
    val list: List<ForecastListDto>,

    @field:Json(name = "cnt")
    val cnt: Int,

    @field:Json(name = "cod")
    val cod: Int
)
