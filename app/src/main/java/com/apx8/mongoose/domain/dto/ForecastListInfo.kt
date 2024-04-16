package com.apx8.mongoose.domain.dto

data class ForecastListInfo(

    val dt: Long,

    val temp: Int,

    val dtTxt: String,

    val weatherId: Int,

    val weatherMain: String,

    val weatherIcon: String
)


/**
 * ex
 *
 * ForecastListInfo(
 * dt=1713290400,
 * temp=11,
 * dtTxt=2024-04-16 18:00:00,
 * weatherId=800,
 * weatherMain=Clear,
 * weatherIcon=https://openweathermap.org/img/wn/01n.png
 * )
 */

