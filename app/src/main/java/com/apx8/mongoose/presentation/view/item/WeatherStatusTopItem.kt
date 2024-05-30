package com.apx8.mongoose.view.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.apx8.mongoose.constants.PlayTimeType

@Composable
fun WeatherStatusTopItem(
    weatherMain: String,        // Clouds ..
    temperature: Int,           // 15
    playTime: PlayTimeType,     // PlayTimeType.DAY
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.ic_team_ncd_symbol),
            contentDescription = null,
        )
        Text(
            text = "$temperature°C",
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            color = Color.White,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
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
        weatherMain = "Clouds",
        temperature = 15,
        playTime = PlayTimeType.DAY
    )
}
