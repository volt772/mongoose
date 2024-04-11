package com.apx8.mongoose.v1.data.mappers

import com.apx8.mongoose.v1.data.remote.CurrentWeatherDto
import com.apx8.mongoose.v1.data.remote.WeatherDataDto
import com.apx8.mongoose.v1.data.remote.WeatherDto
import com.apx8.mongoose.v1.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.v1.domain.weather.WeatherData
import com.apx8.mongoose.v1.domain.weather.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]

        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = com.apx8.mongoose.v1.domain.weather.WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}

fun CurrentWeatherDto.toCurrentWeatherInfo(): CurrentWeatherInfo {
    val weatherData = this.weatherData.first()
    return CurrentWeatherInfo(
        id = weatherData.id,
        main = weatherData.main,
        description = weatherData.description,
        icon = "https://openweathermap.org/img/wn/${weatherData.icon}.png",
        name = this.name,
        cod = this.cod
    )
}