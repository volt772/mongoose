package com.apx8.mongoose.view.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.v1.presentation.ui.theme.ErrorGray

@Composable
fun ForecastWeatherScreen(
    state: ForecastWeatherInfo,
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

        Row(
            modifier = modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(6.dp))
                .background(Color.Red),
//                .border(width = 5.dp, color = Color.Blue, shape = RectangleShape),

            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painterResource(id = R.drawable.ic_team_dsb_symbol),
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
                    painterResource(id = R.drawable.ic_team_dsb_symbol),
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

        Row(

        ) {

        }

    }
}