package com.apx8.mongoose.data.repository

import com.apx8.mongoose.data.mappers.toCurrentWeatherInfo
import com.apx8.mongoose.data.mappers.toWeatherInfo
import com.apx8.mongoose.data.remote.WeatherApi
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.util.Resource
import com.apx8.mongoose.domain.util.Resource2
import com.apx8.mongoose.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

    override suspend fun getCurrentWeatherInfo(lat: Double, lon: Double, appId: String): Flow<Resource2<CurrentWeatherInfo>> {
        return try {
            val currentWeatherInfo = api.getCurrentWeatherData(
                lat = lat,
                lon = lon,
                appId = appId
            ).toCurrentWeatherInfo()

            flowOf(Resource2.Success(currentWeatherInfo))
        } catch (e: Exception) {
            flowOf(Resource2.Failed(e.message?: "Error Occurred"))
        }
    }
}