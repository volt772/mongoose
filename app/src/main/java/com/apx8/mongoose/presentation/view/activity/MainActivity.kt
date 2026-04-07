package com.apx8.mongoose.presentation.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.apx8.mongoose.R
import com.apx8.mongoose.preference.PrefManager
import com.apx8.mongoose.presentation.ext.SetStatusBarColor
import com.apx8.mongoose.presentation.ext.openActivity
import com.apx8.mongoose.presentation.ui.theme.MongooseTheme
import com.apx8.mongoose.presentation.view.display.MainScreen
import com.apx8.mongoose.presentation.view.route.MainRoute
import com.apx8.mongoose.presentation.view.vms.MainViewModel
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

//    @Inject
//    lateinit var prefManager: PrefManager

    private val vm: MainViewModel by viewModels()

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

                MainRoute(
                    vm = vm,
                    onInfoClick = {
                        openActivity(InfoActivity::class.java)
                    }
//                    onSelectStadium = { code ->
//                        setCurrentStadium(code)
//                    }
                )

//                MainScreen(
//                    vm = vm,
//                    onConfirmAppInfo = {
//                        vm.setIsFirstRun()
//                    },
//                    onInfoClick = {
//                        openActivity(InfoActivity::class.java)
//                    },
//                    onSelectStadium = { code ->
//                        setCurrentStadium(code)
//                    }
//                )
            }
        }
    }
}