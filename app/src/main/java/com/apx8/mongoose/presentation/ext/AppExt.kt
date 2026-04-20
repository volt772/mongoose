package com.apx8.mongoose.presentation.ext

import com.apx8.mongoose.domain.constants.WeatherType

/**
 * 날씨 코드
 * @see https://openweathermap.org/weather-conditions
 * @desc 800번대는 800(맑음), 80x(구름) 구분해야 함.
 */
fun Int.getWeatherConditionCodes(): WeatherType {
    return (this / 100 to this % 100).let {
        if (it.first == 8 && it.second > 0) {
            /**
             * 801, 802 (0~50%) → 햇빛이 주도 → 맑음
             * 803, 804 (50%+) → 구름이 주도 → 흐림
             */
            if (it.second in listOf(1, 2)) {
                WeatherType.from(WeatherType.Clear.code)
            } else {
                WeatherType.from(WeatherType.Clouds.code)
            }
        } else {
            WeatherType.from(it.first)
        }
    }
}
