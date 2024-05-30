package com.apx8.mongoose.data.repository

import com.apx8.mongoose.data.mappers.toCurrentWeatherInfo
import com.apx8.mongoose.data.mappers.toForecastWeatherInfo
import com.apx8.mongoose.data.remote.WeatherApi
import com.apx8.mongoose.di.DefaultDispatcher
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
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
//    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
//        return try {
//            Resource.Success(
//                data = api.getWeatherData(
//                    lat = lat,
//                    long = long
//                ).toWeatherInfo()
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Resource.Error(e.message?: "An Error Occurred")
//        }
//    }

    override suspend fun getCurrentWeatherInfo(lat: Double, lon: Double, appId: String): Flow<Resource<CurrentWeatherInfo>> {
        return try {
            val currentWeatherInfo = api.getCurrentWeatherData(
                lat = lat,
                lon = lon,
                appId = appId
            ).toCurrentWeatherInfo()

            flowOf(Resource.Success(currentWeatherInfo))
        } catch (e: Exception) {
            flowOf(Resource.Failed(e.message?: "Error Occurred"))
        }
    }

    override suspend fun getForecastWeatherInfo(lat: Double, lon: Double, appId: String): Flow<Resource<ForecastWeatherInfo>> {
        return try {
            val forecastWeatherInfo = api.getForecastWeatherData(
                lat = lat,
                lon = lon,
                appId = appId
            ).toForecastWeatherInfo(defaultDispatcher)

            flowOf(Resource.Success(forecastWeatherInfo))
        } catch (e: Exception) {
            flowOf(Resource.Failed(e.message?: "Error Occurred"))
        }
    }
}