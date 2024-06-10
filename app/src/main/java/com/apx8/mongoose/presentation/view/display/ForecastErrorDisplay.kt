package com.apx8.mongoose.presentation.view.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.presentation.ui.theme.MgSubDarkBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite

@Composable
fun ForecastErrorDisplay(
) {
    /**
     * @box Root
     */
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MgSubDarkBlue),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        /**
         * @view 안내 레이블
         */
        Text(
            text = "일시적인 에러로 불러올 수 없습니다.\n 잠시후 다시 시도해 주세요.",
            fontWeight = FontWeight.W400,
            fontSize = 17.sp,
            color = MgWhite,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewForecastErrorDisplay() {
    ForecastErrorDisplay(
    )
}