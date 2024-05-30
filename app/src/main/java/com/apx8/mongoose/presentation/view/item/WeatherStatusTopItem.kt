package com.apx8.mongoose.presentation.view.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.apx8.mongoose.domain.constants.PlayTimeType
import com.apx8.mongoose.domain.constants.WeatherType

@Composable
fun WeatherStatusTopItem(
    weatherType: WeatherType,        // WeatherType.. ..
    temperature: Int,           // 15
    playTime: PlayTimeType,     // PlayTimeType.DAY
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(10.dp)
    ) {
        /* 아이콘*/
        Image(
            painterResource(id = weatherType.mainRes),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        /* 기온*/
        Text(
            text = "$temperature°C",
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            color = Color.White,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        /* 낮경기 or 저녁경기*/
        Text(
            text = playTime.desc,
            fontWeight = FontWeight.W400,
            fontSize = 18.sp,
            color = Color.White,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Composable
fun PreviewWeatherStatusTopItem() {
    WeatherStatusTopItem(
        weatherType = WeatherType.Clear,
        temperature = 15,
        playTime = PlayTimeType.DAY
    )
}
