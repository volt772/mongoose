package com.apx8.mongoose.presentation.view.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.constants.WeatherType

@Composable
fun WeatherStatusMidItem(
    weatherType: WeatherType,        // WeatherType.. ..
    dayTemperature: Int,           // 15
    nightTemperature: Int,           // 15
    date: String,               // 4월 17일
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier.padding(vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /* 일자*/
        Text(
            text = date,
            fontWeight = FontWeight.W400,
            fontSize = 17.sp,
            color = Color.White,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        /* 아이콘*/
        Image(
            painterResource(id = weatherType.mainRes),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))

        /* 주|야 기온*/
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /* 낮기온*/
            Text(
                text = "$dayTemperature°C",
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color.White,
            )
            Text(
                text = " | ",
                fontWeight = FontWeight.W400,
                fontSize = 18.sp,
                color = Color.White,
            )
            /* 저녁기온*/
            Text(
                text = "$nightTemperature°C",
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
fun PreviewWeatherStatusMidItem() {
    WeatherStatusMidItem(
        weatherType = WeatherType.Rain,
        dayTemperature = 30,
        nightTemperature = 25,
        date = "04월17일"
    )
}
