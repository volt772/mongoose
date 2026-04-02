package com.apx8.mongoose.presentation.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.apx8.mongoose.R
import com.apx8.mongoose.preference.PrefManager
import com.apx8.mongoose.presentation.ext.SetStatusBarColor
import com.apx8.mongoose.presentation.ext.openActivity
import com.apx8.mongoose.presentation.ui.theme.MongooseTheme
import com.apx8.mongoose.presentation.view.display.MainScreen
import com.apx8.mongoose.presentation.view.vms.MainViewModel
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
    private var isFirstLaunch: Boolean = false

    /* BackPress (DoubleTap)*/
    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast

    /**
     * 현재 선택된 경기장 코드
     * @use 경기장 BottomSheet 아이템 선택 시 (고차함수로 호출)
     * @use 앱 초기 진입시
     * @desc v1.0 에서는 경기장 선택 시, 자동으로 내경기장으로 선택됨.
     */
    private fun setCurrentStadium(code: String) {
        vm.apply {
            setCurrentStadium(code)
            setMyStadium(code)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* 뒤로가기종료(Toast)*/
        backToast = Toast.makeText(this, getString(R.string.app_backpress), Toast.LENGTH_SHORT)

        /* 광고*/
        MobileAds.initialize(this)

        /* SplashScreen*/
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }

        lifecycleScope.run {
            /**
             * 첫 실행 여부 확인
             * @use 첫실행시, 사용자에게 안내문구 다이얼로그를 1회 보여주어야 한다.
             * @use 사용자가 확인누르면 데이터 초기화 및 앱재설치시까지 다이얼로그를 보여주지 않는다.
             */
            launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    vm.isFirstRun.collectLatest { isFirst ->
                        isFirstLaunch = isFirst
                    }
                }
            }

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
                    vm.fetch(stadium)
                }
            }
        }

        setContent {
            BackHandler() {
                val currentTime = System.currentTimeMillis()
                if (currentTime - backPressedTime <= 2000) {
                    backToast.cancel()
                    finish()
                }
                else {
                    backPressedTime = currentTime
                    backToast.show()
                }
            }

            MongooseTheme {
                SetStatusBarColor()

                MainScreen(
                    vm = vm,
                    isFirstLaunch = isFirstLaunch,
                    onConfirmAppInfo = {
                        vm.setIsFirstRun()
                    },
                    onInfoClick = {
                        openActivity(InfoActivity::class.java)
                    },
                    onSelectStadium = { code ->
                        setCurrentStadium(code)
                    }
                )
            }
        }
    }
}