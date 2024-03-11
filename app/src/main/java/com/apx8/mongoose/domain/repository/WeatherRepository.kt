package com.apx8.mongoose.domain.repository

import com.apx8.mongoose.domain.util.Resource
import com.apx8.mongoose.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}