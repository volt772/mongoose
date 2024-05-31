package com.apx8.mongoose.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.presentation.ext.getWeatherType
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.ui.theme.MgButton
import com.apx8.mongoose.presentation.ui.theme.MgFontWhite
import com.apx8.mongoose.presentation.view.routes.Routes

@Composable
fun CurrentWeatherScreen(
    info: CurrentWeatherInfo,
    stadium: Stadium,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    /**
     * WeatherType
     * @return WeatherType
     */
    val weatherType = info.weatherId.getWeatherType()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MgBlue)
            .padding(20.dp, 60.dp, 20.dp, 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(20.dp))
        Image(
            painterResource(id = weatherType.mainRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(200.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${info.temp}",
                fontWeight = FontWeight.W400,
                fontSize = 50.sp,
                color = MgFontWhite
            )
            Spacer(modifier = modifier.width(5.dp))
            Text(
                text = "°C",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = MgFontWhite,
                modifier = modifier.align(Alignment.CenterVertically)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stadium.signBoard,
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = MgFontWhite
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                "다른 구장 보기",
                modifier = modifier
                    .drawBehind {
                        drawRoundRect(
                            MgButton,
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    }
                    .padding(12.dp, 6.dp)
                    .clickable {
                        println("probe :: navcontroller : $navController")

//                        navController.navigate(Routes.Stadium.route)
//                    navController.navigate("StadiumListScreen")

                        println("probe :: main :: this hamster!!")
                    },
            )
        }
    }
}


//@Composable
//fun YourComposable() {
//    var show by remember {
//        mutableStateOf(false)
//    }
//    if (show) {
//        BottomSheetDialog(
//            onDismissRequest = {
//                show = false
//            },
//            properties = BottomSheetDialogProperties(
//                ...
//        )
//        ) {
//            // content
//            Surface {
//                ...
//            }
//        }
//    }
//}


@Preview
@Composable
fun PreviewCurrentWeatherScreen() {
    val state = CurrentWeatherInfo(
        weatherId=800,
        weatherMain="Clear",
        weatherDescription="clear sky",
        temp=27,
        humidity=42,
        feelsLike=27,
        weatherIcon="https://openweathermap.org/img/wn/01d.png",
        cityName="Seoul",
        cod=200
    )

    CurrentWeatherScreen(
        info = state,
        stadium = Stadium.SOJ
    )
}
