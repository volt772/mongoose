package com.apx8.mongoose.presentation.view.display

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.domain.dto.ForecastListInfo
import com.apx8.mongoose.presentation.ext.getWeatherConditionCodes
import com.apx8.mongoose.presentation.ui.theme.MgSubDarkBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import com.apx8.mongoose.presentation.ui.theme.MgYellowTransparent

@Composable
fun ForecastTodayDisplay(
    infoList: List<ForecastListInfo>,
    modifier: Modifier = Modifier
) {

    if (infoList.isNotEmpty()) {
        /**
         * @box Root
         */
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            /**
             * @view 섹션 레이블 (오늘날씨)
             */
            Text(
                text = "오늘날씨",
                fontSize = 20.sp,
                color = MgYellowTransparent,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))

            /**
             * @list 오늘날씨 아이템
             */
            LazyRow {
                items(infoList.size) {
                    TodayWeatherItem(index = it, infoList = infoList)
                }
            }
        }
    }
}

@Composable
fun TodayWeatherItem(index: Int, infoList: List<ForecastListInfo>) {
    val paddingStart = if (index == 0) 0.dp else 8.dp
    val info = infoList[index]

    /**
     * @box Root
     */
    Box(
        modifier = Modifier
            .padding(start = paddingStart)
    ) {
        /**
         * @box Root
         */
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MgSubDarkBlue)
                .size(130.dp)
                .clickable {}
                .padding(13.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val weatherType = info.weatherId.getWeatherConditionCodes()
            val temperature = info.temp
            val description = info.weatherDescription
            val time = info.dtTxtTime

            /**
             * @view 아이콘
             */
            Image(
                painterResource(id = weatherType.mainRes),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))

            /**
             * @view 시간 : 기온
             * @example 12시 : 22°C
             */
            Text(
                text = "${time.substring(0, time.length - 6)}시 : ${temperature}°C",
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = MgWhite,
            )
            Spacer(modifier = Modifier.height(3.dp))

            /**
             * @view 설명
             * @example 실 비
             */
            Text(
                text = description,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = MgWhite,
            )
        }
    }
}

@Preview
@Composable
fun PreviewTodayWeatherItem() {
    TodayWeatherItem(
        index = 0,
        infoList = previewInfoList,
    )
}

@Preview
@Composable
fun PreviewTodayDisplay() {
    ForecastTodayDisplay(
        infoList = previewInfoList
    )
}

val previewInfoList = mutableListOf<ForecastListInfo>().also { list ->
    list.add(ForecastListInfo(dt=1717210800000, temp=22, dtTxtDate="2024-06-01", dtTxtTime = "12:00:00", weatherId=500, weatherDescription = "실 비", weatherMain="Rain", weatherIcon="https://openweathermap.org/img/wn/10d.png"))
    list.add(ForecastListInfo(dt=1717221600000, temp=24, dtTxtDate="2024-06-01", dtTxtTime = "15:00:00", weatherId=802, weatherDescription = "실 비", weatherMain="Clouds", weatherIcon="https://openweathermap.org/img/wn/03d.png)"))
    list.add(ForecastListInfo(dt=1717232400000, temp=24, dtTxtDate="2024-06-01", dtTxtTime = "18:00:00", weatherId=800, weatherDescription = "실 비", weatherMain="Clear", weatherIcon="https://openweathermap.org/img/wn/01d.png)"))
    list.add(ForecastListInfo(dt=1717243200000, temp=21, dtTxtDate="2024-06-01", dtTxtTime = "21:00:00", weatherId=800, weatherDescription = "실 비", weatherMain="Clear", weatherIcon="https://openweathermap.org/img/wn/01n.png)"))
}
