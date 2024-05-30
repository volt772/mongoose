package com.apx8.mongoose.presentation.ext

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.showKeyboard(view: EditText?) {
    inputMethodManager.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}
