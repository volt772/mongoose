package com.apx8.mongoose.presentation.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.BuildConfig
import com.apx8.mongoose.R
import com.apx8.mongoose.presentation.MongooseApp
import com.apx8.mongoose.presentation.ext.BackButtonScaffoldScreen
import com.apx8.mongoose.presentation.ui.theme.MgBackgroundGray
import com.apx8.mongoose.presentation.ui.theme.MgMenuFontBlack
import com.apx8.mongoose.presentation.ui.theme.MgWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    onBackClick: () -> Unit
) {

    /* TopBar*/
    BackButtonScaffoldScreen(
        title = stringResource(id = R.string.app_info),
        content = { InfoDisplay() },
        backPressed = onBackClick
    )
}

/**
 * 본문 화면
 */
@Composable
fun InfoDisplay(
) {
    /**
     * @box Root
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MgBackgroundGray)
            .padding(20.dp, 100.dp, 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /**
             * @view 앱아이콘
             */
            Image(
                painterResource(id = R.drawable.ic_logo_app_png_title),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
//                        .border(1.dp, MgSubDarkBlue, CircleShape)
            )

            Spacer(modifier = Modifier.height(50.dp))

            /**
             * @box 메뉴박스
             */
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /**
                 * @box 앱버전
                 */
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp, topEnd = 8.dp))
                        .background(MgWhite)
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /**
                     * @view 레이블(앱버전)
                     */
                    Text(
                        modifier = Modifier.padding(start = 18.dp),
                        text = stringResource(id = R.string.app_version),
                        fontSize = 16.sp,
                        color = MgMenuFontBlack
                    )

                    /**
                     * @view 앱버전(v1.0.0)
                     */
                    Text(
                        modifier = Modifier.padding(end = 18.dp),
                        text = stringResource(
                            id = R.string.app_version_template,
                            BuildConfig.VERSION_NAME
                        ),
                        fontSize = 16.sp,
                        color = MgMenuFontBlack
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "본 서비스는 OpenWeather(www.openweathermap.org)의 API를 통해 제공되는 정보를 활용하고 있습니다.",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            )
        }

        if (MongooseApp.isDebugging == "Y") {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    text = stringResource(id = R.string.on_debugging),
                    fontSize = 16.sp,
                    color = MgMenuFontBlack
                )
            }
        }
    }
}

@Composable
private fun LabelWithValue(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview
@Composable
fun PreviewInfoScreen() {
    InfoScreen(
        onBackClick = {}
    )
}
