package com.apx8.mongoose.presentation.view.display

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.R
import com.apx8.mongoose.presentation.ui.theme.MgWhite

@Composable
fun CurrentErrorDisplay(
    refresh: () -> Unit,
    contentColor: Color,
    secondaryColor: Color
) {
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
        Image(
            painterResource(id = R.drawable.ic_error_cloud),
            contentDescription = null,
            modifier = Modifier.size(180.dp),
            colorFilter = ColorFilter.tint(contentColor)
        )

        /**
         * @view 안내문구
         */
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = stringResource(id = R.string.load_data_error),
            fontSize = 16.sp,
            color = contentColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            colors = ButtonColors(
                containerColor = secondaryColor,
                contentColor = contentColor,
                disabledContainerColor = secondaryColor,
                disabledContentColor = contentColor,
            ),
            onClick = {
                refresh.invoke()
            }
        ) {
            Text(
                text = stringResource(id = R.string.app_retry),
                color = contentColor
            )

        }
    }
}

@Preview
@Composable
fun PreviewCurrentErrorDisplay() {
    CurrentErrorDisplay(
        refresh = {},
        contentColor = MgWhite,
        secondaryColor = MgWhite
    )
}