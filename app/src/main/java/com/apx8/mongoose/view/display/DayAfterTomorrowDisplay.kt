package com.apx8.mongoose.view.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo

@Composable
fun DayAfterTomorrowDisplay(
    state: ForecastWeatherInfo,
    modifier: Modifier = Modifier
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

    }
}