package com.apx8.mongoose.view.display

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo

@Composable
fun TodayDisplay(
    forecastState: ForecastWeatherInfo,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(6.dp))
            .background(Color.Red),
//                .border(width = 5.dp, color = Color.Blue, shape = RectangleShape),

        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // 오늘 객체만 추출 (ForecastListInfo)
        // 15시 경기는 낮경기로
        // 18시 경기는 밤경기로


//        val aa = forecastState.forecastList.filter {
//            it.dtTxt
//        }
//        WeatherTypeA(
//
////                weatherMain: String, // Clouds ..
////            temperature: Int,   // 15
////            playTime: PlayTimeType, //
//        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(id = R.drawable.ic_team_ncd_symbol),
                contentDescription = null,
//                    colorFilter = ColorFilter.tint(ErrorGray)
            )
            Text(
                text = "20°C",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = Color.White,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "낮경기",
                fontWeight = FontWeight.W400,
                fontSize = 18.sp,
                color = Color.White,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }
        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
        )
        Column(
        ) {
            Text("4월 16일")
        }
        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.ic_team_ltg_symbol),
                contentDescription = null,
//                    colorFilter = ColorFilter.tint(ErrorGray)
            )
            Text(
                text = "20°C",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = Color.White,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "저녁경기",
                fontWeight = FontWeight.W400,
                fontSize = 18.sp,
                color = Color.White,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}