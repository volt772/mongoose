package com.apx8.mongoose.data.remote

import com.squareup.moshi.Json

data class WeatherMainDto(
    @field:Json(name = "temp")
    val temp: Double,

    @field:Json(name = "feels_like")
    val feelsLike: Double,

    @field:Json(name = "humidity")
    val humidity: Int,

    @field:Json(name = "temp_min")
    val tempMin: Double,

    @field:Json(name = "temp_max")
    val tempMax: Double
)

/**
 * {
 *         "temp": 23.9,
 *         "feels_like": 23.34,
 *         "temp_min": 23.9,
 *         "temp_max": 23.9,
 *         "pressure": 1012,
 *         "humidity": 38,
 *         "sea_level": 1012,
 *         "grnd_level": 1010
 *     },
 */