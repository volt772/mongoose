package com.apx8.mongoose.domain.dto

data class ForecastListInfo(
    val dt: Long,
    val temp: Int,
    val dtTxtDate: String,
    val dtTxtTime: String,
    val weatherId: Int,
    val weatherDescription: String,
    val weatherMain: String,
    val weatherIcon: String
)


/**
 * ex
 *
 * ForecastListInfo(
 * dt=1713290400,
 * temp=11,
 * dtTxtDate=2024-04-16,
 * dtTxtTime=18:00:00,
 * weatherId=800,
 * weatherDescription="실 비",
 * weatherMain=Clear,
 * weatherIcon=https://openweathermap.org/img/wn/01n.png
 * )
 */

