package com.apx8.mongoose.dto

data class WeatherDisplayItem(
    val date: String,           // 5월 21일
    val dWeather: String,       // Clear
    val nWeather: String,       //Clouds
    val dWeatherIcon: String,   // https://openweathermap.org/img/wn/01n.png
    val nWeatherIcon: String,   // https://openweathermap.org/img/wn/03n.png
    val dTemp: Int,             // 20
    val nTemp: Int              // 18
)

