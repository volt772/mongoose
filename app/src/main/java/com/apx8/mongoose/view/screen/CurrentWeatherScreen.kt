package com.apx8.mongoose.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo

@Composable
fun CurrentWeatherScreen(
    state: CurrentWeatherInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(20.dp))
        Image(
            painterResource(id = R.drawable.ic_error),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
        )

        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${state.temp}",
                fontWeight = FontWeight.W400,
                fontSize = 50.sp,
                color = Color.White
            )
            Spacer(modifier = modifier.width(5.dp))
            Text(
                text = "°C",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = Color.White,
                modifier = modifier.align(Alignment.CenterVertically)
            )

        }
        Text(
            text = "운동장",
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            "다른 구장 보기",
            modifier = modifier
                .drawBehind {
                    drawRoundRect(
                        Color(0xFFBBAAEE),
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
                .padding(12.dp, 6.dp)
                .clickable {
                    println("probe :: main :: this hamster!!")
                },
        )
//                                    println("probe :: main activity Current : ${state.data}")
    }

}