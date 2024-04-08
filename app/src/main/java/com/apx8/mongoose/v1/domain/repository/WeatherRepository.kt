package com.apx8.mongoose.v1.domain.repository

import com.apx8.mongoose.v1.domain.util.Resource
import com.apx8.mongoose.v1.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}