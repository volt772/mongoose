package com.apx8.mongoose.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes

fun <T> Context.openActivity(
    cls: Class<T>,
    extras: Bundle.() -> Unit = {},
) {
    val intent = Intent(this, cls)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun Context.showToast(
    message: String,
    isLong: Boolean = false,
) {
    Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Context.showToast(
    @StringRes resourceId: Int,
    isLong: Boolean = false,
) {
    showToast(getString(resourceId), isLong)
}

/**
 * Get String by name
 */
fun Context.getString(name: String?): String {
    return try {
        val id = resources.getIdentifier(name, "string", packageName)
        resources.getString(id)
    } catch (e: Exception) {
        ""
    }
}

val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
