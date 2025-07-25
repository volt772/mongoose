package com.apx8.mongoose.data.util

import android.annotation.SuppressLint
import com.apx8.mongoose.data.local.entity.CurrentWeather
import com.apx8.mongoose.data.remote.CurrentResponseDto
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.squareup.moshi.Json
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.Date


/**
 * Date Convert
 * @desc Millis to String
 */
@SuppressLint("SimpleDateFormat")
fun Long?.convertUTCtoLocalFormat(): String {
    val formed = this?.let { millis ->
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.format(Date(millis))
    } ?: ""

    return formed
}

fun currentHourlyTimestamp(): Long {
    return ZonedDateTime.now()
        .withMinute(0)
        .withSecond(0)
        .withNano(0)
        .toInstant()
        .toEpochMilli()
}