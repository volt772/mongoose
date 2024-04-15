package com.apx8.mongoose.domain.dto

data class CurrentWeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val temp: Int,  // 기온
    val feelsLike: Int,     // 체감온도
    val icon: String,
    val name: String,
    val cod: Int
)
