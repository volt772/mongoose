package com.apx8.mongoose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.constants.WeatherTemperatureType
import com.apx8.mongoose.dto.WeatherTemperature
import com.apx8.mongoose.v1.presentation.ui.theme.DeepBlue
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    modifier: Modifier = Modifier
) {

    state.weatherInfo?.let { weatherInfo ->
        Card(
            colors = CardDefaults.cardColors(containerColor = weatherInfo.currentWeatherData?.weatherType?.bgColor?: DeepBlue),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            weatherInfo.currentWeatherData?.let { currentData ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Today ${
                            currentData.time.format(
                                DateTimeFormatter.ofPattern("HH:mm")
                            )
                        }",
                        modifier = Modifier.align(Alignment.End),
                        color = Color.Black
                    )

                    Spacer(Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = currentData.weatherType.iconRes),
                        contentDescription = null,
                        modifier = Modifier.width(200.dp)
                    )

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "${currentData.temperatureCelsius}°C",
                        fontSize = 50.sp,
                        color = Color.Black,
                    )

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = currentData.weatherType.weatherDesc,
                        fontSize = 20.sp,
                        color = Color.Black,
                    )

                    Spacer(Modifier.height(32.dp))
                    /* <-- Row -->*/
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherDataDisplay(
                            value = currentData.pressure.roundToInt(),
                            unit = "hpa",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                            iconTint = Color.Black,
                            textStyle = TextStyle(color = Color.Black)
                        )
                        WeatherDataDisplay(
                            value = currentData.humidity.roundToInt(),
                            unit = "%",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                            iconTint = Color.Black,
                            textStyle = TextStyle(color = Color.Black)
                        )
                        WeatherDataDisplay(
                            value = currentData.windSpeed.roundToInt(),
                            unit = "km/h",
                            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                            iconTint = Color.Black,
                            textStyle = TextStyle(color = Color.Black)
                        )
                    }
                }
            }

            weatherInfo.weatherDataPerDay[0]?.let { perDay ->
                val perDayList = mutableListOf<WeatherTemperature>().also { list ->
                    perDay.forEach { pd ->
                        val temperatureType = WeatherTemperatureType.fromHour(pd.time.hour)
                        if (temperatureType == WeatherTemperatureType.None) {
                            return@forEach
                        }

                        val temperatureDto = WeatherTemperature(
                            type = WeatherTemperatureType.fromHour(pd.time.hour),
                            temp = "${pd.temperatureCelsius.roundToInt()}°C"
                        )

                        list.add(temperatureDto)
                    }
                }

            /* <-- Row -->*/
                Card(
                    modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.5f))
                            .padding(vertical = 16.dp, horizontal = 12.dp)
                    ) {
                        Text(
                            text = "기온",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W400
                            )
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            perDayList.forEach { pd ->
                                WeatherTemperatureDisplay(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    temperature = pd,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun WeatherCardPreview() {
//    MongooseTheme {
//        WeatherCard()
//    }
//}
