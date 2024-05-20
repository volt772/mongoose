package com.apx8.mongoose.view.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.R

@Composable
fun WeatherStatusMidItem(
    weatherMain: String,        // Clouds ..
    dayTemperature: Int,           // 15
    nightTemperature: Int,           // 15
    date: String,               // 4월 17일
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = date,
            fontWeight = FontWeight.W400,
            fontSize = 18.sp,
            color = Color.White,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Image(
            painterResource(id = R.drawable.ic_team_ncd_symbol),
            contentDescription = null,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$dayTemperature°C",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = Color.White,
            )
            Text(
                text = " | ",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = Color.White,
            )
            Text(
                text = "$nightTemperature°C",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
fun PreviewWeatherStatusMidItem() {
    WeatherStatusMidItem(
        weatherMain = "Clouds",
        dayTemperature = 30,
        nightTemperature = 25,
        date = "4월 17일"
    )
}
