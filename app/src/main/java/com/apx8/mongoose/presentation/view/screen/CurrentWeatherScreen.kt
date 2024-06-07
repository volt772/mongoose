package com.apx8.mongoose.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.presentation.ext.getWeatherConditionCodes
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.ui.theme.MgFontWhite
import com.apx8.mongoose.presentation.ui.theme.MgSubBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import com.apx8.mongoose.presentation.view.bottomsheet.StadiumBottomSheet

@Composable
fun CurrentWeatherScreen(
    info: CurrentWeatherInfo,
    currentStadium: Stadium,
    doSelectStadium: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    /**
     * BottomSheet 상태
     * @desc show and hide
     */
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        StadiumBottomSheet(
            currentStadium = currentStadium,
            doSelectStadium = doSelectStadium
        ) {
            showSheet = false
        }
    }

    /**
     * WeatherType
     * @return WeatherType
     */
    val weatherType = info.weatherId.getWeatherConditionCodes()

    /**
     * @box Root
     */
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MgBlue)
            .padding(horizontal = 20.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(20.dp))
        /**
         * @view 설명
         * @example 맑음
         */
        Text(
            text = info.weatherDescription,
            fontWeight = FontWeight.W400,
            fontSize = 30.sp,
            color = MgFontWhite
        )
        Spacer(modifier = modifier.height(20.dp))

        /**
         * @view 아이콘
         */
        Image(
            painterResource(id = weatherType.mainRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = modifier.height(30.dp))

        /**
         * @Box Root
         */
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /**
             * @Box 기온(숫자만)
             * @example 27
             */
            Text(
                text = "${info.temp}",
                fontWeight = FontWeight.W400,
                fontSize = 50.sp,
                color = MgFontWhite
            )
            Spacer(modifier = modifier.width(5.dp))

            /**
             * @Box 단위레이블
             * @example °C
             */
            Text(
                text = "°C",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = MgFontWhite,
                modifier = modifier.align(Alignment.CenterVertically)
            )
        }

        /**
         * @box Root
         */
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            /**
             * @view 경기장이름
             * @example 서울종합운동장야구장
             */
            Text(
                text = currentStadium.signBoard,
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = MgFontWhite
            )
            Spacer(modifier = modifier.height(10.dp))

            /**
             * @view 다른구장보기
             */
            Button(
                colors = ButtonColors(
                    containerColor = MgSubBlue,
                    contentColor = MgWhite,
                    disabledContainerColor = MgSubBlue,
                    disabledContentColor = MgWhite,
                ),
                onClick = { showSheet = true }
            ) {
                Text(text = "다른 구장 보기")
            }
        }
    }
}

@Preview
@Composable
fun PreviewCurrentWeatherScreen() {
    val state = CurrentWeatherInfo(
        weatherId=800,
        weatherMain="Clear",
        weatherDescription="맑음",
        temp=27,
        humidity=42,
        feelsLike=27,
        weatherIcon="https://openweathermap.org/img/wn/01d.png",
        cityName="Seoul",
        cod=200
    )

    CurrentWeatherScreen(
        info = state,
        currentStadium = Stadium.SOJ,
        doSelectStadium = {},
    )
}
