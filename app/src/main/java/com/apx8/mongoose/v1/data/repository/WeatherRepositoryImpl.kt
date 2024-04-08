package com.apx8.mongoose.v1.data.repository

import com.apx8.mongoose.v1.data.mappers.toWeatherInfo
import com.apx8.mongoose.v1.data.remote.WeatherApi
import com.apx8.mongoose.v1.domain.repository.WeatherRepository
import com.apx8.mongoose.v1.domain.util.Resource
import com.apx8.mongoose.v1.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message?: "An Error Occurred")
        }
    }
}