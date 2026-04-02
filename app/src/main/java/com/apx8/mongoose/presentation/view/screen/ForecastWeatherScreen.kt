package com.apx8.mongoose.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.dto.ForecastListInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.dto.WeatherDisplayItem
import com.apx8.mongoose.presentation.ext.getDateAfter2DaysWithToday
import com.apx8.mongoose.presentation.ext.getDateTo24Hour
import com.apx8.mongoose.presentation.ext.getDateToDay
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue
import com.apx8.mongoose.presentation.view.display.ForecastDayAfterDisplay
import com.apx8.mongoose.presentation.view.display.ForecastErrorDisplay
import com.apx8.mongoose.presentation.view.display.ForecastTodayDisplay

@Composable
fun ForecastWeatherScreen(
    info: ForecastWeatherInfo,
    modifier: Modifier = Modifier
) {

    /**
     * info.forecastList, 즉 List<ForecastListInfo>가 비어있는가
     */
//    var isEmptyList by remember { mutableStateOf(false) }
//    isEmptyList = info.forecastList.isEmpty()

    /**
     * @box Root
     */
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MgDarkBlue)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val forecastByDay = info.forecastList.groupBy { fl ->
            fl.dtTxtDate
        }

        val days = getDateAfter2DaysWithToday()

        forecastByDay[days.first()]?.let { fbd ->
            ForecastTodayDisplay(fbd, modifier)
        }
    }
}

///**
// * 객체변환
// * @desc ForecastListInfo -> WeatherDisplayItem
// */
//private fun forecastInfoToDisplayItem(
//    forecast: List<ForecastListInfo>
//): WeatherDisplayItem? {
//    /**
//     * 유효게임확인
//     * @desc 낮경기(15시기준), 저녁경기(18시기준) 날씨로 판단
//     */
//    val validGame = getValidGame(forecast)
//
//    return if (validGame.isEmpty()) {
//        null
//    } else {
//        val dayGame = validGame.first()
//        val nightGame = validGame.last()
//
//        WeatherDisplayItem(
//            date = dayGame.dtTxtDate.getDateToDay(),
//            dWeather = dayGame.weatherMain,
//            nWeather = nightGame.weatherMain,
//            dWeatherId = dayGame.weatherId,
//            nWeatherId = nightGame.weatherId,
//            dWeatherIcon = dayGame.weatherIcon,
//            nWeatherIcon = nightGame.weatherIcon,
//            dTemp = dayGame.temp,
//            nTemp = nightGame.temp
//        )
//    }
//}
//
///**
// * 유효게임확인
// */
//private fun getValidGame(
//    forecast: List<ForecastListInfo>
//): List<ForecastListInfo> {
//    return forecast.filter { game ->
//        val hour = game.dtTxtTime.getDateTo24Hour()
//        (hour == 15 || hour == 18)
//    }
//}
