package com.apx8.mongoose.data.repository

import com.apx8.mongoose.data.mappers.toCurrentWeatherInfo
import com.apx8.mongoose.data.mappers.toForecastWeatherInfo
import com.apx8.mongoose.data.remote.WeatherApi
import com.apx8.mongoose.di.DefaultDispatcher
import com.apx8.mongoose.domain.dto.AllWeatherInfo
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getAllWeatherInfo(
        lat: Double,
        lon: Double,
        stadiumCode: String
    ): Flow<Resource<AllWeatherInfo>> {
        return try {
            val weather = api.getAllWeatherData(
                lat = lat,
                lon = lon,
                stadiumCode = stadiumCode
            )

            val weatherInfo = AllWeatherInfo(
                currentWeatherInfo = weather.current.toCurrentWeatherInfo(),
                forecastWeatherInfo = weather.forecast.toForecastWeatherInfo(defaultDispatcher)
            )

            flowOf(Resource.Success(weatherInfo))
        } catch (e: Exception) {
            flowOf(Resource.Failed(e.message?: "Error Occurred"))
        }
    }
}