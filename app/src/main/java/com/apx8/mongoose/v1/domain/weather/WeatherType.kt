package com.apx8.mongoose.v1.domain.weather

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.apx8.mongoose.R
import com.apx8.mongoose.v1.presentation.ui.theme.BgCloudy
import com.apx8.mongoose.v1.presentation.ui.theme.BgHeavySnow
import com.apx8.mongoose.v1.presentation.ui.theme.BgRainShower
import com.apx8.mongoose.v1.presentation.ui.theme.BgRainy
import com.apx8.mongoose.v1.presentation.ui.theme.BgRainyThunder
import com.apx8.mongoose.v1.presentation.ui.theme.BgSnowy
import com.apx8.mongoose.v1.presentation.ui.theme.BgSnowyRainy
import com.apx8.mongoose.v1.presentation.ui.theme.BgSunny
import com.apx8.mongoose.v1.presentation.ui.theme.BgThunder
import com.apx8.mongoose.v1.presentation.ui.theme.BgVeryCloudy

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int,
    @ColorRes val bgColor: Color
) {
    data object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.ic_sunny,
        bgColor = BgSunny
    )
    data object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.ic_cloudy,
        bgColor = BgCloudy
    )
    data object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.ic_cloudy,
        bgColor = BgCloudy
    )
    data object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconRes = R.drawable.ic_cloudy,
        bgColor = BgCloudy
    )
    data object Foggy : WeatherType(
        weatherDesc = "Foggy",
        iconRes = R.drawable.ic_very_cloudy,
        bgColor = BgVeryCloudy
    )
    data object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing rime fog",
        iconRes = R.drawable.ic_very_cloudy,
        bgColor = BgVeryCloudy
    )
    data object LightDrizzle : WeatherType(
        weatherDesc = "Light drizzle",
        iconRes = R.drawable.ic_rainshower,
        bgColor = BgRainShower
    )
    data object ModerateDrizzle : WeatherType(
        weatherDesc = "Moderate drizzle",
        iconRes = R.drawable.ic_rainshower,
        bgColor = BgRainShower
    )
    data object DenseDrizzle : WeatherType(
        weatherDesc = "Dense drizzle",
        iconRes = R.drawable.ic_rainshower,
        bgColor = BgRainShower
    )
    data object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Slight freezing drizzle",
        iconRes = R.drawable.ic_snowyrainy,
        bgColor = BgSnowyRainy
    )
    data object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Dense freezing drizzle",
        iconRes = R.drawable.ic_snowyrainy,
        bgColor = BgRainShower
    )
    data object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
        iconRes = R.drawable.ic_rainy,
        bgColor = BgRainy
    )
    data object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.ic_rainy,
        bgColor = BgRainy
    )
    data object HeavyRain : WeatherType(
        weatherDesc = "Heavy rain",
        iconRes = R.drawable.ic_rainy,
        bgColor = BgRainy
    )
    data object HeavyFreezingRain: WeatherType(
        weatherDesc = "Heavy freezing rain",
        iconRes = R.drawable.ic_snowyrainy,
        bgColor = BgSnowyRainy
    )
    data object SlightSnowFall: WeatherType(
        weatherDesc = "Slight snow fall",
        iconRes = R.drawable.ic_snowy,
        bgColor = BgSnowy
    )
    data object ModerateSnowFall: WeatherType(
        weatherDesc = "Moderate snow fall",
        iconRes = R.drawable.ic_heavysnow,
        bgColor = BgHeavySnow
    )
    data object HeavySnowFall: WeatherType(
        weatherDesc = "Heavy snow fall",
        iconRes = R.drawable.ic_heavysnow,
        bgColor = BgHeavySnow
    )
    data object SnowGrains: WeatherType(
        weatherDesc = "Snow grains",
        iconRes = R.drawable.ic_heavysnow,
        bgColor = BgHeavySnow
    )
    data object SlightRainShowers: WeatherType(
        weatherDesc = "Slight rain showers",
        iconRes = R.drawable.ic_rainshower,
        bgColor = BgRainShower
    )
    data object ModerateRainShowers: WeatherType(
        weatherDesc = "Moderate rain showers",
        iconRes = R.drawable.ic_rainshower,
        bgColor = BgRainShower
    )
    data object ViolentRainShowers: WeatherType(
        weatherDesc = "Violent rain showers",
        iconRes = R.drawable.ic_rainshower,
        bgColor = BgRainShower
    )
    data object SlightSnowShowers: WeatherType(
        weatherDesc = "Light snow showers",
        iconRes = R.drawable.ic_snowy,
        bgColor = BgSnowy
    )
    data object HeavySnowShowers: WeatherType(
        weatherDesc = "Heavy snow showers",
        iconRes = R.drawable.ic_snowy,
        bgColor = BgSnowy
    )
    data object ModerateThunderstorm: WeatherType(
        weatherDesc = "Moderate thunderstorm",
        iconRes = R.drawable.ic_thunder,
        bgColor = BgThunder
    )
    data object SlightHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        iconRes = R.drawable.ic_rainythunder,
        bgColor = BgRainyThunder
    )
    data object HeavyHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        iconRes = R.drawable.ic_rainythunder,
        bgColor = BgRainyThunder
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}