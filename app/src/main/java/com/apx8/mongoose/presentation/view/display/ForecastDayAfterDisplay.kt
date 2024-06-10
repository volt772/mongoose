package com.apx8.mongoose.presentation.view.display

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.domain.constants.WeatherType
import com.apx8.mongoose.domain.dto.WeatherDisplayItem
import com.apx8.mongoose.presentation.ext.getWeatherConditionCodes
import com.apx8.mongoose.presentation.ui.theme.MgBlueTransparent
import com.apx8.mongoose.presentation.ui.theme.MgSubDarkBlue

@Composable
fun ForecastDayAfterDisplay(
    items: List<WeatherDisplayItem>,
    modifier: Modifier = Modifier
) {

    /**
     * @box Root
     */
    Column {
        /**
         * @view 섹션 레이블 (주간예보)
         */
        Text(
            text = "주간예보",
            fontSize = 20.sp,
            color = MgBlueTransparent,
            fontWeight = FontWeight.W400,
        )
        Spacer(modifier = Modifier.height(5.dp))

        /**
         * @box Root
         */
        Row(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(MgSubDarkBlue),

            horizontalArrangement = Arrangement.SpaceAround
        ) {

            /**
             * @view 예보 아이템
             * @desc
             * weatherType      -> 메인날씨 기준 : `낮경기` 기준으로 표시
             * dayTemperature   -> 15시경기 기온
             * nightTemperature -> 18시경기 기온
             * date             -> `낮경기` 기준으로 표시
             */
            items.forEach { item ->
                DayAfterWeatherItem(
                    weatherType = item.dWeatherId.getWeatherConditionCodes(),
                    dayTemperature = item.dTemp,
                    nightTemperature = item.nTemp,
                    date = item.date
                )
            }
        }
    }
}

@Composable
fun DayAfterWeatherItem(
    weatherType: WeatherType,
    dayTemperature: Int,
    nightTemperature: Int,
    date: String,
    modifier: Modifier = Modifier
) {

    /**
     * @box Root
     */
    Column(
        modifier = Modifier.padding(vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /**
         * @view 일자
         * @example 5월 22일
         */
        Text(
            text = date,
            fontWeight = FontWeight.W400,
            fontSize = 17.sp,
            color = Color.White,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        /**
         * @view 날씨 아이콘
         */
        Image(
            painterResource(id = weatherType.mainRes),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))

        /**
         * @view 주간기온
         * @example 15시 : 18°C
         */
        Text(
            text = "15시 : $dayTemperature°C",
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            color = Color.White,
        )
        /**
         * @view 야간기온
         * @example 18시 : 17°C
         */
        Text(
            text = "18시 : $nightTemperature°C",
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            color = Color.White,
        )
    }
}

@Preview
@Composable
fun PreviewWeatherStatusMidItem() {
    DayAfterWeatherItem(
        weatherType = WeatherType.Rain,
        dayTemperature = 30,
        nightTemperature = 25,
        date = "04월17일"
    )
}

@Preview
@Composable
fun PreviewDayAfterTomorrowDisplay() {
    val fList = mutableListOf<WeatherDisplayItem>().also { list ->
        list.add(WeatherDisplayItem(date="05월22일", dWeather="Clouds", nWeather="Clouds", dWeatherId = 800, nWeatherId = 200, dWeatherIcon="https://openweathermap.org/img/wn/04n.png", nWeatherIcon="https://openweathermap.org/img/wn/04n.png", dTemp=18, nTemp=17 ),)
        list.add(WeatherDisplayItem(date="05월23일", dWeather="Clouds", nWeather="Clear",  dWeatherId = 800, nWeatherId = 200, dWeatherIcon="https://openweathermap.org/img/wn/02n.png", nWeatherIcon="https://openweathermap.org/img/wn/04n.png", dTemp=18, nTemp=17 ),)
        list.add(WeatherDisplayItem(date="05월24일", dWeather="Clouds", nWeather="Clouds", dWeatherId = 800, nWeatherId = 200, dWeatherIcon="https://openweathermap.org/img/wn/04n.png", nWeatherIcon="https://openweathermap.org/img/wn/04n.png", dTemp=19, nTemp=19 ),)
    }
    ForecastDayAfterDisplay(
        items = fList
    )
}