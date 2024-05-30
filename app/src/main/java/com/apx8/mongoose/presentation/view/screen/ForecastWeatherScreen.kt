package com.apx8.mongoose.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.dto.ForecastListInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.dto.WeatherDisplayItem
import com.apx8.mongoose.presentation.ext.getDateAfter2DaysWithToday
import com.apx8.mongoose.presentation.ext.getDateTo24Hour
import com.apx8.mongoose.presentation.ext.getDateToDay
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.ui.theme.MgMainBlue
import com.apx8.mongoose.presentation.ui.theme.MgRed
import com.apx8.mongoose.presentation.view.display.DayAfterTomorrowDisplay
import com.apx8.mongoose.presentation.view.display.TodayDisplay

@Composable
fun ForecastWeatherScreen(
    info: ForecastWeatherInfo,
    modifier: Modifier = Modifier
) {
    println("probe :: info : $info")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MgBlue)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val forecastByDay = info.forecastList.groupBy {
            val spd = it.dtTxt.split(" ")
            spd.first()
        }

        /* DateList [오늘, 내일, 모레, 글피]*/
        val days = getDateAfter2DaysWithToday()

        /* DataSet : 오늘 경기장 날씨*/
        val todayForecast = forecastByDay[days.first()]?.let { fbd ->
            getTodayForecast(fbd)
        }

        /* DataSet : 오늘 제외한 경기장 날씨*/
        val dayAfterForecast = mutableListOf<WeatherDisplayItem>().also { list ->
            days.forEachIndexed { index, day ->
                if (index > 0) {
                    forecastByDay[day]?.let { fbd ->
                        list.add(getTodayForecast(fbd))
                    }
                }
            }
        }

        /* Layout Inflate : 오늘 날씨 예보(낮경기, 저녁경기)*/
        todayForecast?.let {
            TodayDisplay(item = todayForecast, modifier = modifier)
        }?: run {
            // 로딩실패
        }

        Spacer(modifier = Modifier.height(20.dp))

        /* Layout Inflate : 익일 ~ 3일간 날씨 예보(낮경기, 저녁경기)*/
        if (dayAfterForecast.isNotEmpty()) {
            DayAfterTomorrowDisplay(items = dayAfterForecast, modifier = modifier)
        } else {
            // 로딩실패
        }
    }
}

private fun getValidGame(forecast: List<ForecastListInfo>): List<ForecastListInfo> {
    return forecast.filter { game ->
        val hour = game.dtTxt.getDateTo24Hour()
        (hour == 15 || hour == 18)
    }
}

private fun getTodayForecast(forecast: List<ForecastListInfo>): WeatherDisplayItem {
    val validGame = getValidGame(forecast)

    val dayGame = validGame.first()
    val nightGame = validGame.last()

    return WeatherDisplayItem(
        date = dayGame.dtTxt.getDateToDay(),
        dWeather = dayGame.weatherMain,
        nWeather = nightGame.weatherMain,
        dWeatherId = dayGame.weatherId,
        nWeatherId = nightGame.weatherId,
        dWeatherIcon = dayGame.weatherIcon,
        nWeatherIcon = nightGame.weatherIcon,
        dTemp = dayGame.temp,
        nTemp = nightGame.temp

    )
}