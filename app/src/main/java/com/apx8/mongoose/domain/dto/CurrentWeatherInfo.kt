package com.apx8.mongoose.domain.dto

data class CurrentWeatherInfo(
    val weatherId: Int = 800,                     // 날씨 ID
    val weatherMain: String = "Clear",            // 날씨 간단 설명 (Clear 등)
    val weatherDescription: String = "맑음",     // 날씨 상세 설명
    val temp: Int = 0,                           // 기온 (°C)
    val humidity: Int = 0,                       // 습도 (%)
    val feelsLike: Int = 0,                      // 체감온도 (°C)
    val weatherIcon: String = "https://openweathermap.org/img/wn/50d.png",            // 아이콘 Url
    val cityName: String = "Seoul",               // 지역이름
    val cod: Int = 400                            // 코드
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