package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class CoordDto(
    @field:Json(name = "lat")
    val lat: Double,

    @field:Json(name = "lon")
    val lon: Double
)
