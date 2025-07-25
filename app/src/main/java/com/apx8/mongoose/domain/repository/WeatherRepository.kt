package com.apx8.mongoose.domain.repository

import com.apx8.mongoose.domain.dto.AllWeatherInfo
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getAllWeatherInfo(
        lat: Double,
        lon: Double,
        stadiumCode: String
    ): Flow<Resource<AllWeatherInfo>>

    suspend fun getCurrentWeatherInfo(
        lat: Double,
        lon: Double,
        appId: String
    ): Flow<Resource<CurrentWeatherInfo>>

    suspend fun getForecastWeatherInfo(
        lat: Double,
        lon: Double,
        appId: String
    ): Flow<Resource<ForecastWeatherInfo>>

}