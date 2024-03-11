package com.apx8.mongoose.data.repository

import android.annotation.SuppressLint
import com.apx8.mongoose.data.mappers.toWeatherInfo
import com.apx8.mongoose.data.remote.WeatherApi
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.util.Resource
import com.apx8.mongoose.domain.weather.WeatherInfo
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