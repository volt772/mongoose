package com.apx8.mongoose.domain.constants

import androidx.annotation.DrawableRes
import com.apx8.mongoose.R
import com.apx8.mongoose.presentation.model.WeatherUiColors
import com.apx8.mongoose.presentation.ui.theme.AxBlue200
import com.apx8.mongoose.presentation.ui.theme.AxBlue50
import com.apx8.mongoose.presentation.ui.theme.AxBlue500
import com.apx8.mongoose.presentation.ui.theme.AxBlueGray500
import com.apx8.mongoose.presentation.ui.theme.AxCyan500
import com.apx8.mongoose.presentation.ui.theme.AxGray100
import com.apx8.mongoose.presentation.ui.theme.AxGray300
import com.apx8.mongoose.presentation.ui.theme.AxGray400
import com.apx8.mongoose.presentation.ui.theme.AxGray50
import com.apx8.mongoose.presentation.ui.theme.AxIndigo700
import com.apx8.mongoose.presentation.ui.theme.AxIndigo900
import com.apx8.mongoose.presentation.ui.theme.AxSky200
import com.apx8.mongoose.presentation.ui.theme.AxSky300
import com.apx8.mongoose.presentation.ui.theme.AxSlate800
import com.apx8.mongoose.presentation.ui.theme.AxWhite
import com.apx8.mongoose.presentation.ui.theme.AxYellow700

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
            background = AxIndigo900,
            content = AxYellow700,
            secondary = AxIndigo700
        )
    )

    /* 300 Drizzle*/
    data object Drizzle : WeatherType(
        code = 3,
        mainRes = R.drawable.ic_drizzle,
        subRes = R.drawable.ic_weather_drizzle,
        colors = WeatherUiColors(
            background = AxBlueGray500,
            content = AxWhite,
            secondary = AxBlue200
        )
    )

    /* 500 Rain*/
    data object Rain : WeatherType(
        code = 5,
        mainRes = R.drawable.ic_rainy,
        subRes = R.drawable.ic_weather_rain,
        colors = WeatherUiColors(
            background = AxSlate800,
            content = AxSky200,
            secondary = AxBlue500
        )
    )

    /* 600 Snow*/
    data object Snow: WeatherType(
        code = 6,
        mainRes = R.drawable.ic_snowy,
        subRes = R.drawable.ic_weather_snow,
        colors = WeatherUiColors(
            background = AxBlue50,
            content = AxSlate800,
            secondary = AxSky300
        )
    )

    /* 700 Atmosphere*/
    data object Atmosphere: WeatherType(
        code = 7,
        mainRes = R.drawable.ic_cloudy,
        subRes = R.drawable.ic_weather_default,
        colors = WeatherUiColors(
            background = AxGray400,
            content = AxGray50,
            secondary = AxGray300
        )
    )

    /* 800 Clear*/
    data object Clear: WeatherType(
        code = 8,
        mainRes = R.drawable.ic_sunny,
        subRes = R.drawable.ic_weather_clear,
        colors = WeatherUiColors(
            background = AxSky300,
            content = AxWhite,
            secondary = AxCyan500
        )
    )

    /* 800 Clouds*/
    data object Clouds : WeatherType(
        code = 80,
        mainRes = R.drawable.ic_cloudy,
        subRes = R.drawable.ic_weather_clouds,
        colors = WeatherUiColors(
            background = AxGray300,
            content = AxSlate800,
            secondary = AxGray100
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