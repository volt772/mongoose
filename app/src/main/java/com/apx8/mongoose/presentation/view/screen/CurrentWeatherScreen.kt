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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.apx8.mongoose.presentation.ui.theme.MgRed
import com.apx8.mongoose.presentation.ui.theme.MgSubBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite

@Composable
fun CurrentWeatherScreen(
    info: CurrentWeatherInfo,
    stadium: Stadium,
    modifier: Modifier = Modifier,
    selectStadium: (String) -> Unit
) {

    val navController = rememberNavController()

    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        BottomSheet(selectStadium = selectStadium) {
            showSheet = false
        }
    }

    /**
     * WeatherType
     * @return WeatherType
     */
    val weatherType = info.weatherId.getWeatherType()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MgBlue)
            .padding(horizontal = 20.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(20.dp))
        /* 아이콘*/
        Image(
            painterResource(id = weatherType.mainRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(200.dp)
        )

        /* 기온*/
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /* 기온*/
            Text(
                text = "${info.temp}",
                fontWeight = FontWeight.W400,
                fontSize = 50.sp,
                color = MgFontWhite
            )
            Spacer(modifier = modifier.width(5.dp))
            /* 단위*/
            Text(
                text = "°C",
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = MgFontWhite,
                modifier = modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = modifier.width(10.dp))
            /* 구분선*/
            VerticalDivider(
                modifier = Modifier.height(30.dp),
                color = MgWhite
            )
            Spacer(modifier = modifier.width(10.dp))

            /* 설명*/
            Text(
                text = info.weatherDescription,
                fontWeight = FontWeight.W400,
                fontSize = 30.sp,
                color = MgFontWhite
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            /* 경기장이름*/
            Text(
                text = stadium.signBoard,
                fontWeight = FontWeight.W400,
                fontSize = 20.sp,
                color = MgFontWhite
            )
            Spacer(modifier = modifier.height(10.dp))

            /* 경기장변경*/
            Button(
                colors = ButtonColors(
                    containerColor = MgSubBlue,
                    contentColor = MgWhite,
                    disabledContainerColor = MgSubBlue,
                    disabledContentColor = MgWhite,
                ),
                onClick = { showSheet = true }
            ) {
                Text(text = "다른 구장 보기")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(selectStadium: (String)->Unit, onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
//        CountryList(onDismiss)

        val stadiums = mutableListOf<BottomSheetStadium>().also { list ->
            Stadium.entries.forEach { entry ->
                if (entry != Stadium.NAN) {
                    list.add(
                        BottomSheetStadium(
                            name = entry.signBoard,
                            flag = 1234,
                            code = entry.code
                        )
                    )
//                    list.add(entry.signBoard to "hhh")
                }
            }
        }


//        val countries = listOf(
//            Pair("United States", "\uD83C\uDDFA\uD83C\uDDF8"),
//            Pair("Canada", "\uD83C\uDDE8\uD83C\uDDE6"),
//            Pair("India", "\uD83C\uDDEE\uD83C\uDDF3"),
//            Pair("Germany", "\uD83C\uDDE9\uD83C\uDDEA"),
//            Pair("France", "\uD83C\uDDEB\uD83C\uDDF7"),
//            Pair("Japan", "\uD83C\uDDEF\uD83C\uDDF5"),
//            Pair("China", "\uD83C\uDDE8\uD83C\uDDF3"),
//            Pair("Brazil", "\uD83C\uDDE7\uD83C\uDDF7"),
//            Pair("Australia", "\uD83C\uDDE6\uD83C\uDDFA"),
//            Pair("Russia", "\uD83C\uDDF7\uD83C\uDDFA"),
//            Pair("United Kingdom", "\uD83C\uDDEC\uD83C\uDDE7"),
//        )
        LazyColumn {
            items(stadiums) { stadium ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .clickable {
                            selectStadium.invoke(stadium.code)
                            onDismiss.invoke()
                        }
                ) {
                    Text(
                        text = "${stadium.flag}",
                        modifier = Modifier.padding(end = 20.dp)
                    )
                    Text(text = stadium.name)
                }
            }
        }
    }
}

// 나중에 favorite 추가
data class BottomSheetStadium(
    val name: String,
    val flag: Int,
    val code: String,
)

@Composable
fun CountryList(onDismiss: () -> Unit) {
    val stadiums = mutableListOf<Pair<String, String>>().also { list ->
        Stadium.entries.forEach { entry ->
            if (entry != Stadium.NAN) {
                list.add(entry.signBoard to "hhh")
            }
        }
    }


    val countries = listOf(
        Pair("United States", "\uD83C\uDDFA\uD83C\uDDF8"),
        Pair("Canada", "\uD83C\uDDE8\uD83C\uDDE6"),
        Pair("India", "\uD83C\uDDEE\uD83C\uDDF3"),
        Pair("Germany", "\uD83C\uDDE9\uD83C\uDDEA"),
        Pair("France", "\uD83C\uDDEB\uD83C\uDDF7"),
        Pair("Japan", "\uD83C\uDDEF\uD83C\uDDF5"),
        Pair("China", "\uD83C\uDDE8\uD83C\uDDF3"),
        Pair("Brazil", "\uD83C\uDDE7\uD83C\uDDF7"),
        Pair("Australia", "\uD83C\uDDE6\uD83C\uDDFA"),
        Pair("Russia", "\uD83C\uDDF7\uD83C\uDDFA"),
        Pair("United Kingdom", "\uD83C\uDDEC\uD83C\uDDE7"),
    )
    LazyColumn {
        items(stadiums) { (signboard, flag) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .clickable {
                        onDismiss.invoke()
                    }
            ) {
                Text(
                    text = flag,
                    modifier = Modifier.padding(end = 20.dp)
                )
                Text(text = signboard)
            }
        }
    }

//    LazyColumn {
//        items(countries) { (country, flag) ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 10.dp, horizontal = 20.dp)
//            ) {
//                Text(
//                    text = flag,
//                    modifier = Modifier.padding(end = 20.dp)
//                )
//                Text(text = country)
//            }
//        }
//    }
}




@Preview
@Composable
fun PreviewCurrentWeatherScreen() {
    val state = CurrentWeatherInfo(
        weatherId=800,
        weatherMain="Clear",
        weatherDescription="맑음",
        temp=27,
        humidity=42,
        feelsLike=27,
        weatherIcon="https://openweathermap.org/img/wn/01d.png",
        cityName="Seoul",
        cod=200
    )

//    CurrentWeatherScreen(
//        info = state,
//        stadium = Stadium.SOJ
//    )
}
