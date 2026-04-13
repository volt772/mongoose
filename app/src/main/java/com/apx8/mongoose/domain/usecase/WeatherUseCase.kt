package com.apx8.mongoose.domain.usecase

import com.apx8.mongoose.domain.dto.AllWeatherInfo
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.util.Resource
import kotlinx.coroutines.flow.Flow

data class WeatherUseCase(
    val getAllWeatherInfo: GetAllWeatherInfo,
    val getCurrentWeatherInfo: GetCurrentWeatherInfo,
    val getForecastWeatherInfo: GetForecastWeatherInfo
)

class GetAllWeatherInfo(private val repository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double, stadiumCode: String): Flow<Resource<AllWeatherInfo>> {
        return repository.getAllWeatherInfo(lat = lat, lon = lon, stadiumCode = stadiumCode)
    }
}

/**
 * Not Use @26.04.13
 */
class GetCurrentWeatherInfo(private val repository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double, appId: String): Flow<Resource<CurrentWeatherInfo>> {
        return repository.getCurrentWeatherInfo(lat, lon, appId)
    }
}

/**
 * Not Use @26.04.13
 */
class GetForecastWeatherInfo(private val repository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double, appId: String): Flow<Resource<ForecastWeatherInfo>> {
        return repository.getForecastWeatherInfo(lat, lon, appId)
    }
}