package com.apx8.mongoose.preference

import android.content.SharedPreferences

/**
 * PrefManager
 */
interface PrefManager {

    val preferences: SharedPreferences
    fun setString(key: String, code: String)
    fun getString(key: String, default: String): String
    fun setBoolean(key: String, flag: Boolean)
    fun getBoolean(key: String): Boolean

}
