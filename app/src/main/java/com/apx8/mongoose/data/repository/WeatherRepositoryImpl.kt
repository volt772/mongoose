package com.apx8.mongoose.data.repository

import com.apx8.mongoose.data.local.dao.CurrentWeatherDao
import com.apx8.mongoose.data.local.dao.ForecastWeatherDao
import com.apx8.mongoose.data.local.dao.WeatherDownloadLogDao
import com.apx8.mongoose.data.local.entity.CurrentWeather
import com.apx8.mongoose.data.local.entity.ForecastWeather
import com.apx8.mongoose.data.local.entity.WeatherDownloadLog
import com.apx8.mongoose.data.mappers.toCurrentWeatherInfo
import com.apx8.mongoose.data.mappers.toForecastWeatherInfo
import com.apx8.mongoose.data.remote.CurrentResponseDto
import com.apx8.mongoose.data.remote.ForecastResponseDto
import com.apx8.mongoose.data.remote.WeatherApi
import com.apx8.mongoose.data.remote.WeatherDirectApi
import com.apx8.mongoose.data.util.currentHourlyTimestamp
import com.apx8.mongoose.di.DefaultDispatcher
import com.apx8.mongoose.domain.dto.AllWeatherInfo
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.util.Resource
import com.apx8.mongoose.presentation.MongooseApp.Companion.apiKey
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val api: WeatherApi,
    private val directApi: WeatherDirectApi,
    private val weatherDownloadLogDao: WeatherDownloadLogDao,
    private val currentWeatherDao: CurrentWeatherDao,
    private val forecastWeatherDao: ForecastWeatherDao,
): WeatherRepository {

    override suspend fun getAllWeatherInfo(
        lat: Double,
        lon: Double,
        stadiumCode: String
    ): Flow<Resource<AllWeatherInfo>> = flow {

        val moshi = Moshi.Builder().build()
        val currentAdapter = moshi.adapter(CurrentResponseDto::class.java)
        val forecastAdapter = moshi.adapter(ForecastResponseDto::class.java)

        val league = "kbo"
        val updatedAt = currentHourlyTimestamp()

        // 1. 캐시 존재 여부 확인
        val isCached = weatherDownloadLogDao.isDownloadedExact(stadiumCode, league, updatedAt) > 0

        if (isCached) {
            /* 현재날씨*/
            val current = currentWeatherDao.getLatest(stadiumCode)
                ?.weatherJson
                ?.let { currentAdapter.fromJson(it) }

            /* 주간예보*/
            val forecast = forecastWeatherDao.getLatest(stadiumCode)
                ?.weatherJson
                ?.let { forecastAdapter.fromJson(it) }

            if (current != null && forecast != null) {
                emit(Resource.Success(
                    /* 데이터 반환*/
                    AllWeatherInfo(
                        currentWeatherInfo = current.toCurrentWeatherInfo(),
                        forecastWeatherInfo = forecast.toForecastWeatherInfo(defaultDispatcher)
                    )
                ))
            } else {
                emit(Resource.Failed("CAHE DATA INVALID : NO API, NO DB"))

                /* 최후의 방법 : Direct로 쏘기*/
                try {
                    coroutineScope {
                        val currentDeferred = async {
                            getCurrentWeatherInfo(lat, lon, apiKey).first()
                        }
                        val forecastDeferred = async {
                            getForecastWeatherInfo(lat, lon, apiKey).first()
                        }

                        val current = currentDeferred.await()
                        val forecast = forecastDeferred.await()

                        if (current is Resource.Success && forecast is Resource.Success) {
                            emit(
                                Resource.Success(
                                    AllWeatherInfo(
                                        currentWeatherInfo = current.data,
                                        forecastWeatherInfo = forecast.data
                                    )
                                )
                            )
                        } else {
                            emit(Resource.Failed("OPENWEATHER 데이터 수신 실패"))
                        }
                    }
                } catch (e: Exception) {
                    emit(Resource.Failed("OPENWEATHER 호출 실패: ${e.message}"))
                }
            }
        } else {
            try {
                // 2. 캐시 MISS → 서버 호출
                val weather = api.getAllWeatherData(lat, lon, stadiumCode)

                // → DB에 저장 (download_log, current, forecast)
                /* 현재날씨*/
                val currentEntity = CurrentWeather(
                    stadiumCode = stadiumCode,
                    league = league,
                    weatherJson = currentAdapter.toJson(weather.current),
                    updatedAt = updatedAt
                )
                /* 주간예보*/
                val forecastEntity = ForecastWeather(
                    stadiumCode = stadiumCode,
                    league = league,
                    weatherJson = forecastAdapter.toJson(weather.forecast),
                    updatedAt = updatedAt
                )
                /* 다운로드 기록*/
                val log = WeatherDownloadLog(
                    stadiumCode = stadiumCode,
                    league = league,
                    updatedAt = updatedAt
                )

                // 삽입 (replace 트랜잭션 포함)
                currentWeatherDao.replace(stadiumCode, league, currentEntity)
                forecastWeatherDao.replace(stadiumCode, league, forecastEntity)
                weatherDownloadLogDao.insert(log)

                emit(Resource.Success(
                    AllWeatherInfo(
                        currentWeatherInfo = weather.current.toCurrentWeatherInfo(),
                        forecastWeatherInfo = weather.forecast.toForecastWeatherInfo(defaultDispatcher)
                    )
                ))
            } catch (e: Exception) {
                emit(Resource.Failed(e.message ?: "STORMBEAVER 요청 실패"))
            }
        }
    }

    /**
     * DIRECT FETCH : 현재날씨
     */
    override suspend fun getCurrentWeatherInfo(
        lat: Double,
        lon: Double,
        appId: String
    ): Flow<Resource<CurrentWeatherInfo>> {
        return try {
            val currentWeatherInfo = directApi.getCurrentWeatherData(
                lat = lat,
                lon = lon,
                appId = appId
            ).toCurrentWeatherInfo()

            flowOf(Resource.Success(currentWeatherInfo))
        } catch (e: Exception) {
            flowOf(Resource.Failed(e.message?: "Error Occurred"))
        }
    }

    /**
     * DIRCT FETCH : 예보날씨
     */
    override suspend fun getForecastWeatherInfo(
        lat: Double,
        lon: Double,
        appId: String
    ): Flow<Resource<ForecastWeatherInfo>> {
        return try {
            val forecastWeatherInfo = directApi.getForecastWeatherData(
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
