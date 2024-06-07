package com.apx8.mongoose.data.mappers

import com.apx8.mongoose.data.remote.CurrentResponseDto
import com.apx8.mongoose.data.remote.ForecastResponseDto
import com.apx8.mongoose.data.util.convertUTCtoLocalFormat
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastListInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

const val weatherIconBaseURL = "https://openweathermap.org/img/wn"

/**
 * 데이터 변환 [CurrentResponseDto] to [CurrentWeatherInfo]
 */
fun CurrentResponseDto.toCurrentWeatherInfo(): CurrentWeatherInfo {
    val weatherData = this.weatherData.first()
    return CurrentWeatherInfo(
        weatherId = weatherData.id,
        weatherMain = weatherData.main,
        weatherDescription = weatherData.description,
        temp = this.main.temp.roundToInt(),
        humidity = this.main.humidity,
        feelsLike = this.main.feelsLike.roundToInt(),
        weatherIcon = "$weatherIconBaseURL/${weatherData.icon}.png",
        cityName = this.name,
        cod = this.cod
    )
}

/**
 * 데이터 변환 [ForecastResponseDto] to [ForecastWeatherInfo]
 *
 * @Desc 시간변환 문제
 * UTC 시간을 Local시간으로 변환하여 제공한다 (+9시간)
 * ForecastListInfo내에는 일자와 시간을 분리하여 저장한다
 */
suspend fun ForecastResponseDto.toForecastWeatherInfo(
    defaultDispatcher: CoroutineDispatcher
): ForecastWeatherInfo {
    val forecastDto = this
    return withContext(defaultDispatcher) {
        val forecast = forecastDto.list.map { fd ->
            val weatherData = fd.weather.first()

            /* UTC to Local*/
            val localDateTime = fd.dt * 1000
            val localDtTxt = localDateTime.convertUTCtoLocalFormat()

            /**
             * Split `Date` and `Time`
             * @Date : '2020-01-01'
             * @Time : '15:00:00'
             */
            val localDtTxtArr = localDtTxt.split(" ")
            val (localDtTxtDate, localDtTxtTime) = localDtTxtArr.first() to localDtTxtArr.last()

            ForecastListInfo(
                dt = localDateTime,
                temp = fd.main.temp.roundToInt(),
                dtTxtDate = localDtTxtDate,
                dtTxtTime = localDtTxtTime,
                weatherId = weatherData.id,
                weatherDescription = weatherData.description,
                weatherMain = weatherData.main,
                weatherIcon = "$weatherIconBaseURL/${weatherData.icon}.png",
            )
        }

        ForecastWeatherInfo(
            forecastList = forecast
        )
    }
}