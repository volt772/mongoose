package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class WeatherDataDto(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "main")
    val main: String,

    @field:Json(name = "description")
    val description: String,

    @field:Json(name = "icon")
    val icon: String
)