package com.apx8.mongoose.data.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date


/**
 * Date Convert
 * @desc Millis to String
 */
@SuppressLint("SimpleDateFormat")
fun Long?.convertUTCtoLocalFormat(): String {
    val formed = this?.let { millis ->
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.format(Date(millis))
    } ?: ""

    return formed
}