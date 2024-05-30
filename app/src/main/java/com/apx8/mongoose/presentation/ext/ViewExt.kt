package com.apx8.mongoose.presentation.ext

import android.app.Activity
import android.graphics.Rect
import android.os.SystemClock
import android.view.View
import android.view.WindowInsetsController
import com.apx8.mongoose.R

/**
 * Component, Visibility (single)
 * VISIBLE / GONE
 */
fun View?.visibilityExt(isVisibility: Boolean) {
    this?.visibility = if (isVisibility) View.VISIBLE else View.GONE
}

/**
 * Component, Show
 */
fun List<View>?.showMenuAnimationExt(isVisible: Boolean) {
    val duration = 500L
    this?.forEach {
        val alphaValue = if (isVisible) 1f else 0f

        it.animate().alpha(alphaValue).duration = duration
        it.visibilityExt(isVisible)
    }
}

/**
 * ListVisibility
 * @desc 노출여부 리스트 처리
 */
fun setVisibilityListOneTime(views: VisibleViewDto) {
    views.exposeList.forEach { view ->
        view.visibilityExt(true)
    }

    views.hideList.forEach { view ->
        view.visibilityExt(false)
    }
}

private const val MIN_CLICK_INTERVAL = 500L

/**
 * Single Click
 */
fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    var lastClickTime = 0L

    setOnClickListener {
        val elapsedTime = SystemClock.elapsedRealtime() - lastClickTime

        if (elapsedTime < MIN_CLICK_INTERVAL) return@setOnClickListener

        lastClickTime = SystemClock.elapsedRealtime()

        onSingleClick(this)
    }
}

val View?.boundingRect: Rect
    get() {
        val rect = Rect()
        this?.getGlobalVisibleRect(rect)
        return rect
    }

fun View?.contains(x: Int, y: Int): Boolean {
    val rect = this.boundingRect
    return rect.contains(x, y)
}

data class VisibleViewDto(val exposeList: List<View>, val hideList: List<View>)

/* Status Bar*/
fun Activity?.statusBar(color: Int? = R.color.white) {
    this?.let { _activity ->
        _activity.window.apply {
            insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )

            color?.let { _color ->
                statusBarColor = getColor(_color)
            }
        }
    }
}
