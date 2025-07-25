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
import com.apx8.mongoose.data.util.currentHourlyTimestamp
import com.apx8.mongoose.di.DefaultDispatcher
import com.apx8.mongoose.domain.dto.AllWeatherInfo
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.util.Resource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val api: WeatherApi,
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
                emit(Resource.Failed("캐시 데이터가 유효하지 않습니다."))
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
                emit(Resource.Failed(e.message ?: "API 요청 실패"))
            }
        }
    }
}
