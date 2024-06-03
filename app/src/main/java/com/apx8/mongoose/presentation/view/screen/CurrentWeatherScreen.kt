package com.apx8.mongoose.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.presentation.ext.getWeatherType
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.ui.theme.MgFontWhite
import com.apx8.mongoose.presentation.ui.theme.MgSubBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite

@Composable
fun CurrentWeatherScreen(
    info: CurrentWeatherInfo,
    currentStadium: Stadium,
    doSelectStadium: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        BottomSheet(
            currentStadium = currentStadium,
            doSelectStadium = doSelectStadium
        ) {
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
        /* 설명*/
        Text(
            text = info.weatherDescription,
            fontWeight = FontWeight.W400,
            fontSize = 30.sp,
            color = MgFontWhite
        )
        Spacer(modifier = modifier.height(20.dp))

        /* 아이콘*/
        Image(
            painterResource(id = weatherType.mainRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = modifier.height(30.dp))
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
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            /* 경기장이름*/
            Text(
                text = currentStadium.signBoard,
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
fun BottomSheet(
    currentStadium: Stadium,
    doSelectStadium: (String) -> Unit,
    onDismiss: () -> Unit
) {

    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        val stadiums = mutableListOf<BottomSheetStadium>().also { list ->
            Stadium.entries.forEach { entry ->
                if (entry != Stadium.NAN) {
                    list.add(
                        BottomSheetStadium(
                            color = entry.teamColor,
                            name = entry.signBoard,
                            code = entry.code
                        )
                    )
                }
            }
        }

        LazyColumn {
            items(stadiums) { stadium ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            doSelectStadium.invoke(stadium.code)
                            onDismiss.invoke()
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /* 경기장 색상 아이콘*/
                        Image(
                            painterResource(id = stadium.color),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                                .padding(end = 8.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                        )
                        /* 경기장 사인보드(이름)*/
                        Text(text = stadium.name)
                    }

                    /* 선택된 경기장*/
                    if (currentStadium.code == stadium.code) {
                        Image(
                            painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            modifier = Modifier.size(23.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * 경기장 List Item Data
 */
data class BottomSheetStadium(
    val color: Int,
    val name: String,
    val code: String,
)


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

    CurrentWeatherScreen(
        info = state,
        currentStadium = Stadium.SOJ,
        doSelectStadium = {},
    )
}
