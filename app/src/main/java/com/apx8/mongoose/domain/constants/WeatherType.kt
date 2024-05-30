package com.apx8.mongoose.domain.constants

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.apx8.mongoose.R
import com.apx8.mongoose.v1.presentation.ui.theme.MgAtmosphere
import com.apx8.mongoose.v1.presentation.ui.theme.MgClear
import com.apx8.mongoose.v1.presentation.ui.theme.MgClouds
import com.apx8.mongoose.v1.presentation.ui.theme.MgDrizzle
import com.apx8.mongoose.v1.presentation.ui.theme.MgRain
import com.apx8.mongoose.v1.presentation.ui.theme.MgSnow
import com.apx8.mongoose.v1.presentation.ui.theme.MgThunderStorm

sealed class WeatherType(
    val code: Int,
    @DrawableRes val mainRes: Int,
    @DrawableRes val subRes: Int,
    @ColorRes val color: Color
) {

    /* 200 ThunderStorm*/
    data object Thunderstorm: WeatherType(
        code = 2,
        mainRes = R.drawable.ic_thunder,
        subRes = R.drawable.ic_weather_thunderstorm,
        color = MgThunderStorm
    )

    /* 300 Drizzle*/
    data object Drizzle : WeatherType(
        code = 3,
        mainRes = R.drawable.ic_rainshower,
        subRes = R.drawable.ic_weather_drizzle,
        color = MgDrizzle
    )

    /* 500 Rain*/
    data object Rain : WeatherType(
        code = 5,
        mainRes = R.drawable.ic_rainy,
        subRes = R.drawable.ic_weather_rain,
        color = MgRain
    )

    /* 600 Snow*/
    data object Snow: WeatherType(
        code = 6,
        mainRes = R.drawable.ic_snowy,
        subRes = R.drawable.ic_weather_snow,
        color = MgSnow
    )

    /* 700 Atmosphere*/
    data object Atmosphere: WeatherType(
        code = 7,
        mainRes = R.drawable.ic_very_cloudy,
        subRes = R.drawable.ic_weather_default,
        color = MgAtmosphere
    )

    /* 800 Clear*/
    data object Clear: WeatherType(
        code = 80,
        mainRes = R.drawable.ic_sunny,
        subRes = R.drawable.ic_weather_clear,
        color = MgClear
    )

    /* 800 Clouds*/
    data object Clouds : WeatherType(
        code = 8,
        mainRes = R.drawable.ic_cloudy,
        subRes = R.drawable.ic_weather_clouds,
        color = MgClouds
    )

    companion object {
        fun from(code: Int): WeatherType {
            return when(code) {
                Thunderstorm.code -> Thunderstorm
                Drizzle.code -> Drizzle
                Rain.code -> Rain
                Snow.code -> Snow
                Atmosphere.code -> Atmosphere
                Clear.code -> Clear
                Clouds.code -> Clouds
                else -> Clear
            }
        }
    }
}