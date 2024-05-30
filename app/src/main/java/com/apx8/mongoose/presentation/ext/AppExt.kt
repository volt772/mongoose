package com.apx8.mongoose.presentation.ext

import com.apx8.mongoose.domain.constants.WeatherType

/**
 * WeatherType
 * @param weatherId
 * @return WeatherType
 */
fun Int.getWeatherType(): WeatherType {
    return (this / 100 to this % 100).let {
        if (it.first == 8 && it.second > 0) {
            WeatherType.from(WeatherType.Clouds.code)
        } else {
            WeatherType.from(it.first)
        }
    }
}
