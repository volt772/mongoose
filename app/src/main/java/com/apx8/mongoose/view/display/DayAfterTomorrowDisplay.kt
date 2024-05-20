package com.apx8.mongoose.view.display

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.dto.ForecastListInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.ext.getDateAfter2DaysWithTomorrow
import com.apx8.mongoose.ext.getDateTo24Hour
import com.apx8.mongoose.ext.getDateToDay
import com.apx8.mongoose.view.item.WeatherStatusMidItem

@Composable
fun DayAfterTomorrowDisplay(
    info: ForecastWeatherInfo,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(6.dp))
            .background(Color.Magenta),
//                .border(width = 5.dp, color = Color.Blue, shape = RectangleShape),

        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        /* 일자별 Grouping (ex. `2024-05-20` = [])*/
        val forecastByDay = info.forecastList.groupBy {
            val spd = it.dtTxt.split(" ")
            spd.first()
        }

        /* 일자 생성 (내일, 모래, 글피)*/
        val days = getDateAfter2DaysWithTomorrow()
        days.forEach { day ->
            val forecast = forecastByDay[day]
            forecast?.let { f ->
                /* 리스트필터 : 낮경기 (15시기준), 저녁경기 (18시기준)*/
                val validGameTime = f.filter { game ->
                    val hour = game.dtTxt.getDateTo24Hour()
                    (hour == 15 || hour == 18)
                }

                /* Item Composition*/
                WeatherStatusMidItem(
                    weatherMain = validGameTime.first().weatherMain,        // `낮경기` 기준으로 표시
                    dayTemperature = validGameTime.first().temp,            // `낮경기` 기준으로 표시
                    nightTemperature = validGameTime.last().temp,           // `저녁경기` 기준으로 표시
                    date = validGameTime.first().dtTxt.getDateToDay()       // `낮경기` 기준으로 표시
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDayAfterTomorrowDisplay() {
    val forecastWeatherInfo = ForecastWeatherInfo(
        cityName = "Gwacheon",
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
    DayAfterTomorrowDisplay(
        info = forecastWeatherInfo
    )
}