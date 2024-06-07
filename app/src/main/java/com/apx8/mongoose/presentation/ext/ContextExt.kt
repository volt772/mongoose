package com.apx8.mongoose.presentation.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Activity 이동
 */
fun <T> Context.openActivity(
    cls: Class<T>,
    extras: Bundle.() -> Unit = {},
) {
    val intent = Intent(this, cls)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}