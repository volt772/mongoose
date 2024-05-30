package com.apx8.mongoose.presentation.view.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.dto.ForecastListInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.dto.WeatherDisplayItem
import com.apx8.mongoose.presentation.ext.getWeatherType
import com.apx8.mongoose.presentation.ui.theme.MgSubBlue
import com.apx8.mongoose.presentation.view.item.WeatherStatusMidItem

@Composable
fun DayAfterTomorrowDisplay(
    items: List<WeatherDisplayItem>,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(6.dp))
            .background(MgSubBlue),

        horizontalArrangement = Arrangement.SpaceAround
    ) {

        items.forEach { item ->
            /* Item Composition*/
            WeatherStatusMidItem(
                weatherType = item.dWeatherId.getWeatherType(),        // 메인날씨 기준 : `낮경기` 기준으로 표시
                dayTemperature = item.dTemp,        // 낮경기 기온 : `낮경기` 기준으로 표시
                nightTemperature = item.nTemp,      // 저녁경기 기온 : `저녁경기` 기준으로 표시
                date = item.date                   // 일자 : `낮경기` 기준으로 표시
            )
        }
    }
}

@Preview
@Composable
fun PreviewDayAfterTomorrowDisplay() {
    val forecastWeatherInfo = ForecastWeatherInfo(
//        cityName = "Gwacheon",
        forecastList = listOf(
            ForecastListInfo(
                dt = 1716174000L,
                temp = 19,
                dtTxt = "2024-05-20 03:00:00",
                weatherId = 804,
                weatherMain = "Clouds",
                weatherIcon = "https://openweathermap.org/img/wn/04d.png"
            )
        )
    )
    val fList = mutableListOf<WeatherDisplayItem>().also { list ->
        list.add(WeatherDisplayItem(date="05월22일", dWeather="Clouds", nWeather="Clouds", dWeatherId = 800, nWeatherId = 200, dWeatherIcon="https://openweathermap.org/img/wn/04n.png", nWeatherIcon="https://openweathermap.org/img/wn/04n.png", dTemp=18, nTemp=17 ),)
        list.add(WeatherDisplayItem(date="05월23일", dWeather="Clouds", nWeather="Clear",  dWeatherId = 800, nWeatherId = 200, dWeatherIcon="https://openweathermap.org/img/wn/02n.png", nWeatherIcon="https://openweathermap.org/img/wn/04n.png", dTemp=18, nTemp=17 ),)
        list.add(WeatherDisplayItem(date="05월24일", dWeather="Clouds", nWeather="Clouds", dWeatherId = 800, nWeatherId = 200, dWeatherIcon="https://openweathermap.org/img/wn/04n.png", nWeatherIcon="https://openweathermap.org/img/wn/04n.png", dTemp=19, nTemp=19 ),)
    }
    DayAfterTomorrowDisplay(
        items = fList
    )
}