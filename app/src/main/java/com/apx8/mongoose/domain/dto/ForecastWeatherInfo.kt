package com.apx8.mongoose.domain.dto

data class ForecastWeatherInfo(
    val cityName: String,
    val forecastList: List<ForecastListInfo>
)

/**
 * ForecastWeatherInfo(
 * 	cityName=Gwacheon,
 * 	forecastList=[
 * 		ForecastListInfo(
 * 			dt=1713258000,
 * 			temp=16,
 * 			dtTxt=2024-04-16 09:00:00,
 * 			weatherId=802,
 * 			weatherMain=Clouds,
 * 			weatherIcon=https://openweathermap.org/img/wn/03d.png
 * 		)
 * 		...
 * 	]
 * )
 *
 */