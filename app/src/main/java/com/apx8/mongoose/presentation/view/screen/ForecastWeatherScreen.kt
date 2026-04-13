package com.apx8.mongoose.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.presentation.ext.getDateAfter2DaysWithToday
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue
import com.apx8.mongoose.presentation.view.display.ForecastTodayDisplay
import androidx.compose.ui.graphics.Color

@Composable
fun ForecastWeatherScreen(
    info: ForecastWeatherInfo,
    backgroundColor: Color,
    contentColor: Color,
    secondaryColor: Color,
    modifier: Modifier = Modifier
) {

    /**
     * @box Root
     */
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val forecastByDay = info.forecastList.groupBy { fl ->
            fl.dtTxtDate
        }

        val days = getDateAfter2DaysWithToday()

        forecastByDay[days.first()]?.let { fbd ->
            ForecastTodayDisplay(
                infoList = fbd,
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                secondaryColor = secondaryColor,
                modifier = modifier
            )
        }
    }
}
