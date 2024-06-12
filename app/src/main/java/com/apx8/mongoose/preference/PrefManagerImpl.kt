package com.apx8.mongoose.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.inject.Inject


/**
 * PrefManagerImpl
 */
class PrefManagerImpl @Inject constructor(
    private val context: Context,
) : PrefManager {

    override val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setString(key: String, code: String) {
        preferences.edit().putString(key, code).apply()
    }

    override fun getString(key: String, default: String) =
        preferences.getString(key, default)?: default

    override fun setBoolean(key: String, flag: Boolean) {
        preferences.edit().putBoolean(key, flag).apply()
    }

    override fun getBoolean(key: String) =
        preferences.getBoolean(key, true)
}
