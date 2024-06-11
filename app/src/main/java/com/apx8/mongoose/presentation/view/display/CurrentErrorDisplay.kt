package com.apx8.mongoose.presentation.view.display

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.R
import com.apx8.mongoose.presentation.ui.theme.MgYellowTransparent

@Composable
fun CurrentErrorDisplay() {
    /**
     * @box Root
     */
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /**
         * @view 아이콘
         */
        /**
         * @view 아이콘
         */
        Image(
            painterResource(id = R.drawable.ic_error_cloud),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        /**
         * @view 안내문구
         */

        /**
         * @view 안내문구
         */
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "데이터 제공사의 상황에 의해 조회할 수 없습니다. 잠시 후 다시 사용해 주시기 바랍니다.",
            fontSize = 16.sp,
            color = MgYellowTransparent,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewCurrentErrorDisplay() {
    CurrentErrorDisplay(
    )
}