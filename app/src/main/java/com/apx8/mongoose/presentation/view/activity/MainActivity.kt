package com.apx8.mongoose.presentation.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apx8.mongoose.R
import com.apx8.mongoose.presentation.ext.SetStatusBarColor
import com.apx8.mongoose.presentation.ui.theme.MongooseTheme
import com.apx8.mongoose.presentation.view.route.MainRoute
import com.apx8.mongoose.presentation.view.screen.InfoScreen
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
            val navController = rememberNavController()
            val currentBackStackEntry = navController.currentBackStackEntryAsState().value
            val currentRoute = currentBackStackEntry?.destination?.route

            BackHandler() {
                when(currentRoute) {
                    "main" -> {
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
                    "info" -> {
                        navController.popBackStack()
                    }
                }
            }

            MongooseTheme {
                SetStatusBarColor()

                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable(route = "main") {
                        MainRoute(
                            vm = vm,
                            onInfoClick = {
                                navController.navigate("info")
                            }
                        )
                    }

                    composable(
                        route = "info",
                        enterTransition = { slideInHorizontally { it } + fadeIn() },
                        exitTransition = { slideOutHorizontally { it } + fadeOut() },
                        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
                        popExitTransition = { slideOutHorizontally { -it } + fadeOut() }
                    ) {
                        InfoScreen(
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}