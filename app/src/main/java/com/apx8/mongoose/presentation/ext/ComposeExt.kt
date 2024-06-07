package com.apx8.mongoose.presentation.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * 상단 상태바 색상지정
 * @desc Light, Dark 모드 색상구분
 */
@Composable
fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setNavigationBarColor(
            color = if (useDarkIcons) {
                Color.White
            } else {
                Color.Black
            },
            darkIcons = useDarkIcons
        )
        systemUiController.setStatusBarColor(
            color = MgBlue,
            darkIcons = useDarkIcons
        )

        onDispose { }
    }
}

/**
 * Top AppBar With `Back`
 * `뒤로가기`버튼이 있는 AppBar
 * @param title 타이틀
 * @param content 내용
 * @param backPressed 뒤로가기 눌렀을때의 함수
 */
@ExperimentalMaterial3Api
@Composable
fun BackButtonScaffoldScreen(
    title: String,
    content: @Composable () -> Unit,
    backPressed: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults
        .pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        /**
         * @desc 색상 : 기본 및 스크롤시 색상을 앱 기본색(MgBlue)으로 표시한다.
         * @desc 타이틀 : 타이틀
         * @desc 아이콘 : ArrowBack으로 지정한다.
         */
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MgBlue,
                    scrolledContainerColor = MgBlue,
                ),
                title = {
                    Text(
                        text = title,
                        color = MgWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backPressed.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MgWhite
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MgWhite)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            content()
        }
    }
}
