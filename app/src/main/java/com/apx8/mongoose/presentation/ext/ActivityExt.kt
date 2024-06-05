package com.apx8.mongoose.presentation.ext

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//@Composable
//fun SetStatusBarColor() {
//    val systemUiController = rememberSystemUiController()
//    val useDarkIcons = !isSystemInDarkTheme()
//
//    DisposableEffect(systemUiController, useDarkIcons) {
//        systemUiController.setNavigationBarColor(
//            color = if (useDarkIcons) {
//                Color.White
//            } else {
//                Color.Black
//            },
//            darkIcons = useDarkIcons
//        )
//        systemUiController.setStatusBarColor(
//            color = MgBlue,
//            darkIcons = useDarkIcons
//        )
//
//        onDispose { }
//    }
//}

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.showKeyboard(view: EditText?) {
    inputMethodManager.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}
