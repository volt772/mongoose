package com.apx8.mongoose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.view.display.DayAfterTomorrowDisplay
import com.apx8.mongoose.view.display.TodayDisplay

@Composable
fun ForecastWeatherScreen(
    forecastState: ForecastWeatherInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /* 오늘 날씨 예보(낮경기, 저녁경기)*/
        TodayDisplay(forecastState = forecastState, modifier = modifier)

        /* 익일 ~ 3일간 날씨 예보(낮경기, 저녁경기)*/
        DayAfterTomorrowDisplay(state = forecastState, modifier = modifier)

//        Row(
//            modifier = modifier
//                .height(IntrinsicSize.Min)
//                .fillMaxWidth()
//                .clip(shape = RoundedCornerShape(6.dp))
//                .background(Color.Red),
////                .border(width = 5.dp, color = Color.Blue, shape = RectangleShape),
//
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Image(
//                    painterResource(id = R.drawable.ic_team_dsb_symbol),
//                    contentDescription = null,
////                    colorFilter = ColorFilter.tint(ErrorGray)
//                )
//                Text(
//                    text = "20°C",
//                    fontWeight = FontWeight.W400,
//                    fontSize = 20.sp,
//                    color = Color.White,
//                    modifier = modifier.align(Alignment.CenterHorizontally)
//                )
//                Text(
//                    text = "낮경기",
//                    fontWeight = FontWeight.W400,
//                    fontSize = 18.sp,
//                    color = Color.White,
//                    modifier = modifier.align(Alignment.CenterHorizontally)
//                )
//            }
//            Divider(
//                color = Color.White,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(2.dp)
//            )
//            Column(
//            ) {
//                Text("4월 16일")
//            }
//            Divider(
//                color = Color.White,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(2.dp)
//            )
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painterResource(id = R.drawable.ic_team_kat_symbol),
//                    contentDescription = null,
////                    colorFilter = ColorFilter.tint(ErrorGray)
//                )
//                Text(
//                    text = "20°C",
//                    fontWeight = FontWeight.W400,
//                    fontSize = 20.sp,
//                    color = Color.White,
//                    modifier = modifier.align(Alignment.CenterHorizontally)
//                )
//                Text(
//                    text = "저녁경기",
//                    fontWeight = FontWeight.W400,
//                    fontSize = 18.sp,
//                    color = Color.White,
//                    modifier = modifier.align(Alignment.CenterHorizontally)
//                )
//            }
//        }


    }
}