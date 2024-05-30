package com.apx8.mongoose.domain.repository

import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
//    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>

    suspend fun getCurrentWeatherInfo(lat: Double, lon: Double, appId: String): Flow<Resource<CurrentWeatherInfo>>

    suspend fun getForecastWeatherInfo(lat: Double, lon: Double, appId: String): Flow<Resource<ForecastWeatherInfo>>
}