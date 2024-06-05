package com.apx8.mongoose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apx8.mongoose.BuildConfig
import com.apx8.mongoose.R
import com.apx8.mongoose.presentation.ext.BackButtonScaffoldScreen
import com.apx8.mongoose.presentation.ext.SetStatusBarColor
import com.apx8.mongoose.presentation.ext.openActivity
import com.apx8.mongoose.presentation.ui.theme.MgBackgroundGray
import com.apx8.mongoose.presentation.ui.theme.MgGreen
import com.apx8.mongoose.presentation.ui.theme.MgLinetGray
import com.apx8.mongoose.presentation.ui.theme.MgMenuFontBlack
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoActivity: ComponentActivity() {

    private val vm: InfoViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SetStatusBarColor()
            InfoScreen(::infoBackPressed)
        }
    }

    private fun infoBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun moveToOpenSource() {
        openActivity(OpenSourceActivity::class.java)
    }

    @ExperimentalMaterial3Api
    @Composable
    fun InfoScreen(backPressed: () -> Unit) {
        BackButtonScaffoldScreen(
            title = "앱정보",
            content = { InfoDisplay() },
            backPressed = backPressed
        )
    }

    @Composable
    fun InfoDisplay(
    ) {
        Column(
            modifier = Modifier
                .background(MgWhite)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.ic_logo_app_png_title),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            /* 오픈라이센스*/
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(MgBackgroundGray)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                    ) {
                        moveToOpenSource()
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    text = "오픈라이센스",
                    fontSize = 16.sp,
                    color = MgMenuFontBlack
                )
            }

            /* 구분자*/
            HorizontalDivider(Modifier.height(1.dp).background(MgLinetGray))

            /* 앱버전*/
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(MgBackgroundGray)
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                /* 앱버전(레이블)*/
                Text(
                    modifier = Modifier.padding(start = 18.dp),
                    text = "앱버전",
                    fontSize = 16.sp,
                    color = MgMenuFontBlack
                )

                /* 앱버전*/
                Text(
                    modifier = Modifier.padding(end = 18.dp),
                    text = "v${BuildConfig.VERSION_NAME}",
                    fontSize = 16.sp,
                    color = MgMenuFontBlack
                )
            }
        }
    }

    @Preview
    @Composable
    fun PreviewInfoScreen() {
        InfoDisplay(
        )
    }
}