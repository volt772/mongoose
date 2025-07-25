package com.apx8.mongoose.domain.repository

import com.apx8.mongoose.domain.dto.AllWeatherInfo
import com.apx8.mongoose.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getAllWeatherInfo(
        lat: Double,
        lon: Double,
        stadiumCode: String
    ): Flow<Resource<AllWeatherInfo>>
}