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

//private data class IndexedWeatherData(
//    val index: Int,
//    val data: WeatherData
//)

//fun WeatherDataDto2.toWeatherDataMap(): Map<Int, List<WeatherData>> {
//    return time.mapIndexed { index, time ->
//        val temperature = temperatures[index]
//        val weatherCode = weatherCodes[index]
//        val windSpeed = windSpeeds[index]
//        val pressure = pressures[index]
//        val humidity = humidities[index]
//
//        IndexedWeatherData(
//            index = index,
//            data = WeatherData(
//                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
//                temperatureCelsius = temperature,
//                pressure = pressure,
//                windSpeed = windSpeed,
//                humidity = humidity,
//                weatherType = WeatherType.fromWMO(weatherCode)
//            )
//        )
//    }.groupBy {
//        it.index / 24
//    }.mapValues {
//        it.value.map { it.data }
//    }
//}

//fun WeatherDto.toWeatherInfo(): WeatherInfo {
//    val weatherDataMap = weatherData.toWeatherDataMap()
//    val now = LocalDateTime.now()
//    val currentWeatherData = weatherDataMap[0]?.find {
//        val hour = if(now.minute < 30) now.hour else now.hour + 1
//        it.time.hour == hour
//    }
//    return WeatherInfo(
//        weatherDataPerDay = weatherDataMap,
//        currentWeatherData = currentWeatherData
//    )
//}

fun CurrentResponseDto.toCurrentWeatherInfo(): CurrentWeatherInfo {
    val weatherData = this.weatherData.first()
    return CurrentWeatherInfo(
        weatherId = weatherData.id,
        weatherMain = weatherData.main,
        weatherDescription = weatherData.description,
        temp = this.main.temp.roundToInt(),
        humidity = this.main.humidity,
        feelsLike = this.main.feelsLike.roundToInt(),
        weatherIcon = "https://openweathermap.org/img/wn/${weatherData.icon}.png",
        cityName = this.name,
        cod = this.cod
    )
}

suspend fun ForecastResponseDto.toForecastWeatherInfo(
    defaultDispatcher: CoroutineDispatcher
): ForecastWeatherInfo {
    val forecastDto = this
    return withContext(defaultDispatcher) {
        val forecast = forecastDto.list.map {
            val weatherData = it.weather.first()

            /* UTC to Local*/
            val localDateTime = it.dt * 1000
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
                temp = it.main.temp.roundToInt(),
                dtTxtDate = localDtTxtDate,
                dtTxtTime = localDtTxtTime,
                weatherId = weatherData.id,
                weatherDescription = weatherData.description,
                weatherMain = weatherData.main,
                weatherIcon = "https://openweathermap.org/img/wn/${weatherData.icon}.png",
            )
        }

        ForecastWeatherInfo(
            forecastList = forecast
        )
    }
}

