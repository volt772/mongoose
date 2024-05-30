package com.apx8.mongoose.view.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.constants.PlayTimeType
import com.apx8.mongoose.dto.WeatherDisplayItem
import com.apx8.mongoose.ext.getDateToDay
import com.apx8.mongoose.v1.presentation.ui.theme.MgSubBlue
import com.apx8.mongoose.view.item.WeatherStatusTopItem

@Composable
fun TodayDisplay(
    item: WeatherDisplayItem,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(6.dp))
            .background(MgSubBlue),
//                .border(width = 5.dp, color = Color.Blue, shape = RectangleShape),

        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        /* 낮경기*/
        WeatherStatusTopItem(
            weatherMain = item.dWeather,
            temperature = item.dTemp,
            playTime = PlayTimeType.DAY
        )
        /* 구분자*/
        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
        )
        Column(
        ) {
            Text(item.date)
        }
        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
        )
        /* 저녁경기*/
        WeatherStatusTopItem(
            weatherMain = item.nWeather,
            temperature = item.nTemp,
            playTime = PlayTimeType.NIGHT
        )
    }
}

@Preview
@Composable
fun PreviewTodayDisplay() {
    val todayForecast = WeatherDisplayItem(
        date="2024-05-21 15:00:00".getDateToDay(),
        dWeather="Clear",
        nWeather="Clouds",
        dWeatherIcon="https://openweathermap.org/img/wn/01n.png",
        nWeatherIcon="https://openweathermap.org/img/wn/03n.png",
        dTemp=20,
        nTemp=18
    )
    TodayDisplay(
        item = todayForecast
    )
}
