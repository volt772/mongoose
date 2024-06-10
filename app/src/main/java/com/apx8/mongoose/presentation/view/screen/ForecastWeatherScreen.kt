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
    var isEmptyList by remember { mutableStateOf(false) }
    isEmptyList = info.forecastList.isEmpty()

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
        /**
         * 조건 : info.forecastList, 즉 List<ForecastListInfo>가 비어있는가
         * True : `오늘날씨`, `주간예보` 표시
         * False : `에러박스` 표시
         */
        if (isEmptyList) {
            ForecastErrorDisplay()
        } else {
            /**
             * 날씨정보 그룹핑
             * @desc 일자정보를 키로 지정하여 Map형태로 그룹핑
             * @example list[2024-05-01] = Data[~~~]
             */
            val forecastByDay = info.forecastList.groupBy { fl ->
                fl.dtTxtDate
            }

            /**
             * 일자 리스트
             * @desc [오늘, 내일, 모레, 글피]
             * @example [2024-05-01, 2024-05-02, 2024-05-03, 2024-05-04]
             */
            val days = getDateAfter2DaysWithToday()

            /**
             * 조건 : days의 사이즈가 4인가 (반드시, [오늘, 내일, 모레, 글피]가 나와야 함)
             * True : `오늘날씨` 박스 표시
             * False : `에러박스` 표시
             */
            if (days.size == 4) {
                /**
                 * 오늘날씨 (View Rendering)
                 * @desc forecastByDay에서 정리한 데이터를 가지고 오늘날씨 랜더링
                 */
                forecastByDay[days.first()]?.let { fbd ->
                    ForecastTodayDisplay(fbd, modifier)
                }

                /**
                 * 오늘 제외한 경기장 날씨
                 * @desc forecastByDay에서 정리한 `오늘을 제외한` 나머지 날씨
                 * @desc 오늘날씨는 리스트 인덱스 0으로 판단한다.
                 */
                val dayAfterForecast = mutableListOf<WeatherDisplayItem>().also { list ->
                    days.forEachIndexed { index, day ->
                        if (index > 0) {
                            forecastByDay[day]?.let { fbd ->
                                list.add(forecastInfoToDisplayItem(fbd))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                /**
                 * 예보날씨 (View Rendering)
                 * @desc 익일 ~ 3일간 날씨 예보(낮경기, 저녁경기)
                 */
                if (dayAfterForecast.isNotEmpty()) {
                    ForecastDayAfterDisplay(items = dayAfterForecast, modifier = modifier)
                } else {
                    ForecastErrorDisplay()
                }
            } else {
                ForecastErrorDisplay()
            }
        }
    }
}

/**
 * 객체변환
 * @desc ForecastListInfo -> WeatherDisplayItem
 */
private fun forecastInfoToDisplayItem(
    forecast: List<ForecastListInfo>
): WeatherDisplayItem {
    /**
     * 유효게임확인
     * @desc 낮경기(15시기준), 저녁경기(18시기준) 날씨로 판단
     */
    val validGame = getValidGame(forecast)

    val dayGame = validGame.first()
    val nightGame = validGame.last()

    return WeatherDisplayItem(
        date = dayGame.dtTxtDate.getDateToDay(),
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

/**
 * 유효게임확인
 */
private fun getValidGame(
    forecast: List<ForecastListInfo>
): List<ForecastListInfo> {
    return forecast.filter { game ->
        val hour = game.dtTxtTime.getDateTo24Hour()
        (hour == 15 || hour == 18)
    }
}
