package com.apx8.mongoose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.preference.PrefManager
import com.apx8.mongoose.presentation.ext.SetStatusBarColor
import com.apx8.mongoose.presentation.ext.openActivity
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.ui.theme.MgFontWhite
import com.apx8.mongoose.presentation.ui.theme.MgRed
import com.apx8.mongoose.presentation.ui.theme.MgSubBlue
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import com.apx8.mongoose.presentation.ui.theme.MgYellow
import com.apx8.mongoose.presentation.view.screen.CurrentWeatherScreen
import com.apx8.mongoose.presentation.view.screen.ForecastWeatherScreen
import com.apx8.mongoose.v1.presentation.ui.theme.MongooseTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject
    lateinit var prefManager: PrefManager

    private val vm: MainViewModel by viewModels()
    private var currentStadium: Stadium = Stadium.NAN

    /**
     * `현재 선택된` 경기장 코드
     * @use 경기장 BottomSheet 아이템 선택시
     * @use 앱 초기 진입시
     * @desc `초기버전`에서는 경기장 선택시, `자동으로 내경기장`으로 자동선택됨.
     */
    private fun setCurrentStadium(code: String) {
        vm.apply {
            setCurrentStadium(code)     // 현재 선택된 경기장
            setMyStadium(code)          // 내 경기장
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)

        /* SplashScreen*/
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }

        lifecycleScope.run {
            launch {
                /**
                 * GET : 조회할 경기장 코드
                 * @flow `내 경기장`코드를 조회한 뒤, `현재 경기장`코드로 대입
                 * @use 선택 안된 경우, 무조건 `잠실경기장(SOJ)`로 표시
                 */
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    val myStadium = vm.getMyStadium()
                    setCurrentStadium(myStadium.code)
                }
            }

            launch {
                /**
                 * FETCH : 경기장 데이터 조회
                 * @flow `현재 경기장`코드가 정리된 후, Current, Forecast API 다운로드
                 * @use View에서 사용되는 `currentStadium`값도 여기에서 생성
                 */
                vm.currentStadium.collectLatest { stadium ->
                    currentStadium = stadium
                    vm.fetch(stadium)
                }
            }
        }

        setContent {
            val scrollState = rememberScrollState()

            MongooseTheme {
                SetStatusBarColor()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MgBlue),
                ) {

                    /* AD*/
                    Column(
                        modifier = Modifier.background(MgBlue)
                    ) {
                        BannersAds()
                    }

                    /* Content*/
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = scrollState)
                            .background(MgBlue),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .background(MgBlue)
                        ) {
                            /* Current Weather Screen*/
                            RenderCurrentWeatherScreen()

                            /* Forecast Weather Screen*/
                            RenderForecastWeatherScreen()

                            /* App Info.*/
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
                            Spacer(modifier = Modifier.height(15.dp))
                            RenderAppInfo()
                            Spacer(modifier = Modifier.height(15.dp))

                        }
                    }
                }
            }
        }
    }


    @Composable
    fun RenderCurrentWeatherScreen() {
        /* Column1 : Current Screen*/
        when (val state = vm.currentWeather.collectAsStateWithLifecycle().value) {
            is CommonState.Loading -> {
                LoadingProgressIndicator()
            }

            is CommonState.Error -> {}
            is CommonState.Success -> {
//                LoadingProgressIndicator()
                CurrentWeatherScreen(
                    info = state.data,
                    currentStadium = currentStadium,
                    doSelectStadium = ::setCurrentStadium,
                    modifier = Modifier
                )
            }
        }
    }

    @Composable
    fun RenderForecastWeatherScreen() {
        /* Column2 : Forecast Screen*/
        when (val state = vm.forecastWeather.collectAsStateWithLifecycle().value) {
            is CommonState.Loading -> { }
            is CommonState.Error -> { }
            is CommonState.Success -> {
                ForecastWeatherScreen(
                    info = state.data,
                    modifier = Modifier
                )
            }
        }
    }

    @Composable
    fun RenderAppInfo() {
        /* Column3 : App Info*/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MgBlue)
                .padding(end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            /* 주의*/
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "데이터 제공사의 상황에 따라 일부 부정확할 수 있습니다. 모든 내용은 참고용도로 이용하시기 바랍니다.",
                fontSize = 16.sp,
                color = MgYellow,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                colors = ButtonColors(
                    containerColor = MgSubBlue,
                    contentColor = MgWhite,
                    disabledContainerColor = MgSubBlue,
                    disabledContentColor = MgWhite,
                ),
                onClick = {
                    openActivity(InfoActivity::class.java)
                }
            ) {
                Text(text = "앱정보")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewAppInfo() {
        RenderAppInfo()
    }
}


@Composable
private fun LoadingProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(300.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(30.dp, 30.dp),
            strokeCap = StrokeCap.Round,
            color = MgWhite
        )
    }
}

@Composable
fun BannersAds() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/9214589741"
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}