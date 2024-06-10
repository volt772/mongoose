package com.apx8.mongoose.presentation.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.ui.theme.MgMenuFontBlack
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoActivity: ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SetStatusBarColor()
            InfoScreen(::infoBackPressed)
        }
    }

    /**
     * 뒤로가기 Listener
     */
    private fun infoBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    /**
     * `오픈라이센스`메뉴 진입
     */
    private fun moveToOpenSource() {
        openActivity(OpenSourceActivity::class.java)
    }

    /**
     * 상단 앱바
     */
    @ExperimentalMaterial3Api
    @Composable
    fun InfoScreen(backPressed: () -> Unit) {
        /* TopBar*/
        BackButtonScaffoldScreen(
            title = "앱정보",
            content = { InfoDisplay() },
            backPressed = backPressed
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
//                .padding(horizontal = 20.dp),
                .padding(20.dp, 100.dp, 20.dp),
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
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(3.dp, MgBlue, CircleShape)
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
                 * @box 오픈라이센스
                 */
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(MgWhite)
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
                    /**
                     * @view 레이블(오픈라이센스)
                     */
                    Text(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        text = "오픈라이센스",
                        fontSize = 16.sp,
                        color = MgMenuFontBlack
                    )
                }

                /**
                 * @view 구분자
                 * @info HorizontalDivider로 사용하면 1.dp시 색상지정을 못함
                 */
                Spacer(modifier = Modifier.height(1.dp))

                /**
                 * @box 앱버전
                 */
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
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
                        text = "앱버전",
                        fontSize = 16.sp,
                        color = MgMenuFontBlack
                    )

                    /**
                     * @view 앱버전(v1.0.0)
                     */
                    Text(
                        modifier = Modifier.padding(end = 18.dp),
                        text = "v${BuildConfig.VERSION_NAME}",
                        fontSize = 16.sp,
                        color = MgMenuFontBlack
                    )
                }
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