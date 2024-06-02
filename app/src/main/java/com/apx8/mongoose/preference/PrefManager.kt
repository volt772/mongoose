package com.apx8.mongoose.preference

import android.content.SharedPreferences

/**
 * PrefManager
 */
interface PrefManager {

    val preferences: SharedPreferences

    fun setMyStadium(code: String)

    fun getMyStadium(): String

}
