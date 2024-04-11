package com.apx8.mongoose.v1.presentation.constants

sealed class WeatherTemperatureType(
    val index: Int,
    val label: String
) {
    data object Morning: WeatherTemperatureType(6, "오전")
    data object Afternoon: WeatherTemperatureType(12, "오후")
    data object Evening: WeatherTemperatureType(18, "저녁")
    data object Night: WeatherTemperatureType(23, "야간")
    data object None: WeatherTemperatureType(0, "")

    companion object {
        fun fromHour(hour: Int): WeatherTemperatureType {
            return when(hour) {
                Morning.index -> Morning
                Afternoon.index -> Afternoon
                Evening.index -> Evening
                Night.index -> Night
                else -> None
            }
        }
    }
}
