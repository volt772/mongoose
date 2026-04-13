package com.apx8.mongoose.domain.constants

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.apx8.mongoose.R
import com.apx8.mongoose.presentation.model.WeatherUiColors
import com.apx8.mongoose.presentation.ui.theme.MgAtmosphere
import com.apx8.mongoose.presentation.ui.theme.MgClear
import com.apx8.mongoose.presentation.ui.theme.MgClouds
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue
import com.apx8.mongoose.presentation.ui.theme.MgDrizzle
import com.apx8.mongoose.presentation.ui.theme.MgRain
import com.apx8.mongoose.presentation.ui.theme.MgSnow
import com.apx8.mongoose.presentation.ui.theme.MgThunderStorm
import com.apx8.mongoose.presentation.ui.theme.MgWhite

sealed class WeatherType(
    val code: Int,
    @DrawableRes val mainRes: Int,
    @DrawableRes val subRes: Int,
    val colors: WeatherUiColors
) {

    /* 200 ThunderStorm*/
    data object Thunderstorm: WeatherType(
        code = 2,
        mainRes = R.drawable.ic_thunder,
        subRes = R.drawable.ic_weather_thunderstorm,
        colors = WeatherUiColors(
            background = MgDarkBlue,
            content = MgWhite,
            secondary = MgWhite
        )
    )

    /* 300 Drizzle*/
    data object Drizzle : WeatherType(
        code = 3,
        mainRes = R.drawable.ic_drizzle,
        subRes = R.drawable.ic_weather_drizzle,
        colors = WeatherUiColors(
            background = MgDarkBlue,
            content = MgWhite,
            secondary = MgWhite
        )
    )

    /* 500 Rain*/
    data object Rain : WeatherType(
        code = 5,
        mainRes = R.drawable.ic_rainy,
        subRes = R.drawable.ic_weather_rain,
        colors = WeatherUiColors(
            background = MgDarkBlue,
            content = MgWhite,
            secondary = MgWhite
        )
    )

    /* 600 Snow*/
    data object Snow: WeatherType(
        code = 6,
        mainRes = R.drawable.ic_snowy,
        subRes = R.drawable.ic_weather_snow,
        colors = WeatherUiColors(
            background = MgDarkBlue,
            content = MgWhite,
            secondary = MgWhite
        )
    )

    /* 700 Atmosphere*/
    data object Atmosphere: WeatherType(
        code = 7,
        mainRes = R.drawable.ic_cloudy,
        subRes = R.drawable.ic_weather_default,
        colors = WeatherUiColors(
            background = MgDarkBlue,
            content = MgWhite,
            secondary = MgWhite
        )
    )

    /* 800 Clear*/
    data object Clear: WeatherType(
        code = 8,
        mainRes = R.drawable.ic_sunny,
        subRes = R.drawable.ic_weather_clear,
        colors = WeatherUiColors(
            background = MgDarkBlue,
            content = MgWhite,
            secondary = MgWhite
        )
    )

    /* 800 Clouds*/
    data object Clouds : WeatherType(
        code = 80,
        mainRes = R.drawable.ic_cloudy,
        subRes = R.drawable.ic_weather_clouds,
        colors = WeatherUiColors(
            background = MgDarkBlue,
            content = MgWhite,
            secondary = MgWhite
        )
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