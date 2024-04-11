package com.apx8.mongoose.domain.dto

data class CurrentWeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
    val name: String,
    val cod: Int
)
