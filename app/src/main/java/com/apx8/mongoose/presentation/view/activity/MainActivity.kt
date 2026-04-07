package com.apx8.mongoose.presentation.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.apx8.mongoose.R
import com.apx8.mongoose.presentation.ext.SetStatusBarColor
import com.apx8.mongoose.presentation.ext.openActivity
import com.apx8.mongoose.presentation.ui.theme.MongooseTheme
import com.apx8.mongoose.presentation.view.route.MainRoute
import com.apx8.mongoose.presentation.view.vms.MainViewModel
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val vm: MainViewModel by viewModels()

    /* BackPress (DoubleTap)*/
    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast

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
                )
            }
        }
    }
}