package com.apx8.mongoose.domain.dto

data class CurrentWeatherInfo(
    val weatherId: Int,                 // 날씨 ID
    val weatherMain: String,            // 날씨 간단 설명 (Clear 등)
    val weatherDescription: String,     // 날씨 상세 설명
    val temp: Int,                      // 기온 (°C)
    val humidity: Int,                  // 습도 (%)
    val feelsLike: Int,                 // 체감온도 (°C)
    val weatherIcon: String,            // 아이콘 Url
    val cityName: String,               // 지역이름
    val cod: Int                        // 코드
)

/**
 * ex)
 * CurrentWeatherInfo(
 * 		id=721,
 * 		main=Haze,
 * 		description=haze,
 * 		temp=16,
 * 	    humidity=58,
 * 		feelsLike=15,
 * 		icon=https://openweathermap.org/img/wn/50d.png,
 * 		name=Gwacheon,
 * 		cod=200
 * )
 */