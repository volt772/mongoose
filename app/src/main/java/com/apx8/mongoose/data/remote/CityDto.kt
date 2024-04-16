package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class CityDto(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "coord")
    val coord: CoordDto
)
